package com.sdjnshq.circle.ui.page.friend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.bridge.friend.FriendViewModel;
import com.sdjnshq.circle.data.bean.AddFriend;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.adapter.NewFriendAdapter;
import com.sdjnshq.circle.ui.adapter.SearchUserAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;
import com.tencent.imsdk.friendship.TIMFriendPendencyItem;
import com.tencent.imsdk.friendship.TIMFriendPendencyRequest;
import com.tencent.imsdk.friendship.TIMFriendPendencyResponse;
import com.tencent.imsdk.friendship.TIMFriendResponse;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMPendencyType;
import com.tencent.qcloud.tim.demo.contact.NewFriendActivity;
import com.tencent.qcloud.tim.demo.contact.NewFriendListAdapter;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.utils.BackgroundTasks;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.tencent.qcloud.tim.uikit.utils.ThreadHelper;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewFriendFragment extends BaseFragment {
    private static final String TAG = "NEW_FRIEND";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    @BindView(R.id.rl_empty)
    RelativeLayout rlEmpty;
    @BindView(R.id.et_search)
    EditText etSearch;

    SearchUserAdapter searchAdapter;
    NewFriendAdapter mAdapter;
    FriendViewModel friendViewModel;
    // 好友列表
    List<ContactItemBean> contactItemBeanList = new ArrayList<>();

    public static NewFriendFragment newInstance() {

        Bundle args = new Bundle();

        NewFriendFragment fragment = new NewFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_new_friend;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new NewFriendAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() == R.id.agree) {
                    TIMFriendPendencyItem data = (TIMFriendPendencyItem) baseQuickAdapter.getItem(i);
                    TIMFriendResponse response = new TIMFriendResponse();
                    response.setIdentifier(data.getIdentifier());
                    response.setResponseType(TIMFriendResponse.TIM_FRIEND_RESPONSE_AGREE_AND_ADD);
                    TIMFriendshipManager.getInstance().doResponse(response, new TIMValueCallBack<TIMFriendResult>() {
                        @Override
                        public void onError(int i, String s) {
                            DemoLog.e(TAG, "deleteFriends err code = " + i + ", desc = " + s);
                            ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
                        }

                        @Override
                        public void onSuccess(TIMFriendResult timUserProfiles) {
                            DemoLog.i(TAG, "deleteFriends success");
                            initPendency();
                        }
                    });
                }

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);

        mAdapter.bindToRecyclerView(recyclerView);

        initSearch();
    }

    private void initSearch() {
        // 搜索好友
        searchAdapter = new SearchUserAdapter();
        searchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() == R.id.bt_add) {
                    FriendAddActivity.newIntent(getActivity(), ((User) baseQuickAdapter.getItem(i)).getId());
                }
            }
        });
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.custom_divider));
        rvSearch.addItemDecoration(divider);
        searchAdapter.bindToRecyclerView(rvSearch);
        friendViewModel = getFragmentViewModelProvider(this).get(FriendViewModel.class);
        friendViewModel.searchUserLive.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for (User user : users) {
                    if (contactItemBeanList != null && contactItemBeanList.size() > 0) {
                        for (int i = 0; i < contactItemBeanList.size(); i++) {
                            if (contactItemBeanList.get(i).getId().equals(user.getId())) {
                                user.setIsFriend(1);
                                break;
                            }
                        }
                    }
                }
                searchAdapter.setNewData(users);
                if (users != null && users.size() > 0) {
                    recyclerView.setVisibility(View.GONE);
                    rvSearch.setVisibility(View.VISIBLE);
                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    rvSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    friendViewModel.searchUser(etSearch.getText().toString());
                }
                return false;
            }
        });
    }

    private void initPendency() {
        final TIMFriendPendencyRequest timFriendPendencyRequest = new TIMFriendPendencyRequest();
        timFriendPendencyRequest.setTimPendencyGetType(TIMPendencyType.TIM_PENDENCY_COME_IN);
        timFriendPendencyRequest.setSeq(0);
        timFriendPendencyRequest.setTimestamp(0);
        timFriendPendencyRequest.setNumPerPage(10);
        TIMFriendshipManager.getInstance().getPendencyList(timFriendPendencyRequest, new TIMValueCallBack<TIMFriendPendencyResponse>() {
            @Override
            public void onError(int i, String s) {
                DemoLog.e(TAG, "getPendencyList err code = " + i + ", desc = " + s);
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(TIMFriendPendencyResponse timFriendPendencyResponse) {
                DemoLog.i(TAG, "getPendencyList success result = " + timFriendPendencyResponse.toString());
                if (timFriendPendencyResponse.getItems() != null) {
                    if (timFriendPendencyResponse.getItems().size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        rlEmpty.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                rlEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                mAdapter.setNewData(timFriendPendencyResponse.getItems());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initPendency();
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
        assembleFriendListData(timFriends);
    }

    private void assembleFriendListData(final List<TIMFriend> timFriends) {
        contactItemBeanList.clear();
        for (TIMFriend timFriend : timFriends) {
            ContactItemBean info = new ContactItemBean();
            info.covertTIMFriend(timFriend);
            contactItemBeanList.add(info);
        }
    }

}
