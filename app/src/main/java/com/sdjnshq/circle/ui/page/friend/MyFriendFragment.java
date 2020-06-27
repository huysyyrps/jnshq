package com.sdjnshq.circle.ui.page.friend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.adapter.MyFriendAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.utils.BackgroundTasks;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ThreadHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// 我的朋友
public class MyFriendFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "我的朋友";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MyFriendAdapter mAdapter;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    public static MyFriendFragment newInstance() {

        Bundle args = new Bundle();

        MyFriendFragment fragment = new MyFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_my_friend;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));

        // 我的好友
        mAdapter = new MyFriendAdapter();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
                ContactItemBean user = (ContactItemBean) adapter.getItem(i);
                XUtils.chatC2C(getActivity(), user.getId(), user.getNickname());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(divider);
        mAdapter.bindToRecyclerView(recyclerView);
        // 添加header
        View newFriend = LayoutInflater.from(getContext()).inflate(R.layout.lay_my_friend_header, null, false);
        newFriend.findViewById(R.id.ll_add_friend).setOnClickListener(this);
        newFriend.findViewById(R.id.ll_new_friend).setOnClickListener(this);
        mAdapter.addHeaderView(newFriend);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        loadFriendListDataAsync();
    }

    private void loadFriendListDataAsync() {
        TUIKitLog.i(TAG, "loadFriendListDataAsync");
        ThreadHelper.INST.execute(new Runnable() {
            @Override
            public void run() {
                // 压测时数据量比较大，query耗时比较久，所以这里使用新线程来处理
                List<TIMFriend> timFriends = TIMFriendshipManager.getInstance().queryFriendList();
                if (timFriends == null) {
                    timFriends = new ArrayList<>();
                }
                TUIKitLog.i(TAG, "queryFriendList:" + timFriends.size());
                fillFriendListData(timFriends);
            }
        });
    }

    private void fillFriendListData(final List<TIMFriend> timFriends) {
        // 外部调用是在其他线程里面，但是更新数据同时会刷新UI，所以需要放在主线程做。
        BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (timFriends.size() == 0) {
                    TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMFriend>>() {
                        @Override
                        public void onError(int code, String desc) {
                            TUIKitLog.e(TAG, "getFriendList err code = " + code);
                        }

                        @Override
                        public void onSuccess(List<TIMFriend> timFriends) {
                            if (timFriends == null) {
                                timFriends = new ArrayList<>();
                            }
                            TUIKitLog.i(TAG, "getFriendList success result = " + timFriends.size());
                            assembleFriendListData(timFriends);
                        }
                    });
                } else {
                    assembleFriendListData(timFriends);
                }
            }
        });
    }

    private void assembleFriendListData(final List<TIMFriend> timFriends) {
        List<ContactItemBean> contactItemBeanList = new ArrayList<>();
        for (TIMFriend timFriend : timFriends) {
            ContactItemBean info = new ContactItemBean();
            info.covertTIMFriend(timFriend);
            info.setEnable(false);
            contactItemBeanList.add(info);
        }

        mAdapter.setNewData(contactItemBeanList);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_friend:
                ArrayList<ContactItemBean> list = new ArrayList<>();
                list.addAll(mAdapter.getData());
                start(SearchFriendFragment.newInstance(list));
                break;
            case R.id.ll_new_friend:
                start(NewFriendFragment.newInstance());
                break;
        }
    }
}
