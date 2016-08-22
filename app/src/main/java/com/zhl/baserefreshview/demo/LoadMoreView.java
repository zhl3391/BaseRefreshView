package com.zhl.baserefreshview.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhl.baserefreshview.ILoadMoreView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreView implements ILoadMoreView {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_no_more)
    TextView mTvNoMore;
    private View mRootView;

    public LoadMoreView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_more_vertical, null);
        ButterKnife.bind(this, mRootView);
    }

    @NonNull
    @Override
    public View getView() {
        return mRootView;
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvNoMore.setVisibility(View.GONE);
    }

    @Override
    public void showNoMore() {
        mProgressBar.setVisibility(View.GONE);
        mTvNoMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {

    }
}
