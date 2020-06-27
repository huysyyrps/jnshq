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
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.lifecycle.Observer;

import com.sdjnshq.architecture.ui.simple.SimpleTextWatcher;
import com.sdjnshq.architecture.utils.CountDownTimerUtils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.login.LoginViewModel;
import com.sdjnshq.circle.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginCodeFragment extends BaseFragment {

    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_mobile)
    TextView tvSendMobile;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkBox;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    private LoginViewModel mLoginViewModel;
    private CountDownTimerUtils countDownTimerUtils;

    public static LoginCodeFragment newInstance() {

        Bundle args = new Bundle();

        LoginCodeFragment fragment = new LoginCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public String secretMobile(String mobile) {
        if (mobile.length() == 11) {
            mobile = mobile.replace(mobile.substring(3, 7), "****");
        }
        return mobile;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_login_code;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(LoginViewModel.class);
        mLoginViewModel.getMobiileLive().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mobile) {
                tvSendMobile.setText("短信验证码已发送至 +86 " + secretMobile(mobile));
            }
        });

        TextWatcher watcher = new SimpleTextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                canSubmit();
            }
        };
        etCode.addTextChangedListener(watcher);

        countDownTimerUtils = new CountDownTimerUtils(tvSend,60000,1000);
        countDownTimerUtils.start();
    }

    public void canSubmit(){
        btSubmit.setEnabled(!TextUtils.isEmpty(etCode.getText()));
    }

    @OnClick({R.id.iv_back, R.id.bt_submit})
    public void cilck(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.bt_submit:
                // 登录
                if (checkBox.isChecked()) {
                    mLoginViewModel.loginByCode(etCode.getText().toString());
                } else {
                    showLongToast("请同意并勾选用户服务协议与隐私政策");
                }
                break;
        }
    }

}
