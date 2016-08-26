package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.zhl.baserefreshview.IPlaceHolderView;
import com.zhl.baserefreshview.ILoadMoreView;

public abstract class BaseSwipeRefreshView extends RelativeLayout implements IBaseSwipeRefreshView{

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RefreshListener mRefreshListener;
    protected IPlaceHolderView mPlaceHolderView;
    protected ILoadMoreView mLoadMoreView;
    protected AbsListView.OnScrollListener mOnScrollListener;

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

    protected abstract void init();

    @Override
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

    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        mSwipeRefreshLayout.setEnabled(refreshEnable);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
        mIsLoading = true;
        mIsRefresh = true;
    }

    @Override
    public void setPlaceHolderView(@NonNull IPlaceHolderView placeHolderView) {
        if (mPlaceHolderView != null) {
            this.removeView(mPlaceHolderView.getView());
        }
        mPlaceHolderView = placeHolderView;
        this.addView(mPlaceHolderView.getView(),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @Override
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
    }

    @Override
    public void showEmpty() {
        mIsLoading = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        if (mPlaceHolderView != null) {
            mPlaceHolderView.showEmpty();
        }
    }

    @Override
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

    @Override
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
