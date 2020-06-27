package com.tencent.qcloud.tim.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.helper.CustomAVCallUIController;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.PrivateConstants;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.vivo.push.PushClient;

import java.util.List;

// 即时通讯初始化
public class TimApplication {
    private static final String TAG = "tim";
    private static Application mApplication;
    private static Class  mUserDetail;
    private static TimApplication mInstance = new TimApplication();
    public static TimApplication getInstance(){
        return  mInstance;
    }

    public static final String getFilePath(){
        return mApplication.getFilesDir().getPath();
    }

    public static final void init(Application application,Class userDetail){
        mApplication = application;
        mUserDetail = userDetail;

        CustomAVCallUIController.getInstance().onCreate();
        IMEventListener imEventListener = new IMEventListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgs) {
                DemoLog.i(TAG, "onNewMessages");
                CustomAVCallUIController.getInstance().onNewMessage(msgs);
            }
        };
        TUIKit.addIMEventListener(imEventListener);
    }

    public static Context getApplication() {
        return mApplication;
    }

    public static void startUserDetailActivity(String userId){
        Intent intent = new Intent(mApplication, mUserDetail);
        intent.putExtra("userId", userId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mApplication.startActivity(intent);
    }
}
