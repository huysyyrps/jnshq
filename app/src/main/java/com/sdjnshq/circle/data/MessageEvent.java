package com.sdjnshq.circle.data;

public class MessageEvent<T> { /* Additional fields if needed */
   public  boolean show;
   public String page;
   public  T data;

    public MessageEvent() {
    }

    public MessageEvent(String page) {
        this.page = page;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MessageEvent(boolean show) {
        this.show = show;
    }
}
