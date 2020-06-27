package com.sdjnshq.circle.ui.page.user;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.HomeViewModel;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.SystemInfo;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.ui.adapter.MyVisitorAdapter;
import com.sdjnshq.circle.ui.adapter.SystemInfoAdapter;
import com.sdjnshq.circle.ui.page.RecyclerFragment;
import com.sdjnshq.circle.ui.page.UserDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 系统消息
public class SystemInfoFragment extends RecyclerFragment {
    UserViewModel userViewModel;
    SystemInfoAdapter systemAdapter;

    public static SystemInfoFragment newInstance() {
        Bundle args = new Bundle();

        SystemInfoFragment fragment = new SystemInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_system_info;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                userViewModel.systemInfo();
            }
        });
        userViewModel = getFragmentViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setUserId(getArguments().getString("userId"));
        userViewModel.systemLive.observe(getViewLifecycleOwner(), new Observer<List<SystemInfo>>() {
            @Override
            public void onChanged(List<SystemInfo> circles) {
                EventBus.getDefault().post(new MessageEvent("system"));

                getSmartRefreshLayout().finishLoadMore(true);
                if (circles.size() != 0) {
                    systemAdapter.setNewData(circles);
                }
            }
        });
        HomeViewModel homeViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(HomeViewModel.class);
        homeViewModel.getMessageCount();
        userViewModel.systemInfo();
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        systemAdapter = new SystemInfoAdapter();
        systemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
            }
        });
        return systemAdapter;
    }

}
