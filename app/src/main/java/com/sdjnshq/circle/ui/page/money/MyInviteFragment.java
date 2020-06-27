package com.sdjnshq.circle.ui.page.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingScaleTabLayout;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.money.MoneyViewModel;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.CircleAdapter;
import com.sdjnshq.circle.ui.adapter.InviteFragmentAdapter;
import com.sdjnshq.circle.ui.adapter.NearAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.MainFragment;
import com.sdjnshq.circle.ui.page.RecyclerFragment;
import com.sdjnshq.circle.ui.page.UserDetailActivity;
import com.sdjnshq.circle.ui.page.circle.CircleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// 我的邀请人列表
public class MyInviteFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    SlidingScaleTabLayout tabLayout;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    MoneyViewModel moneyViewModel;

    public static MyInviteFragment newInstance() {
        Bundle args = new Bundle();

        MyInviteFragment fragment = new MyInviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_my_invite;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();

        moneyViewModel = getFragmentViewModelProvider(this).get(MoneyViewModel.class);
        moneyViewModel.balanceLive.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double balance) {
                tvMoney.setText(String.valueOf(balance)+"元");
            }
        });

        moneyViewModel.getBalance();
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        // 我邀请的人
        InviteUserFragment inviteFragment = InviteUserFragment.newInstance(0);
        InviteUserTwoFragment inviteFragment2 = InviteUserTwoFragment.newInstance(1);

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));
//        inviteFragment2.setDividerItemDecoration(divider);

        fragments.add(inviteFragment);
        fragments.add(inviteFragment2);
        viewPager.setAdapter(new InviteFragmentAdapter(getChildFragmentManager(), fragments));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

    @OnClick(R.id.bt_cashOut)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cashOut:
                start(CashOutFragment.newInstance());
                break;
        }
    }
}
