package com.sdjnshq.circle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;
import com.sdjnshq.architecture.utils.Utils;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.thirdPush.Push;
import com.sdjnshq.circle.ui.base.BaseApplication;
import com.sdjnshq.circle.ui.page.UserDetailActivity;
import com.sdjnshq.circle.utils.AMapUtil;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.DataCleanManager;
import com.sdjnshq.circle.utils.MethodsCompat;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.sdjnshq.circle.utils.PrivateConstants;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.demo.TimApplication;

import me.yokeyword.fragmentation.Fragmentation;

public class CircleApplication extends BaseApplication implements ViewModelStoreOwner {

    private static CircleApplication mInstance;

    public static CircleApplication getInstance() {
        return mInstance;
    }

    // 刷新控件
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private ViewModelStore mAppViewModelStore;
    private ViewModelProvider.Factory mFactory;
    private UserViewModel mUserViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);

        // bugly上报
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(TIMManager.getInstance().getVersion());
        CrashReport.initCrashReport(getApplicationContext(), PrivateConstants.BUGLY_APPID, true, strategy);
        // 即时通讯 - 推送 初始化
        TimApplication.init(this, UserDetailActivity.class);
        Push.init(this);

        // 网络请求
        mAppViewModelStore = new ViewModelStore();
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Utils.init(this);
        AppSP.init(this,"circle");
        // 定位
        AMapUtil.init();
        // fragment 管理
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();

    }


    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    public ViewModelProvider getAppViewModelProvider(Activity activity) {
        return new ViewModelProvider((CircleApplication) activity.getApplicationContext(),
                ((CircleApplication) activity.getApplicationContext()).getAppFactory(activity));
    }

    private ViewModelProvider.Factory getAppFactory(Activity activity) {
        Application application = checkApplication(activity);
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application);
        }
        return mFactory;
    }

    public UserViewModel getUserViewModel() {
        return mUserViewModel;
    }


    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }

    private Activity checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
        }
        return activity;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
    }

}
