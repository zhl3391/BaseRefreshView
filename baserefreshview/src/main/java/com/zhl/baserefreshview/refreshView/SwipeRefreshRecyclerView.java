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
                int lastPosition = getLastPosition();

                if (!mIsLoading
                        && mIsHasMore
                        && mAdapter.getItemCount() == (lastPosition + 1)
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mIsLoading = true;
                    mIsRefresh = false;
                    if (mRefreshListener != null) {
                        mMoreViewHolder.showLoading();
                        mRefreshListener.onLoadMore();
                    }
                } else if (!mIsHasMore) {
                    showNoMore();
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

    private int getFirstPosition() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        int firstPosition = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            firstPosition = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] firstPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            staggeredGridLayoutManager.findFirstVisibleItemPositions(firstPositions);
            firstPosition = firstPositions[0];
            for (int firstPositionTmp : firstPositions) {
                if (firstPosition > firstPositionTmp) {
                    firstPosition = firstPositionTmp;
                }
            }
        }

        return firstPosition;
    }

    private int getLastPosition() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
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

        return lastPosition;
    }

    @Override
    protected void showNoMore() {
        if (getFirstPosition() == 0) {
            mMoreViewHolder.hide();
        } else {
            mMoreViewHolder.showNoMore();
        }
    }

    public void showList(boolean isHasMore) {
        super.showList(isHasMore);
        if (mMoreViewHolder != null){
            mMoreViewHolder.hide();
            if (!isHasMore) {
               showNoMore();
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
