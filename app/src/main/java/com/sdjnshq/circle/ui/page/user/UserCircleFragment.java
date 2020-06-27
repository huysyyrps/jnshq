package com.sdjnshq.circle.ui.page.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.sdjnshq.architecture.utils.BarUtils;
import com.sdjnshq.architecture.utils.ColorUtils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.ui.adapter.CircleAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.RecyclerFragment;
import com.sdjnshq.circle.ui.page.SendCircleActivity;
import com.sdjnshq.circle.utils.AppSP;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// 其他用户列表
public class UserCircleFragment extends RecyclerFragment {
    UserViewModel userViewModel;
    CircleAdapter circleAdapter;

    public static UserCircleFragment newInstance(int position, String userId) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("userId", userId);

        UserCircleFragment fragment = new UserCircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_user_circle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                userViewModel.initCircleList();
            }
        });
        userViewModel = getFragmentViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setUserId(getArguments().getString("userId"));
        userViewModel.getCircleListLive().observe(getViewLifecycleOwner(), new Observer<List<Circle>>() {
            @Override
            public void onChanged(List<Circle> circles) {
                getSmartRefreshLayout().finishLoadMore(true);
                if (circles.size() != 0) {
                    circleAdapter.setNewData(circles);
                }
            }
        });
        userViewModel.initCircleList();

    }

    @Override
    public BaseQuickAdapter initAdapter() {
        circleAdapter = new CircleAdapter(false);
        return circleAdapter;
    }

}
