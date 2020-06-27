package com.sdjnshq.circle.ui.adapter;

import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;

// 创建群聊—— 选择朋友
public class SelectFriendAdapter extends BaseQuickAdapter<ContactItemBean, BaseViewHolder> {

    public SelectFriendAdapter() {
        super(R.layout.lay_select_friend_item);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        viewHolder.setOnCheckedChangeListener(R.id.checkbox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 getData().get(viewHolder.getAdapterPosition()).setEnable(isChecked);
            }
        });
        return viewHolder;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ContactItemBean user) {
        XUtils.loadHear(getRecyclerView(),user.getAvatarurl(),viewHolder.getView(R.id.iv_head));
        viewHolder.setText(R.id.tv_name,user.getNickname());
        viewHolder.setChecked(R.id.checkbox,user.isEnable());
    }
}
