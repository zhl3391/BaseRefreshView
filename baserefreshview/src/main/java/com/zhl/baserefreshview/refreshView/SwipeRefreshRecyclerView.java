package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhl.baserefreshview.IPlaceHolderView;
import com.zhl.baserefreshview.MoreViewHolder;
import com.zhl.baserefreshview.R;
import com.zhl.commonadapter.CommonRecyclerAdapter;

public class SwipeRefreshRecyclerView extends RelativeLayout implements IRefreshRecyclerView{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RefreshListener mRefreshListener;
    private RecyclerView mRecyclerView;
    private MoreViewHolder mMoreViewHolder;
    private IPlaceHolderView mPlaceHolderView;
    private CommonRecyclerAdapter mAdapter;

    private boolean mIsLoading;
    private boolean mIsHasMore;
    private boolean mIsRefresh;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.isInEditMode();
        View view = inflate(getContext(), R.layout.view_swipe_refresh_recycler, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    @Override
    public void setRefreshListener(final RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIsRefresh = true;
                if (mRefreshListener != null) {
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
        mIsLoading = true;
        mIsRefresh = true;
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public boolean isRefresh() {
        return mIsRefresh;
    }

    @Override
    public void setAdapter(@NonNull CommonRecyclerAdapter adapter) {
        mAdapter = adapter;
        if (mMoreViewHolder != null){
            mAdapter.addFooter(mMoreViewHolder);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setMoreViewHolder(@NonNull MoreViewHolder moreViewHolder) {
        mMoreViewHolder = moreViewHolder;
    }

    @Override
    public void setLayoutManager(@NonNull final RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = 0;
                if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                }

                if (!mIsLoading
                        && mIsHasMore
                        && mAdapter.getItemCount() == (lastPosition + 1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mIsLoading = true;
                    mIsRefresh = false;
                    if (mRefreshListener != null){
                        mRefreshListener.onLoadMore();
                    }
                }
            }
        });
    }

    public void setPlaceHolderView(@NonNull IPlaceHolderView placeHolderView) {
        if (mPlaceHolderView != null) {
            this.removeView(mPlaceHolderView.getView());
        }
        mPlaceHolderView = placeHolderView;
        this.addView(mPlaceHolderView.getView(),
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
        if (mMoreViewHolder != null){
            if (mIsHasMore){
                mMoreViewHolder.showLoading();
            }else {
                mMoreViewHolder.showNoMore();
            }
            if (mAdapter != null){
                mAdapter.notifyDataSetChanged();
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
            if (mMoreViewHolder != null) {
                mMoreViewHolder.showError();
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
