package com.jm.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.JmModelDataBean;
import com.jm.bean.ModelDataDTOBean;
import com.jm.bean.ShopBean;
import com.jm.helper.OkHttp3Utils;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseFragment;
import com.jm.mvp.contract.WarehousContract;
import com.jm.mvp.presenter.WarehousPresenter;
import com.jm.mvp.ui.activity.DetailActivity;
import com.jm.mvp.ui.activity.MessageActivity;
import com.jm.mvp.ui.adapter.RecycDetailedAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.ToastUtils;
import com.jm.utils.permissions.DetailedDialog;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 *miaozhihong 入庫
 */

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
 * 库房统计
 */
public class WarehousFragment extends BaseFragment<WarehousPresenter> implements WarehousContract.View {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
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
    private RecycDetailedAdapter mDetailedAdapter;
    private WarehousPresenter mPresenter;
    private ModelDataDTOBean mBean;
    final ArrayList<String> mShop = new ArrayList<>();
    final ArrayList<ShopBean.DataBean> mShopList = new ArrayList<>();
    private List<JmModelDataBean.DataBean.ListBean> mList = new ArrayList<>();
    private List<JmModelDataBean.DataBean.ListBean> mLists = new ArrayList<>();
    private Dialog loadingDialog;
    private int page = 0;
    private Map<String, String> mMaps = new HashMap<>();
    private String data1;

    public static WarehousFragment newInstance() {
        WarehousFragment fragment = new WarehousFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new WarehousPresenter(this);
        return inflater.inflate(R.layout.fragment_warehous, container, false);
    }

    @SuppressLint("NewApi")
    @Override
    public void initData(Bundle savedInstanceState) {
        /**
         * 进行首次加载数据
         */
        mBean = new ModelDataDTOBean();
        mBean.setModelType(1 + "");
        mPresenter.netLoadData(mBean, Constants.TEL_DETAIL);
        mTitle.setText("库房统计");
        mIvMessage.setImageDrawable(getActivity().getDrawable(R.mipmap.message));
        mIvMessage.setVisibility(View.VISIBLE);
        mIvTitleBackChoose.setVisibility(View.GONE);
        //ReclerView加载数据
        mSsfTel.setLinearLayout();
        mSsfTel.setFooterViewText("正在加载");
        mDetailedAdapter = new RecycDetailedAdapter(mLists, SampleApplicationContext.context, 1);
        mDetailedAdapter.setListener(new RecycDetailedAdapter.ClickListener() {
            @Override
            public void allocation(final int positionList) {//调拨
                if (mList.get(positionList).getStatus() == 2) {
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
                                if (null != mMaps.get(positionList + "")) {
                                    initRequestDetail(id, shopId, data1, positionList + "");
                                } else {
                                    initRequestDetail(id, shopId, "", positionList + "");
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void warehou(int id) {//取消入库
                getWarehouData(id + "");
            }

            @Override
            public void clickItem(int id) {//点击进去详情
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.DETALI_TIME, "");
                intent.putExtra(Constants.DETALI_ID, id + "");
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
                page = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLists.clear();
                        mDetailedAdapter.notifyDataSetChanged();
                        mBean = new ModelDataDTOBean();
                        mBean.setModelType(1 + "");
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);
            }

            /**
             * 上拉加载
             */
            @Override
            public void onLoadMore() {
                page++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ModelDataDTOBean mBean = new ModelDataDTOBean();
                        mBean.setModelType(1 + "");
                        mPresenter.netAndData(mBean, Constants.DOWN_REFRESH);
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);
            }
        });
    }


    @OnClick({R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_message:
                //邮箱
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public WarehousPresenter obtainPresenter() {
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
    public boolean httpError(int code, String message, int type) {
        return false;
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
                                mMaps.put(positionList, data);
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
     * 入库
     */
    private void getWarehouData(String id) {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "入库中...");
        Call requestDetailData = OkHttp3Utils.getRequestDetailData(Constants.HOST + Constants.Warehou, "id", id, "modelType", 0 + "");
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
                        mBean.setModelType(1 + "");
                        mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
                        mDetailedAdapter.notifyDataSetChanged();
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
     * 加载数据
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden == false) {
            mLists.clear();
            mDetailedAdapter.notifyDataSetChanged();
            mBean = new ModelDataDTOBean();
            mBean.setModelType(1 + "");
            mPresenter.netLoadData(mBean, Constants.DOWN_REFRESH);
        }
        super.onHiddenChanged(hidden);
    }
}
