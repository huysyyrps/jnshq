package com.sdjnshq.circle.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.utils.XUtils;

public class NearAdapter extends BaseQuickAdapter<Near, BaseViewHolder> {


    public NearAdapter() {
        super(R.layout.lay_near_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Near near) {
        XUtils.loadHear(getRecyclerView(),near.getHead(),(ImageView) viewHolder.getView(R.id.iv_head));
        viewHolder.setText(R.id.tv_name,near.getRelName());
        viewHolder.setBackgroundRes(R.id.ll_sex,near.isMale()?R.drawable.bg_male_cricle:R.drawable.bg_female_cricle);
        viewHolder.setImageResource(R.id.iv_sex,near.isMale()?R.mipmap.ic_male_white:R.mipmap.ic_female_white);
        viewHolder.setText(R.id.tv_age,near.getAge());
        viewHolder.setText(R.id.tv_dis,near.getDistance());
        viewHolder.setText(R.id.tv_sign,"签名："+near.getSignName());
    }
}
