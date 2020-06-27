package com.sdjnshq.circle.data.http;

import android.widget.Toast;

import com.sdjnshq.circle.CircleApplication;

/**
 * Created by lixiaoqi on 17/10/24.
 */

public abstract class SObserver<T> extends BaseObserver<T> {
    boolean showError = true;
    public SObserver() {
    }

    public SObserver(boolean showError){
        this.showError = showError;
    }

    @Override
    public void onError(ExceptionHandle.ResponseThrowable e) {
        if (e.code == ExceptionHandle.ERROR.TOKEN_ERROR) {
            // 登录
        }
//        else if(e.code == ExceptionHandle.ERROR.RETURN_ERROR){
//            ToastUtil.show(mContext,e.message);
//        }else if(e.code == ExceptionHandle.ERROR.TIMEOUT_ERROR){
//            ToastUtil.show(mContext,e.message);
//        }
        onError(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 成功事件
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 失败事件
     */
    public void onError(String message) {
        if(showError) {
            Toast.makeText(CircleApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 结束事件
     */


}
