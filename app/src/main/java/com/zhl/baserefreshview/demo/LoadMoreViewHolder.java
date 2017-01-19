package com.zhl.baserefreshview.demo;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhl.baserefreshview.MoreViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreViewHolder extends MoreViewHolder {

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.tv_no_more)
    TextView mTvNoMore;
    @BindView(R.id.tv_error)
    TextView mTvError;

    public LoadMoreViewHolder() {
        super();
    }

    @Override
    public void findView(View view) {
        ButterKnife.bind(this, view);
        if (view.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.setFullSpan(true);
        }
    }

    @Override
    public void updateView(LoadMore data, int position) {
        if (mItemView != null) {
            mItemView.setVisibility(View.VISIBLE);
            switch (mLoadMore.showWhat) {
                case LoadMore.SHOW_ERROR:
                    mTvError.setVisibility(View.VISIBLE);
                    mPbLoading.setVisibility(View.GONE);
                    mTvNoMore.setVisibility(View.GONE);
                    break;
                case LoadMore.SHOW_LOADING:
                    mPbLoading.setVisibility(View.VISIBLE);
                    mTvError.setVisibility(View.GONE);
                    mTvNoMore.setVisibility(View.GONE);
                    break;
                case LoadMore.SHOW_NO_MORE:
                    mTvNoMore.setVisibility(View.VISIBLE);
                    mPbLoading.setVisibility(View.GONE);
                    mTvError.setVisibility(View.GONE);
                    break;
                case LoadMore.HIDE:
                    mItemView.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_load_more;
    }
}
