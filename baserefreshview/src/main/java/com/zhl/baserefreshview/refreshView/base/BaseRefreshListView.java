package com.zhl.baserefreshview.refreshView.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhl.baserefreshview.ILoadMoreView;

/**
 * Created by zhouhl on 2017/1/21.
 * BaseRefreshListView
 */

public abstract class BaseRefreshListView extends BaseRefreshView {

    protected ListView mListView;

    public BaseRefreshListView(Context context) {
        super(context);
    }

    public BaseRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected AbsListView addAbsListView() {
        mListView = new ListView(getContext());
        mRefreshLayout.getSelf().addView(mListView, 0, new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return mListView;
    }

    public ListView getListView() {
        return mListView;
    }

    public void setLoadMoreView(@NonNull ILoadMoreView loadMoreView, boolean isSelectable) {
        if (mLoadMoreView != null) {
            mListView.removeFooterView(mLoadMoreView.getView());
        }
        mLoadMoreView = loadMoreView;
        mListView.addFooterView(mLoadMoreView.getView(), null, isSelectable);
    }

    public void hideDivider() {
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDividerHeight(0);
    }
}
