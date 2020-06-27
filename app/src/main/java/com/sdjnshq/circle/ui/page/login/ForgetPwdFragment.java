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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.sdjnshq.architecture.ui.simple.SimpleTextWatcher;
import com.sdjnshq.architecture.utils.CountDownTimerUtils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.login.LoginViewModel;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class ForgetPwdFragment extends BaseFragment {

    private LoginViewModel mLoginViewModel;

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    private CountDownTimerUtils countDownTimerUtils;

    public static ForgetPwdFragment newInstance() {
        Bundle args = new Bundle();
        
        ForgetPwdFragment fragment = new ForgetPwdFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(LoginViewModel.class);
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_forget_pwd;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countDownTimerUtils = new CountDownTimerUtils(tvSend,60000,1000);
        TextWatcher watcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                canSubmit();
            }
        };
        etPwd.addTextChangedListener(watcher);
        etMobile.addTextChangedListener(watcher);
        etCode.addTextChangedListener(watcher);


        mLoginViewModel.getPagePostLive().observe(getViewLifecycleOwner(), new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if (pagePost != null) {
                    if (pagePost.getCode() == PagePost.REFOUND_MOBILE_SUC) {
                        showLongToast("找回成功，请重新登录");
                        pop();
                    }
                }
            }
        });
    }


    public void canSubmit() {
        btSubmit.setEnabled(!TextUtils.isEmpty(etMobile.getText()) && !TextUtils.isEmpty(etCode.getText()) && !TextUtils.isEmpty(etPwd.getText()));
    }

    @OnClick(R.id.tv_send)
    public void sendCode() {
        mLoginViewModel.sendCodeFound(etMobile.getText().toString());
        countDownTimerUtils.start();
    }

    @OnClick(R.id.bt_submit)
    public void register() {

    }

    @OnClick({R.id.iv_back, R.id.bt_submit})
    public void cilck(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.bt_submit:
                if(etPwd.getText().toString().length()>20 || etPwd.getText().toString().length()<8){
                    showLongToast("请输入适合的密码");
                }else {
                    mLoginViewModel.reFoundPwd(etMobile.getText().toString(), etPwd.getText().toString(), etCode.getText().toString());
                }
                break;
        }
    }

}
