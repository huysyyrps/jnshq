package com.sdjnshq.circle.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.utils.XUtils;

// 我的访客
public class MyVisitorAdapter extends BaseQuickAdapter<Visitor, BaseViewHolder> {


    public MyVisitorAdapter() {
        super(R.layout.lay_visitor_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Visitor visitor) {
        XUtils.loadHear(getRecyclerView(),XUtils.getImagePath(visitor.getImgUrl()),viewHolder.getView(R.id.iv_head));
        viewHolder.setText(R.id.tv_name,visitor.getRelName());
        boolean isMale = visitor.getSex()!=null && visitor.getSex().equals("1");
        viewHolder.setBackgroundRes(R.id.ll_sex,isMale?R.drawable.bg_male_cricle:R.drawable.bg_female_cricle);
        viewHolder.setImageResource(R.id.iv_sex,isMale?R.mipmap.ic_male_white:R.mipmap.ic_female_white);
        viewHolder.setText(R.id.tv_age,visitor.getAge());
        viewHolder.setText(R.id.tv_time,visitor.getAddTime());
    }
}
