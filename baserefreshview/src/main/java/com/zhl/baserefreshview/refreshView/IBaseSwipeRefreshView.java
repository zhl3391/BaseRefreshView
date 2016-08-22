package com.zhl.baserefreshview.refreshView;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

import com.zhl.baserefreshview.IPlaceHolderView;
import com.zhl.baserefreshview.IListDataView;
import com.zhl.baserefreshview.ILoadMoreView;

public interface IBaseSwipeRefreshView extends IListDataView {

    SwipeRefreshLayout getSwipeRefreshLayout();

    void setRefreshListener(RefreshListener refreshListener);

    void setRefreshEnable(boolean refreshEnable);

    void setRefreshing(boolean isRefreshing);

    void setPlaceHolderView(@NonNull IPlaceHolderView emptyView);

    void setLoadMoreView(@NonNull ILoadMoreView loadMoreView, boolean isSelectable);

    void setOnScrollListener(AbsListView.OnScrollListener onScrollListener);
}
