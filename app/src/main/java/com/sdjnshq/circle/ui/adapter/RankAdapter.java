package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.tools.ValueOf;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Rank;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.qcloud.tim.uikit.component.photoview.Util;

import java.util.List;

public class RankAdapter extends BaseQuickAdapter<Rank, BaseViewHolder> {


    public RankAdapter() {
        super(R.layout.lay_rank_item);
    }

    @Override
    public void convert(@NonNull BaseViewHolder viewHolder, Rank rank) {
        viewHolder.setVisible(R.id.iv_king, viewHolder.getAdapterPosition() < 3);
        if(viewHolder.getAdapterPosition() == 0){
            viewHolder.setImageResource(R.id.iv_king,R.mipmap.ic_rank_king);
        }else if(viewHolder.getAdapterPosition() == 0){
            viewHolder.setImageResource(R.id.iv_king,R.mipmap.ic_rank_king2);
        }else if(viewHolder.getAdapterPosition() == 0){
            viewHolder.setImageResource(R.id.iv_king,R.mipmap.ic_rank_king3);
        }


        viewHolder.setVisible(R.id.tv_num, viewHolder.getAdapterPosition() != 0);
        viewHolder.setText(R.id.tv_num, String.valueOf(viewHolder.getAdapterPosition()));
        XUtils.loadHear(getRecyclerView(), XUtils.getImagePath(rank.getImgUrl()), viewHolder.getView(R.id.iv_head));
        viewHolder.setText(R.id.tv_money, "+" + rank.getDoneMoney() + "元");
        viewHolder.setText(R.id.tv_num_count, "已邀请" + rank.getNum() + "人");
        viewHolder.setText(R.id.tv_name, rank.getRelName());
    }
}
