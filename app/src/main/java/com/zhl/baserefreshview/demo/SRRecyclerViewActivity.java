package com.zhl.baserefreshview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.zhl.baserefreshview.refreshView.base.RefreshListener;
import com.zhl.baserefreshview.refreshView.SwipeRefreshLayout.SwipeRefreshRecyclerView;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SRRecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.sr_recycler_view)
    SwipeRefreshRecyclerView mSrRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        final List<String> textList = new ArrayList<>(MainActivity.sTextList);

        final CommonRecyclerAdapter<String> adapter = new CommonRecyclerAdapter<String>(textList) {
            @Override
            public BaseViewHolder<String> createViewHolder(int type) {
                return new TextViewHolder();
            }
        };

        PlaceHolderView placeHolderView = new PlaceHolderView(this);
        placeHolderView.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSrRecyclerView.showList(true);
            }
        });

        mSrRecyclerView.setPlaceHolderView(placeHolderView);
        mSrRecyclerView.setMoreViewHolder(new LoadMoreViewHolder());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mSrRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mSrRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (textList.get(position).equals("click to show error")) {
                    mSrRecyclerView.showError();
                } else if (textList.get(position).equals("click to show empty")) {
                    mSrRecyclerView.showEmpty();
                }
            }
        });

        mSrRecyclerView.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                mSrRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add(0, "test-refresh");
                        adapter.notifyDataSetChanged();
                        mSrRecyclerView.showList(true);
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mSrRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add("test-more");
                        adapter.notifyDataSetChanged();
                        mSrRecyclerView.showList(false);
                    }
                }, 1000);
            }
        });

        mSrRecyclerView.showLoading();
        mSrRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSrRecyclerView.showList(true);
            }
        }, 1000);
    }
}
