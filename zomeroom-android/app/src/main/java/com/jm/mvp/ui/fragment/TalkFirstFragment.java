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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
 * 咨询首页面
 */
public class TalkFirstFragment extends Fragment implements View.OnClickListener {
    private View inflate;
    private SearchDialogFragment mDialogFragment;
    private TitlesDao titlesDao;
    private ImageView ivMessage;
    private RadioButton rgTitleLeft;
    private RadioButton rgTitleRight;
    private ImageView mIvTitleBackChoose;
    private ImageView ivAdd;
    private QBadgeView qBadgeView;
    private ImageView mIvTitleSecondChoose;
    private PullLoadMoreRecyclerView mSsfTel;
    private MyPopupWindow mTalkAreaPop;
    private MyPopupWindow mTalkShopPop;
    private MyPopupWindow mTalkIntentionPop;
    private CalendarDay mIntentionDate;
    private PopChooseAdapter mIntentionPopAdapter;
    private LinearLayout mLlTel;
    private TextView tvTalkArea;
    private boolean isClear = false;
    private int refresh = 1;
    private int are = 9;//判断位置
    private int areId = 9;//判断id
    private int intetnId = 9999;//意向
    private int timeId = 9;//time
    private String dataId = "";//data
    private boolean reset = false;
    private boolean isReset = false;
    private boolean isAdmin = false;
    private boolean isIntentionrest = false;
    private boolean intentionrest = false;
    private TextView tvTalkShop;
    private TextView tvTalkIntention;
    private RecycTalkAdapter mTalkAdapter;
    ArrayList<String> mArea = new ArrayList<>();
    ArrayList<AreaBean.DataBean> mData = new ArrayList<>();//区域
    ArrayList<AreaBean.DataBean> mSub = new ArrayList<>();//地铁
    ArrayList<AreaBean.DataBean> mSubs = new ArrayList<>();//地铁
    ArrayList<TelBean> mTelList = new ArrayList<>();//电话集合
    ArrayList<TalkJmRoomOriginBean.DataBean.ListBean> mTalkList = new ArrayList<>();
    private TalkJmRoomOriginBean obj = null;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mData.clear();
                    mData.addAll((ArrayList<AreaBean.DataBean>) msg.obj);
                    break;
                case 2:
                    mSub.clear();
                    mSub.addAll((ArrayList<AreaBean.DataBean>) msg.obj);
                    break;

