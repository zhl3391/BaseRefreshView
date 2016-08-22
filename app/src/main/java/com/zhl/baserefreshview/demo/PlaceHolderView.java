package com.zhl.baserefreshview.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhl.baserefreshview.IPlaceHolderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceHolderView implements IPlaceHolderView {

    @BindView(R.id.img_empty)
    ImageView mImgEmpty;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    @BindView(R.id.img_error)
    ImageView mImgError;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.tv_loading)
    TextView mTvLoading;
    RelativeLayout mRootView;
    @BindView(R.id.layout_empty)
    LinearLayout mLayoutEmpty;
    @BindView(R.id.layout_error)
    LinearLayout mLayoutError;
    @BindView(R.id.layout_loading)
    LinearLayout mLayoutLoading;

    public PlaceHolderView(Context context) {
        mRootView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.view_place_holder_view, null);
        ButterKnife.bind(this, mRootView);
        showNothing();
    }

    @Override
    public void showLoading() {
        mRootView.setVisibility(View.VISIBLE);
        mLayoutLoading.setVisibility(View.VISIBLE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mRootView.setVisibility(View.VISIBLE);
        mLayoutError.setVisibility(View.VISIBLE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mRootView.setVisibility(View.VISIBLE);
        mLayoutEmpty.setVisibility(View.VISIBLE);
        mLayoutError.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void showNothing() {
        mRootView.setVisibility(View.GONE);
    }

    @Override
    public View getView() {
        return mRootView;
    }
}
