package com.zhl.baserefreshview.refreshView.SwipeRefreshLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.zhl.baserefreshview.refreshView.base.BaseRefreshGridView;
import com.zhl.baserefreshview.refreshView.base.RefreshLayoutInterface;

public class SwipeRefreshGridView extends BaseRefreshGridView {

    private XSwipeRefreshLayout mXSwipeRefreshLayout;

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
    protected RefreshLayoutInterface createRefreshLayout() {
        mXSwipeRefreshLayout = new XSwipeRefreshLayout(getContext());
        return mXSwipeRefreshLayout;
    }

    public XSwipeRefreshLayout getXSwipeRefreshLayout() {
        return mXSwipeRefreshLayout;
    }


}
