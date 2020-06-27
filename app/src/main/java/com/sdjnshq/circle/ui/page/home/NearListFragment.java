package com.sdjnshq.circle.ui.page.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.HomeViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.Location;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.ui.adapter.NearAdapter;
import com.sdjnshq.circle.ui.page.RecyclerFragment;
import com.sdjnshq.circle.ui.page.UserDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class NearListFragment extends RecyclerFragment {
    HomeViewModel mHomeViewModel;

    public static NearListFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        NearListFragment fragment = new NearListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        NearAdapter nearAdapter = new NearAdapter();
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));
        nearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                intent.putExtra("userId", String.valueOf(((Near) baseQuickAdapter.getItem(i)).getUserId()));
                startActivity(intent);
            }
        });
        return nearAdapter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeViewModel = getFragmentViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.getNearListLive().observe(getViewLifecycleOwner(), new Observer<List<Near>>() {
            @Override
            public void onChanged(List<Near> nears) {
                SmartRefreshLayout refreshLayout = getSmartRefreshLayout();
                // 关闭刷新 或者加载更多
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh();
                }
                if (refreshLayout.isLoading()) {
                    refreshLayout.finishLoadMore();
                }
                mAdapter.setNewData(nears);
            }
        });
        mHomeViewModel.getNearList();
        getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mHomeViewModel.getNearList();
            }
        });
        getSmartRefreshLayout().setEnableLoadMore(false);
    }

    public void refresh() {
        mHomeViewModel.nearCurrentPage = 1;
        mHomeViewModel.getNearList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getPage().equals("refresh")) {
            if (mAdapter != null && mAdapter.getData() != null) {
                refresh();
            }
        }
    }
}
