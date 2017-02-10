package com.zhl.baserefreshview.refreshView.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.zhl.baserefreshview.IPlaceHolderView;
import com.zhl.baserefreshview.ILoadMoreView;

public abstract class BaseRefreshView extends RelativeLayout {

    protected RefreshListener mRefreshListener;
    protected IPlaceHolderView mPlaceHolderView;
    protected ILoadMoreView mLoadMoreView;
    protected AbsListView.OnScrollListener mOnScrollListener;
    protected BaseAdapter mBaseAdapter;
    protected RefreshLayoutInterface mRefreshLayout;

    private AbsListView mAbsListView;

    protected boolean mIsLoading;
    protected boolean mIsHasMore;
    protected boolean mIsRefresh = true;

    public BaseRefreshView(Context context) {
        this(context, null, 0);
    }

    public BaseRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        this.isInEditMode();
        mRefreshLayout = createRefreshLayout();
        this.addView(mRefreshLayout.getSelf(), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
                            mLoadMoreView.showLoading();
                            mRefreshListener.onLoadMore();
                        }
                    } else if (!mIsHasMore) {
                        showNoMore();
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

    protected abstract RefreshLayoutInterface createRefreshLayout();

    public void setAdapter(@NonNull BaseAdapter adapter) {
        mBaseAdapter = adapter;
        mAbsListView.setAdapter(adapter);
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
        mRefreshLayout.setOnRefreshListener(new RefreshLayoutInterface.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsLoading = true;
                mIsRefresh = true;
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
    }

    public void setRefreshEnable(boolean refreshEnable) {
        mRefreshLayout.setRefreshEnable(refreshEnable);
    }

    public void setRefreshing(boolean isRefreshing) {
        mRefreshLayout.setRefreshing(isRefreshing);
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

    public RefreshLayoutInterface getRefreshLayout() {
        return mRefreshLayout;
    }

    public void showList(boolean isHasMore) {
        mRefreshLayout.setVisibility(View.VISIBLE);
        mRefreshLayout.setRefreshing(false);
        mIsLoading = false;
        mIsHasMore = isHasMore;

        if (mPlaceHolderView != null) {
            mPlaceHolderView.showNothing();
        }

        if (mLoadMoreView != null) {
            mLoadMoreView.hide();
            if (!isHasMore) {
                showNoMore();
            }
        }

        if (mBaseAdapter != null) {
            mBaseAdapter.notifyDataSetChanged();
        }
    }

    public void showEmpty() {
        mIsLoading = false;
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setVisibility(View.GONE);
        if (mPlaceHolderView != null) {
            mPlaceHolderView.showEmpty();
        }
    }

    public void showError() {
        if (mIsRefresh) {
            mIsLoading = false;
            mRefreshLayout.setRefreshing(false);
            mRefreshLayout.setVisibility(View.GONE);
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
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setVisibility(View.GONE);
        if (mPlaceHolderView != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPlaceHolderView.showLoading();
                }
            }, 300);
        }
    }

    protected void showNoMore() {
        if (mAbsListView.getFirstVisiblePosition() == 0) {
            mLoadMoreView.hide();
        } else {
            mLoadMoreView.showNoMore();
        }
    }
}
