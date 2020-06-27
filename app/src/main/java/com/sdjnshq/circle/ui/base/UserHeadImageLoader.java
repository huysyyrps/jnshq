package com.sdjnshq.circle.ui.base;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sdjnshq.circle.data.bean.UserHead;
import com.youth.banner.loader.ImageLoader;

public class UserHeadImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        //Glide 加载图片简单用法
        Glide.with(context).load(((UserHead)path).getHeadimgURL()).into(imageView);

    }
}