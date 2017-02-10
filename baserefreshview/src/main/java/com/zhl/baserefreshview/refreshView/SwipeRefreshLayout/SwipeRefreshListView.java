package com.zhl.baserefreshview.refreshView.SwipeRefreshLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.zhl.baserefreshview.refreshView.base.BaseRefreshListView;
import com.zhl.baserefreshview.refreshView.base.RefreshLayoutInterface;

public class SwipeRefreshListView extends BaseRefreshListView {

    public SwipeRefreshListView(Context context) {
        super(context);
    }

    public SwipeRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RefreshLayoutInterface createRefreshLayout() {
        return new XSwipeRefreshLayout(getContext());
    }

}
