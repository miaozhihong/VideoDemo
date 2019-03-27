package com.jm.mvp.ui.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.RemarkBean;
import com.jm.bean.TelDetailBean;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.bean.UpTelDetailBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.TelDetailContract;
import com.jm.mvp.presenter.TelDetailPresenter;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.jm.service.NotificationMonitorService;
import com.jm.utils.CommonUtils;
import com.jm.utils.PickerUtils;
import com.jm.utils.PopUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * @author pc 张立男
 * @Description TelDetailActivity 电话咨询详情页面
 * @date 2018/2/26 15:23
 * o(＞﹏＜)o
 */
public class TelDetailActivity extends BaseActivity<TelDetailPresenter> implements TelDetailContract.View {
    int size = 0;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_tel_detail_time)
    TextView mTvTelDetailTime;
    @BindView(R.id.et_tel_detail_tel)
    TextView mEtTelDetailTel;
    @BindView(R.id.tv_tel_detail_shop)
    TextView mTvTelDetailShop;
    @BindView(R.id.tv_tel_detail_ad)
    TextView mTvTelDetailAd;
    @BindView(R.id.tv_tel_detail_subway)
    TextView mTvTelDetailSubway;
    @BindView(R.id.tv_tel_detail_wx)
    TextView mTvTelDetailWx;
    @BindView(R.id.tv_tel_detail_visit)
    TextView mTvTelDetailVisit;
    @BindView(R.id.tv_tel_detail_come)
    TextView mTvTelDetailCome;
    @BindView(R.id.tv_tel_detail_push)
    TextView mTvTelDetailPush;
    @BindView(R.id.tv_tel_detail_time_intention)
    TextView mTvTelDetailTimeIntention;
    @BindView(R.id.iv_title_second)
    ImageView mIvTitleSecond;
    @BindView(R.id.tv_tel_detail_add)
    TextView mTvTelDetailAdd;
    @BindView(R.id.ll_tel_detail_remark)
    LinearLayout mLlTelDetailRemark;
    @BindView(R.id.tv_tel_detail_confirm)
    TextView mTvTelDetailConfirm;
    @BindView(R.id.ll_tel_detail_time_room)
    LinearLayout mLlRoom;
    @BindView(R.id.et_tel_detail_time_room)
    EditText mEtRoom;
    @BindView(R.id.et_tel_detail_code)
    EditText mEtCode;
    @BindView(R.id.tv_tel_detail_source)
    TextView tv_tel_detail_source;
    private OptionPicker mAdPicker;
    private OptionPicker mWxPicker;
    private DateTimePicker mVisitPicker;
    private DateTimePicker mComePicker;
    private OptionPicker mIntentionPicker;
    private MyPopupWindow mRemarkPop;

    public static  String DEFAULT = "请选择";
    private TelDetailPresenter mPresenter;
    private OptionPicker mPushPicker;
    private OptionPicker mShopPicker;
    private UpTelDetailBean mBean;
    private int mShopId;
    private String mShareUrl;
    private TelDetailBean.DataBean.DetailInfoBean bean;
    private String substring;
    int id = 0;
    int i = 0;
    private int color;
    private String endText;
    private int tels;
    private boolean Tag=false;
    private  DaoSession  daoSession;
    private  TitlesDao titlesDao;
    private boolean isEnable=false;
    private boolean isRemark=false;
    //进行备注做限制
    private String shopName;//店名
    private String advertisement;//广告渠道
    private String subway;//地铁站
    private String revisitdays;//咨询回访
    private String appointmenttime;//预约进店时间
    private String push;//推送
    private String intentions;//意向
    @Override
    public int initView(Bundle savedInstanceState) {
        mPresenter = new TelDetailPresenter(this);
        //未读数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "jm_notread.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        titlesDao = daoSession.getTitlesDao();
        return R.layout.activity_tel_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTvTitle.setText("电话咨询详情");
        mIvTitleSecond.setVisibility(View.VISIBLE);
        mIvTitleSecond.setImageResource(R.mipmap.share);
        // 备注的颜色 color
        mBean = new UpTelDetailBean();
        mSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_color_333));
        mShopId = getIntent().getIntExtra(Constants.SHOP_ID, -1);
        tels = getIntent().getIntExtra("tels", 1);
        color = getIntent().getIntExtra("color", 1);
        // 设置随机码
        String code = String.valueOf(System.currentTimeMillis());
        mEtCode.setText(code.substring(2, code.length()));
        //添加备注选择框
        mRemarkPop = PopUtils.initAddRemark(SampleApplicationContext.context, this,
                new PopUtils.RemarkListener() {
                    @Override
                    public void addRemark(String remark) {
                        mPresenter.addRemark(String.valueOf(mShopId), remark);
                    }
                });

        //微信好友
        mWxPicker = PickerUtils.initSinglePicker(this, Constants.YES_OR_NO, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvTelDetailWx.setText(item);
                // 0 否 1 是
                mBean.setIsWechat(index);
            }
        });

        //咨询回访时间
        mVisitPicker = PickerUtils.initDatePicker(this);
        mVisitPicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                mTvTelDetailVisit.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                mBean.setConsultReturnTimeStr(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        //预约进店时间
        mComePicker = PickerUtils.initDatePicker(this);
        mComePicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                mTvTelDetailCome.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                mBean.setAppointmentEnterTimeStr(year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
        //顾客意向
        mIntentionPicker = PickerUtils.initSinglePicker(this, Constants.INTENTION, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvTelDetailTimeIntention.setText(item);
                if (index == 4) {
                    mBean.setClientIntention(6);
                } else {
                    mBean.setClientIntention(index + 1);
                }
                if (index == 2) {
                    mLlRoom.setVisibility(View.VISIBLE);
                } else {
                    mLlRoom.setVisibility(View.GONE);
                }
            }
        });

        mPresenter.netAndData(String.valueOf(mShopId));
        // TODO: 2018/6/23 当有分享地址时，替换即可
        http:
            //192.168.31.116:8080/app/enjoy/consult/2480/consultDetail
        mShareUrl = "http://xs.zomeroom.com:8080/app/enjoy/consult/" + mShopId + "/consultDetail";
    }

    @Override
    public TelDetailPresenter obtainPresenter() {
        return mPresenter;
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
            Tag=true;
            String name = platform.getName();
            if (name != null && !name.equals("")) {
                if (name.equals("wxsession")) {
                    i = 1;
                    ToastUtils.ToastCenter("分享成功");
                    TelDetailActivity.this.finish();
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
            TelDetailActivity.this.finish();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.ToastCenter("分享取消");
            setResult(Constants.TEL_DETAIL);
            TelDetailActivity.this.finish();
        }
    };

    @OnClick({R.id.iv_title_back, R.id.tv_tel_detail_shop, R.id.tv_tel_detail_ad,
            R.id.tv_tel_detail_subway, R.id.tv_tel_detail_wx, R.id.tv_tel_detail_visit,
            R.id.tv_tel_detail_come, R.id.tv_tel_detail_push, R.id.tv_tel_detail_time_intention,
            R.id.tv_tel_detail_add, R.id.tv_tel_detail_confirm, R.id.iv_title_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                if (isEnable==true){
                    String s4_tag = mTvTelDetailTimeIntention.getText().toString();
                    if (null==s4_tag || s4_tag.equals(" ") || s4_tag.equals("与销售无关")|| s4_tag.equals("转进店")){
                        TelDetailActivity.this.finish();
                    }else {
                        if (Tag==false){
                            Toast.makeText(this, "请进行分享此条数据", Toast.LENGTH_SHORT).show();
                        }else {
                            TelDetailActivity.this.finish();
                        }
                    }
                }else {
                    TelDetailActivity.this.finish();
                }


                break;
            //分享
            case R.id.iv_title_second:
                String s4 = mTvTelDetailTimeIntention.getText().toString();
                if (s4.equals("与销售无关") || s4.equals("转进店")) {
                    ToastUtils.ToastBottow("此条数据不能进行分享");
                    if (mBuilder != null && !mBuilder.equals("")) {
                        String s = mBuilder.toString();
                        if (s != null && !s.equals("")) {
                            substring = endText;
                        }
                    }
                } else {
                    share();
                }

                break;
            //分店
            case R.id.tv_tel_detail_shop:
                showPicker(mShopPicker, mTvTelDetailShop);
                break;
            //广告渠道
            case R.id.tv_tel_detail_ad:
                showPicker(mAdPicker, mTvTelDetailAd);
                break;
            //地铁站
            case R.id.tv_tel_detail_subway:
                startActivityForResult(new Intent(this, SubwayActivity.class), Constants.NO_USE);
                break;
            //是否加微信
            case R.id.tv_tel_detail_wx:
                showPicker(mWxPicker, mTvTelDetailWx);
                break;
            //回访时间
            case R.id.tv_tel_detail_visit:
                mVisitPicker.show();
                break;
            //进店时间
            case R.id.tv_tel_detail_come:
                mComePicker.show();
                break;
            //推送其他分店
            case R.id.tv_tel_detail_push:
                showPicker(mPushPicker, mTvTelDetailPush);
                break;
            //顾客意向
            case R.id.tv_tel_detail_time_intention:
                showPicker(mIntentionPicker, mTvTelDetailTimeIntention);
                break;
            //添加备注
            case R.id.tv_tel_detail_add:
                isRemark=true;
                if (mRemarkPop != null && !mRemarkPop.isShowing()) {
                    mRemarkPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                    CommonUtils.setBackgroundAlpha(this, 0.6f);
                }
                break;
            //完成
            case R.id.tv_tel_detail_confirm:
                String intention = mTvTelDetailTimeIntention.getText().toString().trim();
                if (DEFAULT.equals(intention) || TextUtils.isEmpty(intention)) {
                    ToastUtils.ToastCenter("请选择顾客意向");
                    return;
                }
                String tel = mEtTelDetailTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.ToastCenter("请填写手机号");
                    return;
                }
                mBean.setTel(tel);
                if (Constants.INTENTION[3].equals(intention)) {
                    // 与销售无关，只需判断备注
                    TextView childAt = (TextView) mLlTelDetailRemark.getChildAt(0);
                    if (childAt != null && !TextUtils.isEmpty(childAt.getText().toString().trim())) {
                        // 有备注 可以
                        mPresenter.updateData(mBean);
                    } else {
                        // 请填写备注
                        ToastUtils.ToastCenter("请填写备注");
                    }
                } else {
                    if (TextUtils.isEmpty(mBean.getShopName())) {
                        ToastUtils.ToastCenter("请选择分店信息");
                        return;
                    }
                    if (TextUtils.isEmpty(mBean.getChannelName())) {
                        ToastUtils.ToastCenter("请选择广告渠道");
                        return;
                    }
                    if (TextUtils.isEmpty(mBean.getStationName())) {
                        ToastUtils.ToastCenter("请选择地铁站");
                        return;
                    }
                    if (DEFAULT.equals(mTvTelDetailWx.getText().toString().trim()) ||
                            TextUtils.isEmpty(mTvTelDetailWx.getText().toString().trim())) {
                        ToastUtils.ToastCenter("请选择是否为微信好友");
                        return;
                    }
                    if (TextUtils.isEmpty(mBean.getConsultReturnTimeStr()) && TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
                        ToastUtils.ToastCenter("请选至少填写一个时间");
                        return;
                    }
//                    if (TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
//                        ToastUtils.ToastCenter("请选择预约进店时间");
//                        return;
//                    }
                    if (Constants.INTENTION[2].equals(intention)) {
                        String room = CommonUtils.getEditText(mEtRoom);
                        if (TextUtils.isEmpty(room)) {
                            ToastUtils.ToastCenter("请填写房间号码");
                            return;
                        }
                        mBean.setRoomNum(room);
                        //成交时间
                        long timeStamp = System.currentTimeMillis();
                        String str=timeStamp+"";
                        mBean.setTransactionTime(str);
                    }else {
                        mBean.setTransactionTime("0");
                        mBean.setRoomNum(" ");
                    }


                    if (null==mBean.getPushShopName() || mBean.getClientIntention()!=1) {
                    }else {
                        //获取当前时间
                        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
                        Date curDate =  new Date(System.currentTimeMillis());
                        String time =formatter.format(curDate);
                        titlesDao.insert(new Titles(null,NotificationMonitorService.pak_Name,"推送消息","已将"+mBean.getTel()+"这个电话咨询用户推送给"+mBean.getPushShopName(),time,"推送邮件","","否",(String)SPUtils.get("userName",""),mBean.getPushShopName()));
                    }
                    mPresenter.updateData(mBean);
                }
                break;
            default:
                break;
        }
    }


    /**
     * @param picker
     * @param tv
     */
    private void showPicker(OptionPicker picker, TextView tv) {
        if (picker == null) {
            return;
        }
        String choose = tv.getText().toString().trim();
        if (!DEFAULT.equals(choose)) {
            picker.setSelectedItem(choose);
        }
        picker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String subway = data.getStringExtra(Constants.SUBWAY);
            id = data.getIntExtra(Constants.SUBWAY_ID, -1);
            mTvTelDetailSubway.setText(subway);
            mBean.setStationName(subway);
            if (id != -1) {
                mBean.setStationId(id);
            }
        }
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showData(TelDetailBean.DataBean data) {
        // 将返回的数据装入
        bean = data.getDetailInfo();
        // 预约进店时间
        mBean.setAppointmentEnterTimeStr(bean.getAppointmentEnterTimeStr());
        mBean.setChannelId(bean.getChannelId());
        mBean.setChannelName(bean.getChannelName());
        mBean.setNumberSource(bean.getLoginName());
        int intention = bean.getClientIntention();
        mBean.setClientIntention(intention);
        if (mBean.getNumberSource() != null && !mBean.getNumberSource().equals("")) {
            tv_tel_detail_source.setText(mBean.getNumberSource());
        }
        if (intention == 3) {
            mLlRoom.setVisibility(View.VISIBLE);
            mEtRoom.setText(bean.getRoomNum());
        } else {
            mLlRoom.setVisibility(View.GONE);
        }
        // 咨询回访时间
        mBean.setConsultReturnTimeStr(bean.getConsultReturnTimeStr());
        // 创建时间
        mBean.setCreateTimeStr(bean.getCreateTimeStr());
        mBean.setId(bean.getId());
        mBean.setIsCall(bean.getIsCall());
        // 微信好友
        mBean.setIsWechat(bean.getIsWechat());
        // 推送
        mBean.setPushShopId(bean.getPushShopId());
        mBean.setPushShopName(bean.getPushShopName());
        // 分店
        mBean.setShopId(bean.getShopId());
        mBean.setShopName(bean.getShopName());
        mBean.setStationId(bean.getStationId());
        mBean.setStationName(bean.getStationName());
        mBean.setTel(bean.getTel().replace(" ", ""));
        // 数据回写到页面
        mTvTelDetailTime.setText(mBean.getCreateTimeStr());
        mEtTelDetailTel.setText(mBean.getTel().replace(" ", ""));
        if (!TextUtils.isEmpty(mBean.getShopName())) {
            mTvTelDetailShop.setText(mBean.getShopName());
            shopName=mBean.getShopName();
        }
        if (!TextUtils.isEmpty(mBean.getChannelName())) {
            mTvTelDetailAd.setText(mBean.getChannelName());
            advertisement=mBean.getChannelName();
        }
        if (!TextUtils.isEmpty(mBean.getStationName())) {
            mTvTelDetailSubway.setText(mBean.getStationName());
            subway=mBean.getStationName();
        }
        mTvTelDetailWx.setText(Constants.YES_OR_NO[mBean.getIsWechat()]);
        mWxPicker.setSelectedIndex(mBean.getIsWechat());
        if (!TextUtils.isEmpty(mBean.getConsultReturnTimeStr())) {
            mTvTelDetailVisit.setText(mBean.getConsultReturnTimeStr());
            revisitdays=mBean.getConsultReturnTimeStr();
            int[] time = CommonUtils.toIntTime(mBean.getConsultReturnTimeStr());
            mVisitPicker.setSelectedItem(time[0], time[1], time[2], time[3], time[4]);
        }
        if (!TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
            mTvTelDetailCome.setText(mBean.getAppointmentEnterTimeStr());
            appointmenttime=mBean.getAppointmentEnterTimeStr();
            int[] time = CommonUtils.toIntTime(mBean.getAppointmentEnterTimeStr());
            mComePicker.setSelectedItem(time[0], time[1], time[2], time[3], time[4]);
        }
        if (!TextUtils.isEmpty(mBean.getPushShopName())) {
            mTvTelDetailPush.setText(mBean.getPushShopName());
            push=mBean.getPushShopName();
        }
        if (mBean.getClientIntention() != 0) {
            mTvTelDetailTimeIntention.setText(CommonUtils.getIntention(mBean.getClientIntention()));
            intentions=CommonUtils.getIntention(mBean.getClientIntention());
            mIntentionPicker.setSelectedIndex(mBean.getClientIntention() - 1);
        }
        // 广告渠道
        final List<TelDetailBean.DataBean.ChannelListBean> adList = data.getChannelList();
        // 推送门店
        final List<TelDetailBean.DataBean.PushListBean> pushList = data.getPushList();
        // 分店
        final List<TelDetailBean.DataBean.ShopListBean> shopList = data.getShopList();

        String[] adArray = CommonUtils.toStringArray(adList, Constants.AD, Constants.TEL);
        String[] pushArray = CommonUtils.toStringArray(pushList, Constants.PUSH_SHOP, Constants.TEL);
        final String[] shopArray = CommonUtils.toStringArray(shopList, Constants.MINE_SHOP, Constants.TEL);
        // 广告渠道
        mAdPicker = PickerUtils.initSinglePicker(this, adArray, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvTelDetailAd.setText(item);
                mBean.setChannelName(item);
                for (int i = 0; i < adList.size(); i++) {
                    if (item.equals(adList.get(i).getChannelName())) {
                        mBean.setChannelId(adList.get(i).getId());
                        return;
                    }
                }
            }
        });
        // 推送店
        mPushPicker = PickerUtils.initSinglePicker(this, pushArray, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvTelDetailPush.setText(item);
                mBean.setPushShopName(item);
                for (int i = 0; i < pushList.size(); i++) {
                    if (item.equals(pushList.get(i).getShopName())) {
                        mBean.setPushShopId(pushList.get(i).getId());
                        return;
                    }
                }
            }
        });
        // 分店
        mShopPicker = PickerUtils.initSinglePicker(this, shopArray, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvTelDetailShop.setText(item);
                mBean.setShopName(item);
                for (int i = 0; i < shopList.size(); i++) {
                    if (item.equals(shopList.get(i).getShopName())) {
                        mBean.setShopId(shopList.get(i).getId());
                        return;
                    }
                }
            }
        });
        // 是否转进店 0 否 1 是 不可更新更改数据
        if (bean.getIsEnterShop() == 1) {
            mTvTelDetailConfirm.setBackgroundColor(getResources().getColor(R.color.bg_A5A4A4));
            setClick(false);
        } else {
            mTvTelDetailConfirm.setBackgroundColor(getResources().getColor(R.color.bg_26c2fc));
            setClick(true);
        }
    }

    private void setClick(boolean b) {
        mTvTelDetailShop.setClickable(b);
        mEtTelDetailTel.setFocusable(b);
        mEtCode.setFocusable(b);
        mTvTelDetailAd.setClickable(b);
        mTvTelDetailSubway.setClickable(b);
        mTvTelDetailWx.setClickable(b);
        mTvTelDetailVisit.setClickable(b);
        mTvTelDetailCome.setClickable(b);
        mTvTelDetailPush.setClickable(b);
        mTvTelDetailTimeIntention.setClickable(b);
        mTvTelDetailConfirm.setClickable(b);
        mTvTelDetailAdd.setClickable(b);
        mEtRoom.setFocusable(b);
    }

    @Override
    public void refresh() {
        //share();
        isEnable=true;
        String s4 = mTvTelDetailTimeIntention.getText().toString();
        if (s4.equals("与销售无关") || s4.equals("转进店")) {
            ToastUtils.ToastBottow("已保存");
            if (mBuilder != null && !mBuilder.equals("")) {
                String s = mBuilder.toString();
                if (s != null && !s.equals("")) {
                    substring = endText;
                }
            }
        } else {
            //每次修改app录入信息，必须添加备注
             if (null!=shopName  && null!=subway && null!=intentions &&null!=advertisement) {
                 /**
                  * 推送其他分店是否有数据
                  */
                 if ( null==push){
                         //判断咨询时间是否有数据
                         if (null==revisitdays){
                             if (!shopName.equals(mTvTelDetailShop.getText().toString()) ||!advertisement.equals(mTvTelDetailAd.getText().toString()) ||!subway.equals(mTvTelDetailSubway.getText().toString())||!appointmenttime.equals(mTvTelDetailCome.getText().toString()) ||!intentions.equals(mTvTelDetailTimeIntention.getText().toString())){
                                 if (isRemark==false){
                                     Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                 }else {
                                     share();
                                 }
                             }else {
                                 share();
                             }
                         }else {
                             //判断预约进店时间是否有数据
                             if (null==appointmenttime){
                                 if (!shopName.equals(mTvTelDetailShop.getText().toString()) ||!advertisement.equals(mTvTelDetailAd.getText().toString()) ||!subway.equals(mTvTelDetailSubway.getText().toString())||!revisitdays.equals(mTvTelDetailVisit.getText().toString()) ||!intentions.equals(mTvTelDetailTimeIntention.getText().toString())){
                                     if (isRemark==false){
                                         Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                     }else {
                                         share();
                                     }
                                 }else {
                                     share();
                                 }
                             }else {
                                 if (!shopName.equals(mTvTelDetailShop.getText().toString()) ||!advertisement.equals(mTvTelDetailAd.getText().toString()) ||!subway.equals(mTvTelDetailSubway.getText().toString())||!revisitdays.equals(mTvTelDetailVisit.getText().toString()) ||!appointmenttime.equals(mTvTelDetailCome.getText().toString()) ||!intentions.equals(mTvTelDetailTimeIntention.getText().toString())){
                                     if (isRemark==false){
                                         Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                     }else {
                                         share();
                                     }
                                 }else {
                                     share();
                                 }
                             }
                         }
                 }else {
                     //判断咨询时间是否有数据
                     if (null==revisitdays){
                         if (!shopName.equals(mTvTelDetailShop.getText().toString()) ||!advertisement.equals(mTvTelDetailAd.getText().toString()) ||!subway.equals(mTvTelDetailSubway.getText().toString())||!appointmenttime.equals(mTvTelDetailCome.getText().toString()) ||!push.equals(mTvTelDetailPush.getText().toString())||!intentions.equals(mTvTelDetailTimeIntention.getText().toString())){
                             if (isRemark==false){
                                 Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                             }else {
                                 share();
                             }
                         }else {
                             share();
                         }
                     }else {
                         //判断预约进店时间是否有数据
                         if (null==appointmenttime){
                             if (!shopName.equals(mTvTelDetailShop.getText().toString()) ||!advertisement.equals(mTvTelDetailAd.getText().toString()) ||!subway.equals(mTvTelDetailSubway.getText().toString())||!revisitdays.equals(mTvTelDetailVisit.getText().toString()) ||!push.equals(mTvTelDetailPush.getText().toString())||!intentions.equals(mTvTelDetailTimeIntention.getText().toString())){
                                 if (isRemark==false){
                                     Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                 }else {
                                     share();
                                 }
                             }else {
                                 share();
                             }
                         }else {
                             if (!shopName.equals(mTvTelDetailShop.getText().toString()) ||!advertisement.equals(mTvTelDetailAd.getText().toString()) ||!subway.equals(mTvTelDetailSubway.getText().toString())||!revisitdays.equals(mTvTelDetailVisit.getText().toString()) ||!appointmenttime.equals(mTvTelDetailCome.getText().toString()) ||!push.equals(mTvTelDetailPush.getText().toString())||!intentions.equals(mTvTelDetailTimeIntention.getText().toString())){
                                 if (isRemark==false){
                                     Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                 }else {
                                     share();
                                 }
                             }else {
                                 share();
                             }
                         }
                     }
                 }
             } else {
                 share();
            }
        }
    }

    private void share() {
        //分店
        String s1 = mTvTelDetailShop.getText().toString();
        //广告渠道
        String s2 = mTvTelDetailAd.getText().toString();
        //地铁站
        String s3 = mTvTelDetailSubway.getText().toString();
        //顾客意向
        String s4 = mTvTelDetailTimeIntention.getText().toString();
        //备注
        if (mBuilder != null && !mBuilder.equals("")) {
            String s = mBuilder.toString();
            if (s != null && !s.equals("")) {
                substring = endText;
            }
        }
        if (null == s1 || s1.equals("") || s1.equals("请选择")) {
            ToastUtils.ToastCenter("请填写分店");
        } else if (null == s2 || s2.equals("") || s2.equals("请选择")) {
            ToastUtils.ToastCenter("请填写广告渠道");
        } else if (null == s3 || s3.equals("") || s3.equals("请选择")) {
            ToastUtils.ToastCenter("请填写地铁站");
        } else if (null == s4 || s4.equals("") || s4.length() < 2) {
            ToastUtils.ToastCenter("请填写顾客意向");
        } else if (mBuilder == null || mBuilder.toString().equals("")) {
            ToastUtils.ToastCenter("请填写备注");
        } else {
            //分享页面
            UMWeb web = new UMWeb(mShareUrl);
            //设置分享标题、图片、描述
            web.setTitle("【" + s1 + "】  " + s2 + "   " + s3);
            if (tels > 0) {
                web.setThumb(new UMImage(getApplication(), R.mipmap.zixunhuifang));
                if (s4.equals("跟进")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunhuifang));
                } else if (s4.equals("pass")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunhuifang));
                } else if (s4.equals("成交")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunchengjiao));
                } else if (s4.equals("推送")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunhuifang));
                } else if (s4.equals("转进店")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunhuifang));
                } else {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunhuifang));
                }
                //电话号码为红色时
            } else if ((tels < 1) && (color == 1)) {
                if (s4.equals("成交")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.zixunchengjiao));
                } else {
                    if (size == 0) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.one));
                    } else if (size == 1) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.one));
                    } else if (size == 2) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.two));
                    } else if (size == 3) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.three));
                    } else if (size == 4) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.four));
                    } else if (size == 5) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.five));
                    } else if (size == 6) {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.sex));
                    } else {
                        web.setThumb(new UMImage(getApplication(), R.mipmap.sexs));
                    }

                }
            }
            if (TextUtils.isEmpty(mBean.getConsultReturnTimeStr())&&!TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
                if (null != substring && !substring.equals("")) {
                    web.setDescription("预约进店时间：" + mBean.getAppointmentEnterTimeStr() + "     " + "\r\n" + s4 + " : " + substring);
                } else {
                    web.setDescription("预约进店时间：" + mBean.getAppointmentEnterTimeStr() + "     " + "\r\n" + "【" + s1 + "】");
                }
            } else if(!TextUtils.isEmpty(mBean.getConsultReturnTimeStr())){
                if (null != substring && !substring.equals("")) {
                    web.setDescription(" 咨询回访时间：" + mBean.getConsultReturnTimeStr() + "     " + "\r\n" + s4 + " : " + substring);
                } else {
                    web.setDescription(" 咨询回访时间：" + mBean.getConsultReturnTimeStr() + "     " + "\r\n" + "【" + s1 + "】");
                }
            }else{
                if (null != substring && !substring.equals("")) {
                    web.setDescription(" " + s4 + " : " + substring);
                } else {
                    web.setDescription("【" + s1 + "】");
                }
            }

            new ShareAction(TelDetailActivity.this)
                    .withText("hello")
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();
        }
    }

    SpannableStringBuilder mBuilder = new SpannableStringBuilder();
    ForegroundColorSpan mSpan;

    @Override
    public void showRemarkList(List<RemarkBean.DataBean> list) {
        size = list.size();
        if (list != null && !list.isEmpty()) {
            int size = list.size() - 1;
            endText = list.get(size).getContent();
            mLlTelDetailRemark.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                TextView textView = new TextView(SampleApplicationContext.context);
                textView.setTextSize(14);
                textView.setPadding(25, 10, 20, 10);
                mBuilder.clear();
                String s = (i + 1) + ". " + list.get(i).getContent() + " 。";
                mBuilder.append(s);
                mBuilder.append(list.get(i).getCreateTimeStr());
                mBuilder.setSpan(mSpan, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                textView.setTextColor(getResources().getColor(R.color.text_color_666));
                textView.setText(mBuilder);
                mLlTelDetailRemark.addView(textView);
            }
        }
    }

    @Override
    public void refreshRemark() {
        ToastUtils.ToastCenter("添加备注成功");
        if (mRemarkPop != null) {
            if (mRemarkPop.isShowing()) {
                mRemarkPop.dismiss();
            }
        }
        mPresenter.getRemarkList(String.valueOf(mShopId));
    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isEnable==true){
            String s4_tag = mTvTelDetailTimeIntention.getText().toString();
            if (null==s4_tag || s4_tag.equals(" ") || s4_tag.equals("与销售无关")|| s4_tag.equals("转进店")){
                TelDetailActivity.this.finish();
            }else {
                if (Tag==false){
                    Toast.makeText(this, "请进行分享此条数据", Toast.LENGTH_SHORT).show();
                }else {
                    TelDetailActivity.this.finish();
                }
            }
        }else {
            TelDetailActivity.this.finish();
        }
    }
}