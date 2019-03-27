package com.jm.mvp.ui.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.CommonBean;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.ModelDataDTOBean;
import com.jm.bean.ShopBean;
import com.jm.bean.ShowRequestDetailBean;
import com.jm.bean.TelBean;
import com.jm.bean.UserBean;
import com.jm.helper.HttpUtils;
import com.jm.helper.OkHttp3Utils;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseFragment;
import com.jm.mvp.contract.DetailContract;
import com.jm.mvp.presenter.DetailPresenter;
import com.jm.mvp.ui.activity.DetailActivity;
import com.jm.mvp.ui.activity.MessageActivity;
import com.jm.mvp.ui.activity.SeeDetailActivity;
import com.jm.mvp.ui.adapter.PopChooseAdapter;
import com.jm.mvp.ui.adapter.RecycDetailedAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.jm.utils.CommonUtils;
import com.jm.utils.DateChangesUtils;
import com.jm.utils.PopAreaUtils;
import com.jm.utils.PopUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.TelDialog;
import com.jm.utils.ToastUtils;
import com.jm.utils.permissions.DetailedDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * miaozhihong
 * 内务管理fragment
 */
public class DetailedFragment extends BaseFragment<DetailPresenter> implements DetailContract.View {
    @BindView(R.id.rg_title_left)
    RadioButton rgTitleLeft;
    @BindView(R.id.rg_title_right)
    RadioButton rgTitleRight;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.tv_detailed_time)
    TextView mTvDetailedTime;
    @BindView(R.id.tv_detailed_area)
    TextView mTvDetailedArea;
    @BindView(R.id.tv_detailed_shop)
    TextView mTvDetailedShop;
    @BindView(R.id.tv_detailed_name)
    TextView mTvDetailedName;
    @BindView(R.id.iv_title_second_choose)
    ImageView mIvTitleSecondChoose;
    @BindView(R.id.iv_title_back_choose)
    ImageView mIvTitleBackChoose;
    @BindView(R.id.rv_talk)
    PullLoadMoreRecyclerView mSsfTel;
    @BindView(R.id.ll_tel_null)
    LinearLayout mLlNull;
    @BindView(R.id.ll_tel)
    LinearLayout mLlTel;
    @BindView(R.id.tv_tel_null)
    TextView mTvNull;
    String modelType = 0 + "";
    boolean isWoo = false;
    boolean isSwith = false;
    String Type = 2 + "";
    private MyPopupWindow mTalkTimePop, mTalkAreaPop, mDetailShopPop, mDetailUserPop;
    private RecycDetailedAdapter mDetailedAdapter;
    private SearchDialogFragment mDialogFragment;
    private DetailPresenter mPresenter;
    private ModelDataDTOBean mBean;
    private CalendarDay mData;
    final ArrayList<String> mShop = new ArrayList<>();
    final ArrayList<String> mUser = new ArrayList<>();
    final ArrayList<ShopBean.DataBean> mShopList = new ArrayList<>();
    final ArrayList<UserBean.DataBean> mUserList = new ArrayList<>();
    private List<JmModelDataBean.DataBean.ListBean> mList = new ArrayList<>();
    private List<JmModelDataBean.DataBean.ListBean> mLists = new ArrayList<>();
    private Map<String, String> mMap = new HashMap<>();
    private Map<String, String> mMap1 = new HashMap<>();
    private Dialog loadingDialog;
    private String data1;
    private String itemTag = "";

    public static DetailedFragment newInstance() {
        DetailedFragment fragment = new DetailedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new DetailPresenter(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        return inflater.inflate(R.layout.fragment_detailed, container, false);
    }

    @SuppressLint("NewApi")
    @Override
    public void initData(Bundle savedInstanceState) {
        /**
         * 查询是否有调拨请求
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                initGetRequestDetailData();
            }
        }).start();
        /**
         * 进行首次加载数据
         */
        mBean = new ModelDataDTOBean();
        mBean.setType(Type);
        mBean.setModelType(modelType);
        mPresenter.netLoadData(mBean, Constants.TEL_DETAIL);
        rgTitleLeft.setText("质检");
        rgTitleRight.setText("待核");
        rgTitleLeft.setChecked(true);
        mIvMessage.setImageDrawable(getActivity().getDrawable(R.mipmap.message));
        mIvMessage.setVisibility(View.VISIBLE);
        mIvTitleBackChoose.setVisibility(View.GONE);
        mIvTitleSecondChoose.setImageResource(R.mipmap.home_search);
        mIvTitleSecondChoose.setVisibility(View.VISIBLE);
        mDialogFragment = new SearchDialogFragment();
        mDialogFragment.setSearchDialog(new SearchDialogFragment.SearchDialog() {
            @Override
            public void search(String name) {
                if (name.equals("")) {
                    ToastUtils.ToastCenter("请输入分店名");
                    return;
                }
                mBean.setPageNo(1);
                mBean.setType(Type);
                mBean.setModelType(modelType);
                mBean.setDate(DateChangesUtils.getNowDate());
                mBean.setPageSize(11);
                mBean.setShopName(name);
                mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
            }
        });

        //ReclerView加载数据
        mSsfTel.setLinearLayout();
        mSsfTel.setFooterViewText("正在加载");
        mDetailedAdapter = new RecycDetailedAdapter(mLists, SampleApplicationContext.context, 0);
        mDetailedAdapter.setListener(new RecycDetailedAdapter.ClickListener() {
            @Override
            public void allocation(final int positionList) {//调拨
                if (mList.get(positionList).getStatus()==2){
                    ToastUtils.ToastCenter("已经调拨了，不能再进行调拨了");
                    return;
                }
                mPresenter.getShopData(new ShopBean());
                if (mShop.size() > 0) {
                    DetailedDialog detailedDialog = new DetailedDialog(getActivity(), mShop);
                    detailedDialog.show();
                    detailedDialog.setCancelable(true);
                    detailedDialog.setCanceledOnTouchOutside(true);
                    detailedDialog.setItemClickListenerInterface(new DetailedDialog.ItemClickListenerInterface() {
                        private String id;
                        private String shopId;

                        @Override
                        public void doClickItem(int posision, String shopName) {
                            if (mList.size() > 0) {
                                id = mList.get(positionList).getId() + "";
                                for (int i = 0; i < mShopList.size(); i++) {
                                    if (shopName.equals(mShopList.get(i).getShopName())) {
                                        shopId = mShopList.get(i).getId() + "";
                                    }
                                }

                                if (isSwith) {//质检，待核
                                    if (null != mMap1.get(positionList + "")) {
                                        initRequestDetail(id, shopId, data1, positionList + "");
                                    } else {
                                        initRequestDetail(id, shopId, "", positionList + "");
                                    }
                                } else {
                                    if (null != mMap.get(positionList + "")) {
                                        initRequestDetail(id, shopId, data1, positionList + "");
                                    } else {
                                        initRequestDetail(id, shopId, "", positionList + "");
                                    }
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void warehou(int id) {
                //入库
                getWarehouData(id + "");
            }

            @Override
            public void clickItem(int id) {
                //点击进去详情
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.DETALI_ID, id + "");
                if (null == mData) {
                    intent.putExtra(Constants.DETALI_TIME, "");
                } else {
                    intent.putExtra(Constants.DETALI_TIME, CommonUtils.formatDate(mData.getDate()));
                }
                startActivity(intent);

            }

            @Override
            public void cancle() {//取消
                cancleRequestDetail(2 + "");
            }
        });
        mSsfTel.setAdapter(mDetailedAdapter);
        mSsfTel.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initGetRequestDetailData();
                    }
                }).start();
                /**
                 * 进行首次加载数据
                 */
                if (isSwith == false) {
                    modelType = 0 + "";
                    Type = 2 + "";
                    mBean.setDate(null);
                    mBean.setArea(null);
                    mBean.setShopId(null);
                    mBean.setUserId(null);
                    mBean.setType(Type);
                    mBean.setModelType(modelType);
                    mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                    mSsfTel.setPullLoadMoreCompleted();
                } else {
                    if (isWoo) {
                        isWoo = true;
                        modelType = 1 + "";
                        Type = "";
                        mBean.setDate(null);
                        mBean.setArea(null);
                        mBean.setShopId(null);
                        mBean.setUserId(null);
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                        mSsfTel.setPullLoadMoreCompleted();
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mBean.setType(Type);
                                mBean.setModelType(modelType);
                                mBean.setDate(DateChangesUtils.getNowDate());
                                mPresenter.netAndData(mBean, Constants.UP_LOAD);
                                mSsfTel.setPullLoadMoreCompleted();
                            }
                        }, 800);
                    }
                }

            }

            /**
             * 上拉加载
             */
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mPresenter.netAndData(mBean, Constants.UP_LOAD);
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);
            }
        });

        //回访时间
        mTalkTimePop = PopUtils.initTalkTimePop(SampleApplicationContext.context, new PopUtils.PopListener() {
            @Override
            public void rvListener(int position, String s) {

            }

            @Override
            public void cvListener(CalendarDay date) {
                mData = date;
            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {
            }

            @Override
            public void confirmListener() {
                //进行加载数据
                if (null != mData) {
                    mBean.setPageNo(1);
                    mBean.setPageSize(11);
                    mBean.setType("");
                    mBean.setModelType(modelType);
                    mBean.setDate(CommonUtils.formatDate(mData.getDate()));
                    mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                } else {
                    mBean.setDate(null);
                    mBean.setType(Type);
                    mBean.setModelType(modelType);
                    mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvDetailedTime);
            }

            @Override
            public void resets() {
                mData = null;
            }

            @Override
            public void onRadioChildClikc(int position) {

            }
        });
        // 区域
        ArrayList<String> Area = new ArrayList<>();
        Area.add("A");
        Area.add("B");
        Area.add("C");
        Area.add("D");
        mTalkAreaPop = PopAreaUtils.initDetailAreaPop(SampleApplicationContext.context, Area, new PopAreaUtils.PopListenerDetail() {
            private String position1;

            @Override
            public void rvListener(int position, String s) {
                position1 = position + "";
            }

            @Override
            public void rgReset() {
                position1 = "";
            }

            @Override
            public void confirmListener() {
                if (null == position1) {
                    ToastUtils.ToastCenter("请选择查询区域");
                } else {
                    if (position1.equals("")) {
                        mBean.setArea(null);
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                    } else {
                        mBean.setPageNo(1);
                        mBean.setPageSize(11);
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mBean.setArea(position1 + "");
                        mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                    }
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvDetailedArea);
            }
        });
        /**
         * 店名
         */
        mPresenter.getShopData(new ShopBean());
        mDetailShopPop = PopAreaUtils.initDetailAreaPop(SampleApplicationContext.context, mShop, new PopAreaUtils.PopListenerDetail() {

            private String id;

            @Override
            public void rvListener(int position, String s) {
                if (mShopList.size() > 0) {
                    for (int i = 0; i < mShopList.size(); i++) {
                        if (s.equals(mShopList.get(i).getShopName())) {
                            id = mShopList.get(i).getId() + "";
                        }
                    }
                    return;
                }
                ToastUtils.ToastCenter("暂无数据");
            }

            @Override
            public void rgReset() {
                id = "";
            }

            @Override
            public void confirmListener() {
                if (null == id) {
                    ToastUtils.ToastCenter("请选择查询分店");
                } else {
                    if (id.equals("")) {
                        mBean.setShopId(null);
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                    } else {
                        mBean.setPageNo(1);
                        mBean.setPageSize(11);
                        mBean.setShopId(id);
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                    }
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvDetailedArea);
            }
        });
        /**
         * 用户
         */
        mPresenter.getUserData(new UserBean());
        mDetailUserPop = PopAreaUtils.initDetailAreaPop(SampleApplicationContext.context, mUser, new PopAreaUtils.PopListenerDetail() {
            private String id;

            @Override
            public void rvListener(int position, String s) {
                if (mUserList.size() > 0) {
                    for (int i = 0; i < mUserList.size(); i++) {
                        if (s.equals(mUserList.get(i).getName())) {
                            id = mUserList.get(i).getId() + "";
                        }
                    }
                    return;
                }
                ToastUtils.ToastCenter("暂无数据");
            }

            @Override
            public void rgReset() {
                id = "";
            }

            @Override
            public void confirmListener() {
                if (null == id) {
                    ToastUtils.ToastCenter("请选择查询姓名");
                } else {
                    if (id.equals("")) {
                        mBean.setUserId(null);
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                    } else {
                        mBean.setPageNo(1);
                        mBean.setPageSize(11);
                        mBean.setUserId(id + "");
                        mBean.setDate(DateChangesUtils.getNowDate());
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                    }
                }
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvDetailedArea);
            }
        });
    }


    @OnClick({R.id.iv_title_second_choose, R.id.rg_title_left, R.id.rg_title_right, R.id.iv_message, R.id.tv_detailed_time, R.id.tv_detailed_area, R.id.tv_detailed_shop, R.id.tv_detailed_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_second_choose:
                if (mDialogFragment != null) {
                    if (mDialogFragment.isVisible()) {
                        mDialogFragment.dismiss();
                    } else {
                        mDialogFragment.show(getFragmentManager(), "search");
                    }
                }
                break;
            case R.id.iv_message:
                //邮箱
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.tv_detailed_time:
                //日期
                showPop(mTalkTimePop, mTvDetailedTime);
                break;
            case R.id.tv_detailed_area:
                //区域
                showPop(mTalkAreaPop, mTvDetailedArea);
                break;
            case R.id.tv_detailed_shop:
                //店名
                showPop(mDetailShopPop, mTvDetailedShop);
                break;
            case R.id.tv_detailed_name:
                //姓名
                int loginType = (int) SPUtils.get("loginType", 9);
                if (loginType == 3) {
                    showPop(mDetailUserPop, mTvDetailedName);
                }
                break;
            case R.id.rg_title_left:
                isSwith = false;
                //使用
                modelType = 0 + "";
                Type = 2 + "";
                mBean.setDate(null);
                mBean.setArea(null);
                mBean.setShopId(null);
                mBean.setUserId(null);
                mBean.setType(Type);
                mBean.setModelType(modelType);
                mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                break;
            case R.id.rg_title_right:
                //闲置
                isWoo = true;
                isSwith = true;
                modelType = 1 + "";
                Type = "";
                mBean.setDate(null);
                mBean.setArea(null);
                mBean.setShopId(null);
                mBean.setUserId(null);
                mBean.setType(Type);
                mBean.setModelType(modelType);
                mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                break;
            default:
                break;
        }
    }

    @Override
    public DetailPresenter obtainPresenter() {
        return mPresenter;
    }

    @Override
    public void showNetError() {

    }

    /**
     * 返回数据
     *
     * @param list 返回的数据
     */
    @Override
    public void showData(List<JmModelDataBean.DataBean.ListBean> list) {
        mLlNull.setVisibility(View.VISIBLE);
        mTvNull.setVisibility(View.GONE);
        mLists.clear();
        mLists.addAll(list);
        mDetailedAdapter.notifyDataSetChanged();
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public void showEmpty() {
        mLlNull.setVisibility(View.GONE);
        mTvNull.setVisibility(View.GONE);
    }

    @Override
    public void showNotMore() {
        ToastUtils.ToastCenter("没有更多了");
    }


    @Override
    public void showShopData(List<ShopBean.DataBean> list) {
        mShopList.clear();
        mShopList.addAll(list);
        mShop.clear();
        for (ShopBean.DataBean dataBean : mShopList) {
            mShop.add(dataBean.getShopName());
        }
    }

    @Override
    public void showUserData(List<UserBean.DataBean> list) {
        mUserList.clear();
        mUserList.addAll(list);
        mUser.clear();
        for (UserBean.DataBean dataBean : mUserList) {
            mUser.add(dataBean.getName());
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void showDetailData() {

    }

    @Override
    public void showRequstDetailData(final List<ShowRequestDetailBean.DataBean> bean) {

    }

    @Override
    public void showAreggRequstDetailData() {

    }

    @Override
    public void showNotAreggRequstDetailData() {

    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }

    /**
     * 日期pop
     *
     * @param popupWindow
     * @param textView
     */
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
        PopUtils.hidePop(mTalkTimePop);
    }

    /**
     * 请求调拨
     *
     * @param id
     * @param shopId
     */
    private void initRequestDetail(final String id, final String shopId, final String tId, final String positionList) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call call = OkHttp3Utils.getRequestDetailsData(Constants.HOST + Constants.getRequestDetailList, "id", id, "shopId", shopId, "tId", tId);
                call.enqueue(new Callback() {


                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String data = String.valueOf(new JSONObject(response.body().string()).get("data"));
                            if (data.equals("null")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.ToastCenterLong("正在调拨中，请勿重复操作");
                                        LoadingDialogUtils.closeDialog(loadingDialog);
                                    }
                                });
                            } else {
                                data1 = data;
                                if (isSwith) {
                                    mMap1.put(positionList, data);
                                } else {
                                    mMap.put(positionList, data);
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.ToastCenterLong("调拨成功，等待对方同意");
                                        LoadingDialogUtils.closeDialog(loadingDialog);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 判断是否有请求
     */
    private void initGetRequestDetailData() {
        Response dataSynFromNet = HttpUtils.getDataSynFromNet(Constants.HOST + Constants.GETREQUEST);
        try {
            ShowRequestDetailBean showRequestDetailBean = new Gson().fromJson(dataSynFromNet.body().string(), ShowRequestDetailBean.class);
            final List<ShowRequestDetailBean.DataBean> data = showRequestDetailBean.getData();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /**
                     * 返回调拨数据
                     */
                    if (data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            final int finalI = i;
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext()).setTitle("调拨申请")
                                    .setMessage(data.get(i).getSendShopName() + "申请调拨" + data.get(i).getCombinationName() + "样板间到" + data.get(i).getReceiveShopName())
                                    .setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            isWoo = true;
                                            GetAreggRequstDetailData(Constants.HOST + Constants.NotAreggRequst, data.get(finalI).getId() + "", 1);
                                        }
                                    }).setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            isWoo = true;
                                            GetAreggRequstDetailData(Constants.HOST + Constants.AreggRequst, data.get(finalI).getId() + "", 2);
                                        }
                                    });
                            alertDialog.setIcon(R.mipmap.logoyimei).create().show();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 同意请求
     */
    private void GetAreggRequstDetailData(final String utl, final String id, final int type) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
        loadingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call call = OkHttp3Utils.getIdTalkData(utl, "id", id);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (type == 2) {
                                    int loginType = (int) SPUtils.get("loginType", 9);
                                    if (loginType == 3) {
                                        ToastUtils.ToastCenterLong("此次调拨成功");
                                    } else {
                                        ToastUtils.ToastCenterLong("正在等待管理员同意");
                                    }
                                } else {
                                    ToastUtils.ToastCenterLong("已取消此次调拨");
                                }
                                LoadingDialogUtils.closeDialog(loadingDialog);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * 入库
     */
    private void getWarehouData(String id) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "入库中...");
        Call requestDetailData = OkHttp3Utils.getRequestDetailData(Constants.HOST + Constants.Warehou, "id", id, "modelType", 1 + "");
        requestDetailData.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ModelDataDTOBean mBean = new ModelDataDTOBean();
                        mBean.setType(Type);
                        mBean.setModelType(modelType);
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                        ToastUtils.ToastCenterLong("入库成功");
                        LoadingDialogUtils.closeDialog(loadingDialog);
                    }
                });
            }
        });
    }

    /**
     * 取消调拨
     */
    private void cancleRequestDetail(String id) {
        Call call = OkHttp3Utils.getIdTalkData(Constants.HOST + Constants.CANALE, "id", id);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.ToastCenterLong("已取消调拨");
                    }
                });
            }
        });
    }

    /**
     * 懒加载
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden == false) {
            /**
             * 查询是否有调拨请求
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initGetRequestDetailData();
                }
            }).start();
            /**
             * 进行首次加载数据
             */
            mBean = new ModelDataDTOBean();
            mBean.setType(Type);
            mBean.setModelType(modelType);
            mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
        }
        super.onHiddenChanged(hidden);
    }
}
