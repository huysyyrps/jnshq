package com.sdjnshq.circle.ui.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.MediaInfo;

import java.util.List;

public class SelectMediaAdapter extends BaseQuickAdapter<MediaInfo, BaseViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    public static final int SELECT_MAX = 3;

    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }


    public SelectMediaAdapter(@Nullable List<MediaInfo> data, onAddPicClickListener addPicClickListener) {
        super(R.layout.gv_filter_image, data);
        this.mOnAddPicClickListener = addPicClickListener;
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_image:
                        if (mOnAddPicClickListener != null) {
                            mOnAddPicClickListener.onAddPicClick();
                        }
                        break;
                    case R.id.iv_del:
                        delete(position);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position == getData().size() ? TYPE_CAMERA : TYPE_PICTURE;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, MediaInfo localMedia) {
        //少于8张，显示继续添加的图标s
        if (viewHolder.getItemViewType() == TYPE_CAMERA) {
            viewHolder.setImageResource(R.id.iv_image, R.mipmap.ic_select_img_add);
            viewHolder.addOnClickListener(R.id.iv_image);
            viewHolder.setVisible(R.id.iv_del, false);
        } else {
            viewHolder.setVisible(R.id.iv_del, true);
            viewHolder.addOnClickListener(R.id.iv_del);
            MediaInfo media = getItem(viewHolder.getAdapterPosition());
            if (media == null
                    || TextUtils.isEmpty(media.getPath())) {
                return;
            }


            Log.i(TAG, "原图地址::" + media.getPath());




            Glide.with(viewHolder.itemView.getContext())
                    .load(media.getPath())
                    .centerCrop()
                    .placeholder(R.color.gray)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((ImageView) viewHolder.getView(R.id.iv_image));
        }
    }

    @Override
    public int getItemCount() {
        if (getData().size() > 0 && getData().size() < SELECT_MAX) {
            return getData().size() + 1;
        } else {
            return getData().size();
        }
    }

    /**
     * 删除
     */
    public void delete(int position) {
        try {
            if (position != RecyclerView.NO_POSITION && getData().size() > position) {
                remove(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
