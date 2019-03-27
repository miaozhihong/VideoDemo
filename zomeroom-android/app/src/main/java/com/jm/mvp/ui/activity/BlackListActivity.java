package com.jm.mvp.ui.activity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.BlackListBean;
import com.jm.helper.SampleApplicationContext;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.contract.BlackListContract;
import com.jm.mvp.presenter.BlackListPresenter;
import com.jm.mvp.ui.adapter.BlackTelAdapter;
import com.jm.mvp.ui.widget.MyPopupWindow;
import com.jm.utils.AlertDialogUtils;
import com.jm.utils.CommonUtils;
import com.jm.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author pc 张立男
 * @Description BlackListActivity 黑名单页面
 * @date 2018/2/26 14:59
 * o(＞﹏＜)o
 */
public class BlackListActivity extends BaseActivity<BlackListPresenter> implements BlackListContract.View {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_title_second)
    ImageView mIvTitleSecond;
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.et_black_tel)
    EditText mEtBlackTel;
    @BindView(R.id.tv_black_select)
    TextView mTvBlackSelect;
    @BindView(R.id.rv_black)
    RecyclerView mRvBlack;
    @BindView(R.id.tv_black_null)
    TextView mTvBlackNull;
    private MyPopupWindow mAddPop;
    private BlackTelAdapter mBlackTelAdapter;
    private BlackListPresenter mPresenter;

    @Override
    public int initView(Bundle savedInstanceState) {
        mPresenter = new BlackListPresenter(this);
        return R.layout.activity_black_list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mIvTitleSecond.setVisibility(View.VISIBLE);
        mIvTitleSecond.setImageResource(R.mipmap.black_add);
        mTvTitle.setText("我的黑名单");

        mAddPop = initAddBlack();


        mBlackTelAdapter = new BlackTelAdapter(new ArrayList<BlackListBean.DataBean.ListBean>(), SampleApplicationContext.context);
        mRvBlack.setLayoutManager(new LinearLayoutManager(BlackListActivity.this));
        mRvBlack.setAdapter(mBlackTelAdapter);
        mBlackTelAdapter.setListener(new BlackTelAdapter.DelListener() {
            @Override
            public void del(final int position) {
                AlertDialogUtils.customAlertDialog(BlackListActivity.this, "提示", "确定要删除吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delBlackTel(mPresenter.getId(position));
                    }
                });
            }
        });
        mPresenter.netAndData("", Constants.DOWN_REFRESH);
    }

    @Override
    public BlackListPresenter obtainPresenter() {
        return mPresenter;
    }

    @OnClick({R.id.iv_title_back, R.id.iv_title_second, R.id.tv_black_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            //添加黑名单
            case R.id.iv_title_second:
                if (mAddPop != null) {
                    if (!mAddPop.isShowing()) {
                        mAddPop.showAtLocation(view, Gravity.CENTER, 0, 0);
                        CommonUtils.setBackgroundAlpha(BlackListActivity.this, 0.6f);
                    } else {
                        mAddPop.dismiss();
                    }
                }
                break;
            //查询黑名单
            case R.id.tv_black_select:
                String blackTel = CommonUtils.getEditText(mEtBlackTel);
                if (CommonUtils.phoneNum(blackTel)) {
                    mPresenter.netAndData(blackTel, Constants.DOWN_REFRESH);
                } else if (TextUtils.isEmpty(blackTel)) {
                    mPresenter.netAndData("", Constants.DOWN_REFRESH);
                } else {
                    ToastUtils.ToastCenter("请输入正确的手机号码");
                }
                break;
            default:
                break;
        }
    }

    public MyPopupWindow initAddBlack() {
        View view = LayoutInflater.from(BlackListActivity.this).inflate(R.layout.pop_add_black, null);
        final MyPopupWindow addBlackPop = new MyPopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addBlackPop.setBackgroundDrawable(new ColorDrawable(0));
        //不设置true无法弹起输入框
        addBlackPop.setFocusable(true);
        addBlackPop.setOutsideTouchable(false);
        addBlackPop.setTouchable(true);

        ImageView ivBlackClose = (ImageView) view.findViewById(R.id.iv_pop_add_close);
        final EditText etBlackTel = (EditText) view.findViewById(R.id.et_pop_add_tel);
        final EditText etBlackReason = (EditText) view.findViewById(R.id.et_pop_add_reason);
        TextView tvBlackConfirm = (TextView) view.findViewById(R.id.tv_pop_add_confirm);

        ivBlackClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addBlackPop.isShowing()) {
                    addBlackPop.dismiss();
                }
            }
        });
        tvBlackConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = CommonUtils.getEditText(etBlackTel);
                String reason = CommonUtils.getEditText(etBlackReason);
                //添加成功后清除 edit的内容
                mPresenter.addBlackTel(tel, reason);
                etBlackReason.setText("");
                etBlackTel.setText("");
            }
        });
        addBlackPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommonUtils.setBackgroundAlpha(BlackListActivity.this, 1f);
            }
        });
        return addBlackPop;
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void display(List<BlackListBean.DataBean.ListBean> list) {
        mTvBlackNull.setVisibility(View.GONE);
        mBlackTelAdapter.replace(list);
    }

    @Override
    public void showEmpty() {
        mTvBlackNull.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNotMore() {
        ToastUtils.ToastCenter("没有更多了");
    }

    @Override
    public void refresh() {
        mPresenter.netAndData("", Constants.DOWN_REFRESH);
        if (mAddPop != null) {
            if (mAddPop.isShowing()) {
                mAddPop.dismiss();
            }
        }
    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }
}
