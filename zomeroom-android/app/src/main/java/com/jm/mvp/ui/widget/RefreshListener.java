package com.jm.mvp.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.R;

/**
 * @author pc 张立男
 * @Description RefreshListener 初始化加载布局，刷新回调
 * @date 2018/3/3 15:04
 * o(＞﹏＜)o
 */

public class RefreshListener {

    private static int distanceSave = 0;

    public static void initRefresh(final SuperSwipeRefreshLayout superSwipeRefresh, Context context,
                                   final RvListener listener) {
        //增加下拉刷新
        View headerView = LayoutInflater.from(context).inflate(R.layout.item_downrefresh_header, null);
        ImageView animation_list = (ImageView) headerView.findViewById(R.id.animation_list);
        ((AnimationDrawable) animation_list.getBackground()).start();
        final TextView refreshDownTv = (TextView) headerView.findViewById(R.id.refreshDownTv);
        superSwipeRefresh.setHeaderView(headerView);
        //下拉刷新回调
        superSwipeRefresh.setOnPullRefreshListener(
                new SuperSwipeRefreshLayout.OnPullRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //刷新结束
                        superSwipeRefresh.setRefreshing(false);
                        superSwipeRefresh.setLoadMore(false);
                        if (listener != null) {
                            listener.onFresh();
                        }
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        if ((distance - distanceSave) < 0) {
                            refreshDownTv.setText("更新中");
                        }
                        distanceSave = distance;
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        if (!enable) {
                            refreshDownTv.setText("下拉刷新");
                        } else {
                            refreshDownTv.setText("松开刷新");
                        }
                    }
                });
        //增加加载更多
        final View footerView = LayoutInflater.from(context).inflate(R.layout.item_uprefresh_footer, null);
        ImageView animation_list_footer = (ImageView) footerView.findViewById(R.id.animation_list_footer);
        ((AnimationDrawable) animation_list_footer.getBackground()).start();
        TextView loadMoreTv = (TextView) footerView.findViewById(R.id.refreshReminder);
        superSwipeRefresh.setFooterView(footerView);
        //加载更多
        superSwipeRefresh.setOnPushLoadMoreListener(
                new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
//                        刷新结束
                        superSwipeRefresh.setRefreshing(false);
                        superSwipeRefresh.setLoadMore(false);
                        if (listener != null) {
                            listener.onLoad(footerView);
                        }
                    }

                    @Override
                    public void onPushDistance(int distance) {

                    }

                    @Override
                    public void onPushEnable(boolean enable) {

                    }
                });
    }

    public interface RvListener {
        void onFresh();

        void onLoad(View footerView);
    }
}
