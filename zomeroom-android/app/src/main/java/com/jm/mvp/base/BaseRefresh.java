//package com.jm.mvp.base;
//
//import android.content.Intent;
//import android.graphics.drawable.AnimationDrawable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.jm.mvp.ui.widget.SuperSwipeRefreshLayout;
//
//
///**
// * @author pc 张立男
// * @Description BaseRefresh 下拉刷新上拉加载 抽取
// * @date 2017/4/27 17:34
// * o(＞﹏＜)o
// */
//
//public abstract class BaseRefresh {
//    //    private View headerView, footerView;
//    private View headerView;
//    private TextView refreshDownTv;
//    private int distanseSave = 0;
//    private ImageView animation_list_footer;
//    private TextView loadMoreTv;
//
//    /*下拉刷新和加载更多*/
//    public void initRefresh(final SuperSwipeRefreshLayout superSwipeRefresh,
//                            final RecyclerView recyclerView, final ImageView imageView, final TextView ivHistory) {
//        initRefresh(superSwipeRefresh, recyclerView, imageView, ivHistory, true);
//    }
//
//    /**
//     * @param superSwipeRefresh 加载控件
//     * @param recyclerView      列表控件
//     * @param imageView         top置顶
//     * @param ivHistory         历史记录
//     * @param isRefresh         是否需要下拉刷新
//     */
//    public void initRefresh(final SuperSwipeRefreshLayout superSwipeRefresh,
//                            final RecyclerView recyclerView, final ImageView imageView, final TextView ivHistory,
//                            final boolean isRefresh) {
//        if (isRefresh) {
//            //增加下拉刷新
//            headerView = LayoutInflater.from(this).inflate(R.layout.item_downrefresh_header, null);
//            ImageView animation_list = (ImageView) headerView.findViewById(R.id.animation_list);
//            ((AnimationDrawable) animation_list.getBackground()).start();
//            refreshDownTv = (TextView) headerView.findViewById(R.id.refreshDownTv);
//            superSwipeRefresh.setHeaderView(headerView);
//            //下拉刷新回调
//            superSwipeRefresh.setOnPullRefreshListener(
//                    new SuperSwipeRefreshLayout.OnPullRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            onFresh();
//                            //刷新结束
//                            superSwipeRefresh.setRefreshing(false);
//                            superSwipeRefresh.setLoadMore(false);
//                        }
//
//                        @Override
//                        public void onPullDistance(int distance) {
//                            if ((distance - distanseSave) < 0) {
//                                refreshDownTv.setText("更新中");
//                            }
//                            distanseSave = distance;
//                        }
//
//                        @Override
//                        public void onPullEnable(boolean enable) {
//                            if (!enable) {
//                                refreshDownTv.setText("下拉刷新");
//                            } else {
//                                refreshDownTv.setText("松开刷新");
//                            }
//                        }
//                    });
//        } else {
//            superSwipeRefresh.setHeaderView(new TextView(BaseRefresh.this));
//            superSwipeRefresh.setOnPullRefreshListener(
//                    new SuperSwipeRefreshLayout.OnPullRefreshListener() {
//                        @Override
//                        public void onRefresh() {
//                            superSwipeRefresh.setRefreshing(false);
//                        }
//
//                        @Override
//                        public void onPullDistance(int distance) {
//
//                        }
//
//                        @Override
//                        public void onPullEnable(boolean enable) {
//
//                        }
//                    });
//        }
//        //增加加载更多
//        final View footerView = LayoutInflater.from(this).inflate(R.layout.item_uprefresh_footer, null);
//        animation_list_footer = (ImageView) footerView.findViewById(R.id.animation_list_footer);
//        ((AnimationDrawable) animation_list_footer.getBackground()).start();
//        loadMoreTv = (TextView) footerView.findViewById(R.id.refreshReminder);
//        superSwipeRefresh.setFooterView(footerView);
//        //加载更多
//        superSwipeRefresh.setOnPushLoadMoreListener(
//                new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        onLoad(footerView);
//                        //刷新结束
//                        superSwipeRefresh.setRefreshing(false);
//                        superSwipeRefresh.setLoadMore(false);
//                    }
//
//                    @Override
//                    public void onPushDistance(int distance) {
//
//                    }
//
//                    @Override
//                    public void onPushEnable(boolean enable) {
//
//                    }
//                });
//
//        //一键置顶
//        // 判断是当前layoutManager是否为LinearLayoutManager
//        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//        if (imageView != null) {
//            recyclerView.getViewTreeObserver()
//                    .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//                        @Override
//                        public void onScrollChanged() {
//                            RecyclerView.LayoutManager layoutManager =
//                                    recyclerView.getLayoutManager();
//                            if (layoutManager instanceof LinearLayoutManager) {
//                                LinearLayoutManager linearManager =
//                                        (LinearLayoutManager) layoutManager;
//                                final int lastItemPosition =
//                                        linearManager.findLastVisibleItemPosition(); //获取最后一个可见view的位置
//                                int firstItemPosition =
//                                        linearManager.findFirstVisibleItemPosition();//获取第一个可见view的位置
//
//                                if (firstItemPosition > 0) {
//                                    if (ivHistory != null) {
//                                        ivHistory.setVisibility(View.VISIBLE);
//                                        ivHistory.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                startActivity(new Intent(BaseRefresh.this,
//                                                        HistoryActivity.class));
//                                            }
//                                        });
//                                    }
//                                    imageView.setVisibility(View.VISIBLE);
//                                    imageView.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            for (int i = lastItemPosition; i >= 0; i--) {
//                                                recyclerView.smoothScrollToPosition(0);//点击定位到第一条数据
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    imageView.setVisibility(View.GONE);
//                                    if (ivHistory != null) {
//                                        ivHistory.setVisibility(View.GONE);
//                                    }
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    public abstract void onFresh();
//
//    public abstract void onLoad(View footerView);
//}