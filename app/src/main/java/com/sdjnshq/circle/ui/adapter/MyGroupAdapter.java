package com.sdjnshq.circle.ui.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;

import java.util.List;

public class MyGroupAdapter extends BaseQuickAdapter<ContactItemBean, BaseViewHolder> {
    public MyGroupAdapter() {
        super(R.layout.lay_my_group_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ContactItemBean itemBean) {
        XUtils.loadHear(getRecyclerView(),itemBean.getAvatarurl(),viewHolder.getView(R.id.iv_head));
        if(TextUtils.isEmpty(itemBean.getNickname())){
            viewHolder.setText(R.id.tv_name,itemBean.getRemark()+"的群聊" );
            viewHolder.setText(R.id.tv_desc, "");
        }else {
            viewHolder.setText(R.id.tv_name,itemBean.getNickname());
            viewHolder.setText(R.id.tv_desc, itemBean.getRemark());
        }
    }
}
