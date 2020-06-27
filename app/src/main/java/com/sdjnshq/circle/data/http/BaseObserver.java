package com.sdjnshq.circle.data.http;

import android.net.ParseException;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        // 用于数据缓存跳转
        if(e instanceof NoFetchNetException){
            onComplete();
        } else if (e instanceof JsonSyntaxException || e instanceof JSONException || e instanceof ParseException) {
            onError((ExceptionHandle.ResponseThrowable) e);
        } else if (e instanceof ExceptionHandle.ResponseThrowable) {
            onError((ExceptionHandle.ResponseThrowable) e);
        }
//        else if (e instanceof ReturnException) {
//            onError((ExceptionHandle.ResponseThrowable) e);
//        } else if (e instanceof ExceptionHandle.TokenException) {
//            ExceptionHandle.ResponseThrowable e1 = (ExceptionHandle.ResponseThrowable) e;
//            onError(e1);
//        }
        else {
            onError(new ExceptionHandle.ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onComplete() {

    }


    public abstract void onError(ExceptionHandle.ResponseThrowable e);

}
