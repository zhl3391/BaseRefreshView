package com.zhl.baserefreshview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static List<String> sTextList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sTextList.add("click to show error");
        sTextList.add("click to show empty");
        for (int i = 0; i < 3; i++) {
            sTextList.add("test" + i);
        }
    }

    @OnClick({R.id.btn_list_view, R.id.btn_recycler_view, R.id.btn_grid_view, R.id.btn_ptr_list_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_list_view:
                startActivity(new Intent(this, SRListViewActivity.class));
                break;
            case R.id.btn_recycler_view:
                startActivity(new Intent(this, SRRecyclerViewActivity.class));
                break;
            case R.id.btn_grid_view:
                startActivity(new Intent(this, SRGridViewActivity.class));
                break;
            case R.id.btn_ptr_list_view:
                startActivity(new Intent(this, PtrListViewActivity.class));
                break;
        }
    }
}
