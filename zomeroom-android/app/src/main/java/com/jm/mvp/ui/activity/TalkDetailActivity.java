package com.jm.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.AreaBean;
import com.jm.bean.GetIdJmBusubess;
import com.jm.bean.JmBusinessManager;
import com.jm.bean.JmRoomOrigin;
import com.jm.bean.JmRoomRemark;
import com.jm.bean.JmRoomType;
import com.jm.bean.PeopleBean;
import com.jm.bean.TalkTypeBean;
import com.jm.helper.HttpUtils;
import com.jm.helper.OkHttp3Utils;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.base.LngLat;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.jm.utils.CommonUtils;
import com.jm.utils.CoodinateCovertor;
import com.jm.utils.PickerUtils;
import com.jm.utils.PopUtils;
import com.jm.utils.SelectPicPopupWindowUitls;
import com.jm.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.yktour.network.base.IPresenter;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;
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
 * 店面详情页面
 */
public class TalkDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivPropertyPeopleSelect, talk_take_photos_three, talk_take_photos_two, talk_take_photos_one, iv_title_back, iv_title_second;
    private TextView tv_development_select, tv_title, tv_sub_select, mPropertyPeopleSecond, tv_administrative_division_select, tv_intention_floor_select, tv_intention_select, tv_talk_detail_confirm, tv_tale_add_remark, tv_talk_time_select, tv_area_selec, tv_cooperation_mode_select, tv_floor_select;
    private EditText ed_room_select, ed_shop_select, ed_intention_room_select, mTvTelDetailVisitSencond, mPropertyPeopleFirst, mTvTelDetailVisitFirst, mTvTelDetailVisit, mPropertyPeople, ed_sub_distance_select, ed_talk_shining, ed_talk_midnight, ed_talk_price, ed_area_select;
    private DateTimePicker dateTimePicker;
    private ImageView iv_outside_one, iv_outside_three, iv_pblic_one, iv_outside_two, iv_pblic_two, iv_pblic_three, ivImageOne, ivImageTwo, ivImageThree, ivImageFour, ivImageFive, ivImageSex;
    private OptionPicker mIntentionPicker, mAreaPicker, mModePicker, mShowDevelopmentPicker, mFloorPicker, mIntentionFloorPicker;
    private EditText ed_room_one, ed_room_two, ed_room_three, ed_room_four, ed_room_five, ed_room_sex;
    private ImageView ivRoomOne1, ivRoomOne2, ivRoomOne3, ivRoomTwo1, ivRoomTwo2, ivRoomTwo3, ivRoomThree1, ivRoomThree2, ivRoomThree3, ivRoomSex3, ivRoomFour1, ivRoomFour2, ivRoomFour3, ivRoomFive1, ivRoomFive2, ivRoomFive3, ivRoomSex1, ivRoomSex2;
    private Dialog loadingDialog;
    private boolean isUpData = false;
    private MyPopupWindow mRemarkPop;
    private LinearLayout mPeopleFirst,hezuo, hezuo1, mPeopleSecond, map, ll_tel_talk_remark, typeOne, typeTwo, typeThree, typeFour, typeFive;
    private TextView mTvTalkLongitude, mTvTalkLatitude;
    private String photosServertUrl, getTypes, absolutePath,areaName,cooperationModeString;
    private SelectPicPopupWindowUitls selectPicPopupWindowUitls;
    private String[] mAreas;
    private String[] mPeoples;
    public static String DEFAULT = "请选择";
    private int mPeopleFirstTag = 0;
    private int mPeopleSecondTag = 0;
    private int dataId,intent,intExtra,mode;
    boolean isCheck = false;
    private Uri imageUri;
    private GetIdJmBusubess.DataBean data;
    private File outputImage;
    private String context = "";
    private String title = "";
    private String backString="";
    private boolean isAdd = false;
    private boolean isDelet = false;
    private double doublelatitude = 0.0;
    private double doubleloggitude = 0.0;
    ArrayList<AreaBean.DataBean> mArea = new ArrayList<>();
    ArrayList<String> imageUrlOutSide = new ArrayList<>();
    ArrayList<String> imageUrlPblic = new ArrayList<>();
    ArrayList<String> imageUrlRoom = new ArrayList<>();
    ArrayList<String> imageUrlRoom1 = new ArrayList<>();
    ArrayList<String> imageUrlRoom2 = new ArrayList<>();
    ArrayList<String> imageUrlRoom3 = new ArrayList<>();
    ArrayList<String> imageUrlRoom4 = new ArrayList<>();
    ArrayList<String> imageUrlRoom5 = new ArrayList<>();
    ArrayList<PeopleBean.DataBean> mPeople = new ArrayList<>();
    ArrayList<JmBusinessManager> managers = new ArrayList<>();
    ArrayList<JmRoomRemark> mJmRoomRemark = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager1 = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager2 = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager3 = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager4 = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager5 = new ArrayList<>();
    ArrayList<JmRoomType> mJmRoomManager6 = new ArrayList<>();
    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
    SpannableStringBuilder mBuilder = new SpannableStringBuilder();
    ForegroundColorSpan mSpan;
    private LinearLayout linTalkDetailContract;
    private TextView tvTalkDetailContract;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mArea.clear();
                    ArrayList<AreaBean.DataBean> obj = (ArrayList<AreaBean.DataBean>) msg.obj;
                    mArea.addAll(obj);
                    mAreas = CommonUtils.toStringArray(mArea, Constants.AREA, Constants.TEL);
                    break;
                case 2:
                    mPeople.clear();
                    ArrayList<PeopleBean.DataBean> bj1 = (ArrayList<PeopleBean.DataBean>) msg.obj;
                    mPeople.addAll(bj1);
                    mPeoples = CommonUtils.toStringArray(mPeople, Constants.PEOPLE, Constants.TEL);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * android 7.0系统解决拍照的问题
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        setContentView(R.layout.activity_talk_detail);
        initFind();
    }


    @SuppressLint("ResourceType")
    @Override
    public int initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final String id = intent.getStringExtra(Constants.DATA_ID);
        String type = "id";
        if (null != id) {
            loadingDialog = LoadingDialogUtils.createLoadingDialog(TalkDetailActivity.this, "加载中...");
            loadingDialog.show();
            initShowData(Constants.HOST + Constants.details, type, id + "");
        }
        //初始化拍照pop
        selectPicPopupWindowUitls = new SelectPicPopupWindowUitls(this);
        return 0;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Response dataSynFromNet = HttpUtils.getDataSynFromNet(Constants.HOST + Constants.AREA_TALK);
                try {
                    AreaBean areaBean = new Gson().fromJson(dataSynFromNet.body().string(), AreaBean.class);
                    if (areaBean.getData().size() > 0) {
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = areaBean.getData();
                        handler.sendMessage(message);
                    }
                    //加载开发负责人
                    Response mPeopleBody = HttpUtils.getDataSynFromNet(Constants.HOST + Constants.PEOPLE_TALK);
                    PeopleBean peopleBean = new Gson().fromJson(mPeopleBody.body().string(), PeopleBean.class);
                    if (peopleBean.getData().size() > 0) {
                        Message messages = Message.obtain();
                        messages.what = 2;
                        messages.obj = peopleBean.getData();
                        handler.sendMessage(messages);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        //咨询回访时间
        dateTimePicker = PickerUtils.initDatePicker(this);
        dateTimePicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tv_talk_time_select.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                jmRoomOrigin.setReturnTimeStr(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });

        //顾客意向
        mIntentionPicker = PickerUtils.initSinglePicker(this, Constants.INTENTION_TALK, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                isCheck = true;
                tv_intention_select.setText(item);
                if (item.equals("跟进")) {
                    intent = 0;
                    jmRoomOrigin.setCooperationIntention(0);
                    jmRoomOrigin.setSigningTimeStr(null);
                    linTalkDetailContract.setVisibility(View.GONE);
                } else if (item.equals("pass")) {
                    intent = 1;
                    jmRoomOrigin.setCooperationIntention(1);
                    jmRoomOrigin.setSigningTimeStr(null);
                    linTalkDetailContract.setVisibility(View.GONE);
                } else if (item.equals("签约")) {
                    intent = 2;
                    jmRoomOrigin.setCooperationIntention(2);
                    jmRoomOrigin.setSigningTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())));
                    linTalkDetailContract.setVisibility(View.VISIBLE);
                } else {
                }
            }
        });

        //合作模式
        mModePicker = PickerUtils.initSinglePicker(this, Constants.INTENTION_MODE, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                tv_cooperation_mode_select.setText(item);
                if (item.equals("直营")) {
                    jmRoomOrigin.setCooperationMode(0);
                } else if (item.equals("平台管理")) {
                    jmRoomOrigin.setCooperationMode(1);
                } else {
                }

                if (tv_cooperation_mode_select.getText().toString().equals("平台管理")) {
                    hezuo.setVisibility(View.INVISIBLE);
                    hezuo1.setVisibility(View.INVISIBLE);
                } else {
                    hezuo.setVisibility(View.VISIBLE);
                    hezuo1.setVisibility(View.VISIBLE);
                }
            }
        });


        //总楼层
        mFloorPicker = PickerUtils.initSinglePicker(this, Constants.FLOOR, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                tv_floor_select.setText(item);
                jmRoomOrigin.setTotalFloor(Integer.parseInt(item));
            }
        });
        //意向合作楼层
        mIntentionFloorPicker = PickerUtils.initSinglePicker(this, Constants.FLOOR, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                tv_intention_floor_select.setText(item);
                jmRoomOrigin.setCooperationFloor(Integer.parseInt(item));
            }
        });


    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                if (isDelet) {
                    Toast.makeText(this, "请先进行保存数据", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
                break;
            case R.id.iv_title_second:
                //分享
                save();
                break;
            case R.id.tv_development_select:
                //开发负责人
                initPeopleData();
                showDevelopment(mShowDevelopmentPicker, tv_development_select);
                break;
            case R.id.tv_sub_select:
                //临近地铁
                startActivityForResult(new Intent(this, SubwayTalkActivity.class), Constants.SUB);
                break;
            case R.id.tv_administrative_division_select:
                //行政区域
                initAreaData();
                showArea(mAreaPicker, tv_administrative_division_select);
                break;
            case R.id.tv_talk_time_select:
                //回访时间
                dateTimePicker.show();
                break;
            case R.id.iv_property_people_select:
                //物业负责人
                property();
                break;
            case R.id.tv_cooperation_mode_select:
                //合作模式
                showMode(mModePicker, tv_cooperation_mode_select);
                break;
            case R.id.tv_floor_select:
                //总楼层
                showFloor(mFloorPicker, tv_floor_select);
                break;
            case R.id.tv_intention_floor_select:
                //意向合作楼层
                showIntentionFloor(mIntentionFloorPicker, tv_intention_floor_select);
                break;
            case R.id.tv_intention_select:
                //合作意向
                showPicker(mIntentionPicker, tv_intention_select);
                break;
            case R.id.talk_take_photos_one:
                if (null != iv_outside_three.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosOne();
                break;
            case R.id.talk_take_photos_two:
                if (null != iv_pblic_three.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosTwo();
                break;
            case R.id.talk_take_photos_three:
                takeTypePoto();
                break;
            case R.id.tv_tale_add_remark:
                //添加备注选择框
                remark();
                break;
            case R.id.map:
                //获取经纬度
                startActivityForResult(new Intent(this, MapTalkActivity.class), Constants.DD);
                break;
            case R.id.iv_image_one:
                if (TextUtils.isEmpty(ed_room_one.getText().toString().trim()) && ed_room_one.getText().toString().trim().length() < 1) {
                    ToastUtils.ToastCenter("请填写床房类型");
                    return;
                }
                if (null != ivRoomOne3.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosThree();
                break;
            case R.id.iv_image_two:
                if (TextUtils.isEmpty(ed_room_two.getText().toString().trim()) && ed_room_two.getText().toString().trim().length() < 1) {
                    ToastUtils.ToastCenter("请填写床房类型");
                    return;
                }
                if (null != ivRoomTwo3.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosThree1();
                break;
            case R.id.iv_image_three:
                if (TextUtils.isEmpty(ed_room_three.getText().toString().trim()) && ed_room_three.getText().toString().trim().length() < 1) {
                    ToastUtils.ToastCenter("请填写床房类型");
                    return;
                }
                if (null != ivRoomThree3.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosThree2();
                break;
            case R.id.iv_image_four:
                if (TextUtils.isEmpty(ed_room_four.getText().toString().trim()) && ed_room_four.getText().toString().trim().length() < 1) {
                    ToastUtils.ToastCenter("请填写床房类型");
                    return;
                }
                if (null != ivRoomFour3.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosThree3();
                break;
            case R.id.iv_image_five:
                if (null != ivRoomFive3.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(ed_room_five.getText().toString().trim()) && ed_room_five.getText().toString().trim().length() < 1) {
                    ToastUtils.ToastCenter("请填写床房类型");
                    return;
                }
                showTakePhotosThree4();
                break;
            case R.id.iv_image_sex:
                if (TextUtils.isEmpty(ed_room_five.getText().toString().trim()) && ed_room_five.getText().toString().trim().length() < 1) {
                    ToastUtils.ToastCenter("请填写床房类型");
                    return;
                }
                if (null != ivRoomSex3.getDrawable()) {
                    Toast.makeText(this, "最多添加三张照片", Toast.LENGTH_SHORT).show();
                    break;
                }
                showTakePhotosThree5();
                break;
            case R.id.tv_tel_detail_contract:
                //合同
                Intent intent = new Intent();
                intent.setClass(this,ContractActivity.class);
                Bundle bundle = new Bundle();
                if (backString.equals("")){
                    if (null==data){
                        bundle.putString("contract","");
                    }else if (null==data.getRoomPhoto()){
                        bundle.putString("contract","");
                    }else {
                        bundle.putString("contract",data.getRoomPhoto());
                    }

                }else {
                    bundle.putString("contract",backString);
                }
                intent.putExtras(bundle);
                startActivityForResult(intent,Constants.CONTRACT);
                break;
                case R.id.tv_talk_detail_confirm:
                //点击完成
                save();
                break;
        }
    }

    /**
     * 链接分享
     *
     * @param data
     * @param id
     */
    private void share(JmRoomOrigin data, int id) {
        UMWeb web = new UMWeb(Constants.talkShareUrl + id);
        //设置分享标题、图片、描述
        int cooperationMode = data.getCooperationMode();
        if (cooperationMode == 0) {
            cooperationModeString = "直营";
        } else {
            cooperationModeString = "平台管理";
        }
        web.setTitle("【" + data.getShopName() + "】  " + data.getAddress() + "   " + cooperationModeString);
        web.setDescription("总房量：" + data.getTotalRoom() + "间 明窗" + data.getCasement() + "间 楼道窗" + data.getCorridor() + "间" + "\r\n" + "报价:" + data.getPrice() + "元" + "\r\n" + "备注：" + data.getRemarkList().get(0).getContent());
        int cooperationIntention = data.getCooperationIntention();
        if (cooperationIntention == 0) {
            web.setThumb(new UMImage(TalkDetailActivity.this, R.mipmap.genjin));
        } else if (cooperationIntention == 1) {
            web.setThumb(new UMImage(TalkDetailActivity.this, R.mipmap.pass));
        } else {
            web.setThumb(new UMImage(TalkDetailActivity.this, R.mipmap.qiangyue));
        }
        new ShareAction(TalkDetailActivity.this)
                .withText("hello")
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(shareListener).open();
    }

    private void config(String url, int type) {
        String development = tv_development_select.getText().toString();
        if (DEFAULT.equals(development) || TextUtils.isEmpty(development)) {
            ToastUtils.ToastCenter("请选择项目开发负责人");
            return;
        }


        String shop = ed_shop_select.getText().toString().trim();
        if (TextUtils.isEmpty(shop) && shop.length() < 1) {
            ToastUtils.ToastCenter("请填写分店名称");
            return;
        }

        jmRoomOrigin.setShopName(shop);

        String longitude = mTvTalkLongitude.getText().toString();
        String latitude = mTvTalkLatitude.getText().toString();
        if (TextUtils.isEmpty(longitude) && longitude.length() < 1 && TextUtils.isEmpty(latitude) && latitude.length() < 1) {
            ToastUtils.ToastCenter("请获取经纬度");
            return;
        }
        /**
         * 转化为百度坐标
         */
        if (doublelatitude != 0.0 || doubleloggitude != 0.0) {
            LngLat lngLat = new LngLat(doubleloggitude, doublelatitude);
            double lantitude1 = CoodinateCovertor.bd_encrypt(lngLat).getLantitude();
            double longitude1 = CoodinateCovertor.bd_encrypt(lngLat).getLongitude();
            jmRoomOrigin.setLongitude(longitude1 + "");
            jmRoomOrigin.setLatitude(lantitude1 + "");
        }


        String sub = tv_sub_select.getText().toString();
        if (DEFAULT.equals(sub) || TextUtils.isEmpty(sub)) {
            ToastUtils.ToastCenter("请选择临近地铁");
            return;
        }

        String distance = ed_sub_distance_select.getText().toString().trim();
        if (TextUtils.isEmpty(distance) && distance.length() < 1) {
            ToastUtils.ToastCenter("请填写距离地铁大概多少米");
            return;
        }
        jmRoomOrigin.setStationDistance(distance);

        String division = tv_administrative_division_select.getText().toString();
        if (DEFAULT.equals(division) || TextUtils.isEmpty(division)) {
            ToastUtils.ToastCenter("请选择行政区域");
            return;
        }

        String time = tv_talk_time_select.getText().toString();
        if (DEFAULT.equals(time) || TextUtils.isEmpty(time)) {
            ToastUtils.ToastCenter("请选择回访时间");
            return;
        }

        String area = ed_area_select.getText().toString().trim();
        if (TextUtils.isEmpty(area) && area.length() < 1) {
            ToastUtils.ToastCenter("请填写具体地址");
            return;
        }
        jmRoomOrigin.setAddress(area);
        /**
         * 物业联系人
         */
        if (null != data) {
            String people = mPropertyPeople.getText().toString();
            String vsit = mTvTelDetailVisit.getText().toString();
            if (TextUtils.isEmpty(people) && people.length() < 1 && TextUtils.isEmpty(vsit) && vsit.length() < 1) {
                ToastUtils.ToastCenter("请填写物业负责人及联系方式");
                return;
            }
            managers.add(new JmBusinessManager(people, vsit));

            String peopleFirst = mPropertyPeopleFirst.getText().toString();
            String vsitFirst = mTvTelDetailVisitFirst.getText().toString();
            if (TextUtils.isEmpty(peopleFirst) && peopleFirst.length() < 1 && TextUtils.isEmpty(vsitFirst) && vsitFirst.length() < 1) {
            } else {
                managers.add(new JmBusinessManager(peopleFirst, vsitFirst));
            }

            String peopleSecond = mPropertyPeopleSecond.getText().toString();
            String vsitSecond = mTvTelDetailVisitSencond.getText().toString();
            if (TextUtils.isEmpty(peopleSecond) && peopleSecond.length() < 1 && TextUtils.isEmpty(vsitSecond) && vsitSecond.length() < 1) {
            } else {
                managers.add(new JmBusinessManager(peopleSecond, vsitSecond));
            }
            jmRoomOrigin.setManagerList(managers);

        } else {
            String people = mPropertyPeople.getText().toString();
            String vsit = mTvTelDetailVisit.getText().toString();
            if (TextUtils.isEmpty(people) && people.length() < 1 && TextUtils.isEmpty(vsit) && vsit.length() < 1) {
                ToastUtils.ToastCenter("请填写物业负责人及联系方式");
                return;
            }
            managers.add(new JmBusinessManager(people, vsit));

            if (mPeopleFirstTag == 1) {
                String peopleFirst = mPropertyPeopleFirst.getText().toString();
                String vsitFirst = mTvTelDetailVisitFirst.getText().toString();
                if (TextUtils.isEmpty(peopleFirst) && peopleFirst.length() < 1 && TextUtils.isEmpty(vsitFirst) && vsitFirst.length() < 1) {
                    ToastUtils.ToastCenter("请填写物业负责人1及联系方式1");
                    return;
                }
                managers.add(new JmBusinessManager(peopleFirst, vsitFirst));
            }

            if (mPeopleSecondTag == 2) {
                String peopleSecond = mPropertyPeopleSecond.getText().toString();
                String vsitSecond = mTvTelDetailVisitSencond.getText().toString();
                if (TextUtils.isEmpty(peopleSecond) && peopleSecond.length() < 1 && TextUtils.isEmpty(vsitSecond) && vsitSecond.length() < 1) {
                    ToastUtils.ToastCenter("请填写物业负责人2及联系方式2");
                    return;
                }
                managers.add(new JmBusinessManager(peopleSecond, vsitSecond));
            }
            jmRoomOrigin.setManagerList(managers);
        }

        String mode = tv_cooperation_mode_select.getText().toString();
        if (DEFAULT.equals(mode) || TextUtils.isEmpty(mode)) {
            ToastUtils.ToastCenter("请选择合作模式");
            return;
        }

        if (mode.equals("平台管理")) {
            String floor = tv_floor_select.getText().toString();
            if (DEFAULT.equals(floor) || TextUtils.isEmpty(floor)) {
                ToastUtils.ToastCenter("请选择总楼层数");
                return;
            }
            String room = ed_room_select.getText().toString().trim();
            if (TextUtils.isEmpty(room) && room.length() < 1) {
                ToastUtils.ToastCenter("请填写总房间数");
                return;
            }
            jmRoomOrigin.setTotalRoom(Integer.parseInt(room));
        } else {
            String floor = tv_floor_select.getText().toString().trim();
            if (DEFAULT.equals(floor) || TextUtils.isEmpty(floor)) {
                ToastUtils.ToastCenter("请选择总楼层数");
                return;
            }

            String room = ed_room_select.getText().toString().trim();
            if (TextUtils.isEmpty(room) && room.length() < 1) {
                ToastUtils.ToastCenter("请填写总房间数");
                return;
            }
            jmRoomOrigin.setTotalRoom(Integer.parseInt(room));

            String intention_floor = tv_intention_floor_select.getText().toString().trim();
            if (DEFAULT.equals(intention_floor) || TextUtils.isEmpty(intention_floor)) {
                ToastUtils.ToastCenter("请选择意向合作楼层");
                return;
            }


            String intention_room = ed_intention_room_select.getText().toString().trim();
            if (TextUtils.isEmpty(intention_room) && intention_room.length() < 1) {
                ToastUtils.ToastCenter("请填写意向合作房量");
                return;
            }
            jmRoomOrigin.setCooperationRoom(Integer.parseInt(intention_room));
        }


        String shining = ed_talk_shining.getText().toString().trim();
        if (TextUtils.isEmpty(shining) && shining.length() < 1) {
            ToastUtils.ToastCenter("请填写明窗");
            return;
        }
        jmRoomOrigin.setCasement(Integer.parseInt(shining));

        String midnight = ed_talk_midnight.getText().toString().trim();
        if (TextUtils.isEmpty(midnight) && midnight.length() < 1) {
            ToastUtils.ToastCenter("请填写楼道窗");
            return;
        }
        jmRoomOrigin.setCorridor(Integer.parseInt(midnight));

        String price = ed_talk_price.getText().toString().trim();
        if (TextUtils.isEmpty(price) && price.length() < 1) {
            ToastUtils.ToastCenter("请填写报价");
            return;
        }
        jmRoomOrigin.setPrice(price);

        String intention = tv_intention_select.getText().toString();
        if (DEFAULT.equals(intention) || TextUtils.isEmpty(intention)) {
            ToastUtils.ToastCenter("请选择合作意向");
            return;
        }

//        if (iv_outside_one.getDrawable() == null) {
//            ToastUtils.ToastCenter("请对物业外围环境进行拍照");
//            return;
//        }
//        if (iv_pblic_one.getDrawable() == null) {
//            ToastUtils.ToastCenter("请对公区进行拍照");
//            return;
//        }
//
//        String edRoomOne = ed_room_one.getText().toString().trim();
//        if (TextUtils.isEmpty(edRoomOne) && edRoomOne.length() < 1) {
//            ToastUtils.ToastCenter("请填写床房类型");
//            return;
//        }

        if (ivRoomOne1.getDrawable() == null) {
            ToastUtils.ToastCenter("请对房间进行拍照");
            return;
        }
        if (null != data) {
        } else {
            if (mJmRoomManager1.size() > 0) {
                mJmRoomManager.add(mJmRoomManager1.get(0));
            }
            if (mJmRoomManager2.size() > 0) {
                mJmRoomManager.add(mJmRoomManager2.get(0));
            }
            if (mJmRoomManager3.size() > 0) {
                mJmRoomManager.add(mJmRoomManager3.get(0));
            }
            if (mJmRoomManager4.size() > 0) {
                mJmRoomManager.add(mJmRoomManager4.get(0));
            }
            if (mJmRoomManager5.size() > 0) {
                mJmRoomManager.add(mJmRoomManager5.get(0));
            }
            if (mJmRoomManager6.size() > 0) {
                mJmRoomManager.add(mJmRoomManager6.get(0));
            }
            jmRoomOrigin.setTypeList(mJmRoomManager);
        }

        if (ll_tel_talk_remark.getChildCount() < 1) {
            ToastUtils.ToastCenter("请填写备注");
            return;
        }
        /**
         * 合同
         */
        int cooperationIntention = jmRoomOrigin.getCooperationIntention();
        if (cooperationIntention==2){
            String roomPhoto = jmRoomOrigin.getRoomPhoto();
            if (null==roomPhoto){
                ToastUtils.ToastCenter("请上传合同");
                return;
            }
        }else {
            jmRoomOrigin.setRoomPhoto("");
        }
        /**
         * 添加
         */
        if (type == 1) {
            if (isAdd) {
                Toast.makeText(this, "已添加此条数据", Toast.LENGTH_SHORT).show();
                return;
            }
            loadingDialog = LoadingDialogUtils.createLoadingDialog(TalkDetailActivity.this, "保存中...");
            loadingDialog.show();
            submitAdd(url, jmRoomOrigin);
        } else {
            submitUpDate(url, jmRoomOrigin);
            JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
            jmRoomOrigin.setId(data.getId());
            jmRoomOrigin.setShopName(data.getShopName());
            jmRoomOrigin.setAddress(data.getAreaName());
            jmRoomOrigin.setCooperationMode(data.getCooperationMode());
            jmRoomOrigin.setTotalRoom(Integer.parseInt(ed_room_select.getText().toString()));
            jmRoomOrigin.setCasement(Integer.parseInt(ed_talk_shining.getText().toString()));
            jmRoomOrigin.setCorridor(Integer.parseInt(ed_talk_midnight.getText().toString()));
            if (isCheck) {
                jmRoomOrigin.setCooperationIntention(intent);
            } else {
                jmRoomOrigin.setCooperationIntention(data.getCooperationIntention());
            }
            jmRoomOrigin.setPrice(data.getPrice());
            ArrayList<JmRoomRemark> mRemark = new ArrayList<>();
            if (data.getRemarkList().size() > 0) {
                mRemark.add(new JmRoomRemark(data.getRemarkList().get(0).getContent()));
            } else {
                mRemark.add(new JmRoomRemark("暂无"));
            }
            jmRoomOrigin.setRemarkList(mRemark);
            share(jmRoomOrigin, data.getId());
        }
    }

    /**
     * 添加
     *
     * @param url
     * @param jmRoomOrigin
     */
    private void submitAdd(String url, final JmRoomOrigin jmRoomOrigin) {
        isAdd = true;
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(jmRoomOrigin));
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
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                TalkTypeBean talkTypeBean = new Gson().fromJson(string, TalkTypeBean.class);
                dataId = talkTypeBean.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dataId != 0) {
                            jmRoomOrigin.setAddress(areaName);
                            share(jmRoomOrigin, dataId);
                        } else {
                            Toast.makeText(TalkDetailActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                        LoadingDialogUtils.closeDialog(loadingDialog);
                    }
                });
            }
        });
    }

    /**
     * 更新
     *
     * @param url
     * @param jmRoomOrigin
     */
    private void submitUpDate(String url, final JmRoomOrigin jmRoomOrigin) {
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(jmRoomOrigin));
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
            }
        });
    }

    /**
     * 上传图片
     *
     * @param url
     * @param name
     * @param file
     * @param list
     * @param type
     */
    private void upLoadPhoto(String url, String name, File file, final ArrayList<String> list, final int type) {
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
                photosServertUrl = response.body().string();
                if (type == Constants.TYPE_OUTSIDE) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    jmRoomOrigin.setSettingPhoto(str);
                } else if (type == Constants.TYPE_PUBLIC) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    jmRoomOrigin.setParkPhoto(str);
                } else if (type == Constants.TYPE_ROOM) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager1.clear();
                    mJmRoomManager1.add(new JmRoomType(ed_room_one.getText().toString().trim(), str));

                } else if (type == Constants.TYPE_ROOM1) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager2.clear();
                    mJmRoomManager2.add(new JmRoomType(ed_room_two.getText().toString().trim(), str));
                } else if (type == Constants.TYPE_ROOM2) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager3.clear();
                    mJmRoomManager3.add(new JmRoomType(ed_room_three.getText().toString().trim(), str));
                } else if (type == Constants.TYPE_ROOM3) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager4.clear();
                    mJmRoomManager4.add(new JmRoomType(ed_room_four.getText().toString().trim(), str));
                } else if (type == Constants.TYPE_ROOM4) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager5.clear();
                    mJmRoomManager5.add(new JmRoomType(ed_room_five.getText().toString().trim(), str));
                } else {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager6.clear();
                    mJmRoomManager6.add(new JmRoomType(ed_room_sex.getText().toString().trim(), str));
                }
            }
        });
    }

    /**
     * 上传图片
     *
     * @param url
     * @param name
     * @param file
     * @param list
     * @param type
     */
    private void upDataLoadPhoto(String url, String name, File file, final ArrayList<String> list, final int type) {
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
                photosServertUrl = response.body().string();
                if (type == Constants.TYPE_OUTSIDE) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = data.getSettingPhoto();
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    jmRoomOrigin.setSettingPhoto(str);
                } else if (type == Constants.TYPE_PUBLIC) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = data.getParkPhoto();
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    jmRoomOrigin.setParkPhoto(str);
                } else if (type == Constants.TYPE_ROOM) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager1.clear();
                    mJmRoomManager1.add(new JmRoomType(ed_room_one.getText().toString().trim(), str));

                } else if (type == Constants.TYPE_ROOM1) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager2.clear();
                    mJmRoomManager2.add(new JmRoomType(ed_room_two.getText().toString().trim(), str));
                } else if (type == Constants.TYPE_ROOM2) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager3.clear();
                    mJmRoomManager3.add(new JmRoomType(ed_room_three.getText().toString().trim(), str));
                } else if (type == Constants.TYPE_ROOM3) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager4.clear();
                    mJmRoomManager4.add(new JmRoomType(ed_room_four.getText().toString().trim(), str));
                } else if (type == Constants.TYPE_ROOM4) {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager5.clear();
                    mJmRoomManager5.add(new JmRoomType(ed_room_five.getText().toString().trim(), str));
                } else {
                    list.add(photosServertUrl.substring(1, photosServertUrl.length() - 1));
                    String str = "";
                    for (int i = 0; i < list.size(); i++) {
                        str += list.get(i) + ",";
                    }
                    mJmRoomManager6.clear();
                    mJmRoomManager6.add(new JmRoomType(ed_room_sex.getText().toString().trim(), str));
                }
            }
        });
    }

    /**
     * 加载行政区域数据
     */
    private void initAreaData() {
        if (null != mAreas) {
            //行政区域
            mAreaPicker = PickerUtils.initSinglePicker(this, mAreas, new PickerUtils.SingleListener() {


                @Override
                public void select(int index, String item) {
                    tv_administrative_division_select.setText(item);
                    for (int i = 0; i < mArea.size(); i++) {
                        if (item.equals(mArea.get(i).getName())) {
                            jmRoomOrigin.setAreaId(mArea.get(i).getId());
                            areaName = item;
                            return;
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 加载项目开发负责人数据
     */
    private void initPeopleData() {
        if (null != mPeoples) {
            //开发负责人
            mShowDevelopmentPicker = PickerUtils.initSinglePicker(this, mPeoples, new PickerUtils.SingleListener() {
                @Override
                public void select(int index, String item) {
                    tv_development_select.setText(item);
                    for (int i = 0; i < mPeople.size(); i++) {
                        if (item.equals(mPeople.get(i).getName())) {
                            jmRoomOrigin.setUserId(mPeople.get(i).getId());
                            return;
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "暂无联系人", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拍照
     */
    private void showTakePhotosThree() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (ivRoomOne1.getDrawable() == null) {
                    takeSave(Constants.ROOM1);
                } else if (ivRoomOne2.getDrawable() == null) {
                    takeSave(Constants.ROOM2);
                } else {
                    takeSave(Constants.ROOM3);
                }
            }

            @Override
            public void onPickPhoto() {
                if (ivRoomOne1.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY7);
                } else if (ivRoomOne2.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY8);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY9);
                }
            }
        });

    }

    private void showTakePhotosThree1() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (ivRoomTwo1.getDrawable() == null) {
                    takeSave(Constants.ROOM4);
                } else if (ivRoomTwo2.getDrawable() == null) {
                    takeSave(Constants.ROOM5);
                } else {
                    takeSave(Constants.ROOM6);
                }
            }

            @Override
            public void onPickPhoto() {
                if (ivRoomTwo1.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY10);
                } else if (ivRoomTwo2.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY11);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY12);
                }
            }
        });

    }

    private void showTakePhotosThree2() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (ivRoomThree1.getDrawable() == null) {
                    takeSave(Constants.ROOM7);
                } else if (ivRoomThree2.getDrawable() == null) {
                    takeSave(Constants.ROOM8);
                } else {
                    takeSave(Constants.ROOM9);
                }
            }

            @Override
            public void onPickPhoto() {
                if (ivRoomThree1.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY13);
                } else if (ivRoomThree2.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY14);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY15);
                }
            }
        });

    }

    private void showTakePhotosThree3() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (ivRoomFour1.getDrawable() == null) {
                    takeSave(Constants.ROOM10);
                } else if (ivRoomFour2.getDrawable() == null) {
                    takeSave(Constants.ROOM11);
                } else {
                    takeSave(Constants.ROOM12);
                }
            }

            @Override
            public void onPickPhoto() {
                if (ivRoomFour1.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY16);
                } else if (ivRoomFour2.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY17);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY18);
                }
            }
        });
    }

    private void showTakePhotosThree4() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (ivRoomFive1.getDrawable() == null) {
                    takeSave(Constants.ROOM13);
                } else if (ivRoomFive2.getDrawable() == null) {
                    takeSave(Constants.ROOM14);
                } else {
                    takeSave(Constants.ROOM15);
                }
            }

            @Override
            public void onPickPhoto() {
                if (ivRoomFive1.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY19);
                } else if (ivRoomFive2.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY20);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY21);
                }
            }
        });
    }

    private void showTakePhotosThree5() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (ivRoomSex1.getDrawable() == null) {
                    takeSave(Constants.ROOM16);
                } else if (ivRoomSex2.getDrawable() == null) {
                    takeSave(Constants.ROOM17);
                } else {
                    takeSave(Constants.ROOM18);
                }
            }

            @Override
            public void onPickPhoto() {
                if (ivRoomSex1.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY22);
                } else if (ivRoomSex2.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY23);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY24);
                }
            }
        });
    }


    private void showTakePhotosTwo() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (iv_pblic_one.getDrawable() == null) {
                    takeSave(Constants.PUBLIC1);
                } else if (iv_pblic_two.getDrawable() == null) {
                    takeSave(Constants.PUBLIC2);
                } else {
                    takeSave(Constants.PUBLIC3);
                }
            }

            @Override
            public void onPickPhoto() {
                if (iv_pblic_one.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY4);
                } else if (iv_pblic_two.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY5);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY6);
                }
            }
        });
    }

    private void showTakePhotosOne() {
        selectPicPopupWindowUitls.showAtLocation(talk_take_photos_one, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        selectPicPopupWindowUitls.getBtn_take_photo(new SelectPicPopupWindowUitls.PopupWindowOnTakePhotoOnClick() {
            @Override
            public void onTakePhoto() {
                if (iv_outside_one.getDrawable() == null) {
                    takeSave(Constants.OUTSIDE1);
                } else if (iv_outside_two.getDrawable() == null) {
                    takeSave(Constants.OUTSIDE2);
                } else {
                    takeSave(Constants.OUTSIDE3);
                }

            }

            @Override
            public void onPickPhoto() {
                if (iv_outside_one.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY1);
                } else if (iv_outside_two.getDrawable() == null) {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY2);
                } else {
                    takePickSave(Constants.PHOTO_REQUEST_GALLERY3);
                }
            }
        });

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
            selectPicPopupWindowUitls.dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void takePickSave(int type) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, type);
        selectPicPopupWindowUitls.dismiss();
    }

    //开发负责人
    private void showDevelopment(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!TelDetailActivity.DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();
    }


    //合作意向
    private void showMode(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!TelDetailActivity.DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();
    }

    //总楼层
    private void showFloor(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!TelDetailActivity.DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();
    }

    //意向合作楼层
    private void showIntentionFloor(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!TelDetailActivity.DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();
    }

    //行政区域
    private void showArea(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!TelDetailActivity.DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();
    }

    //合作模式
    private void showPicker(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!TelDetailActivity.DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照回调
        if (requestCode == Constants.OUTSIDE1 && resultCode == RESULT_OK) {
            zipImage(imageUrlOutSide, Constants.TYPE_OUTSIDE, iv_outside_one,imageUri);
        }
        if (requestCode == Constants.OUTSIDE2 && resultCode == RESULT_OK) {
            zipImage(imageUrlOutSide, Constants.TYPE_OUTSIDE, iv_outside_two,imageUri);
        }
        if (requestCode == Constants.OUTSIDE3 && resultCode == RESULT_OK) {
            zipImage(imageUrlOutSide, Constants.TYPE_OUTSIDE, iv_outside_three,imageUri);
        }

        if (requestCode == Constants.PUBLIC1 && resultCode == RESULT_OK) {
            zipImage(imageUrlPblic, Constants.TYPE_PUBLIC, iv_pblic_one,imageUri);
        }
        if (requestCode == Constants.PUBLIC2 && resultCode == RESULT_OK) {
            zipImage(imageUrlPblic, Constants.TYPE_PUBLIC, iv_pblic_two,imageUri);
        }
        if (requestCode == Constants.PUBLIC3 && resultCode == RESULT_OK) {
            zipImage(imageUrlPblic, Constants.TYPE_PUBLIC, iv_pblic_three,imageUri);
        }
        //户外
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY1 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlOutSide, imageUri, iv_outside_one, Constants.TYPE_OUTSIDE);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY2 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlOutSide, imageUri, iv_outside_two, Constants.TYPE_OUTSIDE);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY3 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlOutSide, imageUri, iv_outside_three, Constants.TYPE_OUTSIDE);
        }
        //公共区域
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY4 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlPblic, imageUri, iv_pblic_one, Constants.TYPE_PUBLIC);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY5 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlPblic, imageUri, iv_pblic_two, Constants.TYPE_PUBLIC);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY6 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlPblic, imageUri, iv_pblic_three, Constants.TYPE_PUBLIC);
        }


        /**
         * 房型
         */
        if (requestCode == Constants.ROOM1 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom, Constants.TYPE_ROOM, ivRoomOne1,imageUri);
        }
        if (requestCode == Constants.ROOM2 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom, Constants.TYPE_ROOM, ivRoomOne2,imageUri);
        }
        if (requestCode == Constants.ROOM3 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom, Constants.TYPE_ROOM, ivRoomOne3,imageUri);
        }

        if (requestCode == Constants.ROOM4 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom1, Constants.TYPE_ROOM1, ivRoomTwo1,imageUri);
        }
        if (requestCode == Constants.ROOM5 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom1, Constants.TYPE_ROOM1, ivRoomTwo2,imageUri);
        }
        if (requestCode == Constants.ROOM6 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom1, Constants.TYPE_ROOM1, ivRoomTwo3,imageUri);
        }


        if (requestCode == Constants.ROOM7 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom2, Constants.TYPE_ROOM2, ivRoomThree1,imageUri);
        }
        if (requestCode == Constants.ROOM8 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom2, Constants.TYPE_ROOM2, ivRoomThree2,imageUri);
        }
        if (requestCode == Constants.ROOM9 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom2, Constants.TYPE_ROOM2, ivRoomThree3,imageUri);
        }


        if (requestCode == Constants.ROOM10 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom3, Constants.TYPE_ROOM3, ivRoomFour1,imageUri);
        }
        if (requestCode == Constants.ROOM11 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom3, Constants.TYPE_ROOM3, ivRoomFour2,imageUri);
        }
        if (requestCode == Constants.ROOM12 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom3, Constants.TYPE_ROOM3, ivRoomFour3,imageUri);
        }


        if (requestCode == Constants.ROOM13 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom4, Constants.TYPE_ROOM4, ivRoomFive1,imageUri);
        }
        if (requestCode == Constants.ROOM14 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom4, Constants.TYPE_ROOM4, ivRoomFive2,imageUri);
        }
        if (requestCode == Constants.ROOM15 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom4, Constants.TYPE_ROOM4, ivRoomFive3,imageUri);
        }


        if (requestCode == Constants.ROOM16 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom5, Constants.TYPE_ROOM5, ivRoomSex1,imageUri);
        }
        if (requestCode == Constants.ROOM17 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom5, Constants.TYPE_ROOM5, ivRoomSex2,imageUri);
        }
        if (requestCode == Constants.ROOM18 && resultCode == RESULT_OK) {
            zipImage(imageUrlRoom5, Constants.TYPE_ROOM5, ivRoomSex3,imageUri);
        }

        //房型1
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY7 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom, imageUri, ivRoomOne1, Constants.TYPE_ROOM);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY8 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom, imageUri, ivRoomOne2, Constants.TYPE_ROOM);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY9 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom, imageUri, ivRoomOne3, Constants.TYPE_ROOM);
        }
        //房型2
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY10 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom1, imageUri, ivRoomTwo1, Constants.TYPE_ROOM1);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY11 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom1, imageUri, ivRoomTwo2, Constants.TYPE_ROOM1);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY12 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom1, imageUri, ivRoomTwo3, Constants.TYPE_ROOM1);
        }

        //房型3
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY13 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom2, imageUri, ivRoomThree1, Constants.TYPE_ROOM2);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY14 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom2, imageUri, ivRoomThree2, Constants.TYPE_ROOM2);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY15 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom2, imageUri, ivRoomThree3, Constants.TYPE_ROOM2);
        }
        //房型4
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY16 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom3, imageUri, ivRoomFour1, Constants.TYPE_ROOM3);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY17 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom3, imageUri, ivRoomFour2, Constants.TYPE_ROOM3);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY18 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom3, imageUri, ivRoomFour3, Constants.TYPE_ROOM3);
        }

        //房型5
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY19 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom4, imageUri, ivRoomFive1, Constants.TYPE_ROOM4);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY20 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom4, imageUri, ivRoomFive2, Constants.TYPE_ROOM4);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY21 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom4, imageUri, ivRoomFive3, Constants.TYPE_ROOM4);
        }
        //房型6
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY22 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom5, imageUri, ivRoomSex1, Constants.TYPE_ROOM5);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY23 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom5, imageUri, ivRoomSex2, Constants.TYPE_ROOM5);
        }
        if (requestCode == Constants.PHOTO_REQUEST_GALLERY24 && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            pickImage(imageUrlRoom5, imageUri, ivRoomSex3, Constants.TYPE_ROOM5);
        }


        if (data != null && requestCode == Constants.SUB) {
            String subway = data.getStringExtra(Constants.SUBWAY);
            intExtra = data.getIntExtra(Constants.SUBWAY_ID, -1);//initExtra为 id
            tv_sub_select.setText(subway);
            jmRoomOrigin.setStationId(intExtra);
        }
        //地图
        if (data != null && requestCode == Constants.DD) {
            mTvTalkLongitude.setText(data.getStringExtra(Constants.LONGITUDE));
            mTvTalkLatitude.setText(data.getStringExtra(Constants.LATITUDE));
            doublelatitude = data.getDoubleExtra(Constants.Latitude, 0.0);
            doubleloggitude = data.getDoubleExtra(Constants.Loggitude, 0.0);
            context = data.getStringExtra(Constants.CONTEXT);
            title = data.getStringExtra(Constants.TITLE);
            ed_shop_select.setText(context);
            ed_area_select.setText(title);
            jmRoomOrigin.setAddress(title);
            jmRoomOrigin.setShopName(context);
        }
        //合同
        if (data != null && requestCode == Constants.CONTRACT && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            backString = bundle.getString(Constants.CONTRACT2);
            jmRoomOrigin.setRoomPhoto(backString);
            tvTalkDetailContract.setText("查看合同");
        }
    }


    private void initFind() {
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("电话咨询详情");
        iv_title_second = (ImageView) findViewById(R.id.iv_title_second);
        iv_title_second.setVisibility(View.VISIBLE);
        iv_title_second.setImageResource(R.mipmap.share);
        tv_development_select = (TextView) findViewById(R.id.tv_development_select);
        ed_shop_select = (EditText) findViewById(R.id.ed_shop_select);
        tv_sub_select = (TextView) findViewById(R.id.tv_sub_select);
        ed_sub_distance_select = (EditText) findViewById(R.id.ed_sub_distance_select);
        tv_administrative_division_select = (TextView) findViewById(R.id.tv_administrative_division_select);
        tv_talk_time_select = (TextView) findViewById(R.id.tv_talk_time_select);
        ed_area_select = (EditText) findViewById(R.id.ed_area_select);
        tv_cooperation_mode_select = (TextView) findViewById(R.id.tv_cooperation_mode_select);
        tv_floor_select = (TextView) findViewById(R.id.tv_floor_select);
        tv_intention_floor_select = (TextView) findViewById(R.id.tv_intention_floor_select);
        ed_room_select = (EditText) findViewById(R.id.ed_room_select);
        ed_intention_room_select = (EditText) findViewById(R.id.ed_intention_room_select);
        ed_talk_shining = (EditText) findViewById(R.id.ed_talk_shining);
        ed_talk_midnight = (EditText) findViewById(R.id.ed_talk_midnight);
        ed_talk_price = (EditText) findViewById(R.id.ed_talk_price);
        tv_intention_select = (TextView) findViewById(R.id.tv_intention_select);
        iv_outside_one = (ImageView) findViewById(R.id.iv_outside_one);
        iv_outside_three = (ImageView) findViewById(R.id.iv_outside_three);
        iv_outside_two = (ImageView) findViewById(R.id.iv_outside_two);
        iv_pblic_one = (ImageView) findViewById(R.id.iv_pblic_one);
        iv_pblic_two = (ImageView) findViewById(R.id.iv_pblic_two);
        iv_pblic_three = (ImageView) findViewById(R.id.iv_pblic_three);

        ed_room_one = (EditText) findViewById(R.id.ed_room_one);
        ed_room_two = (EditText) findViewById(R.id.ed_room_two);
        ed_room_three = (EditText) findViewById(R.id.ed_room_three);
        ed_room_four = (EditText) findViewById(R.id.ed_room_four);
        ed_room_five = (EditText) findViewById(R.id.ed_room_five);
        ed_room_sex = (EditText) findViewById(R.id.ed_room_sex);
        linTalkDetailContract = (LinearLayout) findViewById(R.id.lin_talk_detail_contract);
        tvTalkDetailContract = (TextView) findViewById(R.id.tv_tel_detail_contract);
        tvTalkDetailContract.setOnClickListener(this);
        ivImageOne = (ImageView) findViewById(R.id.iv_image_one);
        ivImageOne.setOnClickListener(this);
        ivImageTwo = (ImageView) findViewById(R.id.iv_image_two);
        ivImageTwo.setOnClickListener(this);
        ivImageThree = (ImageView) findViewById(R.id.iv_image_three);
        ivImageThree.setOnClickListener(this);
        ivImageFour = (ImageView) findViewById(R.id.iv_image_four);
        ivImageFour.setOnClickListener(this);
        ivImageFive = (ImageView) findViewById(R.id.iv_image_five);
        ivImageFive.setOnClickListener(this);
        ivImageSex = (ImageView) findViewById(R.id.iv_image_sex);
        ivImageSex.setOnClickListener(this);

        ed_room_one = (EditText) findViewById(R.id.ed_room_one);
        ed_room_two = (EditText) findViewById(R.id.ed_room_two);
        ed_room_three = (EditText) findViewById(R.id.ed_room_three);
        ed_room_four = (EditText) findViewById(R.id.ed_room_four);
        ed_room_five = (EditText) findViewById(R.id.ed_room_five);
        ed_room_sex = (EditText) findViewById(R.id.ed_room_sex);

        ivRoomOne1 = (ImageView) findViewById(R.id.iv_room_one1);
        ivRoomOne1.setOnClickListener(this);
        ivRoomOne2 = (ImageView) findViewById(R.id.iv_room_one2);
        ivRoomOne2.setOnClickListener(this);
        ivRoomOne3 = (ImageView) findViewById(R.id.iv_room_one3);
        ivRoomOne3.setOnClickListener(this);

        ivRoomTwo1 = (ImageView) findViewById(R.id.iv_room_two1);
        ivRoomTwo2 = (ImageView) findViewById(R.id.iv_room_two2);
        ivRoomTwo3 = (ImageView) findViewById(R.id.iv_room_two3);
        ivRoomTwo1.setOnClickListener(this);
        ivRoomTwo2.setOnClickListener(this);
        ivRoomTwo3.setOnClickListener(this);

        ivRoomThree1 = (ImageView) findViewById(R.id.iv_room_three1);
        ivRoomThree2 = (ImageView) findViewById(R.id.iv_room_three2);
        ivRoomThree3 = (ImageView) findViewById(R.id.iv_room_three3);
        ivRoomThree1.setOnClickListener(this);
        ivRoomThree2.setOnClickListener(this);
        ivRoomThree3.setOnClickListener(this);

        ivRoomFour1 = (ImageView) findViewById(R.id.iv_room_four1);
        ivRoomFour2 = (ImageView) findViewById(R.id.iv_room_four2);
        ivRoomFour3 = (ImageView) findViewById(R.id.iv_room_four3);
        ivRoomFour1.setOnClickListener(this);
        ivRoomFour2.setOnClickListener(this);
        ivRoomFour3.setOnClickListener(this);

        ivRoomFive1 = (ImageView) findViewById(R.id.iv_room_five1);
        ivRoomFive2 = (ImageView) findViewById(R.id.iv_room_five2);
        ivRoomFive3 = (ImageView) findViewById(R.id.iv_room_five3);
        ivRoomFive1.setOnClickListener(this);
        ivRoomFive2.setOnClickListener(this);
        ivRoomFive3.setOnClickListener(this);

        ivRoomSex1 = (ImageView) findViewById(R.id.iv_room_sex1);
        ivRoomSex2 = (ImageView) findViewById(R.id.iv_room_sex2);
        ivRoomSex3 = (ImageView) findViewById(R.id.iv_room_sex3);
        ivRoomSex1.setOnClickListener(this);
        ivRoomSex2.setOnClickListener(this);
        ivRoomSex3.setOnClickListener(this);
        typeOne = (LinearLayout) findViewById(R.id.type_one);
        typeTwo = (LinearLayout) findViewById(R.id.type_two);
        typeThree = (LinearLayout) findViewById(R.id.type_three);
        typeFour = (LinearLayout) findViewById(R.id.type_four);
        typeFive = (LinearLayout) findViewById(R.id.type_five);

        tv_tale_add_remark = (TextView) findViewById(R.id.tv_tale_add_remark);
        tv_talk_detail_confirm = (TextView) findViewById(R.id.tv_talk_detail_confirm);
        talk_take_photos_three = (ImageView) findViewById(R.id.talk_take_photos_three);
        talk_take_photos_two = (ImageView) findViewById(R.id.talk_take_photos_two);
        talk_take_photos_one = (ImageView) findViewById(R.id.talk_take_photos_one);
        mPropertyPeople = (EditText) findViewById(R.id.property_people);
        mTvTelDetailVisit = (EditText) findViewById(R.id.tv_tel_detail_visit);
        ivPropertyPeopleSelect = (ImageView) findViewById(R.id.iv_property_people_select);
        mPropertyPeopleFirst = (EditText) findViewById(R.id.property_people_first);
        mTvTelDetailVisitFirst = (EditText) findViewById(R.id.tv_tel_detail_visit_first);
        mPropertyPeopleSecond = (EditText) findViewById(R.id.property_people_second);
        mTvTelDetailVisitSencond = (EditText) findViewById(R.id.tv_tel_detail_visit_sencond);
        mPeopleFirst = (LinearLayout) findViewById(R.id.people_first);
        ll_tel_talk_remark = (LinearLayout) findViewById(R.id.ll_tel_talk_remark);
        mPeopleSecond = (LinearLayout) findViewById(R.id.people_second);
        map = (LinearLayout) findViewById(R.id.map);
        mTvTalkLongitude = (TextView) findViewById(R.id.tv_talk_longitude);
        hezuo = (LinearLayout) findViewById(R.id.hezuo);
        hezuo1 = (LinearLayout) findViewById(R.id.hezuo1);
        mTvTalkLatitude = (TextView) findViewById(R.id.tv_talk_latitude);
        mPeopleFirst.setOnClickListener(this);
        map.setOnClickListener(this);
        ivPropertyPeopleSelect.setOnClickListener(this);
        mPeopleSecond.setOnClickListener(this);
        tv_administrative_division_select.setOnClickListener(this);
        talk_take_photos_one.setOnClickListener(this);
        talk_take_photos_two.setOnClickListener(this);
        iv_title_second.setOnClickListener(this);
        talk_take_photos_three.setOnClickListener(this);
        tv_development_select.setOnClickListener(this);
        tv_sub_select.setOnClickListener(this);
        tv_talk_time_select.setOnClickListener(this);
        iv_title_back.setOnClickListener(this);
        tv_cooperation_mode_select.setOnClickListener(this);
        tv_floor_select.setOnClickListener(this);
        tv_intention_floor_select.setOnClickListener(this);
        tv_intention_select.setOnClickListener(this);
        tv_talk_detail_confirm.setOnClickListener(this);
        tv_tale_add_remark.setOnClickListener(this);
        /**
         * 点击监听
         */
        iv_outside_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iv_outside_one.getDrawable()) {
                    if (isUpData) {
                        if (data.getSettingPhoto().length() > 0) {
                            ImagViewTouch(data.getSettingPhoto(), Constants.OUTSIDE1, 1);
                        }
                    }
                }
            }
        });
        iv_outside_one.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isUpData) {
                    if (data.getSettingPhoto().length() > 0) {
                        initDialog(Constants.HOST + Constants.deletPhotos, data.getSettingPhoto().split(",")[0], iv_outside_one, data.getSettingPhoto(), 0, 1);
                    }
                }
                return true;
            }
        });
        iv_outside_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iv_outside_two.getDrawable()) {
                    if (isUpData) {
                        if (data.getSettingPhoto().length() > 0) {
                            ImagViewTouch(data.getSettingPhoto(), Constants.OUTSIDE1, 2);
                        }
                    }
                }
            }
        });
        iv_outside_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isUpData) {
                    if (data.getSettingPhoto().length() > 0) {
                        initDialog(Constants.HOST + Constants.deletPhotos, data.getSettingPhoto().split(",")[1], iv_outside_two, data.getSettingPhoto(), 1, 1);
                    }
                }
                return true;
            }
        });
        iv_outside_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iv_outside_three.getDrawable()) {
                    if (isUpData) {
                        if (data.getSettingPhoto().length() > 0) {
                            ImagViewTouch(data.getSettingPhoto(), Constants.OUTSIDE1, 3);
                        }
                    }
                }
            }
        });
        iv_outside_three.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isUpData) {
                    if (data.getSettingPhoto().length() > 0) {
                        initDialog(Constants.HOST + Constants.deletPhotos, data.getSettingPhoto().split(",")[2], iv_outside_three, data.getSettingPhoto(), 2, 1);
                    }
                }
                return true;
            }
        });
        iv_pblic_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iv_pblic_one.getDrawable()) {
                    if (isUpData) {
                        if (data.getParkPhoto().length() > 0) {
                            ImagViewTouch(data.getParkPhoto(), Constants.OUTSIDE1, 1);
                        }
                    }
                }
            }
        });
        iv_pblic_one.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isUpData) {
                    if (data.getParkPhoto().length() > 0) {
                        initDialog(Constants.HOST + Constants.deletPhotos, data.getParkPhoto().split(",")[0], iv_pblic_one, data.getParkPhoto(), 0, 2);
                    }
                }
                return true;
            }
        });
        iv_pblic_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iv_pblic_two.getDrawable()) {
                    if (isUpData) {
                        if (data.getParkPhoto().length() > 0) {
                            ImagViewTouch(data.getParkPhoto(), Constants.OUTSIDE1, 2);
                        }
                    }
                }
            }
        });
        iv_pblic_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isUpData) {
                    if (data.getParkPhoto().length() > 0) {
                        initDialog(Constants.HOST + Constants.deletPhotos, data.getParkPhoto().split(",")[1], iv_pblic_two, data.getParkPhoto(), 1, 2);
                    }
                }
                return true;
            }
        });
        iv_pblic_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iv_pblic_three.getDrawable()) {
                    if (isUpData) {
                        if (data.getParkPhoto().length() > 0) {
                            ImagViewTouch(data.getParkPhoto(), Constants.OUTSIDE1, 3);
                        }
                    }
                }
            }
        });
        iv_pblic_three.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isUpData) {
                    if (data.getParkPhoto().length() > 0) {
                        initDialog(Constants.HOST + Constants.deletPhotos, data.getParkPhoto().split(",")[2], iv_pblic_three, data.getParkPhoto(), 2, 2);
                    }
                }
                return true;
            }
        });
        ivRoomOne1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomOne1.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(0).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(0).getUrl(), 1);
                        }
                    }
                }
            }
        });

        ivRoomOne2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomOne2.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(0).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(0).getUrl(), 2);
                        }
                    }
                }
            }
        });

        ivRoomOne3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomOne3.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(0).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(0).getUrl(), 3);
                        }
                    }
                }
            }
        });

        ivRoomTwo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomTwo1.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(1).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(1).getUrl(), 1);
                        }
                    }
                }
            }
        });

        ivRoomTwo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomTwo2.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(1).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(1).getUrl(), 2);
                        }
                    }
                }
            }
        });

        ivRoomTwo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomTwo3.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(1).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(1).getUrl(), 3);
                        }
                    }
                }
            }
        });

        ivRoomThree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomThree1.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(2).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(2).getUrl(), 1);
                        }
                    }
                }
            }
        });

        ivRoomThree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomThree2.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(2).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(2).getUrl(), 2);
                        }
                    }
                }
            }
        });

        ivRoomThree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomThree3.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(2).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(2).getUrl(), 3);
                        }
                    }
                }
            }
        });

        ivRoomFour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomFour1.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(3).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(3).getUrl(), 1);
                        }
                    }
                }
            }
        });


        ivRoomFour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomFour2.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(3).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(3).getUrl(), 2);
                        }
                    }
                }
            }
        });

        ivRoomFour3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomFour3.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(3).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(3).getUrl(), 3);
                        }
                    }
                }
            }
        });

        ivRoomFive1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomFive1.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(4).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(4).getUrl(), 1);
                        }
                    }
                }
            }
        });

        ivRoomFive2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomFive2.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(4).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(4).getUrl(), 2);
                        }
                    }
                }
            }
        });

        ivRoomFive3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomFive3.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(4).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(4).getUrl(), 3);
                        }
                    }
                }
            }
        });

        ivRoomSex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomSex1.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(5).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(5).getUrl(), 1);
                        }
                    }
                }
            }
        });

        ivRoomSex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomSex2.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(5).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(5).getUrl(), 2);
                        }
                    }
                }
            }
        });

        ivRoomSex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != ivRoomSex3.getDrawable()) {
                    if (isUpData) {
                        if (data.getTypeList().get(5).getUrl().length() > 0) {
                            ImagViewTouchType(data.getTypeList().get(5).getUrl(), 3);
                        }
                    }
                }
            }
        });
    }

    /**
     * 点击图片跳转缩放效果
     *
     * @param path
     * @param type
     * @param mun
     */
    private void ImagViewTouch(String path, int type, int mun) {
        String[] split = path.split(",");
        Intent intent = new Intent(TalkDetailActivity.this, ImageTouchActivity.class);
        if (type == Constants.OUTSIDE1) {
            if (mun == 1) {
                intent.putExtra("image", split[0]);
            } else if (mun == 2) {
                intent.putExtra("image", split[1]);
            } else {
                intent.putExtra("image", split[2]);
            }
        } else if (type == Constants.OUTSIDE2) {
            if (mun == 1) {
                intent.putExtra("image", split[0]);
            } else if (mun == 2) {
                intent.putExtra("image", split[1]);
            } else {
                intent.putExtra("image", split[2]);
            }
        } else {
            if (mun == 1) {
                intent.putExtra("image", split[0]);
            } else if (mun == 2) {
                intent.putExtra("image", split[1]);
            } else {
                intent.putExtra("image", split[2]);
            }
        }
        startActivity(intent);
    }

    private void ImagViewTouchType(String path, int mun) {
        String[] split = path.split(",");
        Intent intent = new Intent(TalkDetailActivity.this, ImageTouchActivity.class);
        if (mun == 1) {
            intent.putExtra("image", split[0]);
        } else if (mun == 2) {
            intent.putExtra("image", split[1]);
        } else {
            intent.putExtra("image", split[2]);
        }
        startActivity(intent);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            setResult(Constants.TEL_DETAIL);
            String name = platform.getName();
            if (name != null && !name.equals("")) {
                if (name.equals("wxsession")) {
                    ToastUtils.ToastCenter("分享成功");
                    TalkDetailActivity.this.finish();
                } else {
                    ToastUtils.ToastCenter("请分享到微信群");
                }
            }
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.ToastCenter("" + t.getMessage().toString());
            setResult(Constants.TEL_DETAIL);
            TalkDetailActivity.this.finish();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.ToastCenter("分享取消");
            setResult(Constants.TEL_DETAIL);
            TalkDetailActivity.this.finish();
        }

    };

    /**
     * 压缩图片
     *
     * @param mList
     * @param type
     * @param imageView
     */
    private void zipImage(final ArrayList<String> mList, final int type, final ImageView imageView,Uri imageUri) {
        imageView.setVisibility(View.VISIBLE);
        final String absolutePath = getExternalCacheDir().getAbsolutePath();
        Luban.with(TalkDetailActivity.this)
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
                    public void onSuccess(File files) {
                        //删除旧的图片
                        deleteOriImg(outputImage);
                        final String picPaths = files.getAbsolutePath();
                        final File file1 = new File(picPaths);
                        Glide.with(TalkDetailActivity.this).load(file1).into(imageView);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (isUpData) {
                                    upDataLoadPhoto(Constants.HOST + Constants.upLoadPhotos, picPaths, file1, mList, type);
                                    return;
                                }
                                upLoadPhoto(Constants.HOST + Constants.upLoadPhotos, picPaths, file1, mList, type);
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
     * 加载回来的数据
     *
     * @param url
     * @param type
     * @param id
     */
    private void initShowData(final String url, final String type, final String id) {
        isUpData = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Call idTalkData = OkHttp3Utils.getIdTalkData(url, type, id);
                idTalkData.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        data = new Gson().fromJson(response.body().string(), GetIdJmBusubess.class).getData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                talk_take_photos_three.setVisibility(View.GONE);
                                ivImageOne.setVisibility(View.GONE);
                                ivImageTwo.setVisibility(View.GONE);
                                ivImageThree.setVisibility(View.GONE);
                                ivImageFour.setVisibility(View.GONE);
                                ivImageFive.setVisibility(View.GONE);
                                ivImageSex.setVisibility(View.GONE);
                                //装数据
                                if (!data.getRoomPhoto().equals("")){
                                    linTalkDetailContract.setVisibility(View.VISIBLE);
                                    tvTalkDetailContract.setText("查看合同");
                                }
                                tv_development_select.setText(data.getUserName());
                                ed_shop_select.setText(data.getShopName());
                                String longitude = (String) data.getLongitude();
                                String latitude = (String) data.getLatitude();
                                // LngLat lngLat=new LngLat((Double) ,(Double) data.getLatitude());
//                                mTvTalkLongitude.setText(CoodinateCovertor.bd_decrypt(lngLat).getLongitude() + "");
//                                mTvTalkLatitude.setText(CoodinateCovertor.bd_decrypt(lngLat).getLantitude() + "");
                                mTvTalkLongitude.setText(longitude);
                                mTvTalkLatitude.setText(latitude);
                                tv_sub_select.setText(data.getStationName());
                                ed_sub_distance_select.setText(data.getStationDistance() + "");
                                tv_administrative_division_select.setText(data.getAreaName());
                                tv_talk_time_select.setText(data.getReturnTime());
                                ed_area_select.setText(data.getAddress());
                                if (data.getCooperationMode() == 0) {
                                    tv_cooperation_mode_select.setText("直营");
                                    tv_floor_select.setText(data.getTotalFloor() + "");
                                    tv_intention_floor_select.setText(data.getCooperationFloor() + "");
                                    ed_room_select.setText(data.getTotalRoom() + "");
                                    ed_intention_room_select.setText(data.getCooperationRoom() + "");
                                } else {
                                    tv_cooperation_mode_select.setText("平台管理");
                                    tv_floor_select.setText(data.getTotalFloor() + "");
                                    ed_room_select.setText(data.getTotalRoom() + "");
                                    hezuo.setVisibility(View.GONE);
                                    hezuo1.setVisibility(View.GONE);
                                }

                                //物业负责人
                                List<GetIdJmBusubess.DataBean.ManagerListBean> managerList = data.getManagerList();
                                if (managerList.size() > 0) {
                                    int size = managerList.size();
                                    if (size < 2) {
                                        mPropertyPeople.setText(managerList.get(0).getName());
                                        mTvTelDetailVisit.setText(managerList.get(0).getTel());
                                    } else if (size < 3) {
                                        mPeopleFirst.setVisibility(View.VISIBLE);
                                        mPropertyPeople.setText(managerList.get(0).getName());
                                        mTvTelDetailVisit.setText(managerList.get(0).getTel());
                                        mPropertyPeopleFirst.setText(managerList.get(1).getName());
                                        mTvTelDetailVisitFirst.setText(managerList.get(1).getTel());
                                    } else {
                                        mPeopleSecond.setVisibility(View.VISIBLE);
                                        mPeopleFirst.setVisibility(View.VISIBLE);
                                        mPropertyPeople.setText(managerList.get(0).getName());
                                        mTvTelDetailVisit.setText(managerList.get(0).getTel());
                                        mPropertyPeopleFirst.setText(managerList.get(1).getName());
                                        mTvTelDetailVisitFirst.setText(managerList.get(1).getTel());
                                        mPropertyPeopleSecond.setText(managerList.get(2).getName());
                                        mTvTelDetailVisitSencond.setText(managerList.get(2).getTel());
                                    }
                                }
                                ed_talk_shining.setText(data.getCasement() + "");
                                ed_talk_midnight.setText(data.getCorridor() + "");
                                ed_talk_price.setText(data.getPrice());
                                //意向
                                int cooperationIntention = data.getCooperationIntention();
                                if (cooperationIntention == 0) {
                                    tv_intention_select.setText("跟进");
                                } else if (cooperationIntention == 1) {
                                    tv_intention_select.setText("pass");
                                } else {
                                    tv_intention_select.setText("签约");
                                }

//                                //外围
                                if (data.getSettingPhoto().length() > 0) {
                                    String[] split = data.getSettingPhoto().split(",");
                                    if (split.length == Constants.OUTSIDE1) {
                                        OkHttp3Utils.setIamge(Constants.HOST + split[0], iv_outside_one, getApplicationContext());
                                    } else if (split.length == Constants.OUTSIDE2) {
                                        OkHttp3Utils.setIamge(Constants.HOST + split[0], iv_outside_one, getApplicationContext());
                                        OkHttp3Utils.setIamge(Constants.HOST + split[1], iv_outside_two, getApplicationContext());
                                    } else {
                                        OkHttp3Utils.setIamge(Constants.HOST + split[0], iv_outside_one, getApplicationContext());
                                        OkHttp3Utils.setIamge(Constants.HOST + split[1], iv_outside_two, getApplicationContext());
                                        OkHttp3Utils.setIamge(Constants.HOST + split[2], iv_outside_three, getApplicationContext());
                                    }
                                }
//                                //公共
                                if (data.getParkPhoto().length() > 0) {
                                    String[] split = data.getParkPhoto().split(",");
                                    if (split.length == Constants.OUTSIDE1) {
                                        OkHttp3Utils.setIamge(Constants.HOST + split[0], iv_pblic_one, getApplicationContext());
                                    } else if (split.length == Constants.OUTSIDE2) {
                                        OkHttp3Utils.setIamge(Constants.HOST + split[0], iv_pblic_one, getApplicationContext());
                                        OkHttp3Utils.setIamge(Constants.HOST + split[1], iv_pblic_two, getApplicationContext());
                                    } else {
                                        OkHttp3Utils.setIamge(Constants.HOST + split[0], iv_pblic_one, getApplicationContext());
                                        OkHttp3Utils.setIamge(Constants.HOST + split[1], iv_pblic_two, getApplicationContext());
                                        OkHttp3Utils.setIamge(Constants.HOST + split[2], iv_pblic_three, getApplicationContext());
                                    }
                                }

                                /**
                                 * 房型
                                 */
                                if (data.getTypeList().size() > 0) {
                                    if (data.getTypeList().size() < 2) {
                                        //第一个
                                        initTypeOne();

                                    }
                                    if (data.getTypeList().size() > 1 && data.getTypeList().size() < 3) {
                                        //第二个
                                        typeOne.setVisibility(View.VISIBLE);
                                        ivImageTwo.setEnabled(false);
                                        initTypeOne();
                                        initTypeTwo();

                                    }

                                    if (data.getTypeList().size() > 2 && data.getTypeList().size() < 4) {
                                        //第三个
                                        typeOne.setVisibility(View.VISIBLE);
                                        typeTwo.setVisibility(View.VISIBLE);
                                        ivImageThree.setEnabled(false);
                                        initTypeOne();
                                        initTypeTwo();
                                        initTypeThree();

                                    }

                                    if (data.getTypeList().size() > 3 && data.getTypeList().size() < 5) {
                                        //第四个
                                        typeOne.setVisibility(View.VISIBLE);
                                        typeTwo.setVisibility(View.VISIBLE);
                                        typeThree.setVisibility(View.VISIBLE);
                                        ivImageFour.setEnabled(false);
                                        initTypeOne();
                                        initTypeTwo();
                                        initTypeThree();
                                        initTypeFour();
                                    }

                                    if (data.getTypeList().size() > 4 && data.getTypeList().size() < 6) {
                                        //第五个
                                        typeOne.setVisibility(View.VISIBLE);
                                        typeTwo.setVisibility(View.VISIBLE);
                                        typeThree.setVisibility(View.VISIBLE);
                                        typeFour.setVisibility(View.VISIBLE);
                                        ivImageFive.setEnabled(false);
                                        initTypeOne();
                                        initTypeTwo();
                                        initTypeThree();
                                        initTypeFour();
                                        initTypeFive();
                                    }
                                    if (data.getTypeList().size() > 5 && data.getTypeList().size() < 7) {
                                        typeOne.setVisibility(View.VISIBLE);
                                        typeTwo.setVisibility(View.VISIBLE);
                                        typeThree.setVisibility(View.VISIBLE);
                                        typeFive.setVisibility(View.VISIBLE);
                                        ivImageSex.setEnabled(false);
                                        initTypeOne();
                                        initTypeTwo();
                                        initTypeThree();
                                        initTypeFour();
                                        initTypeFive();
                                        initTypeSex();
                                    }
                                }

                                //备注
                                List<GetIdJmBusubess.DataBean.RemarkListBean> remarkList = data.getRemarkList();
                                if (remarkList.size() > 0) {
                                    for (int i = 0; i < remarkList.size(); i++) {
                                        //  ll_tel_talk_remark.removeAllViews();
                                        TextView textView = new TextView(SampleApplicationContext.context);
                                        textView.setTextSize(14);
                                        textView.setPadding(25, 10, 20, 10);
                                        mBuilder.clear();
                                        String s = remarkList.get(i).getContent() + " 。";
                                        mBuilder.append((i + 1) + ". ");
                                        if (remarkList.get(i).getType() == 0) {
                                            getTypes = "自定义回访";
                                        } else if (remarkList.get(i).getType() == 1) {
                                            getTypes = "进店回访";
                                        } else {
                                            getTypes = "电话回访";
                                        }
                                        mBuilder.append(getTypes + "：");
                                        mBuilder.append(s);
                                        mBuilder.append(remarkList.get(i).getCreateTime());
                                        mBuilder.setSpan(mSpan, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                        textView.setTextColor(getResources().getColor(R.color.text_color_666));
                                        textView.setText(mBuilder);
                                        ll_tel_talk_remark.addView(textView);
                                    }
                                }
                            }
                        });
                        LoadingDialogUtils.closeDialog(loadingDialog);
                    }
                });
            }
        }, 800);
    }

    /**
     * 保存以及分享
     */
    private void save() {
        isDelet = false;
        if (isUpData) {
            //更新
            if (null != data) {
                jmRoomOrigin.setId(data.getId());
                //更新地铁id
                if (data.getUserName().equals(tv_development_select.getText().toString())) {
                    jmRoomOrigin.setUserId(data.getUserId());
                }
                //更新地铁id
                if (data.getStationName().equals(tv_sub_select.getText().toString())) {
                    jmRoomOrigin.setStationId(data.getStationId());
                }
                //更新区域id
                if (data.getAreaName().equals(tv_administrative_division_select.getText().toString())) {
                    jmRoomOrigin.setAreaId(data.getAreaId());
                }
                //更新总房量id
                if (data.getTotalFloor() == Integer.parseInt(tv_floor_select.getText().toString())) {
                    jmRoomOrigin.setTotalFloor(data.getTotalFloor());
                }
                //更新意向合作层


                //合作模式
                String s = tv_cooperation_mode_select.getText().toString();
                if (s.equals("平台管理")) {
                    mode = 1;
                    jmRoomOrigin.setCooperationFloor(0);
                } else {
                    mode = 0;
                    jmRoomOrigin.setCooperationFloor(Integer.parseInt(tv_intention_floor_select.getText().toString()));
                }
                if (data.getCooperationMode() == mode) {
                    jmRoomOrigin.setCooperationMode(data.getCooperationMode());
                }
                config(Constants.HOST + Constants.upLoadTalkUPData, Constants.UPDATA);
            }
        } else {
            jmRoomOrigin.setIsCall(0);
            config(Constants.HOST + Constants.upLoadTalkData, Constants.ADDDATA);
        }
    }

    /**
     * 备注
     */
    private void remark() {
        mRemarkPop = PopUtils.initAddTalkRemark(SampleApplicationContext.context, this, new PopUtils.RemarkTalkListener() {
            @Override
            public void addTalkRemark(String remark, int tag, String type) {
                if (null == data) {
                    mJmRoomRemark.add(new JmRoomRemark(remark, tag));
                    jmRoomOrigin.setRemarkList(mJmRoomRemark);
                    mRemarkPop.dismiss();
                    if (mJmRoomRemark != null && !mJmRoomRemark.isEmpty()) {
                        ll_tel_talk_remark.removeAllViews();
                        for (int i = 0; i < mJmRoomRemark.size(); i++) {
                            TextView textView = new TextView(SampleApplicationContext.context);
                            textView.setTextSize(14);
                            textView.setPadding(25, 10, 20, 10);
                            mBuilder.clear();
                            String s = mJmRoomRemark.get(i).getContent() + " 。";
                            mBuilder.append((i + 1) + ". ");
                            if (mJmRoomRemark.get(i).getType() == 0) {
                                getTypes = "自定义回访";
                            } else if (mJmRoomRemark.get(i).getType() == 1) {
                                getTypes = "进店回访";
                            } else {
                                getTypes = "电话回访";
                            }
                            mBuilder.append(getTypes + "：");
                            mBuilder.append(s);
                            //获取当前时间
                            mBuilder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())));
                            mBuilder.setSpan(mSpan, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                            textView.setTextColor(getResources().getColor(R.color.text_color_666));
                            textView.setText(mBuilder);
                            ll_tel_talk_remark.addView(textView);
                        }
                    }
                } else {
                    for (int i = 0; i < data.getRemarkList().size(); i++) {
                        mJmRoomRemark.add(new JmRoomRemark(data.getRemarkList().get(i).getContent(), data.getRemarkList().get(i).getType()));
                    }
                    /**
                     * 清理集合
                     */
                    data.setRemarkList(new ArrayList<GetIdJmBusubess.DataBean.RemarkListBean>());
                    mJmRoomRemark.add(new JmRoomRemark(remark, tag));
                    jmRoomOrigin.setRemarkList(mJmRoomRemark);
                    mRemarkPop.dismiss();
                    if (mJmRoomRemark != null && !mJmRoomRemark.isEmpty()) {
                        ll_tel_talk_remark.removeAllViews();
                        for (int i = 0; i < mJmRoomRemark.size(); i++) {
                            TextView textView = new TextView(SampleApplicationContext.context);
                            textView.setTextSize(14);
                            textView.setPadding(25, 10, 20, 10);
                            mBuilder.clear();
                            String s = mJmRoomRemark.get(i).getContent() + " 。";
                            mBuilder.append((i + 1) + ". ");
                            if (mJmRoomRemark.get(i).getType() == 0) {
                                getTypes = "自定义回访";
                            } else if (mJmRoomRemark.get(i).getType() == 1) {
                                getTypes = "进店回访";
                            } else {
                                getTypes = "电话回访";
                            }
                            mBuilder.append(getTypes + "：");
                            mBuilder.append(s);
                            //获取当前时间
                            mBuilder.append(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())));
                            mBuilder.setSpan(mSpan, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                            textView.setTextColor(getResources().getColor(R.color.text_color_666));
                            textView.setText(mBuilder);
                            ll_tel_talk_remark.addView(textView);
                        }
                    }
                }
            }
        });
        if (mRemarkPop != null && !mRemarkPop.isShowing()) {
            mRemarkPop.showAtLocation(tv_tale_add_remark, Gravity.CENTER, 0, 0);
            CommonUtils.setBackgroundAlpha(this, 0.6f);
        }
    }

    /**
     * 物业负责人
     */
    private void property() {
        if (!mPropertyPeople.getText().toString().equals("") && !mTvTelDetailVisit.getText().toString().equals("")) {
            mPeopleFirst.setVisibility(View.VISIBLE);
            mPeopleFirstTag = 1;
        } else {
            Toast.makeText(this, "请添加联系人", Toast.LENGTH_SHORT).show();
            mPeopleFirstTag = 0;
        }

        if (!mPropertyPeopleFirst.getText().toString().equals("") && !mTvTelDetailVisitFirst.getText().toString().equals("")) {
            mPeopleSecond.setVisibility(View.VISIBLE);
            mPeopleSecondTag = 2;
        } else {
            Toast.makeText(this, "请添加联系人", Toast.LENGTH_SHORT).show();
            mPeopleSecondTag = 0;
        }
    }

    private void initTypeOne() {
        ed_room_one.setText("房间类型：" + data.getTypeList().get(0).getContent());
        String[] split = data.getTypeList().get(0).getUrl().split(",");
        if (split.length == Constants.OUTSIDE1) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomOne1, getApplicationContext());
        } else if (split.length == Constants.OUTSIDE2) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomOne1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomOne2, getApplicationContext());
        } else {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomOne1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomOne2, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[2], ivRoomOne3, getApplicationContext());
        }
    }

    private void initTypeTwo() {
        ed_room_two.setText("房间类型：" + data.getTypeList().get(1).getContent());
        String[] split = data.getTypeList().get(1).getUrl().split(",");
        if (split.length == Constants.OUTSIDE1) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomTwo1, getApplicationContext());
        } else if (split.length == Constants.OUTSIDE2) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomTwo1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomTwo2, getApplicationContext());
        } else {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomTwo1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomTwo2, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[2], ivRoomTwo3, getApplicationContext());
        }
    }

    private void initTypeThree() {
        ed_room_three.setText("房间类型：" + data.getTypeList().get(2).getContent());
        String[] split = data.getTypeList().get(2).getUrl().split(",");
        if (split.length == Constants.OUTSIDE1) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomThree1, getApplicationContext());
        } else if (split.length == Constants.OUTSIDE2) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomThree1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomThree2, getApplicationContext());
        } else {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomThree1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomThree2, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[2], ivRoomThree3, getApplicationContext());
        }
    }

    private void initTypeFour() {
        ed_room_four.setText("房间类型：" + data.getTypeList().get(3).getContent());
        String[] split = data.getTypeList().get(3).getUrl().split(",");
        if (split.length == Constants.OUTSIDE1) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomFour1, getApplicationContext());
        } else if (split.length == Constants.OUTSIDE2) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomFour1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomFour2, getApplicationContext());
        } else {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomFour1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomFour2, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[2], ivRoomFour3, getApplicationContext());
        }
    }

    private void initTypeFive() {
        ed_room_five.setText("房间类型：" + data.getTypeList().get(4).getContent());
        String[] split = data.getTypeList().get(4).getUrl().split(",");
        if (split.length == Constants.OUTSIDE1) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomFive1, getApplicationContext());
        } else if (split.length == Constants.OUTSIDE2) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomFive1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomFive2, getApplicationContext());
        } else {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomFive1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomFive2, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[2], ivRoomFive3, getApplicationContext());
        }
    }

    private void initTypeSex() {
        ed_room_sex.setText("房间类型：" + data.getTypeList().get(5).getContent());
        String[] split = data.getTypeList().get(5).getUrl().split(",");
        if (split.length == Constants.OUTSIDE1) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomSex1, getApplicationContext());
        } else if (split.length == Constants.OUTSIDE2) {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomSex1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomSex2, getApplicationContext());
        } else {
            OkHttp3Utils.setIamge(Constants.HOST + split[0], ivRoomSex1, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[1], ivRoomSex2, getApplicationContext());
            OkHttp3Utils.setIamge(Constants.HOST + split[2], ivRoomSex3, getApplicationContext());
        }
    }

    /**
     * 点击添加按钮（房型）
     */
    private void takeTypePoto() {
        if (null != ivRoomOne1.getDrawable()) {
            typeOne.setVisibility(View.VISIBLE);
        }
        if (null != ivRoomTwo1.getDrawable()) {
            typeTwo.setVisibility(View.VISIBLE);
        }
        if (null != ivRoomThree1.getDrawable()) {
            typeThree.setVisibility(View.VISIBLE);
        }
        if (null != ivRoomFour1.getDrawable()) {
            typeFour.setVisibility(View.VISIBLE);
        }
        if (null != ivRoomFive1.getDrawable()) {
            typeFive.setVisibility(View.VISIBLE);
        }
        if (null != ivRoomSex1.getDrawable()) {
            Toast.makeText(this, "最多添加6种房型", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从相册选取照片
     *
     * @param imageUris
     * @param imageView
     */
    private void pickImage(final ArrayList<String> mList, Uri imageUris, ImageView imageView, final int type) {
        String[] arr = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(imageUris, arr, null, null, null);
        int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        //创建File对象，用于存储拍照后的图片
        absolutePath = getExternalCacheDir().getAbsolutePath() + System.currentTimeMillis() + ".jpg";
        copyFile(cursor.getString(img_index),absolutePath);
        outputImage= new File(absolutePath);
      //  Uri imageUri=Uri.parse(aaa.toString());
        zipImage(mList,type,imageView,imageUri);

    }

    /**
     * 删图片
     *
     * @param uri
     * @param photoUri
     * @param imageView
     */
    private void initDialog(final String uri, final String photoUri, final ImageView imageView, final String path, final int index, final int photoType) {
        AlertDialog alertDialog = new AlertDialog.Builder(TalkDetailActivity.this)
                .setTitle("温馨提示：")
                .setMessage("是否删除此张照片?")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isDelet = true;
                        //进行网络请求删图片
                        deletPhotos(uri, photoUri, imageView, path, index, photoType);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TalkDetailActivity.this, "已取消删除", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 长按删除图片
     * 数据id
     * 删除uri
     * imageview控件
     */
    private void deletPhotos(String uri, String photoUri, ImageView imageView, String path, int index, int photoType) {
        String[] split = path.split(",");
        String str = "";
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (!s.equals(split[index])) {
                str += split[i] + ",";
            }
        }
        imageView.setVisibility(View.GONE);
        if (photoType == 1) {
            jmRoomOrigin.setSettingPhoto(str);
        } else if (photoType == 2) {
            jmRoomOrigin.setParkPhoto(str);
        } else {
        }

        OkHttp3Utils.getIdTalkData(uri, "url", photoUri).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TalkDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 返回按钮监听
     */
    @Override
    public void onBackPressed() {
        if (isDelet) {
            ToastUtils.ToastCenter("请先保存数据");
        } else {
            TalkDetailActivity.this.finish();
        }
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
}
