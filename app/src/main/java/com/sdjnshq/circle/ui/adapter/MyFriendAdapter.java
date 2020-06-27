package com.sdjnshq.circle.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;

public class MyFriendAdapter extends BaseQuickAdapter<ContactItemBean, BaseViewHolder> {


    public MyFriendAdapter() {
        super(R.layout.lay_my_friend_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ContactItemBean contactBean) {
        if (!TextUtils.isEmpty(contactBean.getRemark())) {
            viewHolder.setText(R.id.tv_name,contactBean.getRemark());
        } else if (!TextUtils.isEmpty(contactBean.getNickname())) {
            viewHolder.setText(R.id.tv_name,contactBean.getNickname());
        } else {
            viewHolder.setText(R.id.tv_name,contactBean.getId());
        }

        XUtils.loadHear(getRecyclerView(),contactBean.getAvatarurl(),(ImageView) viewHolder.getView(R.id.iv_head));
//        viewHolder.setText(R.id.tv_name,user.getNickName());
//        viewHolder.setBackgroundRes(R.id.ll_sex,user.isMale()?R.drawable.bg_male_cricle:R.drawable.bg_female_cricle);
//        viewHolder.setImageResource(R.id.iv_sex,user.isMale()?R.mipmap.ic_male_white:R.mipmap.ic_female_white);
////        viewHolder.setText(R.id.tv_age,user.getAge());
//        viewHolder.setText(R.id.tv_dis,user.getDistance());
    }
}
