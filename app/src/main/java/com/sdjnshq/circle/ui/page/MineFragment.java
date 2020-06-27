package com.sdjnshq.circle.ui.page;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingScaleTabLayout;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.MineFragmentAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.friend.MyFriendFragment;
import com.sdjnshq.circle.ui.page.friend.MyGroupFragment;
import com.sdjnshq.circle.ui.page.home.CircleListFragment;
import com.sdjnshq.circle.ui.page.money.VIPFragment;
import com.sdjnshq.circle.ui.page.user.MyCircleFragment;
import com.sdjnshq.circle.ui.page.user.SettingFragment;
import com.sdjnshq.circle.ui.page.user.UserInfoEditFragment;
import com.sdjnshq.circle.ui.page.user.UserVisitorFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.XUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


// 个人中心
public class MineFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.tabLayout)
    SlidingScaleTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    List<Fragment> fragmentList;

    UserViewModel mUserViewModel;


    @Override
    public int resourceId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // titlebar
        // 设置
        titleBar.findViewById(R.id.ll_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainFragment) getParentFragment()).startBrotherFragment(SettingFragment.newInstance());
            }
        });

        mUserViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(UserViewModel.class);
        mUserViewModel.getUserLive().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                AppSP.getInstance().setUserName(user.getNickName());
                tvName.setText(user.getNickName());
                XUtils.loadHear(MineFragment.this, user.getHead(), ivHead);
                tvDesc.setText(user.getSign());
            }
        });
        mUserViewModel.init(AppSP.getInstance().getUserId());
        mUserViewModel.isVIP();
        initViewPager();
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();

        fragmentList.add(new Fragment());
        fragmentList.add(new Fragment());
        fragmentList.add(new Fragment());
        fragmentList.add(new Fragment());

        viewPager.setAdapter(new MineFragmentAdapter(getChildFragmentManager(), fragmentList));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);
    }

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.ll_vip, R.id.iv_head, R.id.iv_edit, R.id.tv_name, R.id.tv_desc, R.id.iv_friend, R.id.tv_firend, R.id.iv_comment, R.id.tv_comment, R.id.iv_circle, R.id.tv_circle, R.id.iv_group, R.id.tv_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_vip:
                ((MainFragment) getParentFragment()).startBrotherFragment(VIPFragment.newInstance());
                break;
            case R.id.iv_head:
            case R.id.tv_name:
            case R.id.iv_edit:
                ((MainFragment) getParentFragment()).startBrotherFragment(UserInfoEditFragment.newInstance());
                break;
            case R.id.tv_desc:
                break;
            case R.id.iv_friend:
            case R.id.tv_firend:
                ((MainFragment) getParentFragment()).startBrotherFragment(MyFriendFragment.newInstance());
                break;
            case R.id.iv_comment:
            case R.id.tv_comment:

                ((MainFragment) getParentFragment()).startBrotherFragment(UserVisitorFragment.newInstance());

                break;
            case R.id.iv_circle:
            case R.id.tv_circle:
                ((MainFragment) getParentFragment()).startBrotherFragment(MyCircleFragment.newInstance());
                break;
            case R.id.iv_group:
            case R.id.tv_group:
                ((MainFragment) getParentFragment()).startBrotherFragment(MyGroupFragment.newInstance());
                break;
        }
    }


}
