package com.sdjnshq.circle.ui.page.circle;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class CircleFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_image)
    RoundedImageView ivImage;
    @BindView(R.id.cl_images)
    ConstraintLayout clImages;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_thumb)
    TextView tvThumb;
    @BindView(R.id.tv_comment)
    TextView tvComment;

    Circle mCircle;

    public static CircleFragment newInstance(Circle circle) {

        Bundle args = new Bundle();
        args.putParcelable("circle",circle);
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int resourceId() {
        return R.layout.fragment_circle;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCircle = getArguments().getParcelable("circle");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        initCircle();
    }


    private void initCircle() {
        titleBar.getCenterTextView().setText(mCircle.getRelName());
        tvComment.setText(mCircle.getNewsContent());
        tvContent.setText(mCircle.getNewsContent());
        tvInfo.setText(mCircle.getInfo());
        tvComment.setText(String.valueOf(mCircle.getCommentCount()));
        tvThumb.setText(String.valueOf(mCircle.getOrdernum()));
    }


    @OnClick({R.id.iv_thumb, R.id.tv_thumb, R.id.iv_comment, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_thumb:
                break;
            case R.id.tv_thumb:
                break;
            case R.id.iv_comment:
                break;
            case R.id.tv_comment:
                break;
        }
    }
}
