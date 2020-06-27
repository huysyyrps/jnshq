package com.sdjnshq.circle.ui.page.money;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.money.MoneyViewModel;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.MainFragment;

import butterknife.OnClick;


public class VIPFragment extends BaseFragment {

    MoneyViewModel moneyViewModel;

    @Override
    public int resourceId() {
        return R.layout.fragment_vip;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moneyViewModel = getFragmentViewModelProvider(this).get(MoneyViewModel.class);
    }

    public static VIPFragment newInstance() {
        VIPFragment fragment = new VIPFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {
        }
    }
}
