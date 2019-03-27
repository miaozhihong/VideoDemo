package com.jm.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.SeeListBean;
import com.jm.bean.ShopBean;
import com.jm.bean.TelListBean;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.bean.UpTelSearchBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseFragment;
import com.jm.mvp.contract.SeeContract;
import com.jm.mvp.presenter.SeePresenter;
import com.jm.mvp.ui.activity.MessageActivity;
import com.jm.mvp.ui.activity.SeeDetailActivity;
import com.jm.mvp.ui.adapter.PopChooseAdapter;
import com.jm.mvp.ui.adapter.RecyclerViewAdapter;
import com.jm.mvp.ui.adapter.SeeAdapter;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.jm.mvp.ui.widget.RefreshListener;
import com.jm.mvp.ui.widget.SuperSwipeRefreshLayout;
import com.jm.service.NotificationMonitorService;
import com.jm.utils.CommonUtils;
import com.jm.utils.PopUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.ToastUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author pc 张立男
 * @Description SeeFragment 进店看房列表页面
 * @date 2018/2/19 20:49
 * o(＞﹏＜)o
 */

public class SeeFragment extends BaseFragment<SeePresenter> implements SeeContract.View {
    @BindView(R.id.rv_see)
    PullLoadMoreRecyclerView mRvSee;
    @BindView(R.id.iv_title_back_choose)
    ImageView mIvTitleBackChoose;
    @BindView(R.id.rg_title_left)
    RadioButton mRgTitleLeft;
    @BindView(R.id.rg_title_right)
    RadioButton mRgTitleRight;
    @BindView(R.id.tv_title_second_choose)
    TextView mTvTitleSecondChoose;
    @BindView(R.id.iv_title_second_choose)
    ImageView mIvTitleSecondChoose;
    @BindView(R.id.tv_see_tel)
    TextView mTvSeeTel;
    @BindView(R.id.tv_see_shop)
    TextView mTvSeeShop;
    @BindView(R.id.tv_see_intention)
    TextView mTvSeeIntention;
    @BindView(R.id.ll_tel)
    LinearLayout mLlTel;
    @BindView(R.id.v_see)
    View mVTel;
    @BindView(R.id.ll_see_null)
    LinearLayout mLlNull;
    @BindView(R.id.tv_see_null)
    TextView mTvNull;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    private SeeAdapter mSeeAdapter;
    private MyPopupWindow mTelPop;
    private MyPopupWindow mShopPop;
    private MyPopupWindow mIntentionPop;
    private SeePresenter mPresenter;
    private MyPopupWindow mSearchPop;
    private TitlesDao titlesDao;
    private TitlesDao titlesDao1;
    private QBadgeView qBadgeView;
    /**
     * 筛选的数据
     */
    private UpTelSearchBean mBean;
    private CalendarDay mTelDate;
    private CalendarDay mShopDate;
    private CalendarDay mIntentionDate;
    private PopChooseAdapter mIntentionPopAdapter;
    private PopChooseAdapter mTelPopAdapter;
    private PopChooseAdapter mShopPopAdapter;
    private SearchDialogFragment mDialogFragment;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedPreferences sharedPreferences;
    private int ids=0;
    ArrayList<SeeListBean.DataBean.ListBean> mList=new ArrayList<>();
    ArrayList<String> mTel=new ArrayList<>();
    ArrayList<String> mDao=new ArrayList<>();
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new SeePresenter(this);
        //未读数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "jm_notread.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        titlesDao = daoSession.getTitlesDao();

        //已读数据
        DaoMaster.DevOpenHelper devOpenHelper1 = new DaoMaster.DevOpenHelper(getContext(), "jm_alreadyread.db", null);
        DaoMaster daoMaster1 = new DaoMaster(devOpenHelper1.getWritableDatabase());
        DaoSession daoSession1 = daoMaster1.newSession();
        titlesDao1 = daoSession1.getTitlesDao();
        return inflater.inflate(R.layout.fragment_see, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("loginpref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor edit = sharedPreferences.edit();
        mBean = new UpTelSearchBean();
        ivMessage.setImageDrawable(getActivity().getDrawable(R.mipmap.message));
        ivMessage.setVisibility(View.VISIBLE);
        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MessageActivity.class));
                qBadgeView.setVisibility(View.GONE);
            }
        });
        mIvTitleBackChoose.setVisibility(View.GONE);
        mRgTitleLeft.setText("全部");
        mRgTitleRight.setText("未拨");
        mIvTitleSecondChoose.setImageResource(R.mipmap.home_search);
        mIvTitleSecondChoose.setVisibility(View.VISIBLE);
        mRvSee.setLinearLayout();
        mRvSee.setFooterViewText("正在加载");
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<SeeListBean.DataBean.ListBean>(),
                SampleApplicationContext.context, Constants.SEE);
        recyclerViewAdapter.setListener(new SeeAdapter.ClickListener() {
            @Override
            public void call(String tel, int id) {
                ids=1;
                edit.putInt("tels",id);
                edit.commit();
                CommonUtils.callTel(tel, getActivity());
                mPresenter.call(String.valueOf(id));
            }

            @Override
            public void come(int shopId) {
                mPresenter.cancelToShop(String.valueOf(shopId));
            }

            @Override
            public void clickItem(int id) {
                Intent intent = new Intent(getActivity(), SeeDetailActivity.class);
                intent.putExtra(Constants.SHOP_ID, id);
                int tels = sharedPreferences.getInt("tels", 1);
                if (id==tels){
                    intent.putExtra("tels",1);
                }else {
                    intent.putExtra("tels",0);
                }
                startActivityForResult(intent, Constants.SEE);
            }

            @Override
            public void addBlack(String tel) {
                mPresenter.addBlackTel(tel, "");
            }
        });
