package com.sdjnshq.circle.ui.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingScaleTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sdjnshq.architecture.utils.BarUtils;
import com.sdjnshq.architecture.utils.ColorUtils;
import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.ViewPagerFragmentAdapter;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.base.UserHeadImageLoader;
import com.sdjnshq.circle.ui.page.friend.FriendAddActivity;
import com.sdjnshq.circle.ui.page.user.UserCircleFragment;
import com.sdjnshq.circle.ui.page.user.UserInfoFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.chat.ChatActivity;
import com.tencent.qcloud.tim.demo.utils.Constants;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapseLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    SlidingScaleTabLayout tabLayout;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_dis)
    TextView tvDis;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.iv_say_hello)
    ImageView ivSayHello;
    @BindView(R.id.iv_firend_add)
    ImageView ivFriendAdd;
    @BindView(R.id.iv_send_m)
    ImageView ivBig;
    double present = 0;
    UserViewModel mUserViewModel;

    private UserCircleFragment mCircleFragment;
    String mUserId;

    public static void newUserDetailActivity(Activity activity, int userId) {

        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra("userId", String.valueOf(userId));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        initAppbarLayout();


        mUserId = getIntent().getStringExtra("userId");

        mUserViewModel = getActivityViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.getUserLive().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvName.setText("  " + user.getNickName());
                llSex.setBackgroundResource(user.isMale() ? R.drawable.bg_male_cricle : R.drawable.bg_female_cricle);
                ivSex.setImageResource(user.isMale() ? R.mipmap.ic_male_white : R.mipmap.ic_female_white);
                tvDis.setText(user.getDistance());
                tvAge.setText(String.valueOf(user.getAgeByBirth()));

                initBanner(user);
                // 是否是自己
                if (user.getId().equals(AppSP.getInstance().getUserId())) {
                    ivFriendAdd.setVisibility(View.GONE);
                    ivSayHello.setVisibility(View.GONE);
                }
            }
        });
        mUserViewModel.init(mUserId);
        mUserViewModel.visitAdd(mUserId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        // 获取好友信息
        ArrayList<String> list = new ArrayList<>();
        list.add(mUserId);
        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMFriend>>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(List<TIMFriend> timFriends) {
                if (timFriends != null && timFriends.size() > 0) {
                    for (TIMFriend friend : timFriends) {
                        if (TextUtils.equals(friend.getIdentifier(), mUserId)) {
                            if (friend.getIdentifier().equals(AppSP.getInstance().getUserId())) {
                                ivFriendAdd.setVisibility(View.GONE);
                                ivSayHello.setVisibility(View.GONE);
                            } else{
                                ivBig.setVisibility(View.VISIBLE);
                                ivFriendAdd.setVisibility(View.GONE);
                                ivSayHello.setVisibility(View.GONE);
                            }
                            break;
                        }
                    }
                }
            }
        });
        return super.onCreateView(name, context, attrs);
    }

    private void initBanner(User user) {
        banner.setImageLoader(new UserHeadImageLoader());
        banner.setImages(user.getBannerList());
        banner.start();

        //viewpager
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(UserInfoFragment.newInstance(0, user));
        mCircleFragment = UserCircleFragment.newInstance(1, user.getId());
        fragments.add(mCircleFragment);
        viewPager.setAdapter(new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);


        // 设置cooltoolbar折叠的最小高度
        titleBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                collapsingToolbarLayout.setMinimumHeight(titleBar.getHeight());
                titleBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void initAppbarLayout() {
        titleBar.findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                present = Math.abs(verticalOffset) / (Double.valueOf(appBarLayout.getHeight() / 2) - BarUtils.getActionBarHeight() - BarUtils.getStatusBarHeight());
                if (present > 1) {
                    present = 1;
                } else if (present < 0) {
                    present = 0;
                }
                setTitleBarBackDrawable(titleBar.findViewById(R.id.iv_back), present > 0.5 ? R.mipmap.ic_back : R.mipmap.ic_back_white);
                titleBar.setBackgroundColor(ColorUtils.setAlphaComponent(ColorUtils.getColor(R.color.white), (float) present));
            }
        });
    }

    private void setTitleBarBackDrawable(ImageView textView, int resId) {
        textView.setImageResource(resId);
    }

    @OnClick({R.id.iv_say_hello, R.id.iv_firend_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_say_hello:
                // 打招呼
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(TIMConversationType.C2C);
                chatInfo.setId(String.valueOf(mUserViewModel.getUser().getId()));
                String chatName = mUserViewModel.getUser().getNickName();
                chatInfo.setChatName(chatName);
                Intent intent = new Intent(CircleApplication.getInstance(), ChatActivity.class);
                intent.putExtra(Constants.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DemoApplication.instance().startActivity(intent);
                break;
            case R.id.iv_firend_add:
                FriendAddActivity.newIntent(this, mUserId);
                break;
        }
    }
}
