package com.sdjnshq.circle.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.utils.XUtils;

// 搜索后
public class SearchUserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {


    public SearchUserAdapter() {
        super(R.layout.lay_serch_friend_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, User user) {
        XUtils.loadHear(getRecyclerView(),user.getHead(),(ImageView) viewHolder.getView(R.id.iv_head));
        viewHolder.setText(R.id.tv_name,user.getNickName());
        viewHolder.setBackgroundRes(R.id.ll_sex,user.isMale()?R.drawable.bg_male_cricle:R.drawable.bg_female_cricle);
        viewHolder.setImageResource(R.id.iv_sex,user.isMale()?R.mipmap.ic_male_white:R.mipmap.ic_female_white);
        viewHolder.setGone(R.id.bt_add,!user.isFriend());
        viewHolder.addOnClickListener(R.id.bt_add);
    }
}
