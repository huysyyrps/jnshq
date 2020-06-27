package com.sdjnshq.circle.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;

import java.util.List;

public class MediaAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MediaAdapter(@Nullable List<String> data) {
        super(R.layout.lay_gv_image, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, String path) {

        Glide.with(viewHolder.itemView.getContext())
                .load(path)
                .centerCrop()
                .placeholder(R.color.gray)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) viewHolder.getView(R.id.iv_image));
    }


}
