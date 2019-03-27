package com.jm.mvp.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.JmRoomOrigin;
import com.jm.bean.JmStaticsBean;
import com.jm.bean.TalkJmRoomOriginBean;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.helper.OkHttp3Utils;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.ui.activity.MainActivity;
import com.jm.mvp.ui.activity.MessageActivity;
import com.jm.mvp.ui.adapter.RecycStatisticAdapter;
import com.jm.mvp.ui.adapter.RecycTalkAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.PopUtils;
import com.jm.utils.SPUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import q.rorbin.badgeview.QBadgeView;

/**
 * A simple {@link Fragment} subclass.
 * 统计页面
 */
public class StatisticsFragment extends Fragment implements View.OnClickListener {
    private View inflate;
    private TitlesDao titlesDao;
    private SearchDialogFragment mDialogFragment;
    private QBadgeView qBadgeView;
    private RadioButton rgTitleLeft;
    private RadioButton rgTitleRight;
    private ImageView ivMessage;
    private ImageView mIvTitleSecondChoose;
    PullLoadMoreRecyclerView mSsfTel;
    private ImageView mIvTitleBackChoose;
    private RecycStatisticAdapter mStatisticAdapter;
    ArrayList<JmStaticsBean.DataBean> mStatiscs = new ArrayList<>();
    private Dialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_statistics, container, false);
        SPUtils.put("today", 0);
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
        loadingDialog.show();
        initView();
        initData();
        return inflate;
    }

    private void initData() {
        initRight(new JmRoomOrigin());
        mStatisticAdapter = new RecycStatisticAdapter(mStatiscs, SampleApplicationContext.context, Constants.TEL);
        mSsfTel.setAdapter(mStatisticAdapter);
        mSsfTel.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initRight(new JmRoomOrigin());
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);
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
        initFindView();
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
                mStatiscs.clear();
                mStatisticAdapter.notifyDataSetChanged();
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


    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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
            case R.id.rg_title_left:
                loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                SPUtils.put("today", 0);
                initRight(new JmRoomOrigin());
                break;
            case R.id.rg_title_right:
                loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                SPUtils.put("today", 1);
                initRight(new JmRoomOrigin());
                break;
        }
    }


    @SuppressLint("NewApi")
    private void initFindView() {
        rgTitleLeft = inflate.findViewById(R.id.rg_title_left);
        rgTitleRight = inflate.findViewById(R.id.rg_title_right);
        ivMessage = inflate.findViewById(R.id.iv_message);
        mIvTitleBackChoose = inflate.findViewById(R.id.iv_title_back_choose);
        mIvTitleSecondChoose = inflate.findViewById(R.id.iv_title_second_choose);
        mSsfTel = inflate.findViewById(R.id.rv_tel);
        rgTitleLeft.setText("今日");
        rgTitleRight.setText("历史");
        rgTitleLeft.setChecked(true);
        ivMessage.setImageDrawable(getActivity().getDrawable(R.mipmap.message));
        ivMessage.setVisibility(View.VISIBLE);
        mIvTitleBackChoose.setVisibility(View.GONE);
        mIvTitleSecondChoose.setImageResource(R.mipmap.home_search);
        mIvTitleSecondChoose.setOnClickListener(this);
        rgTitleLeft.setOnClickListener(this);
        rgTitleRight.setOnClickListener(this);
        mIvTitleSecondChoose.setVisibility(View.VISIBLE);
    }

    /**
     * title左右切换数据加载
     */
    private void initRight(final JmRoomOrigin jmRoomOrigin) {
        jmRoomOrigin.setType((int) SPUtils.get("today", 0));
        OkHttp3Utils.addTalkDatas(jmRoomOrigin, Constants.HOST + Constants.getStaticeData).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    final JmStaticsBean jmStaticsBean = new Gson().fromJson(response.body().string(), JmStaticsBean.class);
                    final List<JmStaticsBean.DataBean> data = jmStaticsBean.getData();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (data.size() > 0) {
                                mStatiscs.clear();
                                mStatiscs.addAll(data);
                                mStatisticAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                            }
                            LoadingDialogUtils.closeDialog(loadingDialog);
                        }
                    });
                }
            }
        });
    }

}
