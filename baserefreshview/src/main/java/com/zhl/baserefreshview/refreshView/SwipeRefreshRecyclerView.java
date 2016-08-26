package com.zhl.baserefreshview.refreshView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.zhl.baserefreshview.MoreViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

public class SwipeRefreshRecyclerView extends BaseSwipeRefreshView {

    private RecyclerView mRecyclerView;
    private MoreViewHolder mMoreViewHolder;
    private CommonRecyclerAdapter mAdapter;

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

    protected void init(){
        super.init();
        mRecyclerView = new RecyclerView(getContext());
        mSwipeRefreshLayout.addView(mRecyclerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected AbsListView addAbsListView() {
        return null;
    }

    public void setAdapter(@NonNull CommonRecyclerAdapter adapter) {
        mAdapter = adapter;
        if (mMoreViewHolder != null){
            mAdapter.addFooter(mMoreViewHolder);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setMoreViewHolder(@NonNull MoreViewHolder moreViewHolder) {
        mMoreViewHolder = moreViewHolder;
    }

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

    public void showList(boolean isHasMore) {
        super.showList(isHasMore);
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

    public void showError() {
        super.showError();
        if (!mIsRefresh) {
            if (mMoreViewHolder != null) {
                mMoreViewHolder.showError();
            }
        }
    }
}
