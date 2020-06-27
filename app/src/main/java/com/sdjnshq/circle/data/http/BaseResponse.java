package com.sdjnshq.circle.data.http;

/**
 * Created by lixiaoqi on 2018/7/19.
 */
public class BaseResponse<T> {

    public static final int SUCCESS = 1;
    public static final int FAIL = 1;   

    /**
     * 0：成功，1：失败
     */
    private String status;

    private String msg;
    public int code;

    private T data;

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return Integer.valueOf(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
