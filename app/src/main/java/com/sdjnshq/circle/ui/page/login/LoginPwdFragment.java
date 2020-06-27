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

package com.sdjnshq.circle.ui.page.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.sdjnshq.architecture.ui.simple.SimpleTextWatcher;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.login.LoginViewModel;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.AgreementFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginPwdFragment extends BaseFragment {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkBox;

    private LoginViewModel mLoginViewModel;

    public static LoginPwdFragment newInstance() {
        Bundle args = new Bundle();

        LoginPwdFragment fragment = new LoginPwdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(LoginViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextWatcher watcher = new SimpleTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                canSubmit();
            }
        };
        etPwd.addTextChangedListener(watcher);
        etMobile.addTextChangedListener(watcher);

    }

    public void canSubmit(){
        btSubmit.setEnabled(!TextUtils.isEmpty(etMobile.getText())&&!TextUtils.isEmpty(etPwd.getText())&&!TextUtils.isEmpty(etPwd.getText()));
    }
    @Override
    public int resourceId() {
        return R.layout.fragment_login_pwd;
    }

    @OnClick({R.id.tv_forget,R.id.bt_submit,R.id.tv_register, R.id.tv_pwd_login,R.id.tv_user_privacy,R.id.tv_user_service})
    public void back(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                start(RegitserFragment.newInstance());
                break;
            case R.id.tv_pwd_login:
                start(LoginMobileFragment.newInstance());
                break;
            case R.id.tv_forget:
                start(ForgetPwdFragment.newInstance());
                break;
            case R.id.bt_submit:
                if(checkBox.isChecked()) {
                    mLoginViewModel.loginByPwd(etMobile.getText().toString(), etPwd.getText().toString());
                }else{
                    showLongToast("请同意并勾选用户服务协议与隐私政策");
                }
                break;
            case R.id.tv_user_service:
                start(AgreementFragment.newInstance(100));
                break;
            case R.id.tv_user_privacy:
                start(AgreementFragment.newInstance(101));
                break;
        }
    }
}
