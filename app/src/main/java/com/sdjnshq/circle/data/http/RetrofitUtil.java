package com.sdjnshq.circle.data.http;


import com.sdjnshq.circle.data.http.api.API;
import com.sdjnshq.circle.utils.AppSP;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lixiaoqi on 2018/8/6.
 */

public class RetrofitUtil {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AREA_TOKEN = "AreaToken";

    public static ErrorTransformer transformer = new ErrorTransformer();

    private static String getUrlBase() {
        return API.HOST;
    }

    private Retrofit mRetrofit;

    /**
     * 单例模式
     */
    private static class SingletonHolder {
        public static RetrofitUtil netWork = new RetrofitUtil();
    }

    public static final RetrofitUtil getInstance() {
        return SingletonHolder.netWork;
    }

    private RetrofitUtil() {
        //添加所有请求打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(addHeaderInterceptor())
                .addInterceptor(addQueryParameterInterceptor())
                .addInterceptor(loggingInterceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = builder
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(getUrlBase())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    /**
     * 创建请求接口
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        .addQueryParameter("token", AppSP.getInstance().getToken())
//                        .addQueryParameter("userId", "909")
//                        .addQueryParameter("device_open_id", BZDevice.getUniqueDeviceIdentifier())
                        .build();

                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
//                        .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
//                        .header(HEADER_AREA_TOKEN, BZJson.decodeJson(area))
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    //处理线程调度的变换
    public static ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    //处理错误的变换
    private static class ErrorTransformer<T> implements ObservableTransformer {
        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (Observable<T>) upstream
                    .map(new HandleFuc<T>())
                    .onErrorResumeNext(new HttpResponseFunc<T>());
        }
    }

    public static class HandleFuc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) throws Exception {
            //response中code码不会600 出现错误
            if (!isSucess(response)) {
                if (isTokenError(response)) {
                    throw new ExceptionHandle.ResponseThrowable(ExceptionHandle.ERROR.TOKEN_ERROR);
                }
                throw new ExceptionHandle.ResponseThrowable(ExceptionHandle.ERROR.UNKNOWN, response.getStatus(), response.getMsg());
            } else {
                return response.getData();
            }
        }
    }

    /**
     * 判断token是否失效
     *
     * @return
     */
    private static boolean isTokenError(BaseResponse response) {
//        for (int code : NetConstant.NEED_LOGIN_CODE) {
//            if (response.getStatus() == code) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 判断请求是否成功
     *
     * @param response
     * @return
     */
    private static boolean isSucess(BaseResponse response) {
        return response.getCode() == NetConstant.SUCESS_CODE;
    }


    public static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }

    public <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void execute(Observable observable, Observer subscriber) {
        observable.compose(schedulersTransformer)
                .compose(transformer)
                .subscribe(subscriber);
    }

    public static <T> Observable<T> transform(Observable<BaseResponse<T>> observable) {
        return observable
                .compose(transformer);
    }
}
