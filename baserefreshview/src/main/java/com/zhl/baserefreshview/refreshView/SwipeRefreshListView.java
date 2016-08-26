package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zhl.baserefreshview.ILoadMoreView;
import com.zhl.baserefreshview.R;

public class SwipeRefreshListView extends BaseSwipeRefreshView implements IRefreshListView{

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

    protected void init(){
        this.isInEditMode();
        View view = inflate(getContext(), R.layout.view_swipe_refresh_list, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        mListView = (ListView) view.findViewById(R.id.list_view);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!mIsLoading
                        && mIsHasMore
                        && view.getCount() == (view.getLastVisiblePosition() + 1)
                        && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    mIsLoading = true;
                    mIsRefresh = false;
                    if (mRefreshListener != null){
                        mRefreshListener.onLoadMore();
                    }
                }

                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    @Override
    public ListView getListView() {
        return mListView;
    }

    @Override
    public void setLoadMoreView(@NonNull ILoadMoreView loadMoreView, boolean isSelectable) {
        if (mLoadMoreView != null) {
            mListView.removeFooterView(mLoadMoreView.getView());
        }
        mLoadMoreView = loadMoreView;
        mListView.addFooterView(mLoadMoreView.getView(), null, isSelectable);
    }

    @Override
    public void hideDivider() {
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDividerHeight(0);
    }
}
