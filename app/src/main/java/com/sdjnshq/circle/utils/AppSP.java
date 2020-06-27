package com.sdjnshq.circle.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sdjnshq.circle.data.bean.BasePrice;
import com.sdjnshq.circle.data.bean.LoginUser;
import com.sdjnshq.circle.data.bean.MessageCount;
import com.sdjnshq.circle.data.bean.User;
import com.tencent.qcloud.tim.demo.utils.Constants;

import java.util.List;

/**
 * 应用存储
 */
@SuppressWarnings("unused")
public final class AppSP extends SharedPreferenceUtil {

    private static AppSP mInstance;
    private static AppSP mIMInstance;


    public static void init(Context context, String name) {
        if (mInstance == null) {
            mInstance = new AppSP(context, name);
            mIMInstance = new AppSP(context, "userInfo");
        }
    }


    public static AppSP getInstance() {
        return mInstance;
    }

    public String getUserId() {
        return getString("userId", "");
    }

    public String getUserName() {
        return getString("userName", "");
    }

    public double getLat() {
        return Double.parseDouble(getString("lat", "0"));
    }

    public double getLon() {
        return Double.parseDouble(getString("lon", "0"));
    }

    public String getToken() {
        return getString("token", "");
    }

    public void setUserName(String name) {
        put("userName", name);
    }

    public void login(User user) {
        put("token", user.getToken());
        put("userId", String.valueOf(user.getId()));
    }
    public void messageCount(MessageCount messageCount) {
        put("sys", messageCount.getNewSysCount());
        put("vis", messageCount.getNewVisitCount());
    }


    public void login(LoginUser user) {
        put("token", user.getToken());
        put("userId", user.getId());
    }


    public void loginOut() {
        put("token", "");
        put("userId", "");
        put("userName", "");
    }

    public String getAoiName() {
        return getString("aoi", "");
    }

    public void loacation(double lat, double lon, String aoiName) {
        put("lat", String.valueOf(lat));
        put("lon", String.valueOf(lon));
        put("aoi", String.valueOf(aoiName));
    }

    public void setBasePrice(List<BasePrice> priceList) {
        put("r_back", priceList.get(0).getProValue());
        put("vip_price", priceList.get(1).getProValue());
        put("r2_back", priceList.get(2).getProValue());
        put("vip_back", priceList.get(3).getProValue());
        put("vip2_back", priceList.get(4).getProValue());
    }

    /**
     * 登录
     */
    public void loginIM() {
        mIMInstance.put("auto_login", true);
    }

    /**
     * im是否自动登录
     */
    public boolean getIMAutioLogin() {
        return mIMInstance.getBoolean(Constants.AUTO_LOGIN, false);
    }

    private AppSP(Context context, String name) {
        super(context, name);
    }

    /**
     * 是否已经登录
     */
    public boolean isLogin() {
        return !TextUtils.isEmpty(getString("token", null)) && AppSP.getInstance().getIMAutioLogin();
    }

    /**
     * 点击更新过的版本
     */
    void putUpdateVersion(int code) {
        put("update_code", code);
    }

    /**
     * 设置更新过的版本
     */
    public int getUpdateVersion() {
        return getInt("update_code", 0);
    }

    /**
     * 设置不弹出更新
     */
    public void putShowUpdate(boolean isShow) {
        put("update_show", isShow);
    }

    /**
     * 是否弹出更新
     * 或者是新版本重新更新 259200000
     */
    boolean isShowUpdate() {
        return getBoolean("update_show", true);
    }

    /**
     * 是否已经弹出更新
     *
     * @return 不弹出更新代表已经更新
     */
    public boolean hasShowUpdate() {
        return !getBoolean("update_show", true);
    }

    /**
     * 设备唯一标示
     *
     * @param id id
     */
    public void putDeviceUUID(String id) {
        put("device_uuid", id);
    }

    /**
     * 设备唯一标示
     */
    public String getDeviceUUID() {
        return getString("device_uuid", "");
    }


    /**
     * 第一次安装
     */
    public void putFirstInstall() {
        put("first_install", false);
    }

    /**
     * 第一次安装
     */
    public boolean isFirstInstall() {
        return getBoolean("first_install", true);
    }

    /**
     * 第一次使用
     */
    public void putFirstUsing() {
        put("first_using_v2", false);
    }

    /**
     * 第一次使用
     */
    public boolean isFirstUsing() {
        return getBoolean("first_using_v2", true);
    }


    /**
     * putLastNewsId
     *
     * @param id id
     */
    public void putLastNewsId(long id) {
        put("last_news_id", id);
    }

    /**
     * 获取最新的id
     *
     * @return return
     */
    public long getLastNewsId() {
        return getLong("last_news_id", 0);
    }


    /**
     * 返回新闻有多少
     */
    public long getTheNewsId() {
        return getLong("the_last_news_id", 0);
    }

    /**
     * 存储最新新闻数量
     */
    public void putTheNewsId(long count) {
        put("the_last_news_id", count);
    }


    /**
     * 关联剪切版
     *
     * @param isRelate isRelate
     */
    public void putRelateClip(boolean isRelate) {
        put("is_relate_clip", isRelate);
    }

    /**
     * 是否关联剪切版
     *
     * @return 是否关联剪切版
     */
    public boolean isRelateClip() {
        return getBoolean("is_relate_clip", true);
    }

    /**
     * 最后一次分享的url
     *
     * @param url 最后一次分享的url
     */
    public void putLastShareUrl(String url) {
        if (TextUtils.isEmpty(url))
            return;
        put("last_share_url", url);
    }

    /**
     * 最后一次分享的url
     *
     * @return 最后一次分享的url
     */
    public String getLastShareUrl() {
        return getString("last_share_url", "");
    }


    public boolean isFirstOpenUrl() {
        return getBoolean("is_first_open_url", true);
    }

    public void putFirstOpenUrl() {
        put("is_first_open_url", false);
    }
}
