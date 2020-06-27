package com.sdjnshq.circle.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingScaleTabLayout;
import com.rczp.rczpMainActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.HomeViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.data.bean.Moudle;
import com.sdjnshq.circle.ui.adapter.CircleFragmentAdapter;
import com.sdjnshq.circle.ui.adapter.HomeMoudleAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.base.GlideRadiusImageLoader;
import com.sdjnshq.circle.ui.page.home.CircleListFragment;
import com.sdjnshq.circle.ui.page.home.NearListFragment;
import com.sdjnshq.circle.utils.XUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    SlidingScaleTabLayout tabLayout;
    @BindView(R.id.rv_moudle)
    RecyclerView rvMoudle;

    HomeViewModel mHomeViewModel;
    CircleListFragment mCircleFragment;
    NearListFragment mNearbyFragment;
    Intent intent;

    List moudleList = new ArrayList() {{
        add(new Moudle("人才招聘", R.drawable.ic_moudle1));
        add(new Moudle("房屋租赁", R.drawable.ic_moudle2));
        add(new Moudle("吃喝玩乐", R.drawable.ic_moudle3));
        add(new Moudle("生鲜超市", R.drawable.ic_moudle4));
        add(new Moudle("生意转让", R.drawable.ic_moudle7));
        add(new Moudle("二手车网", R.drawable.ic_moudle6));
        add(new Moudle("相亲交友", R.drawable.ic_moudle5));
        add(new Moudle("本地服务", R.drawable.ic_moudle8));
    }};
    private boolean hasData = false;

    @Override
    public int resourceId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setPage("refresh");
                EventBus.getDefault().post(messageEvent);
                refreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setEnableLoadMore(false);
        initMouble();
        initData();
    }

    private void initData() {
        mHomeViewModel = getFragmentViewModelProvider(this).get(HomeViewModel.class);
        mHomeViewModel.getBannerListLive().observe(getViewLifecycleOwner(), new Observer<List<BannerInfo>>() {
            @Override
            public void onChanged(List<BannerInfo> bannerInfos) {
                initBanner(bannerInfos);
            }
        });


        mHomeViewModel.getBanner();

        mSharedViewModel.circleToAddListener.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean change) {
                if (change) {
                    mCircleFragment.refresh();
                }
            }
        });

    }

    private void initBanner(List<BannerInfo> bannerInfos) {
        banner.setImageLoader(new GlideRadiusImageLoader());
        List<String> images = new ArrayList<>();
        for (BannerInfo bannerInfo : bannerInfos) {
            images.add(XUtils.getImagePath(bannerInfo.getImg1path()));
        }
        banner.setImages(images);
        banner.start();
    }


    private void initMouble() {
        rvMoudle.setLayoutManager(new GridLayoutManager(getContext(), 4));
        HomeMoudleAdapter moudleAdapter = new HomeMoudleAdapter(moudleList);
        moudleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (i==0){
                    intent = new Intent(getActivity(), rczpMainActivity.class);
                    startActivity(intent);
                }else {
                    showLongToast("维护中");
                }
            }
        });
        moudleAdapter.bindToRecyclerView(rvMoudle);
    }

    private void initViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        // 附近动态列表
        mCircleFragment = CircleListFragment.newInstance(0);
        // 附近的人
        mNearbyFragment = NearListFragment.newInstance(1);

        fragments.add(mCircleFragment);
        fragments.add(mNearbyFragment);
        viewPager.setAdapter(new CircleFragmentAdapter(getChildFragmentManager(), fragments));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.tv_circle_create, R.id.ll_mobile, R.id.ll_pick, R.id.iv_send_message, R.id.ll_center})
    public void onClikc(View view) {
        switch (view.getId()) {
            case R.id.tv_circle_create:
                Intent intent = new Intent(getActivity(), SendCircleActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_mobile:
                ((MainFragment) getParentFragment()).startBrotherFragment(WebViewFragment.newInstance("手机充值", "http://newcz.m.jd.com/"));
                break;
            case R.id.ll_pick:
                ((MainFragment) getParentFragment()).startBrotherFragment(WebViewFragment.newInstance("火车机票", "http://www.ctrip.com/"));
                break;
            case R.id.iv_send_message:
            case R.id.ll_center:
                showLongToast("维护中");
                break;

        }

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
