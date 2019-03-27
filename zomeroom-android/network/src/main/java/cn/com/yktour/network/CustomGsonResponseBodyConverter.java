package cn.com.yktour.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import cn.com.yktour.network.bean.HttpResult;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by sky on 2017/11/19.
 * description:OkHttp 响应体Gson封装类
 * changed:创建
 */
final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpResult httpResult;
        try {
            //解析为通用的响应结果
            httpResult = gson.fromJson(response, HttpResult.class);
        } catch (Exception e) {
            throw new ApiException("服务器访问异常，请稍后重试");
        }

        //这里可以统一处理访问失败的异常
        if (httpResult.flag != 20000) {
            value.close();
            ApiException exception = new ApiException(httpResult.getMsg());
            exception.code = httpResult.flag;
            throw exception;//在Rxjava里面所有的报错全部会走到ONERROR中
        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
