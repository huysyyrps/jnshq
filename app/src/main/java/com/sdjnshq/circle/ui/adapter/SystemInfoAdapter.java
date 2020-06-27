package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.SystemInfo;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.utils.XUtils;

// 我的访客
public class SystemInfoAdapter extends BaseQuickAdapter<SystemInfo, BaseViewHolder> {

    public SystemInfoAdapter() {
        super(R.layout.lay_system_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SystemInfo systemInfo) {
        viewHolder.setText(R.id.tv_name,"通知");
        viewHolder.setText(R.id.tv_content,systemInfo.getMessContent());
        viewHolder.setText(R.id.tv_time,systemInfo.getAddTime());
    }
}
