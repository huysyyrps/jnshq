package com.sdjnshq.circle.ui.adapter;

import androidx.annotation.NonNull;
import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;

public class PoiAdapter extends BaseQuickAdapter<PoiItem,XViewHolder> {
    public PoiAdapter() {
        super(R.layout.lay_item_poi);
    }

    @Override
    protected void convert(@NonNull XViewHolder viewHolder, PoiItem poiItem) {
        viewHolder.setText(R.id.tv_name,poiItem.getTitle());
    }
}
