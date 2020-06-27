package com.sdjnshq.circle.utils.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sdjnshq.circle.R;
import com.tencent.qcloud.tim.uikit.utils.ScreenUtil;

public class XDiallog  {

    //分享弹窗
    public static AlertDialog showShareDialog(Activity context,String headUrl,String name,String codeUrl){
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
        XUtils.loadHear(baseView,codeUrl,baseView.findViewById(R.id.iv_code));

        baseView.findViewById(R.id.iv_share_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BiliShare shareClient = BiliShare.global();
//
//                BaseShareParam param = new ShareParamImage("济南生活圈","ssss",codeUrl);
//                shareClient.share(context, SocializeMedia.WEIXIN, param, new SocializeListeners.ShareListener() {
//                    @Override
//                    public void onStart(SocializeMedia type) {
//
//                    }
//
//                    @Override
//                    public void onProgress(SocializeMedia type, String progressDesc) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(SocializeMedia type, int code) {
//
//                    }
//
//                    @Override
//                    public void onError(SocializeMedia type, int code, Throwable error) {
//
//                    }
//
//                    @Override
//                    public void onCancel(SocializeMedia type) {
//
//                    }
//                });
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
