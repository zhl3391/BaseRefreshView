package com.zhl.baserefreshview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhl.baserefreshview.refreshView.base.RefreshListener;
import com.zhl.baserefreshview.refreshView.SwipeRefreshLayout.SwipeRefreshListView;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SRListViewActivity extends AppCompatActivity {

    @BindView(R.id.swipe_refresh_list_view)
    SwipeRefreshListView mSwipeRefreshListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);

        final ListView listView = mSwipeRefreshListView.getListView();

        PlaceHolderView placeHolderView = new PlaceHolderView(this);

        final List<String> textList = new ArrayList<>(MainActivity.sTextList);

        final CommonAdapter<String> adapter = new CommonAdapter<String>(textList) {
            @Override
            public BaseViewHolder<String> createViewHolder(int type) {
                return new TextViewHolder();
            }
        };

        placeHolderView.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwipeRefreshListView.showList(true);
            }
        });
        mSwipeRefreshListView.setLoadMoreView(new LoadMoreView(this), false);
        mSwipeRefreshListView.setPlaceHolderView(placeHolderView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (textList.get(i).equals("click to show error")) {
                    mSwipeRefreshListView.showError();
                } else if (textList.get(i).equals("click to show empty")) {
                    mSwipeRefreshListView.showEmpty();
                }
            }
        });
        mSwipeRefreshListView.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add(0, "test-refresh");
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshListView.showList(true);
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mSwipeRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add("test-more");
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshListView.showList(false);
                    }
                }, 1000);
            }
        });

        mSwipeRefreshListView.showLoading();
        mSwipeRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshListView.setAdapter(adapter);
                mSwipeRefreshListView.showList(true);
            }
        }, 2000);
    }
}
