package com.jm.mvp.ui.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.exceptions.HyphenateException;
import com.jm.R;
import com.jm.base.Constants;
import com.jm.bean.UserBean;
import com.jm.mvp.base.BaseFragment;
import com.jm.mvp.contract.ContactsContract;
import com.jm.mvp.contract.DetailContract;
import com.jm.mvp.presenter.ContactsPresenter;
import com.jm.mvp.presenter.DetailPresenter;
import com.jm.mvp.ui.activity.ChatDetailsActivity;
import com.jm.mvp.ui.adapter.ContactsAdapter;
import com.jm.mvp.ui.widget.LoadingDialogUtils;
import com.jm.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * 联系人fragment
 */
public class ContactsFragment extends BaseFragment<ContactsPresenter> implements ContactsContract.View {
    @BindView(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_contracts)
    EditText mEtContracts;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.rv_contacts)
    RecyclerView mRvContacts;
    private ContactsAdapter mContactsAdapter;
    ArrayList<String> mList = new ArrayList<>();
    ArrayList<String> mLists = new ArrayList<>();
    private ContactsPresenter mPresenter;
    private Dialog loadingDialog;

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = new ContactsPresenter(this);
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mIvTitleBack.setVisibility(View.GONE);
        mTvTitle.setText("联系人");
        // mPresenter.getUserData(new UserBean());
        getContacts();
        mContactsAdapter = new ContactsAdapter(mList);
        mRvContacts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRvContacts.setAdapter(mContactsAdapter);
        mContactsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), ChatDetailsActivity.class);
                intent.putExtra(Constants.CHAT_ID, mList.get(position));
                startActivity(intent);
            }
        });
        mEtContracts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    getContacts();
                }
            }
        });
    }

    /**
     * 获取好友
     */
    private void getContacts() {
        loadingDialog = LoadingDialogUtils.createLoadingDialog(getContext(), "获取中...");
        mList.clear();
        new Thread(new Runnable() {
            private List<String> usernames;
            @Override
            public void run() {
                EMContactManager emContactManager = EMClient.getInstance().contactManager();
                try {
                    usernames = emContactManager.getAllContactsFromServer();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mList.addAll(usernames);
                            mContactsAdapter.notifyDataSetChanged();
                            LoadingDialogUtils.closeDialog(loadingDialog);
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public ContactsPresenter obtainPresenter() {
        return null;
    }

    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String subway = mEtContracts.getText().toString().trim();
                if (subway.equals("")) {
                    ToastUtils.ToastCenter("请输入查询联系人");
                } else {
                    mLists.clear();
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).contains(subway)) {
                            mLists.add(mList.get(i));
                        }
                    }
                    mList.clear();
                    mList.addAll(mLists);
                    mContactsAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showNotMore() {

    }

    @Override
    public void showUserData(List<UserBean.DataBean> list) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public boolean httpError(int code, String message, int type) {
        return false;
    }
}
