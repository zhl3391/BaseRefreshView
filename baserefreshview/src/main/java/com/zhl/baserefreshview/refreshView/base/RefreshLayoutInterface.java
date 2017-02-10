package com.zhl.baserefreshview.refreshView.base;

import android.view.ViewGroup;

/**
 * Created by zhouhl on 2017/1/21.
 * 刷新控件接口
 */

public interface RefreshLayoutInterface {

    void setOnRefreshListener(OnRefreshListener onRefreshListener);

    void setRefreshEnable(boolean isEnable);

    void setRefreshing(boolean isRefreshing);

    void setVisibility(int setVisibility);

    ViewGroup getSelf();

    interface OnRefreshListener {

        void onRefresh();
    }
}
