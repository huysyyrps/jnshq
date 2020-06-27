package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Comment;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.qcloud.tim.uikit.component.face.FaceManager;

public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {
    public CommentAdapter() {

        super(R.layout.lay_comment_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Comment comment) {
//        viewHolder.addOnClickListener(R.id.iv_head)
//                .addOnClickListener(R.id.tv_name);

        FaceManager.handlerEmojiText(viewHolder.getView(R.id.tv_content), comment.getCommentInfo(), false);
//        viewHolder.setText(R.id.tv_content, comment.getCommentInfo());
        viewHolder.setText(R.id.tv_name, comment.getRelName());
        viewHolder.setText(R.id.tv_time, comment.getFriendAddTime());
        XUtils.loadHear(getRecyclerView(), comment.getHead(), viewHolder.getView(R.id.iv_head));
    }
}
