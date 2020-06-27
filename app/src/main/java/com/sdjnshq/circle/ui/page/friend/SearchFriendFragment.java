package com.sdjnshq.circle.ui.page.friend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.friend.FriendViewModel;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.UserDetailActivity;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// 搜索添加好友
public class SearchFriendFragment extends BaseFragment {
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;

    FriendViewModel friendViewModel;
    ArrayList<ContactItemBean> contactItemBeans;

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    public static SearchFriendFragment newInstance(ArrayList<ContactItemBean> contactItemBeanList) {

        Bundle args = new Bundle();
        args.putSerializable("contact", contactItemBeanList);
        SearchFriendFragment fragment = new SearchFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_search_friend;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().getSerializable("contact") != null) {
            contactItemBeans = (ArrayList<ContactItemBean>) getArguments().getSerializable("contact");
        }

        friendViewModel = getFragmentViewModelProvider(this).get(FriendViewModel.class);
        friendViewModel.searchUserLive.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users != null && users.size() > 0) {
                    UserDetailActivity.newUserDetailActivity(getActivity(), Integer.parseInt(users.get(0).getId()));
                } else {
                    showLongToast("账号不存在");
                }
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

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvMobile.setText(s);
                llSearch.setVisibility(s.length() == 0 ? View.INVISIBLE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.ll_search)
    public void onViewClicked() {
        friendViewModel.searchUser(etSearch.getText().toString());
    }
}
