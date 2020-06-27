package com.sdjnshq.circle.ui.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.base.GlideRadiusImageLoader;
import com.sdjnshq.circle.utils.XUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageListActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.banner)
    Banner banner;

    ArrayList<String> imgList;

    public static  void newActiviy(Activity activity,ArrayList<String> imgList){
        Intent intent = new Intent(activity,ImageListActivity.class);
        intent.putStringArrayListExtra("imgList",imgList);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        ButterKnife.bind(this);
        imgList = getIntent().getStringArrayListExtra("imgList");
        titleBar.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        banner.setImageLoader(new GlideRadiusImageLoader());
        if(imgList!=null) {
            banner.setImages(imgList);
            banner.start();
        }
    }
}
