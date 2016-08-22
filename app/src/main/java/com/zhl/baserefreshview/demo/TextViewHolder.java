package com.zhl.baserefreshview.demo;

import android.view.View;
import android.widget.TextView;

import com.zhl.commonadapter.BaseViewHolder;

public class TextViewHolder extends BaseViewHolder<String> {

    TextView mTvText;

    @Override
    public void bindView(View view) {
        super.bindView(view);
        mTvText = (TextView) view.findViewById(R.id.tv_text);
    }

    @Override
    public void updateView(String data, int position) {
        mTvText.setText(data);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_text;
    }
}
