package com.sdjnshq.circle.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.BaseViewModel;
import com.sdjnshq.circle.utils.XUtils;

public  class XViewHolder extends BaseViewHolder {

    public XViewHolder(View view) {
        super(view);
    }

    public void loadHead(View view,String url){
        XUtils.loadHear(view,XUtils.getImagePath(url),getView(R.id.iv_head));
    }
}
