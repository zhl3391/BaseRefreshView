package com.zhl.baserefreshview;

public interface IListDataView {

    void showList(boolean isHasMore);

    void showEmpty();

    void showError();

    void showLoading();

}
