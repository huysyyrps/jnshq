package com.sdjnshq.circle.ui.page.user;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sdjnshq.architecture.utils.FileUtils;
import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.mine.AboutFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.DialogHelper;
import com.sdjnshq.circle.utils.MethodsCompat;
import com.sdjnshq.circle.utils.XUtils;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;

    public static SettingFragment newInstance() {
        
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calculateCacheSize();
    }

    @OnClick({R.id.rl_clean_cache, R.id.ll_login_out,R.id.ll_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_clean_cache:
                onClickCleanCache();
                break;
            case R.id.ll_login_out:
                loginOut();
                break;
            case R.id.ll_about:
                start(AboutFragment.newInstance());
                break;
        }
    }

    private void loginOut(){
        new TUIKitDialog(getActivity())
                .builder()
                .setCancelable(true)
                .setCancelOutside(true)
                .setTitle("您确定要退出登录么？")
                .setDialogWidth(0.75f)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TIMManager.getInstance().logout(new TIMCallBack() {
                            @Override
                            public void onError(int code, String desc) {
                                ToastUtil.toastLongMessage("logout fail: " + code + "=" + desc);
                                logout();
                            }

                            @Override
                            public void onSuccess() {
                                AppSP.getInstance().loginOut();
                                logout();
                            }

                            private void logout() {
                                BaseActivity.logout(CircleApplication.getInstance(), false);
                                TUIKit.unInit();
                                if (getActivity() != null) {
                                    getActivity().finish();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
        //
    }

    private void onClickCleanCache() {

        new TUIKitDialog(getActivity())
                .builder()
                .setCancelable(true)
                .setCancelOutside(true)
                .setTitle("是否清空缓存？")
                .setDialogWidth(0.75f)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        XUtils.clearAppCache(true);
                        tvCacheSize.setText("0KB");
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();

        DialogHelper.getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               
            }
        }).show();
    }
    /**
     * 计算缓存的大小
     */
    private void calculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtils.getDirSizeL(filesDir);
        fileSize += FileUtils.getDirSizeL(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (CircleApplication.getInstance().isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(getActivity());
            fileSize += FileUtils.getDirSizeL(externalCacheDir);
        }
        if (fileSize > 0)
            cacheSize = FileUtils.formatFileSize(fileSize);
        tvCacheSize.setText(cacheSize);
    }
}
