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
import androidx.lifecycle.Observer;

import com.sdjnshq.architecture.ui.simple.SimpleTextWatcher;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.login.LoginViewModel;
import com.sdjnshq.circle.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginMobileFragment extends BaseFragment {

    private LoginViewModel mLoginViewModel;

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    public static LoginMobileFragment newInstance() {
        Bundle args = new Bundle();
        
        LoginMobileFragment fragment = new LoginMobileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoginViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(LoginViewModel.class);
        mLoginViewModel.getCodeLive().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                if (value.equals("register")) {
                    start(RegitserFragment.newInstance());
                } else if (value.equals("loginCode")) {
                    start(LoginCodeFragment.newInstance());
                }
            }
        });
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_login_mobile;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextWatcher watcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                canSubmit();
            }
        };
        etMobile.addTextChangedListener(watcher);
    }

    public void canSubmit() {
        btSubmit.setEnabled(!TextUtils.isEmpty(etMobile.getText()));
    }

    @OnClick({R.id.bt_submit,R.id.iv_back})
    public void cilck(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.bt_submit:
                if (etMobile.getText().toString().length() != 11) {
                    showLongToast("请输入正确的手机号");
                } else {
                    mLoginViewModel.getMobiileLive().setValue(etMobile.getText().toString());
                    mLoginViewModel.sendLoginCode(etMobile.getText().toString());
                }
                break;
        }
    }

}
