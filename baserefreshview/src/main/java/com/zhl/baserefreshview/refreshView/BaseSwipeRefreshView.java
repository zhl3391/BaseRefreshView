package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.zhl.baserefreshview.IPlaceHolderView;
import com.zhl.baserefreshview.ILoadMoreView;

public abstract class BaseSwipeRefreshView extends RelativeLayout {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RefreshListener mRefreshListener;
    protected IPlaceHolderView mPlaceHolderView;
    protected ILoadMoreView mLoadMoreView;
    protected AbsListView.OnScrollListener mOnScrollListener;
    protected BaseAdapter mBaseAdapter;

    private AbsListView mAbsListView;

    protected boolean mIsLoading;
    protected boolean mIsHasMore;
    protected boolean mIsRefresh = true;

    public BaseSwipeRefreshView(Context context) {
        super(context);
        init();
    }

    public BaseSwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSwipeRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        this.isInEditMode();
        mSwipeRefreshLayout = new SwipeRefreshLayout(getContext());
        this.addView(mSwipeRefreshLayout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mAbsListView = addAbsListView();
        if (mAbsListView != null) {
            mAbsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    }

    protected abstract AbsListView addAbsListView();

    public void setAdapter(@NonNull BaseAdapter adapter) {
        mBaseAdapter = adapter;
        mAbsListView.setAdapter(adapter);
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsRefresh = true;
                if (mRefreshListener != null){
                    mRefreshListener.onRefresh();
                }
            }
        });
    }

    public void setRefreshEnable(boolean refreshEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);
    }

    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
        mIsLoading = true;
        mIsRefresh = true;
    }

    public void setPlaceHolderView(@NonNull IPlaceHolderView placeHolderView) {
        if (mPlaceHolderView != null) {
            this.removeView(mPlaceHolderView.getView());
        }
        mPlaceHolderView = placeHolderView;
        this.addView(mPlaceHolderView.getView(),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void showList(boolean isHasMore) {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);
        mIsLoading = false;
        mIsHasMore = isHasMore;

        if (mPlaceHolderView != null) {
            mPlaceHolderView.showNothing();
        }

        if (mLoadMoreView != null) {
            if (isHasMore){
                mLoadMoreView.showLoading();
            }else {
                mLoadMoreView.showNoMore();
            }
        }

        if (mBaseAdapter != null) {
            mBaseAdapter.notifyDataSetChanged();
        }
    }

    public void showEmpty() {
        mIsLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        if (mPlaceHolderView != null) {
            mPlaceHolderView.showEmpty();
        }
    }

    public void showError() {
        if (mIsRefresh) {
            mIsLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setVisibility(View.GONE);
            if (mPlaceHolderView != null) {
                mPlaceHolderView.showError();
            }
        } else {
            if (mLoadMoreView != null) {
                mLoadMoreView.showError();
            }
        }
    }

    public void showLoading() {
        mIsLoading = true;
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        if (mPlaceHolderView != null) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPlaceHolderView.showLoading();
                }
            }, 300);
        }
    }


}
