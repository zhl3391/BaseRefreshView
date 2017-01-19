package com.zhl.baserefreshview;

import android.support.annotation.NonNull;
import android.view.View;

public interface ILoadMoreView {

    @NonNull
    View getView();

    void showLoading();

    void showNoMore();

    void showError();

    void hide();
}
