package com.sdjnshq.circle.data.http;

import android.net.ParseException;

import androidx.annotation.Nullable;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;


public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException){
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        }else if(e instanceof RuntimeException){
            ex = new ResponseThrowable(ERROR.CLIENT_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else if(e instanceof  NoFetchNetException){
            ex = new ResponseThrowable(ERROR.FETCHNET_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else if(e instanceof  NoFetchNetException){
            ex = new ResponseThrowable(ERROR.PASSWORD_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else if(e instanceof  NoFetchNetException){
            ex = new ResponseThrowable(ERROR.PHONE_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else if(e instanceof  NoFetchNetException){
            ex = new ResponseThrowable(ERROR.PHONE_CLOSS);
            ex.message = e.getMessage();
            return ex;
        }else if(e instanceof  NoFetchNetException){
            ex = new ResponseThrowable(ERROR.CHECKCODE_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else if(e instanceof  NoFetchNetException){
            ex = new ResponseThrowable(ERROR.LOGIN_ERROR);
            ex.message = e.getMessage();
            return ex;
        }else  if(e instanceof  ResponseThrowable){
//            ex.message = "未知错误";
            return (ResponseThrowable) e;
        }else{
            ex = new ResponseThrowable(ERROR.UNKNOWN,"","未知错误");
            return ex;
        }
    }


    /**
     * 约定异常
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
        /**
         * 请求信息错误
         */
        public static final int CLIENT_ERROR = 1007;
        /**
         * token验证失败
         */
        public static final int TOKEN_ERROR = 1008;
        /**
         * 网络请求跳转
         */
        public static final int FETCHNET_ERROR = 1009;

        /**
         * 手机号错误
         */
        public static final int PHONE_ERROR = 5;
        /**
         * 密码错误
         */
        public static final int PASSWORD_ERROR = 4;
        /**
         * 账号被封
         */
        public static final int PHONE_CLOSS = 10;
        /**
         * 验证码不对
         */
        public static final int CHECKCODE_ERROR = 3;
        /**
         * 登录失败
         */
        public static final int LOGIN_ERROR = 2;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String status;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            this.code = code;
            this.message = throwable.getMessage();

        }
        public ResponseThrowable(int code) {
            this.code = code;
        }
        public ResponseThrowable(int code,String status,String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getStatus() {
            return status;
        }

        @Nullable
        @Override
        public String getMessage() {
            return message;
        }
    }
}

