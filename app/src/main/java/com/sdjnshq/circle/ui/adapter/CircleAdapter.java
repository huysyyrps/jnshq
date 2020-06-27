package com.sdjnshq.circle.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.utils.FullyGridLayoutManager;
import com.sdjnshq.circle.utils.XUtils;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.tools.ScreenUtils;
import com.tencent.qcloud.tim.uikit.component.face.FaceManager;

import cn.jzvd.JzvdStd;

public class CircleAdapter extends BaseQuickAdapter<Circle, BaseViewHolder> {
    boolean isMine;

    public CircleAdapter() {
        super(R.layout.circle_item);
    }

    public CircleAdapter(boolean isMine) {
        super(R.layout.circle_item);
        this.isMine = isMine;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        // 图片
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext,
                3, GridLayoutManager.VERTICAL, false);
        manager.setCanScroll(false);
        RecyclerView recyclerView = viewHolder.getView(R.id.rv_media);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(mContext, 4), false));
        return viewHolder;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Circle circle) {
        viewHolder.setGone(R.id.iv_more, false);
        viewHolder.addOnClickListener(R.id.iv_head)
                .addOnClickListener(R.id.tv_delete)
                .addOnClickListener(R.id.tv_name);
        viewHolder.setVisible(R.id.tv_delete, isMine);

        boolean isMale = circle.getSex() != null && circle.getSex().equals("1");
        viewHolder.setBackgroundRes(R.id.ll_sex, isMale ? R.drawable.bg_male_cricle : R.drawable.bg_female_cricle);
        viewHolder.setImageResource(R.id.iv_sex, isMale ? R.mipmap.ic_male_white : R.mipmap.ic_female_white);
        viewHolder.setText(R.id.tv_age, circle.getAge());

        viewHolder.setGone(R.id.tv_content, !TextUtils.isEmpty(circle.getNewsContent()));

        FaceManager.handlerEmojiText(viewHolder.getView(R.id.tv_content), circle.getNewsContent(), false);
        viewHolder.setText(R.id.tv_name, circle.getRelName());
        viewHolder.setText(R.id.tv_info, circle.getInfo());
        viewHolder.setText(R.id.tv_comment, String.valueOf(circle.getCommentCount()));
        viewHolder.setText(R.id.tv_thumb, String.valueOf(circle.getOrdernum()));

        viewHolder.setText(R.id.tv_location, circle.getSid());

        XUtils.loadHear(getRecyclerView(), circle.getHead(), viewHolder.getView(R.id.iv_head));

        // 图片
        if (circle.getNewsType() == Circle.TYPE_IMAGE) {
            viewHolder.setGone(R.id.rv_media, true);
            viewHolder.setGone(R.id.v_media, true);
            viewHolder.setGone(R.id.jz_video, false);
            viewHolder.setGone(R.id.v_video, false);
            RecyclerView recyclerView = viewHolder.getView(R.id.rv_media);
            viewHolder.addOnClickListener(R.id.v_media);
            if(recyclerView.getAdapter() == null) {
                MediaAdapter mediaAdapter = new MediaAdapter(circle.getImageList());
                mediaAdapter.bindToRecyclerView(recyclerView);
            }else{
                ((MediaAdapter)recyclerView.getAdapter()).setNewData(circle.getImageList());
            }
            if (circle.getImageList().size() == 0) {
                viewHolder.setGone(R.id.rv_media, false);
                viewHolder.setGone(R.id.v_media, false);
            }

        } else {
            // 视频
            viewHolder.setGone(R.id.rv_media, false);
            viewHolder.setGone(R.id.jz_video, true);
            viewHolder.setGone(R.id.v_video, true);
            JzvdStd jzvdStd = viewHolder.getView(R.id.jz_video);
            jzvdStd.setUp(circle.getVideo(), "");
            jzvdStd.posterImageView.setScaleType(ImageView.ScaleType.CENTER);

            Glide.with(getRecyclerView()).load(XUtils.getImagePath(circle.getNewImg())).into(jzvdStd.posterImageView);
            viewHolder.addOnClickListener(R.id.v_video);
        }
    }

}
