package com.jm.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.idst.nls.internal.protocol.Content;
import com.google.gson.Gson;
import com.jm.base.Constants;
import com.jm.bean.JmRoomOrigin;
import com.jm.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.com.yktour.network.HttpLoggingInterceptor;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sky on 2017/11/19.
 * description:封装一个OkHttp3的获取类
 * changed:创建
 */
public class OkHttp3Utils {

    private static OkHttpClient.Builder mOkHttpClient;
    public static boolean isUseCatch;
    private static Call call;
    //设置缓存目录
    // private static File cacheDirectory = new File(MyApplication.getInstance().getApplicationContext().getCacheDir(), "response");
    private static File cacheDirectory = new File(
            SampleApplicationContext.application.getApplicationContext().getExternalCacheDir(),
            "response");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
    private static Response execute;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {
            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                    //网络拦截器
                    .addInterceptor(baseInterceptor)
                    .addNetworkInterceptor(rewriteCacheControlInterceptor)
                    //设置请求读写的超时时间
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
        }
        //if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor("NET");
        loggingInterceptor.setPrintLevel(
                HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        mOkHttpClient.addInterceptor(loggingInterceptor);
        //}
        return mOkHttpClient.build();
    }

    /**
     * 获取缓存
     */
    private static Interceptor baseInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            // start----------------以下代码为添加一些公共参数使用--------------------------
            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());

            // 构建新的请求
            RequestBody body = oldRequest.body();
            Request newRequest;
            String XI = (String) SPUtils.get("X-I", "");
            String XS = (String) SPUtils.get("X-S", "");
            String C = "android/android"
                    + Constants.MOBILE_SYS_VERSION
                    + "/"
                    + Constants.APP_VERSION_NAME;
            Request.Builder builder = oldRequest.newBuilder()
                    .method(oldRequest.method(), body)
                    .url(authorizedUrlBuilder.build())
                    .addHeader("C", C);
            if (!TextUtils.isEmpty(XI)) {
                builder.addHeader("X-I", XI);
            }
            if (!TextUtils.isEmpty(XS)) {
                builder.addHeader("X-S", XS);
            }
            newRequest = builder.build();
            //缓存控制
            if (isUseCatch && !isNetworkReachable(SampleApplicationContext.context)) {
                /**
                 * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
                 */
                CacheControl tempCacheControl = CacheControl.FORCE_NETWORK;//不允许缓存
                if (Constants.Cache) {//使用缓存
                    int maxStale = 60 * 60 * 24 * 3; // 离线时缓存保存1周,单位:秒
                    tempCacheControl = new CacheControl.Builder().onlyIfCached()
                            .maxStale(maxStale, TimeUnit.SECONDS)
                            .build();
                }

                newRequest = newRequest.newBuilder().cacheControl(tempCacheControl)//使用缓存
                        .build();
                Log.e("retrofit", "无网络===========>读取缓存");
                Response response = chain.proceed(newRequest);
                return response;
            }

            Response response = chain.proceed(newRequest);
            return response;
        }
    };

    private static Interceptor rewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (isUseCatch) {
                if (isNetworkReachable(SampleApplicationContext.context)) {
                    int maxAge = 1 * 60; // 有网络时 设置缓存超时时间1分钟
                    response = response.newBuilder()
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 7; // 无网络时，设置超时为1周
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
            return response;
        }
    };

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    /**
     * 上传json
     * @param jmRoomOrigin
     * @param url
     * @return
     */
    public static Call addTalkDatas(JmRoomOrigin jmRoomOrigin, String url) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(jmRoomOrigin));
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        Call call = okHttpClient.newCall(request);
        return call;
    }


    /**
     * 通过id同步数据
     * @param
     * @param url
     * @return
     */
    public static Call getIdTalkData(final String url, final String type, final String id) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(url + "?"+type+"="+ id)
                .build();
        call = okHttpClient.newCall(request);
     return call;
    }
 /**
     * 通过id同步数据
     * @param
     * @param url
     * @return
     */
    public static Call getRequestDetailData(final String url, final String type, final String id,final String type1, final String id1) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(url + "?"+type+"="+ id+"&"+type1+"="+id1)
                .build();
        call = okHttpClient.newCall(request);
     return call;
    }
    /**
     * 通过id同步数据
     * @param
     * @param url
     * @return
     */
    public static Call getRequestDetailsData(final String url, final String type, final String id,final String type1, final String id1,final String type2, final String id2) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        Request request = new Request.Builder()
                .url(url + "?"+type+"="+ id+"&"+type1+"="+id1+"&"+type2+"="+id2)
                .build();
        call = okHttpClient.newCall(request);
     return call;
    }

    //网络请求图片 并转化成bitmap对象
    public static void setIamge(String url, final ImageView imageView,  Context context) {
        OkHttpUtils.get().url(url).tag(context)
                .build()
                .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * 上传图片
     *
     */
    public static Call upLoadFile(String url,String name,File file,String uploadType) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        MediaType parse = MediaType.parse(uploadType);//"jpg/png"\\\\mp4
        //创建Request
        RequestBody builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", name, RequestBody.create(parse, file)).build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", builder)
                .build();
        Call call = okHttpClient.newCall(request);
        return call;
    }
//    /**
//     * 上传图片
//     *
//     */
//    public static void upLoadMovie(String url, File file,String name) {
//        OkHttpClient mOkhttpClient = new OkHttpClient();
//        //补全请求地址MEDIA_TYPE_OBJECT
//        String requestUrl = String.format("%s/%s", u, actionUrl);
//        //创建RequestBody
//        final MediaType MEDIA_TYPE_MP4 = MediaType.parse("mp4");
//        RequestBody body = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart( "file", filePath,RequestBody.create(MEDIA_TYPE_MP4, file))
//                .build();
//        //创建Request
//        final Request request = new Request.Builder().url(requestUrl).method("POST",body).build();
//        final Call call = mOkhttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
//    }
}
