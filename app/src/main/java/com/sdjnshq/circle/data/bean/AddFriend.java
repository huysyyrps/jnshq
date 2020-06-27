package com.sdjnshq.circle.data.bean;

// 添加好友
public class AddFriend {

    /**
     * id : 1
     * typeId : 0
     * typeName :
     * toUserrId : 911
     * toUserForbid : 0
     * addUserForbid : 0
     * isAudit : 0
     * isApprove : 0
     * status : 0
     * remark : 吃vvv
     * addTime : 2020/5/12 16:30:19
     * addUser : 911
     * infoType : 0
     */

    private int id;
    private int typeId;
    private String typeName;
    private int toUserrId;
    private int toUserForbid;
    private int addUserForbid;
    private int isAudit;
    private int isApprove;
    private int status;
    private String remark;
    private String addTime;
    private int addUser;
    private int infoType;
    private String relName;
    private String imgUrl;

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getToUserrId() {
        return toUserrId;
    }

    public void setToUserrId(int toUserrId) {
        this.toUserrId = toUserrId;
    }

    public int getToUserForbid() {
        return toUserForbid;
    }

    public void setToUserForbid(int toUserForbid) {
        this.toUserForbid = toUserForbid;
    }

    public int getAddUserForbid() {
        return addUserForbid;
    }

    public void setAddUserForbid(int addUserForbid) {
        this.addUserForbid = addUserForbid;
    }

    public int getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(int isAudit) {
        this.isAudit = isAudit;
    }

    public int getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(int isApprove) {
        this.isApprove = isApprove;
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

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }
}
