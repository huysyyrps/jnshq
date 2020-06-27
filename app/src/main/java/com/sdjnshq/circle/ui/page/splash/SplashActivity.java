package com.sdjnshq.circle.ui.page.splash;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.MainActivity;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.http.APIManager;
import com.sdjnshq.circle.data.http.ExceptionHandle;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.page.login.LoginActivity;
import com.sdjnshq.circle.utils.AppSP;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.frameSplash)
    FrameLayout mFlameSplash;
    @BindView(R.id.fl_content)
    FrameLayout mFlameContent;

    private static final int REQ_PERMISSION_CODE = 0x100;
    // 广告
    private boolean isShowAd;

    @Override
    protected boolean isNeedTimListener() {
        return false;
    }

    //权限检查
    public static boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(TUIKit.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() != 0) {
                String[] permissionsArray = permissions.toArray(new String[1]);
                ActivityCompat.requestPermissions(activity,
                        permissionsArray,
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (AppSP.getInstance().isLogin()) {
            String mobile = AppSP.getInstance().getString("mobile", "");
            if (TextUtils.isEmpty(mobile)) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                //是否完善信息
                RetrofitUtil.execute(APIManager.getAPI().isFullInfo(mobile), new SObserver(false) {
                    @Override
                    public void onSuccess(Object o) {
                        redirectTo();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponseThrowable e) {
                        super.onError(e);
                        if(e.getStatus().equals("0")){
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            // 完善资料
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.putExtra("data", true);
                            startActivity(intent);
                        }
                    }
                });
            }


        } else {
            // 跳转
            doMerge();
        }
    }

    private void doMerge() {
        if (isShowAd)
            return;
        // Delay...
        loginIM();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (checkPermission(this)) {
            redirectTo();
        }
    }

    private void redirectTo() {
        Intent intent;
//        if (OSCSharedPreference.getInstance().isFirstInstall()) {
//            IntroduceActivity.show(this);
//        }
        if (AppSP.getInstance().isLogin()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 系统请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.toastLongMessage("未全部授权，部分功能可能无法使用！");
                } else {
                    redirectTo();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void loginIM() {
        if (!TextUtils.isEmpty(AppSP.getInstance().getUserId())) {
            String uid = AppSP.getInstance().getUserId();
            String userSig = GenerateTestUserSig.genTestUserSig(uid);
            TUIKit.login(uid, userSig, new IUIKitCallBack() {
                @Override
                public void onError(String module, final int code, final String desc) {
                    CircleApplication.getInstance().showToast("登录失败");
                }

                @Override
                public void onSuccess(Object data) {
                    AppSP.getInstance().loginIM();
                }
            });
        }
    }
}
