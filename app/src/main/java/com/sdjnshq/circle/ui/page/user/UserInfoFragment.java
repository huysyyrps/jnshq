package com.sdjnshq.circle.ui.page.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.ui.base.BaseFragment;

import butterknife.BindView;

// 用户资料页-信息
public class UserInfoFragment extends BaseFragment {
    User user;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    public static UserInfoFragment newInstance(int position ,User user) {
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        args.putInt("position",position);

        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_user_info;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            getView().setTag(arguments.getInt("position"));

            user = arguments.getParcelable("user");
            tvId.setText("ID: "+user.getRemark());
            tvHome.setText("家乡： "+user.getHomeTown());
            tvRegister.setText("注册日期： "+user.getAddTime());
            tvSign.setText("个人签名： "+user.getSign());
        }
    }

}
