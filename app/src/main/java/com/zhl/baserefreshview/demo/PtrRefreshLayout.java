package com.zhl.baserefreshview.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zhl.baserefreshview.refreshView.base.RefreshLayoutInterface;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zhouhl on 2017/1/21.
 * PtrRefreshLayout
 */

public class PtrRefreshLayout extends PtrClassicFrameLayout implements RefreshLayoutInterface {

    public PtrRefreshLayout(Context context) {
        super(context);
    }

    public PtrRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener onRefreshListener) {
        setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefreshListener.onRefresh();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    @Override
    public void setRefreshEnable(boolean isEnable) {

    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        if (isRefreshing) {
            autoRefresh();
        } else {
            refreshComplete();
        }
    }

    @Override
    public ViewGroup getSelf() {
        return this;
    }
}
