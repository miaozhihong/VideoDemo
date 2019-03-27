package com.jm.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.SubwayBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.SubwayContract;
import com.jm.mvp.presenter.SubwayPresenter;
import com.jm.mvp.ui.adapter.BaseRvAdapter;
import com.jm.mvp.ui.adapter.SubwayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author pc 张立男
 * @Description SubwayActivity 地铁选择页面
 * @date 2018/3/3 17:27
 * o(＞﹏＜)o
 */

public class SubwayActivity extends BaseActivity<SubwayPresenter> implements SubwayContract.View {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_subway)
    EditText mEtSubway;
    @BindView(R.id.tv_subway_search)
    TextView mTvSubwaySearch;
    @BindView(R.id.rv_subway)
    RecyclerView mRvSubway;
    @BindView(R.id.tv_subway_null)
    TextView mTvSubwayNull;
    /**
     * 需要展示的地铁站
     */
//    private List<String> mSubwayList;
    private SubwayAdapter mSubwayAdapter;
    private SubwayPresenter mPresenter;

    /**
     * 全部的地铁站
     */
//    private List<String> mAllSubway;
    @Override
    public int initView(Bundle savedInstanceState) {
        mPresenter = new SubwayPresenter(this);
        return R.layout.activity_subway;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mTvTitle.setText("地铁查询");
//        mSubwayList = new ArrayList<>();
//        mAllSubway = getSubwayList();
//        mSubwayList.addAll(mAllSubway);
        mSubwayAdapter = new SubwayAdapter(new ArrayList<SubwayBean.DataBean>(), SampleApplicationContext.context);
        mRvSubway.setLayoutManager(new LinearLayoutManager(SampleApplicationContext.context, LinearLayoutManager.VERTICAL, false));
        mRvSubway.setAdapter(mSubwayAdapter);

        mSubwayAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra(Constants.SUBWAY, mPresenter.getSubway(position));
                intent.putExtra(Constants.SUBWAY_ID, mPresenter.getSubwayId(position));
                setResult(Constants.NO_USE, intent);
                finish();
            }
        });
        mPresenter.netAndData("");
    }

    @Override
    public SubwayPresenter obtainPresenter() {
        return mPresenter;
    }

    @OnClick({R.id.iv_title_back, R.id.tv_subway_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_subway_search:
                String subway = mEtSubway.getText().toString().trim();
                if (TextUtils.isEmpty(subway) || "请输入地铁站".equals(subway)) {
                    mPresenter.netAndData("");
                } else {
                    mPresenter.netAndData(subway);
                }
                break;
            default:
                break;
        }
    }
/// 本地获取地铁数据
//    private List<String> getSubwayList() {
//        List<String> list = new ArrayList<>();
//        JSONObject jsonObject = JSON.parseObject(CommonUtils.getJson("subway.json", this));
//        Gson gson = new Gson();
//        SubwayBean bean = gson.fromJson(jsonObject.toString(), SubwayBean.class);
//        List<SubwayBean.LBean> beanL = bean.getL();
//        // 0 的时候是 s1 线
//        for (int i = 1; i < beanL.size(); i++) {
//            List<SubwayBean.LBean.StBean> st = beanL.get(i).getSt();
//            for (int j = 0; j < st.size(); j++) {
//                list.add(st.get(j).getN());
//            }
//        }
//        return list;
//    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showData(List<SubwayBean.DataBean> list) {
        mTvSubwayNull.setVisibility(View.GONE);
        mRvSubway.setVisibility(View.VISIBLE);
        mSubwayAdapter.replace(list);
    }

    @Override
    public void showEmpty() {
        mTvSubwayNull.setVisibility(View.VISIBLE);
        mRvSubway.setVisibility(View.GONE);
    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }
}
