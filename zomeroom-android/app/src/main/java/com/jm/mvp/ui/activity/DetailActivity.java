package com.jm.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.JmModelDataListBean;
import com.jm.bean.JmRoomOrigin;
import com.jm.bean.JmRoomType;
import com.jm.helper.OkHttp3Utils;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.DetailsContract;
import com.jm.mvp.presenter.DetailsPresenter;
import com.jm.mvp.ui.adapter.DetailListAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.DateChangesUtils;
import com.jm.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Url;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

public class DetailActivity extends BaseActivity<DetailsPresenter> implements DetailsContract.View {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_detail_area)
    EditText mEtDetailArea;
    @BindView(R.id.tv_title_second)
    TextView mTvTitleSecond;
    @BindView(R.id.tv_video)
    TextView mTvideo;
    @BindView(R.id.tv_detail_type)
    TextView mTvDdetailType;
    @BindView(R.id.tv_detail_time)
    TextView mTvDetailTime;
    @BindView(R.id.video_player)
    JCVideoPlayerStandard mVideoPlayer;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private DetailsPresenter mPresenter;
    private boolean isVoide = false;
    private List<JmModelDataListBean.DataBean.ArticleVOListBean> articleVOList1 = new ArrayList<>();
    private DetailListAdapter mDetailListAdapter;
    JmModelDataListBean.DataBean mJmModelDataListBean = new JmModelDataListBean.DataBean();
    private String url = "";
    private Dialog loadingDialog;
    private String absolutePath;
    private File outputImage;
    private Uri imageUri;
    private String item = "";
    private String dateTime;

    @Override
    public int initView(Bundle savedInstanceState) {
        mPresenter = new DetailsPresenter(this);
        /**
         * android 7.0系统解决拍照的问题
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        return R.layout.activity_detail;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        initShowData();
        mTvTitle.setText("样板间明细");
        mTvTitleSecond.setVisibility(View.VISIBLE);
        mTvTitleSecond.setText("完成");
        mDetailListAdapter = new DetailListAdapter(this, articleVOList1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDetailListAdapter);
        //item点击事件
        mDetailListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_list_image:
                        if (null != articleVOList1.get(position).getUrl()) {
                            Intent intent = new Intent(DetailActivity.this, ImageTouchActivity.class);
                            intent.putExtra("image", articleVOList1.get(position).getUrl());
                            startActivity(intent);
                        } else {
                            takeSave(Constants.DETAIL_IMAGE);
                            item = position + "";
                        }
                        break;
                    case R.id.tv_list_month:
                        if (null != articleVOList1.get(position).getLastUrl()) {
                            Intent intent = new Intent(DetailActivity.this, ImageTouchActivity.class);
                            intent.putExtra("image", articleVOList1.get(position).getLastUrl());
                            startActivity(intent);
                        } else {
                            takeSave(Constants.DETAIL_MOTH);
                            item = position + "";
                        }
                        break;
                }
            }
        });
    }

    @Override
    public DetailsPresenter obtainPresenter() {
        return null;
    }

    @OnClick({R.id.iv_title_back, R.id.tv_title_second, R.id.tv_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_title_second:
                conmit();
                break;
            case R.id.tv_video:
                if (isVoide) {
                    mVideoPlayer.setVisibility(View.VISIBLE);
                } else {
                    mTvideo.setVisibility(View.VISIBLE);
                    startActivityForResult(new Intent(this, CrameActivity.class), Constants.NO_CRAME);
                }
                break;
        }
    }

    /**
     * 保存
     */
    private void conmit() {
        String roomArea = mEtDetailArea.getText().toString();
        if (roomArea.equals("")) {
            ToastUtils.ToastCenter("请填写房间号");
            return;
        }

        mJmModelDataListBean.setRoomNum(roomArea);
        if (null==url||url.equals("")) {
            ToastUtils.ToastCenter("请上传视频");
            return;
        }
        submitUpDate(Constants.HOST + Constants.upLoadDetail, mJmModelDataListBean);
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showData(JmModelDataListBean data) {
    }

    @Override
    public void refresh() {
        ToastUtils.ToastCenter("更新成功");
        finish();
    }


    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }

    /**
     * 录制小视频成功后的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.DETAIL_IMAGE && resultCode == RESULT_OK) {
            zipImage(Constants.DETAIL_IMAGE, item);
        }
        if (requestCode == Constants.DETAIL_MOTH && resultCode == RESULT_OK) {
            zipImage(Constants.DETAIL_MOTH, item);
        }
        if (data != null) {
            String url1 = data.getStringExtra("URL");
            if (null != url1) {
                url = url1.substring(1, url1.length() - 1);
                mTvideo.setVisibility(View.GONE);
                mTvDetailTime.setText("上传时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                mJmModelDataListBean.setUploadTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
                mJmModelDataListBean.setUrl(url);
                mVideoPlayer.setVisibility(View.VISIBLE);
                //加载视频
                mVideoPlayer.TOOL_BAR_EXIST = false;
                mVideoPlayer.setUp(Constants.HOST + url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "ZOME");
                Glide.with(getApplicationContext()).load(R.mipmap.video).into(mVideoPlayer.thumbImageView);
            }
        }
    }

    /**
     * 加载数据/装数据
     */
    private void initShowData() {
        Intent intent = getIntent();
        final String intExtra = intent.getStringExtra(Constants.DETALI_ID);
        final String time = intent.getStringExtra(Constants.DETALI_TIME);
        if (null != intExtra) {
            mJmModelDataListBean.setId(Integer.parseInt(intExtra));
            loadingDialog = LoadingDialogUtils.createLoadingDialog(DetailActivity.this, "加载中...");
            loadingDialog.show();
            if (time.equals("")) {
                dateTime = DateChangesUtils.getNowDate();
            } else {
                dateTime = time;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Call call = OkHttp3Utils.getRequestDetailData(Constants.HOST + Constants.getDetailList, "id", intExtra, "time", dateTime);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            /**
                             * 判断是否有数据
                             */
                            String string = response.body().string();
                            try {
                                JSONObject jsonObject=new JSONObject(string);
                                int data = (int) jsonObject.get("flag");
                                if (data==50000){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.ToastCenterLong("后台数据有误，请联系管理员");
                                            mTvTitleSecond.setEnabled(false);
                                            LoadingDialogUtils.closeDialog(loadingDialog);
                                        }
                                    });
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /**
                             * 进行装数据
                             */
                            final JmModelDataListBean bean = new Gson().fromJson(string, JmModelDataListBean.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String status = bean.getData().getStatus();
                                    if (status.equals("2")){
                                        mTvTitleSecond.setVisibility(View.GONE);
                                    }
                                    mJmModelDataListBean.setVId(bean.getData().getVId());
                                    /**
                                     *上传时间
                                     */
                                    if (null != bean.getData().getUploadTime()) {
                                        mTvDetailTime.setText("上传时间：" + bean.getData().getUploadTime());
                                    } else {
                                        mTvDetailTime.setText("上传时间：");
                                    }
                                    /**
                                     *房间
                                     */
                                    if (null != bean.getData().getRoomNum()) {
                                        mEtDetailArea.setText(bean.getData().getRoomNum());
                                    }
                                    /**
                                     *类别
                                     */
                                    if (null != bean.getData().getCName()) {
                                        mTvDdetailType.setText("类别：" + bean.getData().getCName());
                                    } else {
                                        mTvDdetailType.setText("类别：");
                                    }
                                    /**
                                     *视频
                                     */
                                    url = bean.getData().getUrl();
                                    if (null != url && !url.equals("")) {
                                        isVoide = true;
                                        mVideoPlayer.TOOL_BAR_EXIST = false;
                                        mVideoPlayer.setVisibility(View.VISIBLE);
                                        mTvideo.setVisibility(View.GONE);
                                        mJmModelDataListBean.setUrl(url);
                                        mVideoPlayer.setUp(Constants.HOST + url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "ZOME");
                                        Glide.with(getApplicationContext()).load(R.mipmap.video).into(mVideoPlayer.thumbImageView);
                                    } else {
                                        mTvideo.setVisibility(View.VISIBLE);
                                        mVideoPlayer.setVisibility(View.GONE);
                                    }
                                    /**
                                     *集合
                                     */
                                    List<JmModelDataListBean.DataBean.ArticleVOListBean> articleVOList = bean.getData().getArticleVOList();
                                    articleVOList1.clear();
                                    if (articleVOList.size() > 0) {
                                        articleVOList1.addAll(articleVOList);
                                        for (int i = 0; i < articleVOList.size(); i++) {
                                            articleVOList1.get(i).setMUrlId(articleVOList.get(i).getMUrlId());
                                        }
                                    }
                                    mDetailListAdapter.notifyDataSetChanged();
                                    LoadingDialogUtils.closeDialog(loadingDialog);
                                }
                            });
                        }
                    });
                }
            }).start();
        }
    }

    private void takeSave(int type) {
        //创建File对象，用于存储拍照后的图片
        absolutePath = getExternalCacheDir().getAbsolutePath() + System.currentTimeMillis() + ".jpg";
        outputImage = new File(absolutePath);
        imageUri = Uri.fromFile(outputImage);
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, type);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩图片
     *
     * @param type
     */
    private void zipImage(final int type, final String item) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(this, "上传中...");
        loadingDialog.show();
        final String absolutePath = getExternalCacheDir().getAbsolutePath();
        Luban.with(DetailActivity.this)
                .load(imageUri)
                .ignoreBy(100)
                .setTargetDir(absolutePath)
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        return System.currentTimeMillis() + ".jpg";
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File files) {
                        //删除旧的图片
                        deleteOriImg(outputImage);
                        final String picPaths = files.getAbsolutePath();
                        final File file1 = new File(picPaths);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                upLoadPhoto(Constants.HOST + Constants.upLoadPhotos, picPaths, file1, type, item);
                            }
                        }).start();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    /**
     * 删除旧的图片
     */
    private void deleteOriImg(File imageFile) {
        if (imageFile.exists()) {
            imageFile.delete();
        }
    }

    /**
     * 上传图片
     *
     * @param url
     * @param name
     * @param file
     * @param type
     */
    private void upLoadPhoto(String url, String name, File file, final int type, final String item) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        RequestBody builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", name, RequestBody.create(MediaType.parse("jpg/png"), file)).build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", builder)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String substring = response.body().string();
                String photosServertUrl = substring.substring(1, substring.length() - 1);
                if (type == Constants.DETAIL_IMAGE) {
                    if (!item.equals("")) {//图片
                        articleVOList1.get(Integer.parseInt(item)).setUrl(photosServertUrl);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDetailListAdapter.notifyDataSetChanged();
                                LoadingDialogUtils.closeDialog(loadingDialog);
                            }
                        });
                    }
                } else {
                    if (!item.equals("")) {//月末盘点
                        articleVOList1.get(Integer.parseInt(item)).setLastUrl(photosServertUrl);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDetailListAdapter.notifyDataSetChanged();
                                LoadingDialogUtils.closeDialog(loadingDialog);

                            }
                        });
                    }
                }
                mJmModelDataListBean.setArticleVOList(articleVOList1);
            }
        });
    }

    /**
     * 更新
     *
     * @param url
     */
    private void submitUpDate(String url, final JmModelDataListBean.DataBean mJmModelDataListBean) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(DetailActivity.this, "保存中...");
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(mJmModelDataListBean));
        final Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.ToastCenter("保存成功");
                        finish();
                        LoadingDialogUtils.closeDialog(loadingDialog);
                    }
                });
            }
        });
    }
}
