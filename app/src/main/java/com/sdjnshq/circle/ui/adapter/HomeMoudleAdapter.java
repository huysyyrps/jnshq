package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Moudle;

import java.util.List;

public class HomeMoudleAdapter extends BaseQuickAdapter<Moudle, BaseViewHolder> {
    public HomeMoudleAdapter(@Nullable List<Moudle> data) {
        super(R.layout.lay_moudle_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Moudle moudle) {
        viewHolder.setText(R.id.tv_name,moudle.getName());
        viewHolder.setImageResource(R.id.iv_res,moudle.getResId());
    }
}
