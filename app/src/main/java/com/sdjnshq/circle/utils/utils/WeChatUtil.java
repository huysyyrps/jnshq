package com.sdjnshq.circle.utils.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sdjnshq.architecture.utils.Utils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.GetMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class WeChatUtil {
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx25f0d0a93d9b3002";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private static IWXAPI api;

    public static void regToWx(Activity activity) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(activity, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    public static void shareText(String text) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    public static void shareImage(String url, boolean isTimeline) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WXImageObject imgObj = new WXImageObject();
                    WXMediaMessage msg = new WXMediaMessage();
                    msg.mediaObject = imgObj;

                    Bitmap bmp = BitmapFactory.decodeStream(new URL(url).openStream());
                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 120, 176, true);
//                    bmp.recycle();
                    msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
                    imgObj.imageData = Utils.bmpToByteArray(bmp, true);

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("img");
                    req.message = msg;
                    req.scene = isTimeline ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                    api.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static String getSign(Map<String, String> data) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
//            if (k.equals(WXPayConstants.FIELD_SIGN)) {
//                continue;
//            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append("Hdhm2018wxe53748aecb3d4fb5qianzh");
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] array = new byte[0];
        try {
            array = md.digest(sb.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb2 = new StringBuilder();
        for (byte item : array) {
            sb2.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb2.toString().toUpperCase();
    }

}
