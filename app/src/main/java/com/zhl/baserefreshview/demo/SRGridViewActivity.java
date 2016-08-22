package com.zhl.baserefreshview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.zhl.baserefreshview.refreshView.RefreshListener;
import com.zhl.baserefreshview.refreshView.SwipeRefreshGridView;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SRGridViewActivity extends AppCompatActivity {

    @BindView(R.id.sr_grid_view)
    SwipeRefreshGridView mSrGridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        ButterKnife.bind(this);

        GridView gridView = mSrGridView.getGridView();
        gridView.setNumColumns(2);

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
                mSrGridView.showList(true);
            }
        });
        mSrGridView.setLoadMoreView(new LoadMoreView(this), false);
        mSrGridView.setPlaceHolderView(placeHolderView);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (textList.get(i).equals("click to show error")) {
                    mSrGridView.showError();
                } else if (textList.get(i).equals("click to show empty")) {
                    mSrGridView.showEmpty();
                }
            }
        });
        mSrGridView.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                mSrGridView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add(0, "test-refresh");
                        adapter.notifyDataSetChanged();
                        mSrGridView.showList(true);
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mSrGridView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add("test-more");
                        adapter.notifyDataSetChanged();
                        mSrGridView.showList(false);
                    }
                }, 1000);
            }
        });

        mSrGridView.showLoading();
        mSrGridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSrGridView.showList(true);
            }
        }, 1000);
    }
}
