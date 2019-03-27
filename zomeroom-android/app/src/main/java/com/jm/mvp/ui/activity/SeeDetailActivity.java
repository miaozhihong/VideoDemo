package com.jm.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.jm.bean.SeeDetailBean;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.bean.UpSeeDetailBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.SeeDetailContract;
import com.jm.mvp.presenter.SeeDetailPresenter;
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
 * @Description ComeDetailActivity 进店看房详情
 * @date 2018/3/3 17:03
 * o(＞﹏＜)o
 */
public class SeeDetailActivity extends BaseActivity<SeeDetailPresenter> implements SeeDetailContract.View {

    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_title_second)
    ImageView mIvTitleSecond;
    @BindView(R.id.tv_come_detail_time)
    TextView mTvComeDetailTime;
    @BindView(R.id.et_come_detail_tel)
    EditText mEtComeDetailTel;
    @BindView(R.id.tv_come_detail_shop)
    TextView mTvComeDetailShop;
    @BindView(R.id.tv_come_detail_ad)
    TextView mTvComeDetailAd;
    @BindView(R.id.tv_come_detail_subway)
    TextView mTvComeDetailSubway;
    @BindView(R.id.tv_come_detail_account)
    TextView mTvComeDetailAccount;
    @BindView(R.id.tv_come_detail_visit)
    TextView mTvComeDetailVisit;
    @BindView(R.id.tv_come_detail_come)
    TextView mTvComeDetailCome;
    @BindView(R.id.tv_come_detail_push)
    TextView mTvComeDetailPush;
    @BindView(R.id.tv_come_detail_time_intention)
    TextView mTvComeDetailTimeIntention;
    //    @BindView(R.id.tv_come_detail_time_room)
//    TextView mTvComeDetailTimeRoom;
    @BindView(R.id.tv_come_detail_add)
    TextView mTvComeDetailAdd;
    @BindView(R.id.ll_come_detail_remark)
    LinearLayout mLlComeDetailRemark;
    @BindView(R.id.tv_come_detail_confirm)
    TextView mTvComeDetailConfirm;
    @BindView(R.id.ll_come_detail_time_room)
    LinearLayout mLlRoom;
    @BindView(R.id.et_come_detail_time_room)
    EditText mEtRoom;
    private OptionPicker mAdPicker;
    private OptionPicker mWxPicker;
    private DateTimePicker mVisitPicker;
    private DateTimePicker mComePicker;
    private OptionPicker mIntentionPicker;
    private MyPopupWindow mRemarkPop;
    private final String DEFAULT = "请选择";
    private SeeDetailPresenter mPresenter;
    private UpSeeDetailBean mBean;
    private int mShopId;
    private OptionPicker mShopPicker;
    private OptionPicker mPushPicker;
    private String mShareUrl;
    private TextView textView;
    private String substring;
    private DaoSession daoSession;
    private TitlesDao titlesDao;
    private String endText;
    private int tag;
    private boolean Tag = false;
    private boolean isEnable = false;
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
        mPresenter = new SeeDetailPresenter(this);
        //未读数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "jm_notread.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        titlesDao = daoSession.getTitlesDao();
        return R.layout.activity_come_detail;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTvTitle.setText("进店看房详情");
        mIvTitleSecond.setVisibility(View.VISIBLE);
        mIvTitleSecond.setImageResource(R.mipmap.share);
        // 上传的数据类
        mBean = new UpSeeDetailBean();
        // 备注的颜色 color
        mSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_color_333));
        mShopId = getIntent().getIntExtra(Constants.SHOP_ID, -1);
        tag = getIntent().getIntExtra("tels", 1);

        //添加备注选择框
        mRemarkPop = PopUtils.initAddRemark(SampleApplicationContext.context, this,
                new PopUtils.RemarkListener() {
                    @Override
                    public void addRemark(String remark) {
                        mPresenter.addRemark(String.valueOf(mShopId), remark);
                    }
                });
        //微信好友
        mWxPicker = PickerUtils.initSinglePicker(this, Constants.FLLOW_OR_NO, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvComeDetailAccount.setText(item);
                mBean.setIsAttention(index);
            }
        });

        //咨询回访时间
        mVisitPicker = PickerUtils.initDatePicker(this);
        mVisitPicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                mTvComeDetailVisit.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                mBean.setEnterReturnTimeStr(year + "-" + month + "-" + day + " " + hour + ":" + minute);

            }
        });
        //预约进店时间
        mComePicker = PickerUtils.initDatePicker(this);
        mComePicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                mTvComeDetailCome.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                mBean.setAppointmentEnterTimeStr(year + "-" + month + "-" + day + " " + hour + ":" + minute);

            }
        });
        //顾客意向
        mIntentionPicker = PickerUtils.initSinglePicker(this, new String[]{"跟进", "pass", "成交", "与销售无关"}, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvComeDetailTimeIntention.setText(item);
                mBean.setClientIntention(index + 1);
                if (index == 2) {
                    mLlRoom.setVisibility(View.VISIBLE);
                } else {
                    mLlRoom.setVisibility(View.GONE);
                }
            }
        });
