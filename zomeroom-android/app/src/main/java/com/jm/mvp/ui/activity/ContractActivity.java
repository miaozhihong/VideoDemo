package com.jm.mvp.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.idst.nls.internal.protocol.Content;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.helper.OkHttp3Utils;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.adapter.ContractAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.yktour.network.base.IPresenter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * 合同页面（开发系统）
 */
public class ContractActivity extends BaseActivity {
    @BindView(R.id.iv_title_back_choose)
    ImageView mIvTitleBackChoose;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_title_second_choose)
    TextView mTvTitleSecondChoose;
    @BindView(R.id.tv_up_contract)
    TextView mTvUpContract;
    @BindView(R.id.rv_talk_contract)
    RecyclerView mRvTalkContract;
    private ContractAdapter mContractAdapter;
    private String absolutePath;
    private File outputImage;
    ArrayList<String> mList=new ArrayList<>();
    ArrayList<String> mLists=new ArrayList<>();
    private Dialog loadingDialog;
    String str = "";


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_contract;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTitle.setText("我的合同");
        mTvTitleSecondChoose.setVisibility(View.VISIBLE);
        mTvTitleSecondChoose.setText("保存");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String photoUrl=bundle.getString("contract");
        if (!photoUrl.equals("")){//更新
            String[] split = photoUrl.split(",");
            List<String> list = Arrays.asList(split);
            mLists.clear();
            mLists.addAll(list);
            mList.clear();
            mList.addAll(list);
            mContractAdapter = new ContractAdapter(ContractActivity.this,mLists,1);
            mRvTalkContract.setLayoutManager(new GridLayoutManager(ContractActivity.this, 3));
            mRvTalkContract.setAdapter(mContractAdapter);
            initClick();
        }
    }



    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @OnClick({R.id.iv_title_back_choose,R.id.tv_title_second_choose,R.id.tv_up_contract})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back_choose:
               finish();
                break;

            case R.id.tv_title_second_choose:
                if(mLists.size()>0){
                    for (int i = 0; i <mLists.size() ; i++) {
                        str +=  mLists.get(i) + ",";
                    }
                    Intent intent = this.getIntent();
                    Bundle bundle = intent.getExtras();
                    bundle.putString(Constants.CONTRACT2, str);//添加要返回给页面1的数据
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, intent);//返回页面1
                    finish();
                }else {
                    ToastUtils.ToastCenter("请进行上传合同");
                }
                break;

                case R.id.tv_up_contract:
                takePickSave(Constants.ALBUM);
                break;
        }
    }

    /**
     * 从相册选择
     * @param type
     */
    private void takePickSave(int type) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, type);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从相册选取
        if (requestCode == Constants.ALBUM && resultCode == RESULT_OK) {
            pickImage(data.getData());
        }
    }

    /**
     * 压缩图片
     */
    private void zipImage() {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(this, "上传中...");
        loadingDialog.show();
        final String absolutePath = getExternalCacheDir().getAbsolutePath();
        Luban.with(ContractActivity.this)
                .load(outputImage)
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
                    public void onSuccess(final File files) {
                        //删除旧的图片
                        deleteOriImg(outputImage);
                        final String picPaths = files.getAbsolutePath();
                        final File file1 = new File(picPaths);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                upLoadPhoto(Constants.HOST + Constants.upLoadPhotos, picPaths, file1);
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
     * 从相册选取照片
     * @param imageUris
     */
    private void pickImage(Uri imageUris) {
        String[] arr = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(imageUris, arr, null, null, null);
        int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        //创建File对象，用于存储拍照后的图片
        absolutePath = getExternalCacheDir().getAbsolutePath() + System.currentTimeMillis() + ".jpg";
        copyFile(cursor.getString(img_index),absolutePath);
        outputImage= new File(absolutePath);
        zipImage();
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }
    /**
     * 上传图片
     *
     * @param url
     * @param name
     * @param file
     */
    private void upLoadPhoto(String url, String name, File file) {
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
                String photosServertUrl = response.body().string();
                mLists.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mContractAdapter = new ContractAdapter(ContractActivity.this,mLists,1);
                        mRvTalkContract.setLayoutManager(new GridLayoutManager(ContractActivity.this, 3));
                        mRvTalkContract.setAdapter(mContractAdapter);
                        initClick();
                        LoadingDialogUtils.closeDialog(loadingDialog);
                    }
                });
            }
        });
    }
    /**
     * 点击事件
     */
    private void initClick() {
        if(mLists.size()>0){
            /**
             * 短按
             */
            mContractAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mList.size()>0){
                        Intent intent=new Intent(ContractActivity.this,ContractDetailActivity.class);
                        intent.putStringArrayListExtra(Constants.ARRAY,mList);
                        startActivity(intent);
                    }
                }
            });
            /**
             * 长按
             */
            mContractAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ContractActivity.this).setTitle("系统提示")
                            .setMessage("是否删除此张合同")
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToastUtils.ToastCenter("已取消删除");
                                }
                            }).setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mLists.size()>0){
                                                mLists.remove(position);
                                                mContractAdapter.notifyDataSetChanged();
                                            }
                                            mContractAdapter = new ContractAdapter(ContractActivity.this,mLists,1);
                                            mRvTalkContract.setLayoutManager(new GridLayoutManager(ContractActivity.this, 3));
                                            mRvTalkContract.setAdapter(mContractAdapter);
                                            initClick();
                                        }
                                    });
                                }
                            });
                    alertDialog.setIcon(R.mipmap.logoyimei).create().show();
                    return true;
                }
            });
        }
    }
}
