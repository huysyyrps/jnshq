package com.sdjnshq.circle.ui.page.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.KeyboardUtils;
import com.sdjnshq.circle.MainActivity;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.login.LoginViewModel;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.ui.base.BaseActivity;

import java.util.List;

public class LoginActivity extends BaseActivity {
    LoginViewModel mLoginViewModel;

    @Override
    protected boolean isNeedTimListener() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 是否完善资料
        boolean isData = getIntent().getBooleanExtra("data",false);
        if(isData){
            loadRootFragment(R.id.fl_container, ImproveDataFragment.newInstance());
        }else {
            loadRootFragment(R.id.fl_container, LoginPwdFragment.newInstance());
        }
        mLoginViewModel = getActivityViewModelProvider(this).get(LoginViewModel.class);
        mLoginViewModel.getPagePostLive().observe(this, new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if (pagePost.getCode() == PagePost.PUSH_ACTIVITY) {
                    KeyboardUtils.hideSoftInput(LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(pagePost.getCode() == LoginViewModel.USER_INFO){
                    replaceFragment(ImproveDataFragment.newInstance(),true);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
