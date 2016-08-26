package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.zhl.baserefreshview.GridViewWithHeadFoot;
import com.zhl.baserefreshview.ILoadMoreView;

public class SwipeRefreshGridView extends BaseSwipeRefreshView {

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
    protected AbsListView addAbsListView() {
        mGridView = new GridViewWithHeadFoot(getContext());
        mSwipeRefreshLayout.addView(mGridView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return mGridView;
    }


    public GridViewWithHeadFoot getGridView() {
        return mGridView;
    }

    public void setLoadMoreView(@NonNull ILoadMoreView loadMoreView, boolean isSelectable) {
        if (mLoadMoreView != null) {
            mGridView.removeFooterView(mLoadMoreView.getView());
        }
        mLoadMoreView = loadMoreView;
        mGridView.addFooterView(mLoadMoreView.getView(), null, isSelectable);
    }
}