//        mRvSee.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSee.setAdapter(recyclerViewAdapter);
        mRvSee.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mPresenter.netAndData(mBean, Constants.UP_LOAD);
                        mRvSee.setPullLoadMoreCompleted();
                    }
                }, 800);
            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.netAndData(mBean, Constants.UP_LOAD);
                        mRvSee.setPullLoadMoreCompleted();
                    }
                }, 800);
            }
        });


        mRgTitleLeft.setChecked(true);
        // 电话
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, "全部", "咨询回访", "预约进店");
        mTelPop = PopUtils.initPop(SampleApplicationContext.context, list, new PopUtils.PopListener() {
            @Override
            public void rvListener(int position, String s) {
                mBean.setMinorType(String.valueOf(position));
            }

            @Override
            public void cvListener(CalendarDay date) {
                mTelDate = date;
                if (date != null) {
                    mBean.setTime(CommonUtils.formatDate(date.getDate()));
                } else {
                    mBean.setTime("");
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvSeeTel);
            }

            @Override
            public void resets() {

            }

            @Override
            public void onRadioChildClikc(int position) {

            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {
                mBean.setMainType("1");
                mBean.setShopId("0");
                mBean.setClientIntention("-1");
                mBean.setTel("");
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                mTelPopAdapter = adapter;
                setSelect(mIntentionPopAdapter);
                setSelect(mShopPopAdapter);
                ((MaterialCalendarView) mIntentionPop.getContentView().findViewById(R.id.cv_pop_choose))
                        .setDateSelected(mIntentionDate, false);
                ((MaterialCalendarView) mShopPop.getContentView().findViewById(R.id.cv_pop_choose))
                        .setDateSelected(mShopDate, false);
                mShopDate = null;
                mIntentionDate = null;
            }

            @Override
            public void confirmListener() {

            }
        });
        // 意向
        ArrayList<String> intentionList = new ArrayList<>();
        Collections.addAll(intentionList, "全部", "跟进", "pass", "成交", "与销售无关");
        mIntentionPop = PopUtils.initPop(SampleApplicationContext.context, intentionList, new PopUtils.PopListener() {
            @Override
            public void rvListener(int position, String s) {
                if (position == 0) {
                    mBean.setClientIntention(String.valueOf(position - 1));
                } else {
                    mBean.setClientIntention(String.valueOf(position));
                }
            }

            @Override
            public void cvListener(CalendarDay date) {
                mIntentionDate = date;
                if (date != null) {
                    mBean.setTime(CommonUtils.formatDate(date.getDate()));
                } else {
                    mBean.setTime("");
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvSeeIntention);
            }

            @Override
            public void resets() {

            }

            @Override
            public void onRadioChildClikc(int position) {

            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {
                mBean.setMainType("3");
                mBean.setMinorType("0");
                mBean.setShopId("0");
                mBean.setTel("");
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                mIntentionPopAdapter = adapter;
                setSelect(mTelPopAdapter);
                setSelect(mShopPopAdapter);
                ((MaterialCalendarView) mShopPop.getContentView().findViewById(R.id.cv_pop_choose))
                        .setDateSelected(mShopDate, false);
                ((MaterialCalendarView) mTelPop.getContentView().findViewById(R.id.cv_pop_choose))
                        .setDateSelected(mTelDate, false);
                mShopDate = null;
                mTelDate = null;
            }

            @Override
            public void confirmListener() {

            }
        });

        // 搜索
//        mSearchPop = PopUtils.initSearchPop(SampleApplicationContext.context, new PopUtils.SearchListener() {
//            @Override
//            public void select(String tel) {
//                UpTelSearchBean bean = new UpTelSearchBean();
//                bean.setTel(tel);
//                bean.setPageSize("10");
//                mPresenter.netAndData(bean, Constants.DOWN_REFRESH);
//            }
//        });
        mDialogFragment = new SearchDialogFragment();
        mDialogFragment.setSearchDialog(new SearchDialogFragment.SearchDialog() {
            @Override
            public void search(String tel) {
                mBean.setTel(tel);
                mBean.setMainType("0");
                mBean.setIsCall("-1");
                mBean.setMinorType("0");
                mBean.setShopId("0");
                mBean.setClientIntention("-1");
                mBean.setPageSize("10");
                mBean.setPageNo("1");
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
            }
        });
        // 设置默认值
        mBean.setMainType("0");
        mBean.setIsCall("-1");
        mBean.setMinorType("0");
        mBean.setShopId("0");
        mBean.setClientIntention("-1");
        mBean.setPageSize("10");
        mBean.setPageNo("1");

        mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
        mPresenter.getShopList();
    }

    @Override
    public SeePresenter obtainPresenter() {
        return mPresenter;
    }

    public static SeeFragment newInstance() {
        SeeFragment fragment = new SeeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PopUtils.destroyPop(mTelPop);
        PopUtils.destroyPop(mShopPop);
        PopUtils.destroyPop(mIntentionPop);
    }

    @OnClick({R.id.rg_title_left, R.id.rg_title_right, R.id.iv_title_second_choose, R.id.tv_see_tel,
            R.id.tv_see_shop, R.id.tv_see_intention,R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
                case R.id.rg_title_left:
                mBean.setIsCall("-1");
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                break;
            case R.id.rg_title_right:
                mBean.setIsCall("0");
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                break;
            case R.id.iv_title_second_choose:
//                if (mSearchPop != null) {
//                    if (mSearchPop.isShowing()) {
//                        mSearchPop.dismiss();
//                    } else {
//                        mSearchPop.showAsDropDown(mVTel);
//                    }
//                }
                if (mDialogFragment != null) {
                    if (mDialogFragment.isVisible()) {
                        mDialogFragment.dismiss();
                    } else {
                        mDialogFragment.show(getFragmentManager(), "search");
                    }
                }
                break;
            case R.id.tv_see_tel:
                showPop(mTelPop, mTvSeeTel);
                if (mTelDate != null) {
                    mBean.setTime(CommonUtils.formatDate(mTelDate.getDate()));
                }
                break;
            case R.id.tv_see_shop:
                showPop(mShopPop, mTvSeeShop);
                if (mShopDate != null) {
                    mBean.setTime(CommonUtils.formatDate(mShopDate.getDate()));
                }
                break;
            case R.id.tv_see_intention:
                showPop(mIntentionPop, mTvSeeIntention);
                if (mIntentionDate != null) {
                    mBean.setTime(CommonUtils.formatDate(mIntentionDate.getDate()));
                }
                break;
            default:
                break;
        }
    }

    private void showPop(MyPopupWindow popupWindow, TextView textView) {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                hideAllPop();
                popupWindow.showAsDropDown(mLlTel);
                PopUtils.tvUp(textView);
            }
        }
    }

    /**
     * 隐藏全部弹窗
     */
    private void hideAllPop() {
        PopUtils.hidePop(mTelPop);
        PopUtils.hidePop(mShopPop);
        PopUtils.hidePop(mIntentionPop);
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showData(List<SeeListBean.DataBean.ListBean> list) {
        mLlNull.setVisibility(View.VISIBLE);
        mTvNull.setVisibility(View.GONE);
//        //是否存在推送信息
//        for (int i = 0; i <list.size() ; i++) {
//            if (null!=list.get(i).getChannelName()) {
//                if (list.get(i).getChannelName().equals("分店推送") &&null!=list.get(i).getChannelName() && !list.get(i).getChannelName().equals("请选择")) {
//                    mList.add(list.get(i));
//                }
//            }
//        }
//        //將推送用戶信息添加到數據庫中
//        for (int i = 0; i < mList.size(); i++) {
//            String tel = mList.get(i).getTel();
//            mTel.add(tel);
//        }
//        //添加到数据库
//        SimpleDateFormat formatter = new  SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
//        Date curDate =  new Date(System.currentTimeMillis());
//        String time =formatter.format(curDate);
//        List<Titles> titles = titlesDao.loadAll();
//        List<Titles> titles1= titlesDao1.loadAll();
//        if (titles.size()>0){
//            for (int i = 0; i < titles.size(); i++) {
//                mDao.add(titles.get(i).getPhone());
//            }
//            for (int i = 0; i < mTel.size(); i++) {
//                if (mDao.contains(mTel.get(i))){
//                }else {
//                    if (titles1.size()>0){
//                        for (int j = 0; j < titles1.size(); j++) {
//                            mDao.add(titles1.get(j).getPhone());
//                        }
//                        for (int m = 0; m < mTel.size(); m++) {
//                            if (mDao.contains(mTel.get(m))){
//                            }else {
//                                titlesDao.insert(new Titles(null,NotificationMonitorService.pak_Name,"推送消息","其他店长推送一条手机号为"+mTel.get(m)+"的电话进店看房推送邮件,请您尽快处理",time,"推送邮件",mTel.get(m),"是"));
//                            }
//                        }
//                    }else {
//                        for (int k = 0; k < mTel.size(); k++) {
//                            titlesDao.insert(new Titles(null,NotificationMonitorService.pak_Name,"推送消息","其他店长推送一条手机号为"+mTel.get(k)+"的电话进店看房推送邮件,请您尽快处理",time,"推送邮件",mTel.get(k),"是"));
//                        }
//                    }
//                }
//            }
//        }else {
//            if (titles1.size()>0){
//                for (int i = 0; i < titles1.size(); i++) {
//                    mDao.add(titles1.get(i).getPhone());
//                }
//                for (int i = 0; i < mTel.size(); i++) {
//                    if (mDao.contains(mTel.get(i))){
//                    }else {
//                        titlesDao.insert(new Titles(null,NotificationMonitorService.pak_Name,"推送消息","其他店长推送一条手机号为"+mTel.get(i)+"的电话咨询推送邮件,请您尽快处理",time,"推送邮件",mTel.get(i),"是"));
//                    }
//                }
//            }else {
//                for (int i = 0; i < mTel.size(); i++) {
//                    titlesDao.insert(new Titles(null,NotificationMonitorService.pak_Name,"推送消息","其他店长推送一条手机号为"+mTel.get(i)+"的电话咨询推送邮件,请您尽快处理",time,"推送邮件",mTel.get(i),"是"));
//                }
//            }
//        }

        recyclerViewAdapter.replace(list);
//        mSeeAdapter.replace(list);
    }

    @Override
    public void showEmpty() {
        mLlNull.setVisibility(View.GONE);
        mTvNull.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }

    @Override
    public void showNotMore() {
        ToastUtils.ToastCenter("没有更多了");
    }

    @Override
    public void refresh() {
        mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
    }

    @Override
    public void showShopList(final List<ShopBean.DataBean> data) {
        // 店名
        List<String> shopList = CommonUtils.toShopList(data);
        mShopPop = PopUtils.initPop(SampleApplicationContext.context, shopList, new PopUtils.PopListener() {
            @Override
            public void rvListener(int position, String s) {
                if (position == 0) {
                    mBean.setShopId("0");
                } else {
                    mBean.setShopId(String.valueOf(data.get(position - 1).getId()));
                }
            }

            @Override
            public void cvListener(CalendarDay date) {
                mShopDate = date;
                if (date != null) {
                    mBean.setTime(CommonUtils.formatDate(date.getDate()));
                } else {
                    mBean.setTime("");
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvSeeShop);
            }

            @Override
            public void resets() {

            }

            @Override
            public void onRadioChildClikc(int position) {

            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {
                mBean.setMainType("2");
                mBean.setMinorType("0");
                mBean.setClientIntention("-1");
                mBean.setTel("");
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                mShopPopAdapter = adapter;
                setSelect(mIntentionPopAdapter);
                setSelect(mTelPopAdapter);
                ((MaterialCalendarView) mIntentionPop.getContentView().findViewById(R.id.cv_pop_choose))
                        .setDateSelected(mIntentionDate, false);
                ((MaterialCalendarView) mTelPop.getContentView().findViewById(R.id.cv_pop_choose))
                        .setDateSelected(mTelDate, false);
                mTelDate = null;
                mIntentionDate = null;
            }

            @Override
            public void confirmListener() {

            }
        });
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.ToastCenter(msg);
    }

    @Override
    public void cancelToShopSuccess() {
        ToastUtils.ToastCenter("取消转进店成功");
        mPresenter.netAndData(mBean,Constants.DOWN_REFRESH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.SEE_DETAIL) {
            mBean = new UpTelSearchBean();
            mBean.setMainType("0");
            mBean.setPageSize("10");
            mBean.setPageNo("1");
            mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setSelect(PopChooseAdapter adapter) {
        if (adapter != null) {
            adapter.selectPosition(0);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        final List<Titles> titles = titlesDao.loadAll();
        qBadgeView = new QBadgeView(getActivity());
        qBadgeView.setFocusable(true);
        qBadgeView.bindTarget(ivMessage);//设置要显示消息提示的View 控件
        if (titles.size()>0){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    qBadgeView.setBadgeNumber(titles.size());//设置Badge数字
                    qBadgeView.setBadgeTextColor(Color.parseColor("#ff123564"));//设置文本颜色
                    qBadgeView.setExactMode(true);//设置是否显示精确模式数值
                }
            });
        }else {
            qBadgeView.setVisibility(View.GONE);
        }
    }

}
