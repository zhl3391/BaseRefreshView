package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.zhl.baserefreshview.GridViewWithHeadFoot;
import com.zhl.baserefreshview.ILoadMoreView;
import com.zhl.baserefreshview.R;

public class SwipeRefreshGridView extends BaseSwipeRefreshView implements IRefreshGridView{

    private GridViewWithHeadFoot mGridView;

    public SwipeRefreshGridView(Context context) {
        super(context);
    }

    public SwipeRefreshGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(){
        this.isInEditMode();
        View view = inflate(getContext(), R.layout.view_swipe_refresh_grid, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        mGridView = (GridViewWithHeadFoot) view.findViewById(R.id.grid_view);

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!mIsLoading
                        && mIsHasMore
                        && view.getCount() == (view.getLastVisiblePosition() + 1)
                        && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mIsLoading = true;
                    mIsRefresh = false;
                    if (mRefreshListener != null){
                        mRefreshListener.onLoadMore();
                    }
                }

                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    @Override
    public GridViewWithHeadFoot getGridView() {
        return mGridView;
    }

    @Override
    public void setLoadMoreView(@NonNull ILoadMoreView loadMoreView, boolean isSelectable) {
        if (mLoadMoreView != null) {
            mGridView.removeFooterView(mLoadMoreView.getView());
        }
        mLoadMoreView = loadMoreView;
        mGridView.addFooterView(mLoadMoreView.getView(), null, isSelectable);
    }
}
