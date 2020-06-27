package com.sdjnshq.circle.ui.page.friend;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.friend.FriendViewModel;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.SelectFriendAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.chat.ChatActivity;
import com.tencent.qcloud.tim.demo.utils.Constants;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.modules.group.info.GroupInfo;
import com.tencent.qcloud.tim.uikit.modules.group.member.GroupMemberInfo;
import com.tencent.qcloud.tim.uikit.utils.BackgroundTasks;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ThreadHelper;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

// 群 - 选择成员
public class SelectUserFragment extends BaseFragment {
    private String TAG = "选择群成员";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;

    SelectFriendAdapter mAdapter;

    private  List<ContactItemBean> allFriend =  new ArrayList<ContactItemBean>();
    // 建群相关
    private ArrayList<GroupMemberInfo> mMembers = new ArrayList<>();
    private ArrayList<String> mGroupTypeValue = new ArrayList<>();
    private boolean mCreating;

    public static SelectUserFragment newInstance() {
        Bundle args = new Bundle();
        SelectUserFragment fragment = new SelectUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.lay_group_create;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] array = getResources().getStringArray(com.tencent.qcloud.tim.demo.R.array.group_type);
        mGroupTypeValue.addAll(Arrays.asList(array));

        // 我的好友
        mAdapter = new SelectFriendAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);

        mAdapter.bindToRecyclerView(recyclerView);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (etSearch.getText().toString().length() > 0) {
                        List<ContactItemBean> newGroup = new ArrayList<>();
                        for (ContactItemBean itemBean : allFriend) {
                            if (itemBean.getNickname()!=null&&itemBean.getNickname().contains(etSearch.getText().toString())) {
                                newGroup.add(itemBean);
                            }else if(itemBean.getRemark()!=null &&itemBean.getRemark().contains(etSearch.getText().toString())){
                                newGroup.add(itemBean);
                            }
                        }
                        mAdapter.setNewData(newGroup);
                    }
                }
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mAdapter.setNewData(allFriend);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {
        createGroupChat();
    }

    private void createGroupChat() {
        if (mCreating) {
            return;
        }
        mMembers.clear();
        // 群聊 添加自己
        GroupMemberInfo memberInfo = new GroupMemberInfo();
        memberInfo.setAccount(AppSP.getInstance().getUserName());
        mMembers.add(0, memberInfo);

        for (ContactItemBean user : mAdapter.getData()) {
            if(user.isEnable()){
                GroupMemberInfo groupMemberInfo = new GroupMemberInfo();
                groupMemberInfo.setAccount(user.getNickname());
                mMembers.add(groupMemberInfo);
            }
        }


        if (mMembers.size() == 1) {
            ToastUtil.toastLongMessage("请选择群成员");
            return;
        }
        final GroupInfo groupInfo = new GroupInfo();
        String groupName = TIMManager.getInstance().getLoginUser();
        for (int i = 1; i < mMembers.size(); i++) {
            groupName = groupName + "、" + mMembers.get(i).getAccount();
        }
        if (groupName.length() > 20) {
            groupName = groupName.substring(0, 17) + "...";
        }
        groupInfo.setChatName(groupName);
        groupInfo.setGroupName(groupName);
        groupInfo.setMemberDetails(mMembers);
        groupInfo.setGroupType(TUIKitConstants.GroupType.TYPE_PRIVATE);
//        groupInfo.setJoinType(2);

        mCreating = true;
        GroupChatManagerKit.createGroupChat(groupInfo, new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(TIMConversationType.Group);
                chatInfo.setId(data.toString());
                chatInfo.setChatName(groupInfo.getGroupName());
                Intent intent = new Intent(DemoApplication.instance(), ChatActivity.class);
                intent.putExtra(Constants.CHAT_INFO, chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                pop();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mCreating = false;
                ToastUtil.toastLongMessage("createGroupChat fail:" + errCode + "=" + errMsg);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
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
            info.setEnable(false);
            info.covertTIMFriend(timFriend);
            contactItemBeanList.add(info);
        }
        allFriend.clear();
        allFriend.addAll(contactItemBeanList);
        mAdapter.setNewData(allFriend);
    }

}
