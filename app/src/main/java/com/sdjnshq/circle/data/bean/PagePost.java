package com.sdjnshq.circle.data.bean;

public class PagePost {
    public static final int ERROR_TOAST = 400;
    // 跳转
    public static final int PUSH_ACTIVITY = 101;
    // 找回密码成功
    public static final int REFOUND_MOBILE_SUC = 102;
    // 操作成功
    public static final int PAGE_SUCESS = 200;
    public static final int PAGE_CODE_SUCESS = 201;

    private int code;
    private String value;

    public PagePost() {
    }


    public PagePost(int code) {
        this.code = code;
        this.value = "";
    }
    public static PagePost sucess(){
        return new PagePost(PAGE_SUCESS);
    }

    public PagePost(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public boolean isSucess(){
        return this.code == PAGE_SUCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static PagePost errorMsg(String msg) {
        return new PagePost(ERROR_TOAST, msg);
    }
}
