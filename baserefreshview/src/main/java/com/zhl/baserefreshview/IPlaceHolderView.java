package com.zhl.baserefreshview;

import android.view.View;

public interface IPlaceHolderView {

    void showLoading();

    void showError();

    void showEmpty();

    void showNothing();

    View getView();
}
