package com.sdjnshq.circle.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.utils.XUtils;

import java.io.File;

public class SelectImageAdapter extends BaseQuickAdapter<MediaInfo, BaseViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    public int SELECT_MAX = 3;

    public SelectImageAdapter() {
        super(R.layout.lay_select_img_item);
    }


    @Override
    public int getItemViewType(int position) {
        return (position == getData().size() && position < SELECT_MAX) ? TYPE_CAMERA : TYPE_PICTURE;
    }

    @Override
    public int getItemCount() {
        if (getData().size() >= 0 && getData().size() < SELECT_MAX) {
            return getData().size() + 1;
        } else {
            return getData().size();
        }
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, MediaInfo mediaInfo) {
        viewHolder.addOnClickListener(R.id.iv_del);
        if (viewHolder.getItemViewType() == TYPE_CAMERA) {
            viewHolder.setImageResource(R.id.iv_img, R.mipmap.ic_select_img_add);
            viewHolder.setVisible(R.id.iv_del, false);
        } else {
            viewHolder.setVisible(R.id.iv_del, true);
            if (TextUtils.isEmpty(mediaInfo.getUrl())) {
                Glide.with(getRecyclerView()).load(new File(mediaInfo.getPath())).into((ImageView) viewHolder.getView(R.id.iv_img));
            } else {
                Glide.with(getRecyclerView()).load(XUtils.getImagePath(mediaInfo.getUrl())).into((ImageView) viewHolder.getView(R.id.iv_img));
            }

            if (mItemLongClickListener != null) {
                viewHolder.itemView.setOnLongClickListener(v -> {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemLongClickListener.onItemLongClick(viewHolder, adapterPosition, v);
                    return true;
                });
            }
        }

    }

    private OnItemLongClickListener mItemLongClickListener;

    public void setItemLongClickListener(OnItemLongClickListener l) {
        this.mItemLongClickListener = l;
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(RecyclerView.ViewHolder holder, int position, View v);
    }
}