                case 4:
                    obj = (TalkJmRoomOriginBean) msg.obj;
                    if (obj.getData().getTotal() > 0) {
                        List<TalkJmRoomOriginBean.DataBean.ListBean> mLoadMore = obj.getData().getList();
                        if (mLoadMore.size() > 0) {
                            for (int i = 0; i < mLoadMore.size(); i++) {
                                TalkJmRoomOriginBean.DataBean.ListBean listBean = mLoadMore.get(i);
                                mTalkList.add(listBean);
                            }
                            mTalkAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtils.ToastCenter("没有数据了");
                    }
                    break;
            }
        }
    };
    private Dialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_talk_first, container, false);
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
        loadingDialog.show();
        SPUtils.put("seareType", 0);
        initView();
        initLoad();
        initData();
        return inflate;
    }

    private void initLoad() {
        // 地区
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

                    // 地铁
                    Map<String, String> mMap = new HashMap<>();
                    mMap.put("name", "");
                    Response dataSynFromNet1 = HttpUtils.postDataSynToNet(Constants.HOST + Constants.SUBWAY_TALK, mMap);
                    try {
                        SubwayBean subwayBean = new Gson().fromJson(dataSynFromNet1.body().string(), SubwayBean.class);
                        if (subwayBean.getData().size() > 0) {
                            List<SubwayBean.DataBean> data = subwayBean.getData();
                            mSub.clear();
                            for (int i = 0; i < data.size(); i++) {
                                AreaBean.DataBean dataBean = new AreaBean.DataBean();
                                dataBean.setId(data.get(i).getId());
                                dataBean.setName(data.get(i).getStationName());
                                dataBean.setCreateTime(data.get(i).getCreateTime());
                                dataBean.setUpdateTime(data.get(i).getUpdateTime());
                                dataBean.setIsDel(data.get(i).getIsDel());
                                dataBean.setCode(0);
                                mSubs.add(dataBean);
                            }
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = mSubs;
                            handler.sendMessage(message);
                        }

                        initRight(new JmRoomOrigin(), 9999);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        };
        new Thread(runnable).start();
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
                if (tel.equals("")) {
                    ToastUtils.ToastCenter("请输入分店名");
                    return;
                }
                loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                jmRoomOrigin.setShopName(tel);
                initRight(jmRoomOrigin, 9999);

            }
        });


        ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
                qBadgeView.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        // 位置
        Collections.addAll(mArea, "区域", "地铁");
        mTalkAreaPop = PopAreaUtils.initAreaPop(SampleApplicationContext.context, mArea, mData, mSub, new PopAreaUtils.PopListener() {
            private int position1;
            private AreaBean.DataBean rightData;
            private String s1;

            @Override
            public void rvListener(int position, String s) {
                //区域or地铁
                position1 = position;
                s1 = s;
            }

            @Override
            public void rgListener(int position, AreaBean.DataBean dataBean) {
                rightData = dataBean;
            }

            @Override
            public void rgReset() {
                reset = true;
            }

            @Override
            public void cvListener(CalendarDay date) {
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(tvTalkArea);
            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {
                JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                if (position1 == 0) {
                    if (null != rightData) {
                        reset = false;
                        are = position1;
                        areId = rightData.getId();
                        jmRoomOrigin.setAreaId(rightData.getId());
                    } else {
                        ToastUtils.ToastCenter("请选择区域");
                    }
                } else {
                    if (null != rightData) {
                        reset = false;
                        are = position1;
                        areId = rightData.getId();
                        jmRoomOrigin.setStationId(rightData.getId());
                    } else {
                        ToastUtils.ToastCenter("请选择地铁");
                    }
                }
                initRight(jmRoomOrigin, 9999);
            }
        });

        // 意向
        ArrayList<String> intentionList = CommonUtils.getTalkIntentionList();
        mTalkIntentionPop = PopUtils.initTalkPop(SampleApplicationContext.context, intentionList, new PopUtils.PopListener() {
            private int radioPositio = 0;
            private String s3;

            @Override
            public void rvListener(int position, String s) {
                s3 = s;
                refresh=1;
            }

            @Override
            public void cvListener(CalendarDay date) {
                mIntentionDate = date;
            }

            @Override
            public void disMissListener() {
                PopUtils.tvDown(tvTalkIntention);
            }

            @Override
            public void resets() {
                reset = true;
            }

            @Override
            public void onRadioChildClikc(int position) {
                radioPositio = position;
            }

            @Override
            public void confirmListener(PopChooseAdapter adapter) {
                JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                if (s3 != null) {
                    intentionrest = true;
                    if (s3.equals("跟进")) {
                        intetnId = 0;
                        if (radioPositio == Constants.WEEK) {
                            timeId = 1;
                            dataId = "";
                            jmRoomOrigin.setTime(1);
                            initRight(jmRoomOrigin, 0);
                        } else if (radioPositio == Constants.HALF) {
                            timeId = 2;
                            dataId = "";
                            jmRoomOrigin.setTime(2);
                            initRight(jmRoomOrigin, 0);
                        } else if (radioPositio == Constants.MOTH) {
                            timeId = 3;
                            dataId = "";
                            jmRoomOrigin.setTime(3);
                            initRight(jmRoomOrigin, 0);
                        } else {
                            if (null != mIntentionDate) {
                                timeId = 0;
                                dataId = CommonUtils.formatDate(mIntentionDate.getDate());
                                jmRoomOrigin.setDate(CommonUtils.formatDate(mIntentionDate.getDate()));
                            }
                            initRight(jmRoomOrigin, 0);
                        }
                    } else if (s3.equals("pass")) {
                        intetnId = 1;
                        if (radioPositio == Constants.WEEK) {
                            timeId = 1;
                            dataId = "";
                            jmRoomOrigin.setTime(1);
                            initRight(jmRoomOrigin, 1);
                        } else if (radioPositio == Constants.HALF) {
                            timeId = 2;
                            dataId = "";
                            jmRoomOrigin.setTime(2);
                            initRight(jmRoomOrigin, 1);
                        } else if (radioPositio == Constants.MOTH) {
                            timeId = 3;
                            dataId = "";
                            jmRoomOrigin.setTime(3);
                            initRight(jmRoomOrigin, 1);
                        } else {
                            if (null != mIntentionDate) {
                                timeId = 0;
                                dataId = CommonUtils.formatDate(mIntentionDate.getDate());
                                jmRoomOrigin.setDate(CommonUtils.formatDate(mIntentionDate.getDate()));
                            }
                            initRight(jmRoomOrigin, 1);
                        }
                    } else if (s3.equals("签约")) {
                        intetnId = 2;
                        if (radioPositio == Constants.WEEK) {
                            timeId = 1;
                            dataId = "";
                            jmRoomOrigin.setTime(1);
                            initRight(jmRoomOrigin, 2);
                        } else if (radioPositio == Constants.HALF) {
                            timeId = 2;
                            dataId = "";
                            jmRoomOrigin.setTime(2);
                            initRight(jmRoomOrigin, 2);
                        } else if (radioPositio == Constants.MOTH) {
                            timeId = 3;
                            dataId = "";
                            jmRoomOrigin.setTime(3);
                            initRight(jmRoomOrigin, 2);
                        } else {
                            if (null != mIntentionDate) {
                                timeId = 0;
                                dataId = CommonUtils.formatDate(mIntentionDate.getDate());
                                jmRoomOrigin.setDate(CommonUtils.formatDate(mIntentionDate.getDate()));
                            }
                            initRight(jmRoomOrigin, 2);
                        }

                    } else if (s3.equals("全部")) {
                        intetnId = 9999;
                        if (radioPositio == Constants.WEEK) {
                            timeId = 1;
                            dataId = "";
                            jmRoomOrigin.setTime(1);
                            initRight(jmRoomOrigin, 9999);
                        } else if (radioPositio == Constants.HALF) {
                            timeId = 2;
                            dataId = "";
                            jmRoomOrigin.setTime(2);
                            initRight(jmRoomOrigin, 9999);
                        } else if (radioPositio == Constants.MOTH) {
                            timeId = 3;
                            dataId = "";
                            jmRoomOrigin.setTime(3);
                            initRight(jmRoomOrigin, 9999);
                        } else {
                            if (null != mIntentionDate) {
                                timeId = 0;
                                dataId = CommonUtils.formatDate(mIntentionDate.getDate());
                                jmRoomOrigin.setDate(CommonUtils.formatDate(mIntentionDate.getDate()));
                            }
                            initRight(jmRoomOrigin, 9999);
                        }
                    } else {
                        ToastUtils.ToastCenter("请选择意向");
                    }
                } else {
                    ToastUtils.ToastCenter("请选择意向");
                }
            }

            @Override
            public void confirmListener() {

            }
        });


        //ReclerView加载数据
        mSsfTel.setLinearLayout();
        mSsfTel.setFooterViewText("正在加载");
        mTalkAdapter = new RecycTalkAdapter(mTalkList, SampleApplicationContext.context,Constants.CONTEXT);
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
                /**
                 * 删除
                 */
                int loginType = (int) SPUtils.get("loginType", 9);
                if (loginType == 3) {
                    if (isAdmin){
                        initDelet(id,position);
                     return;
                    }
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
                reset = true;
                intentionrest = false;
                refresh = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initRight(new JmRoomOrigin(), 9999);
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);
            }

            @Override
            public void onLoadMore() {
                refresh++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //位置
                        if (isReset) {
                            if (reset) {
                                JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                jmRoomOrigin.setCooperationIntention(9999);
                                initRest(jmRoomOrigin);
                            } else {
                                if (are != 9 && areId != 9) {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    if (are == 0) {
                                        jmRoomOrigin.setAreaId(areId);
                                    } else {
                                        jmRoomOrigin.setStationId(areId);
                                    }
                                    jmRoomOrigin.setCooperationIntention(9999);
                                    initRest(jmRoomOrigin);
                                } else {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    jmRoomOrigin.setCooperationIntention(9999);
                                    initRest(jmRoomOrigin);
                                }
                            }
                        }
                        if (isIntentionrest) {
                            //意向
                            if (intentionrest == false) {
                                JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                jmRoomOrigin.setCooperationIntention(9999);
                                initRest(jmRoomOrigin);
                            } else {
                                if (timeId == 0) {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    jmRoomOrigin.setCooperationIntention(intetnId);
                                    jmRoomOrigin.setDate(dataId);
                                    initRest(jmRoomOrigin);
                                } else if (timeId == 1) {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    jmRoomOrigin.setCooperationIntention(intetnId);
                                    jmRoomOrigin.setTime(1);
                                    jmRoomOrigin.setDate(dataId);
                                    initRest(jmRoomOrigin);
                                } else if (timeId == 2) {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    jmRoomOrigin.setCooperationIntention(intetnId);
                                    jmRoomOrigin.setTime(2);
                                    initRest(jmRoomOrigin);
                                } else if (timeId == 3) {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    jmRoomOrigin.setCooperationIntention(intetnId);
                                    jmRoomOrigin.setTime(3);
                                    jmRoomOrigin.setDate(dataId);
                                    initRest(jmRoomOrigin);
                                } else {
                                    JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                                    jmRoomOrigin.setCooperationIntention(intetnId);
                                    //jmRoomOrigin.setDate(dataId);
                                    initRest(jmRoomOrigin);
                                }
                            }
                        }else {
                            JmRoomOrigin jmRoomOrigin = new JmRoomOrigin();
                            jmRoomOrigin.setCooperationIntention(9999);
                            initRest(jmRoomOrigin);
                        }
                        mSsfTel.setPullLoadMoreCompleted();
                    }
                }, 800);
            }
        });
    }

    public static TalkFirstFragment newInstance() {
        TalkFirstFragment fragment = new TalkFirstFragment();
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

            case R.id.tv_talk_area:
                isReset = true;
                isIntentionrest = false;
                showPop(mTalkAreaPop, tvTalkArea);
                break;

            case R.id.tv_talk_shop:
                break;
            case R.id.rg_title_left:
                reset = true;
                isReset = false;
                isAdmin = false;
                isIntentionrest = false;
                loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                SPUtils.put("seareType", 0);
                initRight(new JmRoomOrigin(), 9999);
                break;
            case R.id.rg_title_right:
                reset = true;
                isReset = false;
                isAdmin = true;
                isIntentionrest = false;
                loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "加载中...");
                loadingDialog.show();
                SPUtils.put("seareType", 1);
                initRight(new JmRoomOrigin(), 9999);
                break;

            case R.id.tv_talk_intention:
                reset = true;
                isReset = false;
                isIntentionrest = true;
                showPop(mTalkIntentionPop, tvTalkIntention);
                break;

            case R.id.iv_add:
                startActivity(new Intent(getContext(), TalkDetailActivity.class));
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
        PopUtils.hidePop(mTalkAreaPop);
        PopUtils.hidePop(mTalkShopPop);
        PopUtils.hidePop(mTalkIntentionPop);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initFindView() {
        rgTitleLeft = inflate.findViewById(R.id.rg_title_left);
        rgTitleRight = inflate.findViewById(R.id.rg_title_right);
        tvTalkArea = inflate.findViewById(R.id.tv_talk_area);
        tvTalkShop = inflate.findViewById(R.id.tv_talk_shop);
        tvTalkIntention = inflate.findViewById(R.id.tv_talk_intention);
        tvTalkArea.setOnClickListener(this);
        tvTalkShop.setOnClickListener(this);
        tvTalkIntention.setOnClickListener(this);
        ivMessage = inflate.findViewById(R.id.iv_message);
        mIvTitleSecondChoose = inflate.findViewById(R.id.iv_title_second_choose);
        mLlTel = inflate.findViewById(R.id.ll_tel);
        mIvTitleBackChoose = inflate.findViewById(R.id.iv_title_back_choose);
        ivAdd = inflate.findViewById(R.id.iv_add);
        ivAdd.setOnClickListener(this);
        mSsfTel = inflate.findViewById(R.id.rv_talk);
        rgTitleLeft.setText("今日");
        rgTitleRight.setText("历史");
        rgTitleLeft.setChecked(true);
        rgTitleLeft.setOnClickListener(this);
        rgTitleRight.setOnClickListener(this);
        ivMessage.setImageDrawable(getActivity().getDrawable(R.mipmap.message));
        ivMessage.setVisibility(View.VISIBLE);
        mIvTitleBackChoose.setVisibility(View.GONE);
        mIvTitleSecondChoose.setImageResource(R.mipmap.home_search);
        mIvTitleSecondChoose.setOnClickListener(this);
        mIvTitleSecondChoose.setVisibility(View.VISIBLE);

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


    /**
     * title左右切换数据加载
     */

    private void initRight(JmRoomOrigin jmRoomOrigin, int type) {
        jmRoomOrigin.setPageNo(1);
        jmRoomOrigin.setPageSize(11);
        jmRoomOrigin.setCooperationIntention(type);
        jmRoomOrigin.setType((int) SPUtils.get("seareType", 0));
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
                                ToastUtils.ToastCenter("没有数据了");
                            }
                            LoadingDialogUtils.closeDialog(loadingDialog);
                        }
                    });
                }
            }
        });
    }

    private void initRest(JmRoomOrigin jmRoomOrigin) {

        //加载数据
        jmRoomOrigin.setPageNo(refresh);
        jmRoomOrigin.setPageSize(11);
        jmRoomOrigin.setType((int) SPUtils.get("seareType", 0));
        OkHttpClient okHttpClient = OkHttp3Utils.getOkHttpClient();
        //创建Request
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(jmRoomOrigin));
        Request request = new Request.Builder()
                .url(Constants.HOST + Constants.getTalkData)
                .method("POST", body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    //请求回访所有的数据
                    TalkJmRoomOriginBean talkJmRoomOriginBean = new Gson().fromJson(response.body().string(), TalkJmRoomOriginBean.class);
                    Message message = Message.obtain();
                    message.what = 4;
                    message.obj = talkJmRoomOriginBean;
                    handler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 删除
     */
    private void initDelet(final int id,final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttp3Utils.getIdTalkData(Constants.HOST + Constants.upDelet, "id", id + "").enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.code()==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response.body().string());
                                Object data = jsonObject.get("data");
                                if (null!=data&&!data.equals("") && Integer.valueOf(data.toString())==1){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mTalkList.remove(position);
                                            mTalkAdapter.notifyDataSetChanged();
                                            ToastUtils.ToastCenter("删除成功");
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.ToastCenter("删除失败");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }
}
