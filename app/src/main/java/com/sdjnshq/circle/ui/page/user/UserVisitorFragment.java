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
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.MessageCount;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.ui.adapter.CircleAdapter;
import com.sdjnshq.circle.ui.adapter.MyVisitorAdapter;
import com.sdjnshq.circle.ui.page.RecyclerFragment;
import com.sdjnshq.circle.ui.page.UserDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 我的访客
public class UserVisitorFragment extends RecyclerFragment {
    UserViewModel userViewModel;
    MyVisitorAdapter visitorAdapter;

    public static UserVisitorFragment newInstance() {
        Bundle args = new Bundle();

        UserVisitorFragment fragment = new UserVisitorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_user_visitor;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                userViewModel.visitInfo();
            }
        });
        HomeViewModel homeViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(HomeViewModel.class);
        homeViewModel.getMessageCount();
        userViewModel = getFragmentViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setUserId(getArguments().getString("userId"));
        userViewModel.visitorLive.observe(getViewLifecycleOwner(), new Observer<List<Visitor>>() {
            @Override
            public void onChanged(List<Visitor> circles) {
                EventBus.getDefault().post(new MessageEvent("visitor"));

                getSmartRefreshLayout().finishLoadMore(true);
                if (circles.size() != 0) {
                    visitorAdapter.setNewData(circles);
                }
            }
        });
        userViewModel.visitInfo();
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        visitorAdapter = new MyVisitorAdapter();
        visitorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
                UserDetailActivity.newUserDetailActivity(getActivity(),((Visitor)adapter.getItem(i)).getAddUser());
            }
        });
        return visitorAdapter;
    }

}
