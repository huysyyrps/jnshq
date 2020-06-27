package com.sdjnshq.circle.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sdjnshq.architecture.utils.Utils;
import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.utils.utils.WeChatUtil;
import com.tencent.qcloud.tim.uikit.utils.ScreenUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

public class XDiallog  {

    //分享弹窗
    public static AlertDialog showShareDialog(Activity context,String headUrl,String name,String codeUrl,String codeUrl2){
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) context).isDestroyed())
                    return null;
            }
        }

        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Translucent_NoTitle);
        builder.setTitle("");
        builder.setCancelable(true);
        dialog = builder.create();

        dialog.show();
        View baseView = LayoutInflater.from(context).inflate(R.layout.lay_share_dialog, null);
        XUtils.loadHear(baseView,headUrl,baseView.findViewById(R.id.iv_head));
        XUtils.loadHear(baseView,codeUrl2,baseView.findViewById(R.id.iv_code));

        baseView.findViewById(R.id.iv_share_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeChatUtil.shareImage(codeUrl,false);
            }
        });
        baseView.findViewById(R.id.iv_share_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeChatUtil.shareImage(codeUrl,true);
            }
        });

        baseView.findViewById(R.id.iv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(CircleApplication.getInstance()).asBitmap().load(codeUrl).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Utils.saveImageToGallery(CircleApplication.getInstance(),resource);
                        ToastUtil.toastLongMessage("保存成功");
                    }
                });
            }
        });

        baseView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((TextView) baseView.findViewById(R.id.tv_name)).setText(name);
        dialog.setContentView(baseView);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ScreenUtil.getPxByDp(350);//定义宽度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//定义高度
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(baseView);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return dialog;
    }

    // 动态弹窗
    public static AlertDialog showCircleDialog(Context context, View.OnClickListener deleteListener){
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) context).isDestroyed())
                    return null;
            }
        }

        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Translucent_NoTitle);
        builder.setTitle("");
        builder.setCancelable(true);
        dialog = builder.create();

        dialog.show();
        View baseView = LayoutInflater.from(context).inflate(R.layout.lay_circle_dialog, null);
        baseView.findViewById(R.id.ll_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.setContentView(baseView);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ScreenUtil.getPxByDp(350);//定义宽度
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;//定义高度
        dialog.getWindow().setAttributes(lp);
        dialog.setContentView(baseView);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return dialog;
    }
}
