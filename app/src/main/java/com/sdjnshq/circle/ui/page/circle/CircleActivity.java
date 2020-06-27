package com.sdjnshq.circle.ui.page.circle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.tools.ScreenUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.circle.CircleViewModel;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.ui.adapter.CommentAdapter;
import com.sdjnshq.circle.ui.adapter.MediaAdapter;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.page.ImageListActivity;
import com.sdjnshq.circle.utils.FullyGridLayoutManager;
import com.sdjnshq.circle.utils.XUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.qcloud.tim.uikit.utils.SoftKeyBoardUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

public class CircleActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    TextView tvContent;
    TextView tvLocation;
    TextView tvInfo;
    TextView tvThumb;
    TextView tvComment;
    @BindView(R.id.et_comment)
    EditText etComment;
    ImageView ivThumb;

    CircleViewModel mCircleViewModel;
    Circle mCircle;
    CommentAdapter mCommentAdapter;
    boolean isThumb = false;

    public static void newIntent(Activity activity, Circle circle) {
        Intent intent = new Intent(activity, CircleActivity.class);
        intent.putExtra("circle", circle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        mCommentAdapter = new CommentAdapter();

        mCircleViewModel = getActivityViewModelProvider(this).get(CircleViewModel.class);
        mCircleViewModel.getPagePostLive().observe(this, new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if (pagePost.getCode() == CircleViewModel.THUMB_UP) {
                    isThumb = true;
                    mCircle.setOrdernum(mCircle.getOrdernum() + 1);
                    tvThumb.setText(String.valueOf(mCircle.getOrdernum()));
                    mCircle.setThumb(true);
                    ivThumb.setImageResource(R.mipmap.action_thumb_y);
                } else if (pagePost.getCode() == CircleViewModel.THUMB_UP_NO) {
                    isThumb = false;
                    mCircle.setOrdernum(mCircle.getOrdernum() - 1);
                    tvThumb.setText(String.valueOf(mCircle.getOrdernum()));
                    mCircle.setThumb(false);
                    ivThumb.setImageResource(R.mipmap.action_thumb);
                } else if (pagePost.isSucess()) {
                    // 刷新评论列表
                    etComment.setText("");
                    SoftKeyBoardUtil.hideKeyBoard(etComment);
                    mCommentAdapter.replaceData(new ArrayList<>());
                    mCircleViewModel.refreshComment(String.valueOf(mCircle.getId()));
                }
            }
        });
        mCircleViewModel.getCommentLiveData().observe(this, new Observer<List>() {
            @Override
            public void onChanged(List list) {
                mCommentAdapter.addData(list);
            }
        });
        ButterKnife.bind(this);
        smartRefreshLayout.setEnableRefresh(false);
        titleBar.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCircle = getIntent().getParcelableExtra("circle");
        initCircle();
        mCircleViewModel.commentList(String.valueOf(mCircle.getId()));
    }

    private void initCircle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        View headView = LayoutInflater.from(this).inflate(R.layout.lay_circle_detail_head, null);
        tvContent = headView.findViewById(R.id.tv_content);
        tvLocation = headView.findViewById(R.id.tv_location);
        tvInfo = headView.findViewById(R.id.tv_info);

        tvThumb = headView.findViewById(R.id.tv_thumb);
        tvComment = headView.findViewById(R.id.tv_comment);
        RecyclerView rvMedia = headView.findViewById(R.id.rv_media);

        JzvdStd jzvdStd = headView.findViewById(R.id.jz_video);
        headView.findViewById(R.id.iv_thumb).setOnClickListener(this);
        headView.findViewById(R.id.tv_thumb).setOnClickListener(this);
        headView.findViewById(R.id.tv_comment).setOnClickListener(this);
        headView.findViewById(R.id.iv_comment).setOnClickListener(this);
        headView.findViewById(R.id.v_media).setOnClickListener(this);
        ivThumb = headView.findViewById(R.id.iv_thumb);
        isThumb = mCircle.getCurrUserGood() == 1;
        ivThumb.setImageResource(mCircle.getCurrUserGood() == 1 ? R.mipmap.action_thumb_y : R.mipmap.action_thumb);


        XUtils.loadHear(this, XUtils.getImagePath(mCircle.getImgUrl()), (ImageView) titleBar.getLeftCustomView().findViewById(R.id.iv_head));
        ((TextView) titleBar.getLeftCustomView().findViewById(R.id.tv_name)).setText(mCircle.getRelName());


        // 图片
        if (mCircle.getNewsType() == Circle.TYPE_IMAGE) {
            rvMedia.setVisibility(View.VISIBLE);
            jzvdStd.setVisibility(View.GONE);
            MediaAdapter mediaAdapter = new MediaAdapter(mCircle.getImageList());
            // 图片
            FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                    3, GridLayoutManager.VERTICAL, false);
            manager.setCanScroll(false);
            rvMedia.setLayoutManager(manager);
            rvMedia.addItemDecoration(new GridSpacingItemDecoration(3,
                    ScreenUtils.dip2px(this, 4), false));

            mediaAdapter.bindToRecyclerView(rvMedia);
            if (mCircle.getImageList().size() == 0) {
                headView.findViewById(R.id.v_media).setVisibility(View.GONE);
                rvMedia.setVisibility(View.GONE);
            }
        } else {
            // 视频
            rvMedia.setVisibility(View.GONE);
            jzvdStd.setVisibility(View.VISIBLE);
            jzvdStd.setUp(mCircle.getVideo(), "");
            Glide.with(this).load(XUtils.getImagePath(mCircle.getNewImg())).into(jzvdStd.posterImageView);
        }
        tvContent.setText(mCircle.getNewsContent());
        tvInfo.setText(mCircle.getInfo() + " " + mCircle.getPoiName() == null ? "" : mCircle.getPoiName());
        tvComment.setText(String.valueOf(mCircle.getCommentCount()));
        tvThumb.setText(String.valueOf(mCircle.getOrdernum()));

        mCommentAdapter.addHeaderView(headView);
        mCommentAdapter.bindToRecyclerView(recyclerView);
    }


    @OnClick({R.id.bt_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_comment:
                mCircleViewModel.comment(String.valueOf(mCircle.getId()), etComment.getText().toString());
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_thumb:
            case R.id.tv_thumb:
                mCircleViewModel.thumbUp(String.valueOf(mCircle.getId()), !isThumb);
                break;
            case R.id.tv_comment:
            case R.id.iv_comment:
                etComment.findFocus();
                break;
            case R.id.v_media:
                ImageListActivity.newActiviy(this, mCircle.getImageList());
                break;
        }
    }
}
