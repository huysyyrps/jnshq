package com.sdjnshq.circle.ui.page.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.utils.XUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.v_content)
    View vContent;
    @BindView(R.id.jz_video)
    JzvdStd jzvdStd;
    String path;
    String img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        path = getIntent().getStringExtra("url");
        img = getIntent().getStringExtra("img");
        jzvdStd.setUp(path,"");
        Glide.with(this).load(XUtils.getImagePath(img)).into(jzvdStd.posterImageView);
        vContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jzvdStd.backPress();
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
    @Override
    protected void onResume() {
        super.onResume();
        jzvdStd.startVideo();
    }
}
