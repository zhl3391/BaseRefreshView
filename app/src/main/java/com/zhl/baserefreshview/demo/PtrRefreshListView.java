package com.zhl.baserefreshview.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhl.baserefreshview.refreshView.base.BaseRefreshListView;
import com.zhl.baserefreshview.refreshView.base.RefreshLayoutInterface;

/**
 * Created by zhouhl on 2017/1/21.
 * PtrRefreshListView
 */

public class PtrRefreshListView extends BaseRefreshListView {

    public PtrRefreshListView(Context context) {
        super(context);
    }

    public PtrRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RefreshLayoutInterface createRefreshLayout() {
        return (RefreshLayoutInterface) LayoutInflater.from(getContext()).inflate(R.layout.view_ptr_refresh, this, false);
    }

    @Override
    protected AbsListView addAbsListView() {
        mListView = (ListView) mRefreshLayout.getSelf().findViewById(R.id.list_view);
        return mListView;
    }
}
