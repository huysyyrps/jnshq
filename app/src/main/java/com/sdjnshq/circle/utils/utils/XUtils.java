package com.sdjnshq.circle.utils.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.R;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.demo.chat.ChatActivity;
import com.tencent.qcloud.tim.demo.utils.Constants;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;

public class XUtils {
    public static final String imgHttp = "http://www.sdjnshq.com/";

    public static String getImagePath(String path) {
        return imgHttp + path;
    }


    public static void loadHear(Fragment fragment, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(fragment).load(url)
                    .placeholder(R.mipmap.ic_default_head)
                    .error(R.mipmap.ic_default_head).into(imageView);
        }else{
            imageView.setImageResource(R.mipmap.ic_default_head);
        }
    }

    public static void loadHear(Activity activity, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(activity).load(url)
                    .placeholder(R.mipmap.ic_default_head)
                    .error(R.mipmap.ic_default_head).into(imageView);
        }else{
            imageView.setImageResource(R.mipmap.ic_default_head);
        }

    }

    public static void loadHear(View view, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view).load(url)
                    .placeholder(R.mipmap.ic_default_head)
                    .error(R.mipmap.ic_default_head).into(imageView);
        }else{
            imageView.setImageResource(R.mipmap.ic_default_head);
        }
    }

    public static void load(View view, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view).load(url)
//                    .placeholder(R.mipmap.ic_default_head)
//                    .error(R.mipmap.ic_default_head).
                    .into(imageView);
        }else{
            imageView.setImageResource(R.mipmap.ic_default_head);
        }
    }


    /**
     * 清除app缓存
     */
    public static void clearAppCache(boolean showToast) {
        @SuppressLint("HandlerLeak") final Handler handler = showToast ? new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    CircleApplication.showToastShort("缓存清除成功");
                } else {
                    CircleApplication.showToastShort("缓存清除失败");
                }
            }
        } : null;
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    CircleApplication.getInstance().clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                if (handler != null)
                    handler.sendMessage(msg);
            }
        });
    }

    // 发起聊天
    public static void chatC2C(Activity activity,String id,String nickName){
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(TIMConversationType.C2C);
        chatInfo.setId(id);
        String chatName = nickName;

        chatInfo.setChatName(chatName);
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(Constants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void chatGroup(Activity activity, ContactItemBean contact){
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(TIMConversationType.Group);
        String chatName = contact.getId();
        if (!TextUtils.isEmpty(contact.getRemark())) {
            chatName = contact.getRemark();
        } else if (!TextUtils.isEmpty(contact.getNickname())) {
            chatName = contact.getNickname();
        }
        chatInfo.setChatName(chatName);
        chatInfo.setId(contact.getId());
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(Constants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