//        "http://118.190.83.174:8080/app/enjoy/consult/"+mShopId+"/enterShopDetail";
//        xs.zomeroom.com:8080
        // TODO: 2018/6/23 当有分享地址时，替换即可
        mShareUrl = "http://xs.zomeroom.com:8080/app/enjoy/consult/" + mShopId + "/enterShopDetail";
        mPresenter.netAndData(String.valueOf(mShopId));
    }

    @Override
    public SeeDetailPresenter obtainPresenter() {
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
            setResult(Constants.SEE_DETAIL);
            Tag = true;
            String name = platform.getName();
            if (name != null && !name.equals("")) {
                if (name.equals("wxsession")) {
                    ToastUtils.ToastCenter("分享成功");
                    SeeDetailActivity.this.finish();
                    setResult(Constants.SEE_DETAIL);
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
            Toast.makeText(SeeDetailActivity.this, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            setResult(Constants.SEE_DETAIL);
            SeeDetailActivity.this.finish();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(SeeDetailActivity.this, "分享取消", Toast.LENGTH_LONG).show();
            setResult(Constants.SEE_DETAIL);
            SeeDetailActivity.this.finish();
        }
    };

    @OnClick({R.id.iv_title_back, R.id.iv_title_second, R.id.tv_come_detail_shop, R.id.tv_come_detail_ad, R.id.tv_come_detail_subway, R.id.tv_come_detail_account, R.id.tv_come_detail_visit, R.id.tv_come_detail_come, R.id.tv_come_detail_push, R.id.tv_come_detail_time_intention, R.id.tv_come_detail_add, R.id.ll_come_detail_remark, R.id.tv_come_detail_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                if (isEnable == true) {
                    String s4_tag = mTvComeDetailTimeIntention.getText().toString();
                    if (null == s4_tag || s4_tag.equals(" ") || s4_tag.equals("与销售无关") || s4_tag.equals("转进店")) {
                        SeeDetailActivity.this.finish();
                    } else {
                        if (Tag == false) {
                            Toast.makeText(this, "请进行分享此条数据", Toast.LENGTH_SHORT).show();
                        } else {
                            SeeDetailActivity.this.finish();
                        }
                    }
                } else {
                    SeeDetailActivity.this.finish();
                }
                break;
            case R.id.iv_title_second:
                String s4 = mTvComeDetailTimeIntention.getText().toString();
                if (s4.equals("与销售无关")) {
                    ToastUtils.ToastBottow("此条数据不能进行分享");
                    if (mBuilder != null && !mBuilder.equals("")) {
                        String s = mBuilder.toString();
                        if (s != null && !s.equals("")) {
                            substring = endText;
                        }
                    }
                } else {
                    shareDialog();
                }
                break;
            case R.id.tv_come_detail_shop:
                showPicker(mShopPicker, mTvComeDetailShop);
                break;
            case R.id.tv_come_detail_ad:
                showPicker(mAdPicker, mTvComeDetailAd);
                break;
            case R.id.tv_come_detail_subway:
                startActivityForResult(new Intent(this, SubwayActivity.class), Constants.NO_USE);
                break;
            // 是否关注公众号
            case R.id.tv_come_detail_account:
                showPicker(mWxPicker, mTvComeDetailAccount);
                break;
            case R.id.tv_come_detail_visit:
                mVisitPicker.show();
                break;
            case R.id.tv_come_detail_come:
                mComePicker.show();
                break;
            case R.id.tv_come_detail_push:
                showPicker(mPushPicker, mTvComeDetailPush);
                break;
            case R.id.tv_come_detail_time_intention:
                showPicker(mIntentionPicker, mTvComeDetailTimeIntention);
                break;
            case R.id.tv_come_detail_add:
                isRemark=true;
                if (mRemarkPop != null && !mRemarkPop.isShowing()) {
                    mRemarkPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                    CommonUtils.setBackgroundAlpha(this, 0.6f);
                }
                break;
            case R.id.tv_come_detail_confirm:
                // 与销售无关 只填备注即可
                String intention = mTvComeDetailTimeIntention.getText().toString().trim();
                if (DEFAULT.equals(intention) || TextUtils.isEmpty(intention)) {
                    ToastUtils.ToastCenter("请选择顾客意向");
                    return;
                }
                String tel = mEtComeDetailTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    ToastUtils.ToastCenter("请填写手机号");
                    return;
                }
                mBean.setTel(tel);
                if (Constants.INTENTION[3].equals(intention)) {
                    // 与销售无关，只需判断备注
                    TextView childAt = (TextView) mLlComeDetailRemark.getChildAt(0);
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
                    if (DEFAULT.equals(mTvComeDetailAccount.getText().toString().trim()) ||
                            TextUtils.isEmpty(mTvComeDetailAccount.getText().toString().trim())) {
                        ToastUtils.ToastCenter("请选择是否关注公众号");
                        return;
                    }
                    if (TextUtils.isEmpty(mBean.getEnterReturnTimeStr()) && TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
                        ToastUtils.ToastCenter("请选至少填写一个时间");
                        return;
                    }


                    if (Constants.INTENTION[2].equals(intention)) {
                        String room = CommonUtils.getEditText(mEtRoom);
                        if (TextUtils.isEmpty(room)) {
                            ToastUtils.ToastCenter("请填写房间号码");
                            return;
                        }
                        mBean.setRoomNum(room);
                        //TODO 成交时间
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
                        titlesDao.insert(new Titles(null,NotificationMonitorService.pak_Name,"推送邮件","已将"+mBean.getTel()+"这个进店看房用户推送给"+mBean.getPushShopName(),time,"推送邮件","0","否",(String) SPUtils.get("userName",""),mBean.getPushShopName()));
                    }
                    mPresenter.updateData(mBean);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

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
            int id = data.getIntExtra(Constants.SUBWAY_ID, -1);
            mTvComeDetailSubway.setText(subway);
            mBean.setStationName(subway);
            if (id != -1) {
                mBean.setStationId(id);
            }
        }
    }

    @Override
    public void showNetError() {

    }

    public void setClick(boolean b) {
        mTvComeDetailShop.setClickable(b);
        mTvComeDetailAd.setClickable(b);
        mTvComeDetailSubway.setClickable(b);
        mTvComeDetailAccount.setClickable(b);
        mTvComeDetailVisit.setClickable(b);
        mTvComeDetailCome.setClickable(b);
        mTvComeDetailPush.setClickable(b);
        mTvComeDetailTimeIntention.setClickable(b);
        mTvComeDetailConfirm.setClickable(b);
        mTvComeDetailAdd.setClickable(b);
        mEtRoom.setFocusable(b);
    }

    @Override
    public void showData(SeeDetailBean.DataBean data) {
        // 将返回的数据装入
        SeeDetailBean.DataBean.DetailInfoBean bean = data.getDetailInfo();

        // 如果返回的数据是已成交
        int intention = bean.getClientIntention();
        // 已成交，不可更新更改数据
        if (intention == 3) {
            mLlRoom.setVisibility(View.VISIBLE);
            mTvComeDetailConfirm.setBackgroundColor(getResources().getColor(R.color.bg_A5A4A4));
            mEtRoom.setText(bean.getRoomNum());
            setClick(false);
        } else {
            mLlRoom.setVisibility(View.GONE);
            mTvComeDetailConfirm.setBackgroundColor(getResources().getColor(R.color.bg_26c2fc));
            setClick(true);
        }

        // 预约进店时间
        mBean.setAppointmentEnterTimeStr(bean.getAppointmentEnterTimeStr());
        mBean.setChannelId(bean.getChannelId());
        mBean.setChannelName(bean.getChannelName());
        mBean.setClientIntention(intention);
        // 进店回访时间
        mBean.setEnterReturnTimeStr(bean.getEnterReturnTimeStr());
        // 创建时间
        mBean.setCreateTimeStr(bean.getCreateTimeStr());
        mBean.setId(bean.getId());
        mBean.setIsCall(bean.getIsCall());
        // 微信好友
        mBean.setIsAttention(bean.getIsAttention());
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
        mTvComeDetailTime.setText(mBean.getCreateTimeStr());
        mEtComeDetailTel.setText(mBean.getTel().replace(" ", ""));
        if (!TextUtils.isEmpty(mBean.getShopName())) {
            mTvComeDetailShop.setText(mBean.getShopName());
            shopName=mBean.getShopName();
        }
        if (!TextUtils.isEmpty(mBean.getChannelName())) {
            mTvComeDetailAd.setText(mBean.getChannelName());
            advertisement=mBean.getChannelName();
        }
        if (!TextUtils.isEmpty(mBean.getStationName())) {
            mTvComeDetailSubway.setText(mBean.getStationName());
            subway=mBean.getStationName();
        }
        mTvComeDetailAccount.setText(Constants.FLLOW_OR_NO[mBean.getIsAttention()]);
        mWxPicker.setSelectedIndex(mBean.getIsAttention());
        if (!TextUtils.isEmpty(mBean.getEnterReturnTimeStr())) {
            mTvComeDetailVisit.setText(mBean.getEnterReturnTimeStr());
            revisitdays=mBean.getEnterReturnTimeStr();
            int[] time = CommonUtils.toIntTime(mBean.getEnterReturnTimeStr());
            mVisitPicker.setSelectedItem(time[0], time[1], time[2], time[3], time[4]);
        }
        if (!TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
            mTvComeDetailCome.setText(mBean.getAppointmentEnterTimeStr());
            appointmenttime=mBean.getAppointmentEnterTimeStr();
            int[] time = CommonUtils.toIntTime(mBean.getAppointmentEnterTimeStr());
            mComePicker.setSelectedItem(time[0], time[1], time[2], time[3], time[4]);
        }
        if (!TextUtils.isEmpty(mBean.getPushShopName())) {
            mTvComeDetailPush.setText(mBean.getPushShopName());
        }
        if (mBean.getClientIntention() != 0) {
            mTvComeDetailTimeIntention.setText(CommonUtils.getIntention(mBean.getClientIntention()));
            intentions=CommonUtils.getIntention(mBean.getClientIntention());
            mIntentionPicker.setSelectedIndex(mBean.getClientIntention() - 1);
        }
        // 广告渠道
        final List<SeeDetailBean.DataBean.ChannelListBean> adList = data.getChannelList();
        // 推送门店
        final List<SeeDetailBean.DataBean.PushListBean> pushList = data.getPushList();
        // 分店
        final List<SeeDetailBean.DataBean.ShopListBean> shopList = data.getShopList();

        String[] adArray = CommonUtils.toStringArray(adList, Constants.AD, Constants.SEE);
        String[] pushArray = CommonUtils.toStringArray(pushList, Constants.PUSH_SHOP, Constants.SEE);
        final String[] shopArray = CommonUtils.toStringArray(shopList, Constants.MINE_SHOP, Constants.SEE);
        // 广告渠道
        mAdPicker = PickerUtils.initSinglePicker(this, adArray, new PickerUtils.SingleListener() {
            @Override
            public void select(int index, String item) {
                mTvComeDetailAd.setText(item);
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
                mTvComeDetailPush.setText(item);
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
                mTvComeDetailShop.setText(item);
                mBean.setShopName(item);
                for (int i = 0; i < shopList.size(); i++) {
                    if (item.equals(shopList.get(i).getShopName())) {
                        mBean.setShopId(shopList.get(i).getId());
                        return;
                    }
                }
            }
        });
    }

    @Override
    public void refresh() {
        isEnable=true;
        String s4 = mTvComeDetailTimeIntention.getText().toString();
        if (s4.equals("与销售无关")) {
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
                            if (!shopName.equals(mTvComeDetailShop.getText().toString()) ||!advertisement.equals(mTvComeDetailAd.getText().toString()) ||!subway.equals(mTvComeDetailSubway.getText().toString())||!appointmenttime.equals(mTvComeDetailCome.getText().toString()) ||!intentions.equals(mTvComeDetailTimeIntention.getText().toString())){
                                if (isRemark==false){
                                    Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                }else {
                                    shareDialog();
                                }
                            }else {
                                shareDialog();
                            }
                        }else {
                            //判断预约进店时间是否有数据
                            if (null==appointmenttime){
                                if (!shopName.equals(mTvComeDetailShop.getText().toString()) ||!advertisement.equals(mTvComeDetailAd.getText().toString()) ||!subway.equals(mTvComeDetailSubway.getText().toString())||!revisitdays.equals(mTvComeDetailVisit.getText().toString()) ||!intentions.equals(mTvComeDetailTimeIntention.getText().toString())){
                                    if (isRemark==false){
                                        Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                    }else {
                                        shareDialog();
                                    }
                                }else {
                                    shareDialog();
                                }
                            }else {
                                if (!shopName.equals(mTvComeDetailShop.getText().toString()) ||!advertisement.equals(mTvComeDetailAd.getText().toString()) ||!subway.equals(mTvComeDetailSubway.getText().toString())||!revisitdays.equals(mTvComeDetailVisit.getText().toString()) ||!appointmenttime.equals(mTvComeDetailCome.getText().toString()) ||!intentions.equals(mTvComeDetailTimeIntention.getText().toString())){
                                    if (isRemark==false){
                                        Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                    }else {
                                        shareDialog();
                                    }
                                }else {
                                    shareDialog();
                                }
                            }
                        }
                    }else {
                        //判断咨询时间是否有数据
                        if (null==revisitdays){
                            if (!shopName.equals(mTvComeDetailShop.getText().toString()) ||!advertisement.equals(mTvComeDetailAd.getText().toString()) ||!subway.equals(mTvComeDetailSubway.getText().toString())||!appointmenttime.equals(mTvComeDetailCome.getText().toString()) ||!push.equals(mTvComeDetailPush.getText().toString())||!intentions.equals(mTvComeDetailTimeIntention.getText().toString())){
                                if (isRemark==false){
                                    Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                }else {
                                    shareDialog();
                                }
                            }else {
                                shareDialog();
                            }
                        }else {
                            //判断预约进店时间是否有数据
                            if (null==appointmenttime){
                                if (!shopName.equals(mTvComeDetailShop.getText().toString()) ||!advertisement.equals(mTvComeDetailAd.getText().toString()) ||!subway.equals(mTvComeDetailSubway.getText().toString())||!revisitdays.equals(mTvComeDetailVisit.getText().toString()) ||!push.equals(mTvComeDetailPush.getText().toString())||!intentions.equals(mTvComeDetailTimeIntention.getText().toString())){
                                    if (isRemark==false){
                                        Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                    }else {
                                        shareDialog();
                                    }
                                }else {
                                    shareDialog();
                                }
                            }else {
                                if (!shopName.equals(mTvComeDetailShop.getText().toString()) ||!advertisement.equals(mTvComeDetailAd.getText().toString()) ||!subway.equals(mTvComeDetailSubway.getText().toString())||!revisitdays.equals(mTvComeDetailVisit.getText().toString()) ||!appointmenttime.equals(mTvComeDetailCome.getText().toString()) ||!push.equals(mTvComeDetailPush.getText().toString())||!intentions.equals(mTvComeDetailTimeIntention.getText().toString())){
                                    if (isRemark==false){
                                        Toast.makeText(this, "请填写修改信息后的备注", Toast.LENGTH_SHORT).show();
                                    }else {
                                        shareDialog();
                                    }
                                }else {
                                    shareDialog();
                                }
                            }
                        }
                    }
                } else {
                    shareDialog();
                }
        }
    }

    private void share() {
        //分店
        String s1 = mTvComeDetailShop.getText().toString();
        //广告渠道
        String s2 = mTvComeDetailAd.getText().toString();
        //地铁站
        String s3 = mTvComeDetailSubway.getText().toString();
        //顾客意向
        String s4 = mTvComeDetailTimeIntention.getText().toString();
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
        } else if (mBuilder.toString().equals("") || mBuilder == null) {
            ToastUtils.ToastCenter("请填写备注");
        } else {
            //分享页面
            UMWeb web = new UMWeb(mShareUrl);
            //设置分享标题、图片、描述
            web.setTitle("【" + s1 + "】  " + s2 + "   " + s3);
            //电话号码为黑色时
            if (tag > 0) {
                web.setThumb(new UMImage(getApplication(), R.mipmap.jindianhuifang));
                if (s4.equals("跟进")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianhuifang));
                } else if (s4.equals("pass")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianhuifang));
                } else if (s4.equals("成交")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianchengjiao));
                } else if (s4.equals("推送")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianhuifang));
                } else if (s4.equals("转进店")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianhuifang));
                } else {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianhuifang));
                }
                //电话号码为红色时
            } else {
                web.setThumb(new UMImage(getApplication(), R.mipmap.jindianzixun));
                if (s4.equals("跟进")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianzixun));
                } else if (s4.equals("pass")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianzixun));
                } else if (s4.equals("成交")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianchengjiao));
                } else if (s4.equals("推送")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianzixun));
                } else if (s4.equals("转进店")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianzixun));
                } else {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianzixun));
                }
            }

