package com.sdjnshq.circle.data.bean;

// 首页banner
public class BannerInfo {


    /**
     * id : 28
     * title : 济南生活圈
     * alt :
     * img1path : /upload/fiendImg/2020426164518.png
     * img2Path :
     * linkurl : /
     * orderNum : 1
     * status : 0
     * addTime : 2016/11/22 10:47:46
     * addUser : 4
     * infoType : 1
     */

    private int id;
    private String title;
    private String alt;
    private String img1path;
    private String img2Path;
    private String linkurl;
    private int orderNum;
    private int status;
    private int infoType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImg1path() {
        return img1path;
    }

    public void setImg1path(String img1path) {
        this.img1path = img1path;
    }

    public String getImg2Path() {
        return img2Path;
    }

    public void setImg2Path(String img2Path) {
        this.img2Path = img2Path;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }
}
