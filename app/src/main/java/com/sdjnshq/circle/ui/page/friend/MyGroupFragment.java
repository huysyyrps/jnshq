package com.sdjnshq.circle.ui.page.friend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.adapter.MyGroupAdapter;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupBaseInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// 我的群组
public class MyGroupFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MyGroupAdapter myGroupAdapter;
    @BindView(R.id.et_search)
    EditText etSearch;
    List<ContactItemBean> allGroup = new ArrayList<>();


    public static MyGroupFragment newInstance() {
        Bundle args = new Bundle();

        MyGroupFragment fragment = new MyGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_my_group;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myGroupAdapter = new MyGroupAdapter();
        myGroupAdapter.bindToRecyclerView(recyclerView);
        myGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int i) {
                ContactItemBean contactItemBean = (ContactItemBean) adapter.getItem(i);
                XUtils.chatGroup(getActivity(), contactItemBean);
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (etSearch.getText().toString().length() > 0) {
                        List<ContactItemBean> newGroup = new ArrayList<>();
                        for (ContactItemBean itemBean : allGroup) {
                            if (itemBean.getNickname()!=null&&itemBean.getNickname().contains(etSearch.getText().toString())) {
                                newGroup.add(itemBean);
                            }else if(itemBean.getRemark()!=null &&itemBean.getRemark().contains(etSearch.getText().toString())){
                                newGroup.add(itemBean);
                            }
                        }
                        myGroupAdapter.setNewData(newGroup);
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
                    myGroupAdapter.setNewData(allGroup);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadGroupListData() {
        TIMGroupManager.getInstance().getGroupList(new TIMValueCallBack<List<TIMGroupBaseInfo>>() {

            @Override
            public void onError(int i, String s) {
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(List<TIMGroupBaseInfo> infos) {
                if (infos.size() == 0) {
                }
                List<ContactItemBean> beanList = new ArrayList<>();

                for (TIMGroupBaseInfo info : infos) {
                    ContactItemBean bean = new ContactItemBean();
                    beanList.add(bean.covertTIMGroupBaseInfo(info));
                }
                allGroup.clear();
                allGroup.addAll(beanList);
                myGroupAdapter.setNewData(allGroup);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadGroupListData();
    }

    @OnClick({R.id.tv_group_create, R.id.iv_group_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_group_create:
            case R.id.iv_group_create:
                start(SelectUserFragment.newInstance());
                break;
        }
    }
}
