/*
 * Copyright 2018-2019 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sdjnshq.circle.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.base.TabSelectedEvent;
import com.sdjnshq.circle.ui.page.money.MoneyFragment;
import com.sdjnshq.circle.ui.view.BottomBar;
import com.sdjnshq.circle.ui.view.BottomBarTab;
import com.sdjnshq.circle.utils.AppSP;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Create by KunMinX at 19/10/29
 */
public class MainFragment extends BaseFragment implements ConversationManagerKit.MessageUnreadWatcher {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    private SupportFragment[] mFragments = new SupportFragment[4];
    private BottomBar mBottomBar;


    @Override
    public int resourceId() {
        return R.layout.fragment_main;
    }


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(getView());
        // 未读消息共计
        ConversationManagerKit.getInstance().addUnreadWatcher(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = MoneyFragment.newInstance();
            mFragments[THIRD] = MessageFragment.newInstance();
            mFragments[FOURTH] = MineFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(MoneyFragment.class);
            mFragments[THIRD] = findChildFragment(MessageFragment.class);
            mFragments[FOURTH] = findChildFragment(MineFragment.class);
        }
    }


    private void initView(View view) {
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.drawable.icon_home_pager_not_selected, "首页"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.icon_money_pager_not_selected, "分享赚"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.icon_message_pager_not_selected, "消息"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.icon_mine_pager_not_selected, "我的"));

        // 模拟未读消息
//

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public void updateUnread(int count) {
        AppSP.getInstance().put("un_read",count);
        setMessageValue();
    }

    public void setMessageValue() {
        int newFriend = AppSP.getInstance().getInt("new_friend", 0);
        int unRead = AppSP.getInstance().getInt("un_read", 0);
        int sysCount = Integer.parseInt(AppSP.getInstance().getString("sys", "0"));
        int visCount = Integer.parseInt(AppSP.getInstance().getString("vis", "0"));
        mBottomBar.getItem(THIRD).setUnreadCount(newFriend + sysCount + visCount+unRead);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getPage().equals("visitor")){
            AppSP.getInstance().put("vis", "0");
        }else if ( messageEvent.getPage().equals("system")) {
            AppSP.getInstance().put("sys", "0");
        }
    }
}
