package com.sdjnshq.circle.ui.page.money;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.sdjnshq.circle.bridge.money.MoneyViewModel;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.CircleAdapter;
import com.sdjnshq.circle.ui.adapter.InviteAdapter;
import com.sdjnshq.circle.ui.page.RecyclerFragment;

import java.util.List;

public class InviteUserFragment extends RecyclerFragment {
    MoneyViewModel moneyViewModel;

    public static InviteUserFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        InviteUserFragment fragment = new InviteUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseQuickAdapter initAdapter() {
        InviteAdapter inviteAdapter = new InviteAdapter();
        return inviteAdapter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moneyViewModel = getFragmentViewModelProvider(this).get(MoneyViewModel.class);
        moneyViewModel.inviteLive.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mAdapter.addData(users);
            }
        });
        moneyViewModel.getInvite();
        getSmartRefreshLayout().setEnableRefresh(false);
        getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                moneyViewModel.getInvite();
            }
        });
    }
}
