package com.jm.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jm.R;
import com.jm.bean.DaoMaster;
import com.jm.bean.DaoSession;
import com.jm.bean.Titles;
import com.jm.bean.TitlesDao;
import com.jm.bean.UMMessage;
import com.jm.mvp.base.BaseActivity;
import com.jm.mvp.ui.adapter.AlreadyReadAdapter;
import com.jm.mvp.ui.adapter.NotReadAdapter;
import com.jm.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.yktour.network.base.IPresenter;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<UMMessage> messages = new ArrayList<>();
    private ArrayList<Titles> mNotRead = new ArrayList<>();
    private ArrayList<Titles> mAlreadyRead = new ArrayList<>();
    private ImageView back;
    private RecyclerView not_read;
    private RecyclerView already_read;
    private NotReadAdapter notReadAdapter;
    private AlreadyReadAdapter alreadyReadAdapter;
    private boolean isRead = false;
    private DaoSession daoSession;
    private DaoSession daoSession1;
    private TitlesDao titlesDao1;
    private TitlesDao titlesDao;
    private ExpandableListView expand_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //未读数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "jm_notread.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        titlesDao = daoSession.getTitlesDao();

        //已读数据
        DaoMaster.DevOpenHelper devOpenHelper1 = new DaoMaster.DevOpenHelper(this, "jm_alreadyread.db", null);
        DaoMaster daoMaster1 = new DaoMaster(devOpenHelper1.getWritableDatabase());
        daoSession1 = daoMaster1.newSession();
        titlesDao1 = daoSession1.getTitlesDao();
        setContentView(R.layout.activity_message);
        initView();
    }


    public void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        //未读数据
        final List<Titles> titles1 = titlesDao.loadAll();
        if (titles1.size() > 0) {
            mNotRead.clear();
            for (int i = 0; i < titles1.size(); i++) {
                mNotRead.add(titles1.get(i));
            }
        }
        //已读数据
        final List<Titles> titles2 = titlesDao1.loadAll();
        if (titles2.size() > 0) {
            if(titles2.size()>15){
                titlesDao1.deleteAll();
                List<Titles> titles = titlesDao1.loadAll();
                int size = titles.size();
            }else {
                mAlreadyRead.clear();
                for (int i = 0; i < titles2.size(); i++) {
                    mAlreadyRead.add(titles2.get(i));
                }
            }
        }
        //未读数据
        not_read = (RecyclerView) findViewById(R.id.not_read);
        not_read.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notReadAdapter = new NotReadAdapter(this, mNotRead);
        not_read.setLayoutManager(new LinearLayoutManager(this));
        not_read.setAdapter(notReadAdapter);
        //未读邮件点击事件
        notReadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MessageActivity.this, Title_Content_Activity.class);
                intent.putExtra("titles", mNotRead.get(position).getTitle());
                intent.putExtra("content", mNotRead.get(position).getContent());
                intent.putExtra("time", mNotRead.get(position).getTime());
                intent.putExtra("sender_letter", mNotRead.get(position).getSender_letter());
                intent.putExtra("addressee_letter", mNotRead.get(position).getAddressee_letter());
                startActivity(intent);
                mAlreadyRead.add(mNotRead.get(position));
                titlesDao1.insert(mNotRead.get(position));
                titlesDao.delete(mNotRead.get(position));
                mNotRead.remove(position);
                notReadAdapter.notifyDataSetChanged();
            }
        });
        notReadAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(MessageActivity.this)
                        .setTitle("温馨提示：")
                        .setMessage("是否删除此条邮件？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MessageActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                                titlesDao.delete(mNotRead.get(position));
                                mNotRead.remove(position);
                                notReadAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MessageActivity.this, "已取消删除", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                alertDialog.show();

                return true;
            }
        });

        //已读数据
        already_read = (RecyclerView) findViewById(R.id.already_read);
        already_read.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        alreadyReadAdapter = new AlreadyReadAdapter(this, mAlreadyRead);
        already_read.setLayoutManager(new LinearLayoutManager(this));
        already_read.setAdapter(alreadyReadAdapter);
        //已读邮件点击事件
        alreadyReadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MessageActivity.this, Title_Content_Activity.class);
                intent.putExtra("titles", mAlreadyRead.get(position).getTitle());
                intent.putExtra("content", mAlreadyRead.get(position).getContent());
                intent.putExtra("time", mAlreadyRead.get(position).getTime());
                intent.putExtra("sender_letter", mAlreadyRead.get(position).getSender_letter());
                intent.putExtra("addressee_letter", mAlreadyRead.get(position).getAddressee_letter());
                startActivity(intent);
            }
        });
        alreadyReadAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                AlertDialog alertDialog = new AlertDialog.Builder(MessageActivity.this)
                        .setTitle("温馨提示：")
                        .setMessage("是否删除此条邮件？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtils.ToastCenter("已删除");
                                //进行数据库删除数据
                                titlesDao1.delete(mAlreadyRead.get(position));
                                mAlreadyRead.remove(position);
                                alreadyReadAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ToastUtils.ToastCenter("已取消删除");
                            }
                        }).create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // EventBus.getDefault().unregister(this);
    }
}
