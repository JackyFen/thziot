package com.kq.platform.kq_common.ui.widget.recyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by sjl on 2018/5/23.
 */


public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    private boolean isLoading = true;
    private int totalItemCount;
    private int previousTotal;

    public RecyclerViewScrollListener(LinearLayoutManager mLinearLayoutManager, SwipeRefreshLayout refreshLayout) {
        this.mLinearLayoutManager = mLinearLayoutManager;
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //recyclerView的item总数l
        totalItemCount = recyclerView.getAdapter().getItemCount();
        int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (refreshLayout.isRefreshing()) {
            isLoading = true;
            previousTotal = 0;
            return;
        }

        if (totalItemCount > previousTotal) {
            //说明数据已经加载结束
            isLoading = false;
            previousTotal = totalItemCount;
        }
        if (!isLoading && lastVisibleItem + 1 == totalItemCount) {
            isLoading = true;
            onLoadMore();
        }
    }

    public abstract void onLoadMore();

    //下拉刷新，重置状态
    public void resetScrollListener() {
        this.previousTotal = 0;
        isLoading = true;
    }
}
