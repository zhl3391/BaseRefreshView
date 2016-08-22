package com.zhl.baserefreshview;

import com.zhl.commonadapter.BaseViewHolder;

public abstract class MoreViewHolder extends BaseViewHolder<MoreViewHolder.LoadMore> {

    protected LoadMore mLoadMore;

    public MoreViewHolder() {
        mLoadMore = new LoadMore();
    }

    public void showLoading() {
        mLoadMore.showWhat = LoadMore.SHOW_LOADING;
    }

    public void showNoMore() {
        mLoadMore.showWhat = LoadMore.SHOW_NO_MORE;
    }

    public void showError() {
        mLoadMore.showWhat = LoadMore.SHOW_ERROR;
    }


    public static class LoadMore {

        public static final int SHOW_LOADING = 0;
        public static final int SHOW_NO_MORE = 1;
        public static final int SHOW_ERROR   = 2;

        public int showWhat;

        public String noMoreText;
    }
}
