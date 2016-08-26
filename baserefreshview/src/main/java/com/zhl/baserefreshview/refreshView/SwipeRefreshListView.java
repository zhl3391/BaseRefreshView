package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhl.baserefreshview.ILoadMoreView;

public class SwipeRefreshListView extends BaseSwipeRefreshView {

    private ListView mListView;

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
    protected AbsListView addAbsListView() {
        mListView = new ListView(getContext());
        mSwipeRefreshLayout.addView(mListView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
