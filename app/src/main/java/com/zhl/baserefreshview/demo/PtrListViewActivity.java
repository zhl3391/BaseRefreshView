package com.zhl.baserefreshview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhl.baserefreshview.refreshView.base.RefreshListener;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zhouhl on 2017/1/21.
 * PtrListViewActivity
 */

public class PtrListViewActivity extends AppCompatActivity {

    @BindView(R.id.ptr_refresh_list_view)
    PtrRefreshListView mPtrRefreshListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_list_view);
        ButterKnife.bind(this);

        final PtrRefreshLayout ptrRefreshLayout = (PtrRefreshLayout) mPtrRefreshListView.getRefreshLayout();
        ptrRefreshLayout.setResistance(1.7f);
        ptrRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrRefreshLayout.setDurationToClose(200);
        ptrRefreshLayout.setDurationToCloseHeader(1000);
        ptrRefreshLayout.setPullToRefresh(false);
        ptrRefreshLayout.setKeepHeaderWhenRefresh(true);

        final ListView listView = mPtrRefreshListView.getListView();

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
                mPtrRefreshListView.showList(true);
            }
        });
        mPtrRefreshListView.setLoadMoreView(new LoadMoreView(this), false);
        mPtrRefreshListView.setPlaceHolderView(placeHolderView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (textList.get(i).equals("click to show error")) {
                    mPtrRefreshListView.showError();
                } else if (textList.get(i).equals("click to show empty")) {
                    mPtrRefreshListView.showEmpty();
                }
            }
        });
        mPtrRefreshListView.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefresh() {
                mPtrRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add(0, "test-refresh");
                        adapter.notifyDataSetChanged();
                        mPtrRefreshListView.showList(true);
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mPtrRefreshListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textList.add("test-more");
                        adapter.notifyDataSetChanged();
                        mPtrRefreshListView.showList(false);
                    }
                }, 1000);
            }
        });

        mPtrRefreshListView.showLoading();
        mPtrRefreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrRefreshListView.setAdapter(adapter);
                mPtrRefreshListView.showList(true);
            }
        }, 2000);
    }
}