//          CommonUtils.getIntention(bean.getClientIntention())

            if (TextUtils.isEmpty(mBean.getEnterReturnTimeStr())&&!TextUtils.isEmpty(mBean.getAppointmentEnterTimeStr())) {
                if (null != substring && !substring.equals("")) {
                    web.setDescription("预约进店时间：" + mBean.getAppointmentEnterTimeStr() + "     " + "\r\n" + s4 + " : " + substring);
                } else {
                    web.setDescription("预约进店时间：" + mBean.getAppointmentEnterTimeStr() + "     " + "\r\n" + "【" + s1 + "】");
                }
            } else if(!TextUtils.isEmpty(mBean.getEnterReturnTimeStr())){
                if (null != substring && !substring.equals("")) {
                    web.setDescription(" 进店回访时间：" + mBean.getEnterReturnTimeStr() + "     " + "\r\n" + s4 + " : " + substring);
                } else {
                    web.setDescription(" 进店回访时间：" + mBean.getEnterReturnTimeStr() + "     " + "\r\n" + "【" + s1 + "】");
                }
            }else{
                if (null != substring && !substring.equals("")) {
                    web.setDescription(" " + s4 + " : " + substring);
                } else {
                    web.setDescription("【" + s1 + "】");
                }
            }
            new ShareAction(SeeDetailActivity.this)
                    .withText("hello")
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();
        }
    }


    SpannableStringBuilder mBuilder = new SpannableStringBuilder();
    ForegroundColorSpan mSpan;

    //设置备注显示最后一条
    @Override
    public void showRemarkList(List<RemarkBean.DataBean> list) {
//        mLlComeDetailRemark
        if (list != null && !list.isEmpty()) {
            int size = list.size() - 1;
            endText = list.get(size).getContent();
            mLlComeDetailRemark.removeAllViews();
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
                mLlComeDetailRemark.addView(textView);
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

    private void shareDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SeeDetailActivity.this).setTitle("系统提示")
                .setMessage("客人是否进店")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        share();
                    }
                }).setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        screenShare();
                    }


                });
        alertDialog.setIcon(R.mipmap.logoyimei).create().show();
    }

    private void screenShare() {
        //分店
        String s1 = mTvComeDetailShop.getText().toString();
        //广告渠道
        String s2 = mTvComeDetailAd.getText().toString();
        //地铁站
        String s3 = mTvComeDetailSubway.getText().toString();
        //顾客意向
        String s4 = mTvComeDetailTimeIntention.getText().toString();
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
        } else if (mBuilder.toString().equals("") || mBuilder == null) {
            ToastUtils.ToastCenter("请填写备注");
        } else {
            //分享页面
            UMWeb web = new UMWeb(mShareUrl);
            //设置分享标题、图片、描述
            web.setTitle("【" + s1 + "】  " + s2 + "   " + s3);
            //电话号码为黑色时
            if (tag > 0) {
                web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                if (s4.equals("跟进")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else if (s4.equals("pass")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else if (s4.equals("成交")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianchengjiao));
                } else if (s4.equals("推送")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else if (s4.equals("转进店")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                }
                //电话号码为红色时
            } else {
                web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                if (s4.equals("跟进")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else if (s4.equals("pass")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else if (s4.equals("成交")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindianchengjiao));
                } else if (s4.equals("推送")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else if (s4.equals("转进店")) {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                } else {
                    web.setThumb(new UMImage(getApplication(), R.mipmap.jindiankanfang));
                }
            }

//          CommonUtils.getIntention(bean.getClientIntention())
            if (!mBean.getEnterReturnTimeStr().equals(" ") && null != mBean.getEnterReturnTimeStr()) {
                if (null != substring && !substring.equals("")) {
                    web.setDescription(" 进店看房：" + mBean.getEnterReturnTimeStr() + "     " + "\r\n" + s4 + " : " + substring);
                } else {
                    web.setDescription(" 进店看房：" + mBean.getEnterReturnTimeStr() + "     " + "\r\n" + "【" + s1 + "】");
                }
            } else {
                if (null != substring && !substring.equals("")) {
                    web.setDescription(" " + s4 + " : " + substring);
                } else {
                    web.setDescription("【" + s1 + "】");
                }
            }
            new ShareAction(SeeDetailActivity.this)
                    .withText("hello")
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();
        }
    }

    @Override
    public void onBackPressed() {
        if (isEnable == true) {
            String s4_tag = mTvComeDetailTimeIntention.getText().toString();
            if (null == s4_tag || s4_tag.equals(" ") || s4_tag.equals("与销售无关") || s4_tag.equals("转进店")) {
                SeeDetailActivity.this.finish();
            } else {
                if (Tag == false) {
                    Toast.makeText(this, "请进行分享此条数据", Toast.LENGTH_SHORT).show();
                } else {
                    SeeDetailActivity.this.finish();
                }
            }
        } else {
            SeeDetailActivity.this.finish();
        }
    }
}
