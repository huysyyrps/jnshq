package com.sdjnshq.circle.utils.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.sdjnshq.circle.CircleApplication;

public class AppUtil {
    /**
     * 获取版本号
     *
     * @return
     */
    public static String getVersionName() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = CircleApplication.getInstance().getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    CircleApplication.getInstance().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
