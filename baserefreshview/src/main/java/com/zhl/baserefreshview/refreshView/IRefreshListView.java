package com.zhl.baserefreshview.refreshView;

import android.widget.ListView;

public interface IRefreshListView extends IBaseSwipeRefreshView{

    ListView getListView();

    void hideDivider();

}
