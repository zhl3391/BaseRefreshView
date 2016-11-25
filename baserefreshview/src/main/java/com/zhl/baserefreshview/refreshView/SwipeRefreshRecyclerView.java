package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.zhl.baserefreshview.MoreViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

public class SwipeRefreshRecyclerView extends BaseSwipeRefreshView {

    private RecyclerView mRecyclerView;
    private MoreViewHolder mMoreViewHolder;
    private CommonRecyclerAdapter mAdapter;

    private boolean mIsSetMoreViewHolder;

    public SwipeRefreshRecyclerView(Context context) {
        super(context);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init(){
        super.init();
        mRecyclerView = new RecyclerView(getContext());
        mSwipeRefreshLayout.addView(mRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected AbsListView addAbsListView() {
        return null;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setAdapter(@NonNull CommonRecyclerAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        if (!mIsSetMoreViewHolder && mMoreViewHolder != null) {
            mAdapter.addFooter(mMoreViewHolder);
            mIsSetMoreViewHolder = true;
        }
    }

    public void setMoreViewHolder(@NonNull MoreViewHolder moreViewHolder) {
        mMoreViewHolder = moreViewHolder;
        if (!mIsSetMoreViewHolder && mAdapter != null) {
            mAdapter.addFooter(mMoreViewHolder);
            mIsSetMoreViewHolder = true;
        }
    }

    public void setLayoutManager(@NonNull final RecyclerView.LayoutManager layoutManager,
                                 GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        mRecyclerView.setLayoutManager(layoutManager);
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            if (spanSizeLookup != null) {
                gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
            }
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = 0;
                if (layoutManager instanceof LinearLayoutManager) {
                    lastPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastPosition = lastPositions[0];
                    for (int lastPositionTmp : lastPositions) {
                        if (lastPosition < lastPositionTmp) {
                            lastPosition = lastPositionTmp;
                        }
                    }
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

    public void setLayoutManager(@NonNull final RecyclerView.LayoutManager layoutManager) {
        setLayoutManager(layoutManager, new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    if (mAdapter != null && mAdapter.getFooterSize() != 0) {
                        if (position == mAdapter.getItemCount() - 1) {
                            return gridLayoutManager.getSpanCount();
                        } else {
                            return 1;
                        }
                    }
                }
                return 1;
            }
        });
    }

    public void showList(boolean isHasMore) {
        super.showList(isHasMore);
        if (mMoreViewHolder != null){
            if (mIsHasMore){
                mMoreViewHolder.showLoading();
            }else {
                mMoreViewHolder.showNoMore();
            }
        }
        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public void showError() {
        super.showError();
        if (!mIsRefresh) {
            if (mMoreViewHolder != null) {
                mMoreViewHolder.showError();
            }
        }
    }
}
