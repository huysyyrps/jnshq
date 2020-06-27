package com.sdjnshq.circle.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendPendencyItem;
import com.tencent.imsdk.friendship.TIMFriendResponse;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMPendencyType;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

// 好友申请
public class NewFriendAdapter extends BaseQuickAdapter<TIMFriendPendencyItem, XViewHolder> {

    public NewFriendAdapter() {
        super(R.layout.lay_new_friend_item);
    }

    @Override
    protected void convert(@NonNull XViewHolder viewHolder, TIMFriendPendencyItem item) {
        viewHolder.setText(R.id.tv_name, item.getNickname());
        viewHolder.setText(R.id.tv_description, item.getAddWording());

        switch (item.getType()) {
            case TIMPendencyType.TIM_PENDENCY_COME_IN:
                viewHolder.setGone(R.id.agree, true);
                viewHolder.setGone(R.id.tv_agreed, false);
                viewHolder.addOnClickListener(R.id.agree);
                break;
            case TIMPendencyType.TIM_PENDENCY_SEND_OUT:
                viewHolder.setGone(R.id.agree, true);
                viewHolder.setGone(R.id.tv_agreed, false);
                viewHolder.setText(R.id.tv_agreed, "等待验证");
                break;
            case TIMPendencyType.TIM_PENDENCY_BOTH:
                viewHolder.setGone(R.id.agree, true);
                viewHolder.setGone(R.id.tv_agreed, false);
                viewHolder.setText(R.id.tv_agreed, "已接受");
                break;
        }

    }


}
