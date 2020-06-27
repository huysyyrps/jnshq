package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Rank;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.utils.XUtils;

public class InviteAdapter extends BaseQuickAdapter<User, BaseViewHolder> {


    public InviteAdapter() {
        super(R.layout.lay_invite_item);
    }

    @Override
    public void convert(@NonNull BaseViewHolder viewHolder, User user) {
        XUtils.loadHear(getRecyclerView(), XUtils.getImagePath(user.getImgUrl()), viewHolder.getView(R.id.iv_head));
        viewHolder.setText(R.id.tv_money,"+"+user.getMoneyTotal()+"元");
        viewHolder.setText(R.id.tv_name, user.getNickName());
    }
}
