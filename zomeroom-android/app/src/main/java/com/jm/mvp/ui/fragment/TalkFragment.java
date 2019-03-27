package com.jm.mvp.ui.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.autonavi.ae.dice.InitConfig;
import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.AreaBean;
import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.GetIdJmBusubess;
import com.jm.bean.JmBusinessManager;
import com.jm.bean.JmRoomOrigin;
import com.jm.bean.SubwayBean;
import com.jm.bean.TalkJmRoomOriginBean;
import com.jm.bean.TelBean;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.bean.UpTelSearchBean;
import com.jm.helper.HttpUtils;
import com.jm.helper.OkHttp3Utils;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.ui.activity.MessageActivity;
import com.jm.mvp.ui.activity.SeeDetailActivity;
import com.jm.mvp.ui.activity.TalkDetailActivity;
import com.jm.mvp.ui.adapter.PopChooseAdapter;
import com.jm.mvp.ui.adapter.RecycStatisticAdapter;
import com.jm.mvp.ui.adapter.RecycTalkAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.jm.utils.CommonUtils;
import com.jm.utils.PopAreaUtils;
import com.jm.utils.PopUtils;
import com.jm.utils.SPUtils;
import com.jm.utils.TelDialog;
import com.jm.utils.ToastUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.validation.TypeInfoProvider;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import q.rorbin.badgeview.QBadgeView;

/**
 * A simple {@link Fragment} subclass.
 * 回访页面
 */
