package com.sdjnshq.circle.data.bean;

public class SystemInfo {

    /**
     * id : 98
     * pid : 0
     * shopId : 919
     * messContent : 欢迎注册济南生活圈APP平台，可免费发布各类目信息。
     * addTime : 2020/5/21 9:45:18
     * addUser : 919
     * status : 0
     * remark :
     * infoType : 1
     */

    private int id;
    private int pid;
    private int shopId;
    private String messContent;
    private String addTime;
    private int addUser;
    private int status;
    private String remark;
    private int infoType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getAddUser() {
        return addUser;
    }

    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }
}
