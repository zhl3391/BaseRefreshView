package com.zhl.baserefreshview;

import com.zhl.commonadapter.BaseViewHolder;

public abstract class MoreViewHolder extends BaseViewHolder<MoreViewHolder.LoadMore> {

    protected LoadMore mLoadMore;

    public MoreViewHolder() {
        mLoadMore = new LoadMore();
    }

    public void showLoading() {
        mLoadMore.showWhat = LoadMore.SHOW_LOADING;
        updateView(mLoadMore, 0);
    }

    public void showNoMore() {
        mLoadMore.showWhat = LoadMore.SHOW_NO_MORE;
        updateView(mLoadMore, 0);
    }

    public void showError() {
        mLoadMore.showWhat = LoadMore.SHOW_ERROR;
        updateView(mLoadMore, 0);
    }

    public void hide() {
        mLoadMore.showWhat = LoadMore.HIDE;
        updateView(mLoadMore, 0);
    }

    public static class LoadMore {

        public static final int SHOW_LOADING = 1;
        public static final int SHOW_NO_MORE = 2;
        public static final int SHOW_ERROR   = 3;
        public static final int HIDE         = 4;

        public int showWhat;

        public String noMoreText;
    }
}
