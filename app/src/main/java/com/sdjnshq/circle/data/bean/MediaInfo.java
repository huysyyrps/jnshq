package com.sdjnshq.circle.data.bean;

public class MediaInfo {
    public static int TYPE_ADD =1;
    private String url;
    private String path;
    private int type;
    private String id;
    private String orderNum;
    private boolean isCompress;




    public static final MediaInfo newADDMediaInfo(){
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setType(TYPE_ADD);
        return  mediaInfo;
    }
    public static final MediaInfo newURLMediaInfo(String id,String url,String orderNum){
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setId(id);
        mediaInfo.setOrderNum(orderNum);
        mediaInfo.setUrl(url);
        return  mediaInfo;
    }

    public static final MediaInfo newPathMediaInfo(String path){
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setPath(path);
        return  mediaInfo;
    }

    public static final MediaInfo newPathMediaInfo(String path,boolean isCompress){
        MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.setPath(path);
        mediaInfo.setCompress(isCompress);
        return  mediaInfo;
    }


    public boolean isCompress() {
        return isCompress;
    }

    public void setCompress(boolean compress) {
        isCompress = compress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
