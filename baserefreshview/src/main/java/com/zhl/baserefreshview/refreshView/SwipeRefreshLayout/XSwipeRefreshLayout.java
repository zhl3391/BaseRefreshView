package com.zhl.baserefreshview.refreshView.SwipeRefreshLayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.zhl.baserefreshview.refreshView.base.RefreshLayoutInterface;

/**
 * Created by zhouhl on 2017/1/21.
 * SwipeRefreshLayout
 */

public class XSwipeRefreshLayout extends SwipeRefreshLayout implements RefreshLayoutInterface {

    public XSwipeRefreshLayout(Context context) {
        super(context);
    }

    public XSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnRefreshListener(final RefreshLayoutInterface.OnRefreshListener onRefreshListener) {
        this.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }
        });
    }

    @Override
    public void setRefreshEnable(boolean isEnable) {
        setEnabled(isEnable);
    }

    @Override
    public ViewGroup getSelf() {
        return this;
    }
}
