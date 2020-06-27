package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import java.util.List;

public class SelectItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SelectItemAdapter(@Nullable List<String> data) {
        super(R.layout.lay_select_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String s) {
        viewHolder.setText(R.id.tv_name,s);
    }
}
