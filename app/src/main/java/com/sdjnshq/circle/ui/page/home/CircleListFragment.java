package com.sdjnshq.circle.ui.page.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.HomeViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.Location;
import com.sdjnshq.circle.ui.adapter.CircleAdapter;
import com.sdjnshq.circle.ui.page.ImageListActivity;
import com.sdjnshq.circle.ui.page.RecyclerFragment;
import com.sdjnshq.circle.ui.page.UserDetailActivity;
import com.sdjnshq.circle.ui.page.circle.CircleActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class CircleListFragment extends RecyclerFragment {
    HomeViewModel mHomeViewModel;


    public static CircleListFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        CircleListFragment fragment = new CircleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        CircleAdapter circleAdapter = new CircleAdapter();
        circleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int i) {
                if (view.getId() == R.id.iv_head) {
                    UserDetailActivity.newUserDetailActivity(getActivity(), ((Circle) adapter.getData().get(i)).getAdd_userid());
                } else if (view.getId() == R.id.v_video) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra("url", ((Circle) adapter.getData().get(i)).getVideo());
                    intent.putExtra("img", ((Circle) adapter.getData().get(i)).getNewImg());
                    getActivity().startActivity(intent);
                } else if (view.getId() == R.id.v_media) {
                    ImageListActivity.newActiviy(getActivity(), ((Circle) adapter.getData().get(i)).getImageList());
                }
            }
        });
        circleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                CircleActivity.newIntent(getActivity(), (Circle) baseQuickAdapter.getData().get(i));
            }
        });
        return circleAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHomeViewModel = getFragmentViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.getCircleListLive().observe(getViewLifecycleOwner(), new Observer<List<Circle>>() {
            @Override
            public void onChanged(List<Circle> circles) {
                SmartRefreshLayout refreshLayout = getSmartRefreshLayout();
                // 关闭刷新 或者加载更多
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh();
                }
                if (refreshLayout.isLoading()) {
                    refreshLayout.finishLoadMore();
                }
                if (mHomeViewModel.circleCurrenPage == 1) {
                    mAdapter.setNewData(circles);
                } else {
                    mAdapter.addData(circles);
                }
                if (circles.size() > 0) {
                    mHomeViewModel.circleCurrenPage++;
                }
            }
        });
        mHomeViewModel.getCircleList();
        getSmartRefreshLayout().setEnableLoadMore(false);
//        . setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                mHomeViewModel.getCircleList();
//            }
//        });
    }

    public void refresh() {
        if (mHomeViewModel != null)
            mHomeViewModel.refreshCircleList();
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
