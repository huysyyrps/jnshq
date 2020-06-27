package com.sdjnshq.circle.thirdPush;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.huawei.android.hms.agent.HMSAgent;
import com.sdjnshq.circle.CircleApplication;
import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.PrivateConstants;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class Push {
    public static void init(CircleApplication app){
        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(app.getApplicationContext())) {
            /**
             * TUIKit的初始化函数
             *
             * @param context  应用的上下文，一般为对应应用的ApplicationContext
             * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
             * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
             */
            TUIKit.init(CircleApplication.getInstance(), GenerateTestUserSig.SDKAPPID, new ConfigHelper().getConfigs());

            if (IMFunc.isBrandXiaoMi()) {
                // 小米离线推送
                MiPushClient.registerPush(CircleApplication.getInstance(), PrivateConstants.XM_PUSH_APPID, PrivateConstants.XM_PUSH_APPKEY);
            }
            else if (IMFunc.isBrandHuawei()) {
                // 华为离线推送
                HMSAgent.init(app);
            }
//            else if (MzSystemUtils.isBrandMeizu(this)) {
//                // 魅族离线推送
//                PushManager.register(this, PrivateConstants.MZ_PUSH_APPID, PrivateConstants.MZ_PUSH_APPKEY);
//            } else if (IMFunc.isBrandVivo()) {
//                // vivo离线推送
//                PushClient.getInstance(getApplicationContext()).initialize();
//            }
            else if (com.heytap.mcssdk.PushManager.isSupportPush(app)) {
                // oppo离线推送，因为需要登录成功后向我们后台设置token，所以注册放在MainActivity中做
            }

            app.registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
        }
    }

    static class StatisticActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
        public static String TAG = "cirle_Push";
        private int foregroundActivities = 0;
        private boolean isChangingConfiguration;
        private IMEventListener mIMEventListener = new IMEventListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgs) {
//                if (CustomMessage.convert2VideoCallData(msgs) != null) {
//                    // 会弹出接电话的对话框，不再需要通知
//                    return;
//                }
                for (TIMMessage msg : msgs) {
                    Log.e("123",msg.toString());

                    // 小米手机需要在设置里面把demo的"后台弹出权限"打开才能点击Notification跳转。TIMOfflinePushNotification后续不再维护，如有需要，建议应用自己调用系统api弹通知栏消息。
                    TIMOfflinePushNotification notification = new TIMOfflinePushNotification(CircleApplication.getInstance(), msg);
                    notification.doNotify(CircleApplication.getInstance(), com.tencent.qcloud.tim.demo.R.drawable.default_user_icon);
                }
            }
        };

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            DemoLog.i(TAG, "onActivityCreated bundle: " + bundle);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用切到前台
                DemoLog.i(TAG, "application enter foreground");
                TIMManager.getInstance().doForeground(new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        DemoLog.e(TAG, "doForeground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        DemoLog.i(TAG, "doForeground success");
                    }
                });
                TUIKit.removeIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                // 应用切到后台
                DemoLog.i(TAG, "application enter background");
                int unReadCount = 0;
                List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
                for (TIMConversation timConversation : conversationList) {
                    unReadCount += timConversation.getUnreadMessageNum();
                }
                TIMBackgroundParam param = new TIMBackgroundParam();
                param.setC2cUnread(unReadCount);
                TIMManager.getInstance().doBackground(param, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        DemoLog.e(TAG, "doBackground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        DemoLog.i(TAG, "doBackground success");
                    }
                });
                // 应用退到后台，消息转化为系统通知
                TUIKit.addIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
