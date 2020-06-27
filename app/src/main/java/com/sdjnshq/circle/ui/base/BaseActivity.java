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

package com.sdjnshq.circle.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.sdjnshq.architecture.data.manager.NetworkStateManager;
import com.sdjnshq.architecture.utils.AdaptScreenUtils;
import com.sdjnshq.architecture.utils.BarUtils;
import com.sdjnshq.architecture.utils.ScreenUtils;
import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.bridge.callback.SharedViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.ui.page.login.LoginActivity;
import com.sdjnshq.circle.utils.AppSP;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.utils.Constants;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.yokeyword.fragmentation.SupportActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 加入tim异地登录监听
 */
public class BaseActivity extends SupportActivity {
    private static final String TAG = com.tencent.qcloud.tim.demo.BaseActivity.class.getSimpleName();

    protected SharedViewModel mSharedViewModel;
    private ViewModelProvider mActivityProvider;

    // 监听做成静态可以让每个子类重写时都注册相同的一份。
    private static IMEventListener mIMEventListener = new IMEventListener() {
        @Override
        public void onForceOffline() {
            ToastUtil.toastLongMessage("您的帐号已在其它终端登录");
            EventBus.getDefault().post(new MessageEvent("loginOut"));
            logout(DemoApplication.instance(), false);
        }
    };

    protected boolean isNeedTimListener(){
        return true;
    }

    public static void logout(Context context, boolean autoLogin) {
        DemoLog.i(TAG, "logout");
        AppSP.getInstance().loginOut();
        SharedPreferences shareInfo = context.getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shareInfo.edit();
        editor.putBoolean(Constants.AUTO_LOGIN, autoLogin);
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.LOGOUT, true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
        BarUtils.setStatusBarLightMode(this, true);

        mSharedViewModel = getAppViewModelProvider().get(SharedViewModel.class);

        getLifecycle().addObserver(NetworkStateManager.getInstance());

        // 监听tim的其他设备登录
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(com.tencent.qcloud.tim.demo.R.color.status_bar_color));
            getWindow().setNavigationBarColor(getResources().getColor(com.tencent.qcloud.tim.demo.R.color.navigation_bar_color));
            int vis = getWindow().getDecorView().getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            getWindow().getDecorView().setSystemUiVisibility(vis);
        }
        if(isNeedTimListener()) {
            TUIKit.addIMEventListener(mIMEventListener);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isNeedTimListener()) {
            boolean login = AppSP.getInstance().getIMAutioLogin();
            if (!login) {
//                //登录
//                // 获取userSig函数
//                String userSig = GenerateTestUserSig.genTestUserSig(String.valueOf(AppSP.getInstance().getUserId()));
//                TUIKit.login(String.valueOf(AppSP.getInstance().getUserId()), userSig, new IUIKitCallBack() {
//                    @Override
//                    public void onError(String module, final int code, final String desc) {
//                        App.getInstance().showToast("登录失败");
//                    }
//
//                    @Override
//                    public void onSuccess(Object data) {
//                        AppSP.getInstance().loginIM();
//                    }
//                });

                logout(DemoApplication.instance(), false);
            }
        }
    }

    public boolean isDebug() {
        return getApplicationContext().getApplicationInfo() != null &&
                (getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    public void showLongToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected ViewModelProvider getAppViewModelProvider() {
        return ((CircleApplication) getApplicationContext()).getAppViewModelProvider(this);
    }

    protected ViewModelProvider getActivityViewModelProvider(AppCompatActivity activity) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(activity);
        }
        return mActivityProvider;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getPage().equals("loginOut")) {
            finish();
        }
    }
}