public class TalkFragment extends Fragment implements View.OnClickListener {
    private View inflate;
    private TitlesDao titlesDao;
    private ImageView ivMessage;
    private CalendarDay mIntentionDate;
    private ImageView mIvTitleBackChoose;
    private ImageView ivAdd;
    private QBadgeView qBadgeView;
    private ImageView mIvTitleSecondChoose;
    private PullLoadMoreRecyclerView mSsfTel;
    private RecycTalkAdapter mTalkAdapter;
    private MyPopupWindow mTalkTimePop;
    private SearchDialogFragment mDialogFragment;
    ArrayList<TalkJmRoomOriginBean.DataBean.ListBean> mTalkList = new ArrayList<>();
    private TextView mTvTalkTime;
    private LinearLayout mLlTel;
    private int page = 1;
    ArrayList<TelBean> mTelList = new ArrayList<>();//电话集合
    private Dialog loadingDialog;


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_talk, container, false);
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
        loadingDialog.show();
        initFindView();
        initView();
        initData();
        return inflate;
    }

    private void initData() {
        /**
         * 加载数据
         */
        JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
        jmRoomOrigin.setReturnTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
        initRight(jmRoomOrigin);
        /**
         * 共享适配器
         */
        mTalkAdapter = new RecycTalkAdapter(mTalkList, SampleApplicationContext.context, Constants.TALK);
        mTalkAdapter.setListener(new RecycTalkAdapter.ClickListener() {
            @Override
            public void call(final int id) {
                /**
                 * 打电话
                 */
                ToastUtils.ToastCenter("正在查询联系人，请稍等......");
                String type = "id";
                initTel(Constants.HOST + Constants.details, type, id + "");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttp3Utils.getIdTalkData(Constants.HOST + Constants.upCalId, "id", id + "").enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void delet(int id,int position) {
                int loginType = (int) SPUtils.get("loginType", 9);
                if (loginType == 3) {
                    ToastUtils.ToastCenter("只能删除历史数据");
                }else {
                    ToastUtils.ToastCenter("暂无权限");
                }
            }

            @Override
            public void clickItem(int id) {
                Intent intent = new Intent(getActivity(), TalkDetailActivity.class);
                intent.putExtra(Constants.DATA_ID, id + "");
                startActivity(intent);
            }

            @Override
            public void addBlack(String tel) {

            }
        });
        mSsfTel.setAdapter(mTalkAdapter);
        mSsfTel.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mIntentionDate) {
                            JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                            jmRoomOrigin.setReturnDateStr(CommonUtils.formatDate(mIntentionDate.getDate()));
                            initRight(jmRoomOrigin);
                        } else {
                            JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                            jmRoomOrigin.setReturnTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
                            initRight(jmRoomOrigin);
                        }
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);

            }

            @Override
            public void onLoadMore() {
                page++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mIntentionDate) {
                            JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                            jmRoomOrigin.setPageNo(page);
                            jmRoomOrigin.setReturnDateStr(CommonUtils.formatDate(mIntentionDate.getDate()));
                            initRight(jmRoomOrigin);
                        } else {
                            JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                            jmRoomOrigin.setReturnTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
                            jmRoomOrigin.setPageNo(page);
                            initRight(jmRoomOrigin);
                        }
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
                mIntentionDate = date;
            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {

            }

            @Override
            public void confirmListener() {
                //进行加载数据
                JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                jmRoomOrigin.setPageNo(1);
                if (null != mIntentionDate) {
                    jmRoomOrigin.setReturnDateStr(CommonUtils.formatDate(mIntentionDate.getDate()));
                } else {
                    jmRoomOrigin.setReturnTimeStr(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
                }
                initRight(jmRoomOrigin);
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(mTvTalkTime);
            }

            @Override
            public void resets() {
                mIntentionDate = null;
            }

            @Override
            public void onRadioChildClikc(int position) {

            }
        });
    }


    @SuppressLint("NewApi")
    private void initView() {
        //未读数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "jm_notread.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        titlesDao = daoSession.getTitlesDao();
        mDialogFragment = new SearchDialogFragment();
        mDialogFragment.setSearchDialog(new SearchDialogFragment.SearchDialog() {

            @Override
            public void search(String tel) {
                //进行关键字检索
                if (tel.equals("")) {
                    Toast.makeText(getContext(), "请输入分店名", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            LoadingDialogUtils.closeDialog(loadingDialog);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                mTalkList.clear();
                mTalkAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
            }

        });
        mSsfTel.setLinearLayout();
        mSsfTel.setFooterViewText("正在加载");

        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
                qBadgeView.setVisibility(View.GONE);
            }
        });

    }


    public static TalkFragment newInstance() {
        TalkFragment fragment = new TalkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 可见状态下进行显示badgView的数量
     */
    @Override
    public void onResume() {
        super.onResume();
        final List<Titles> titles = titlesDao.loadAll();
        qBadgeView = new QBadgeView(getActivity());
        qBadgeView.setFocusable(true);
        qBadgeView.bindTarget(ivMessage);//设置要显示消息提示的View 控件
        if (titles.size() == 0) {
            qBadgeView.setVisibility(View.GONE);
        } else if (titles.size() > 0) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    qBadgeView.setBadgeNumber(titles.size());//设置Badge数字
                    qBadgeView.setBadgeTextColor(Color.parseColor("#ff123564"));//设置文本颜色
                    qBadgeView.setExactMode(true);//设置是否显示精确模式数值
                }
            });
        } else {
            qBadgeView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_second_choose:
                if (mDialogFragment != null) {
                    if (mDialogFragment.isVisible()) {
                        mDialogFragment.dismiss();
                    } else {
                        mDialogFragment.show(getFragmentManager(), "search");
                    }
                }
                break;
            case R.id.tv_talk_time:
                showPop(mTalkTimePop, mTvTalkTime);

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initFindView() {
        ivMessage = inflate.findViewById(R.id.iv_message);
        mTvTalkTime = inflate.findViewById(R.id.tv_talk_time);
        mIvTitleSecondChoose = inflate.findViewById(R.id.iv_title_second_choose);
        mIvTitleBackChoose = inflate.findViewById(R.id.iv_title_back_choose);
        mSsfTel = inflate.findViewById(R.id.rv_talk);
        mLlTel = inflate.findViewById(R.id.ll_tel);
        ivMessage.setImageDrawable(getActivity().getDrawable(R.mipmap.message));
        ivMessage.setVisibility(View.VISIBLE);
        mIvTitleBackChoose.setVisibility(View.GONE);
        mIvTitleSecondChoose.setImageResource(R.mipmap.home_search);
        mIvTitleSecondChoose.setOnClickListener(this);
        mIvTitleSecondChoose.setVisibility(View.VISIBLE);
        mTvTalkTime.setOnClickListener(this);
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
        PopUtils.hidePop(mTalkTimePop);
    }


    /**
     * 加载数据
     */
    /**
     * title左右切换数据加载
     */

    private void initRight(JmRoomOrigin jmRoomOrigin) {
        jmRoomOrigin.setPageNo(1);
        jmRoomOrigin.setPageSize(11);
        jmRoomOrigin.setCooperationIntention(9999);
        OkHttp3Utils.addTalkDatas(jmRoomOrigin, Constants.HOST + Constants.getTalkData).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    final TalkJmRoomOriginBean talkJmRoomOriginBean = new Gson().fromJson(response.body().string(), TalkJmRoomOriginBean.class);
                    final List<TalkJmRoomOriginBean.DataBean.ListBean> list = talkJmRoomOriginBean.getData().getList();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (talkJmRoomOriginBean.getData().getTotal() > 0) {
                                mTalkList.clear();
                                mTalkList.addAll(list);
                                mTalkAdapter.notifyDataSetChanged();
                            } else {
                                mTalkList.clear();
                                mTalkAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "没有数据了", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialogUtils.closeDialog(loadingDialog);
                        }
                    });
                }
            }
        });
    }

    /**
     * 打电话
     *
     * @param url
     * @param type
     * @param id
     */
    private void initTel(final String url, final String type, final String id) {
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<GetIdJmBusubess.DataBean.ManagerListBean> managerList = null;
                                try {
                                    managerList = new Gson().fromJson(response.body().string(), GetIdJmBusubess.class).getData().getManagerList();
                                    mTelList.clear();
                                    if (managerList.size() > 0) {
                                        for (int i = 0; i < managerList.size(); i++) {
                                            mTelList.add(new TelBean(managerList.get(i).getName(), managerList.get(i).getTel()));
                                        }
                                        TelDialog telDialog = new TelDialog(getActivity(), mTelList);
                                        telDialog.show();
                                        telDialog.setCancelable(true);
                                        telDialog.setCanceledOnTouchOutside(true);
                                        telDialog.setItemClickListenerInterface(new TelDialog.ItemClickListenerInterface() {
                                            @Override
                                            public void doClickItem(String tel) {
                                                CommonUtils.callTel(tel, getActivity());
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        }, 800);
    }

}
