package com.sdjnshq.architecture.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

// 倒计时
public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;

    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "s后重新获取");  //设置倒计时时间
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);//重新获得点击
    }
}
