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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class RegitserFragment extends BaseFragment {

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
    @BindView(R.id.checkbox)
    AppCompatCheckBox checkBox;

    private CountDownTimerUtils countDownTimerUtils;
    private LoginViewModel mLoginViewModel;


    public static RegitserFragment newInstance() {
        Bundle args = new Bundle();

        RegitserFragment fragment = new RegitserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int resourceId() {
        return R.layout.fragment_register;
    }

    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(LoginViewModel.class);

        countDownTimerUtils = new CountDownTimerUtils(tvSend, 60000, 1000);
        TextWatcher watcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                canSubmit();
            }
        };
        etPwd.addTextChangedListener(watcher);
        etMobile.addTextChangedListener(watcher);
        etCode.addTextChangedListener(watcher);

        mLoginViewModel.getRegisterive().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("123", "注册" + s);
            }
        });

        mLoginViewModel.getPagePostLive().observe(getViewLifecycleOwner(), new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if (pagePost != null) {
                    if (pagePost.getCode() == PagePost.ERROR_TOAST) {
                        showLongToast(pagePost.getValue());
                    } else if (pagePost.getCode() == PagePost.PAGE_CODE_SUCESS) {
                        countDownTimerUtils.start();
                    }
                }
            }
        });
        mLoginViewModel.getCodeLive().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("registerCode")) {
                    countDownTimerUtils.start();
                    mLoginViewModel.getCodeLive().postValue("");
                }
            }
        });
    }

    public void canSubmit() {
        btSubmit.setEnabled(!TextUtils.isEmpty(etMobile.getText()) && !TextUtils.isEmpty(etCode.getText()) && !TextUtils.isEmpty(etPwd.getText()));
    }

    @OnClick(R.id.iv_back)
    public void back(View view) {
        pop();
    }

    @OnClick(R.id.tv_send)
    public void sendCode() {
        mLoginViewModel.sendCode(etMobile.getText().toString());
    }

    @OnClick(R.id.bt_submit)
    public void register() {
        if (checkBox.isChecked()) {
            if (etPwd.getText().toString().length() > 20 || etPwd.getText().toString().length() < 8) {
                showLongToast("请输入适合的密码");
            } else {
                mLoginViewModel.register(etMobile.getText().toString(), etPwd.getText().toString(), etCode.getText().toString(), "");
            }
        } else {
            showLongToast("请同意并勾选用户服务协议与隐私政策");
        }
    }
}
