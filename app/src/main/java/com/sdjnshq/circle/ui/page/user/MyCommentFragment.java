package com.sdjnshq.circle.ui.page.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.sdjnshq.architecture.utils.BarUtils;
import com.sdjnshq.architecture.utils.ColorUtils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.ui.adapter.CircleAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.SendCircleActivity;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCommentFragment extends BaseFragment {
    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    UserViewModel userViewModel;
    CircleAdapter circleAdapter;

    double present = 0;


    public static MyCommentFragment newInstance() {
        Bundle args = new Bundle();

        MyCommentFragment fragment = new MyCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_my_circle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleBar.findViewById(R.id.tv_circle_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendCircleActivity.class);
                startActivity(intent);
            }
        });
        // 添加新的动态刷新页面
        mSharedViewModel.circleToAddListener.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                circleAdapter.setNewData(new ArrayList<>());
                userViewModel.circleCurrenPage = 1;
                userViewModel.initCircleList();
            }
        });

        initAppbarLayout();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        circleAdapter = new CircleAdapter(true);
        circleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (view.getId() == R.id.tv_delete) {
                    userViewModel.deleteCircle(((Circle) adapter.getData().get(i)).getId(), i);
                }
            }
        });
        circleAdapter.bindToRecyclerView(recyclerView);

        userViewModel = getFragmentViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getCircleListLive().observe(getViewLifecycleOwner(), new Observer<List<Circle>>() {
            @Override
            public void onChanged(List<Circle> circles) {
                circleAdapter.setNewData(circles);
            }
        });
        userViewModel.initCircleList();

        userViewModel.deleteCircleLive.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                circleAdapter.remove(integer);
            }
        });
    }

    private void initAppbarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                present = Math.abs(verticalOffset) / (Double.valueOf(appBarLayout.getHeight() / 2) - BarUtils.getActionBarHeight() - BarUtils.getStatusBarHeight());
                if (present > 1) {
                    present = 1;
                } else if (present < 0) {
                    present = 0;
                }
                setTitleBarBackDrawable(titleBar.findViewById(R.id.iv_back), present > 0.5 ? R.mipmap.ic_back : R.mipmap.ic_back_white);
                titleBar.setBackgroundColor(ColorUtils.setAlphaComponent(ColorUtils.getColor(R.color.white), (float) present));
//                titleBar.getLeftTextView().setTextColor(ColorUtils.setAlphaComponent(ColorUtils.getColor(R.color.font_normal), (float) present));
            }
        });
    }

    private void setTitleBarBackDrawable(ImageView textView, int resId) {
        textView.setImageResource(resId);
    }

}
