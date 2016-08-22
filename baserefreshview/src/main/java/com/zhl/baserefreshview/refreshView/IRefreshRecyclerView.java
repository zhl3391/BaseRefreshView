package com.zhl.baserefreshview.refreshView;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.zhl.baserefreshview.IPlaceHolderView;
import com.zhl.baserefreshview.IListDataView;
import com.zhl.baserefreshview.MoreViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

public interface IRefreshRecyclerView extends IListDataView {

    SwipeRefreshLayout getSwipeRefreshLayout();

    void setRefreshListener(RefreshListener refreshListener);

    void setRefreshEnable(boolean refreshEnable);

    void setRefreshing(boolean isRefreshing);

    boolean isRefresh();

    void setAdapter(@NonNull CommonRecyclerAdapter adapter);

    void setMoreViewHolder(@NonNull MoreViewHolder moreViewHolder);

    void setLayoutManager(@NonNull RecyclerView.LayoutManager layoutManager);

    void setPlaceHolderView(@NonNull IPlaceHolderView emptyView);
}
