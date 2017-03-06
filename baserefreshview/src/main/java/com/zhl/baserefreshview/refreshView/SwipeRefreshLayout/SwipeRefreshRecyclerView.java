package com.zhl.baserefreshview.refreshView.SwipeRefreshLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.zhl.baserefreshview.refreshView.base.BaseRefreshRecyclerView;
import com.zhl.baserefreshview.refreshView.base.RefreshLayoutInterface;

public class SwipeRefreshRecyclerView extends BaseRefreshRecyclerView {

    private XSwipeRefreshLayout mXSwipeRefreshLayout;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RefreshLayoutInterface createRefreshLayout() {
        mXSwipeRefreshLayout = new XSwipeRefreshLayout(getContext());
        return mXSwipeRefreshLayout;
    }

    public XSwipeRefreshLayout getXSwipeRefreshLayout() {
        return mXSwipeRefreshLayout;
    }

}
