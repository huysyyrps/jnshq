package com.sdjnshq.circle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.sdjnshq.circle.bridge.state.MainActivityViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.thirdPush.OPPOPushImpl;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.page.MainFragment;
import com.sdjnshq.circle.ui.page.user.UserInfoEditFragment;
import com.sdjnshq.circle.utils.AMapUtil;
import com.sdjnshq.circle.utils.AppSP;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.sdjnshq.circle.utils.utils.WeChatUtil;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.demo.thirdpush.ThirdPushTokenMgr;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.PrivateConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity {
    private static String TAG = "MainActivity";
    private MainActivityViewModel mMainActivityViewModel;
    private boolean isListened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prepareThirdPushToken();

        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
        // 自动登录腾讯Im
        loginIM();
        WeChatUtil.regToWx(this);

        mMainActivityViewModel = getActivityViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityViewModel.initState();

        // 定位
        location();
        EventBus.getDefault().register(this);
    }

    private void loginIM() {
        // 获取userSig函数
        String userId = AppSP.getInstance().getUserId();
        if (!TextUtils.isEmpty(userId) && TextUtils.isEmpty(TIMManager.getInstance().getLoginUser())) {
            TIMManager.getInstance().autoLogin(userId, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                }

                @Override
                public void onSuccess() {
                }
            });
        }
    }

    private void location() {
        AMapUtil.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.e("123", aMapLocation.toString());
                if (aMapLocation.getLatitude() != 0 && aMapLocation.getLongitude() != 0) {
                    AppSP.getInstance().loacation(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getPoiName());
                    mMainActivityViewModel.updateLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getPoiName());
                    if (aMapLocation.getLongitude() != AppSP.getInstance().getLat()) {
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setPage("refresh");
                        EventBus.getDefault().post(messageEvent);
                    }
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isListened) {
            mSharedViewModel.timeToAddSlideListener.setValue(true);

            isListened = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AMapUtil.startLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        AMapUtil.stopLocation();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case UserInfoEditFragment.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    MessageEvent<List<LocalMedia>> listMessageEvent = new MessageEvent<>();
                    listMessageEvent.data = list;
                    listMessageEvent.page = "UserInfoEditFragment";
                    EventBus.getDefault().post(listMessageEvent);
                    break;
            }
        }
    }

    private void prepareThirdPushToken() {
        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();

        if (ThirdPushTokenMgr.USER_GOOGLE_FCM) {
            return;
        }
        if (IMFunc.isBrandHuawei()) {
            // 华为离线推送
            HMSAgent.connect(this, new ConnectHandler() {
                @Override
                public void onConnect(int rst) {
                    DemoLog.i(TAG, "huawei push HMS connect end:" + rst);
                }
            });
            getHuaWeiPushToken();
        }
        if (IMFunc.isBrandVivo()) {
            // vivo离线推送
//            PushClient.getInstance(getApplicationContext()).turnOnPush(new IPushActionListener() {
//                @Override
//                public void onStateChanged(int state) {
//                    if (state == 0) {
//                        String regId = PushClient.getInstance(getApplicationContext()).getRegId();
//                        DemoLog.i(TAG, "vivopush open vivo push success regId = " + regId);
//                        ThirdPushTokenMgr.getInstance().setThirdPushToken(regId);
//                        ThirdPushTokenMgr.getInstance().setPushTokenToTIM();
//                    } else {
//                        // 根据vivo推送文档说明，state = 101 表示该vivo机型或者版本不支持vivo推送，链接：https://dev.vivo.com.cn/documentCenter/doc/156
//                        DemoLog.i(TAG, "vivopush open vivo push fail state = " + state);
//                    }
//                }
//            });
        }
        if (com.heytap.mcssdk.PushManager.isSupportPush(this)) {
            // oppo离线推送
            OPPOPushImpl oppo = new OPPOPushImpl();
            oppo.createNotificationChannel(this);
            com.heytap.mcssdk.PushManager.getInstance().register(this, PrivateConstants.OPPO_PUSH_APPKEY, PrivateConstants.OPPO_PUSH_APPSECRET, oppo);
        }
    }

    private void getHuaWeiPushToken() {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                DemoLog.i(TAG, "huawei push get token result code: " + rtnCode);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
