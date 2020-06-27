package com.sdjnshq.circle.data.bean;

import com.blankj.utilcode.util.TimeUtils;
import com.sdjnshq.circle.utils.XUtils;

// 动态评论信息
public class Comment {


    /**
     * id : 3
     * dataType : 1
     * fromId : 65
     * commentInfo : 测试2222
     * commentDesc :
     * status : 0
     * remark :
     * addTime : 2020/5/6 14:29:05
     * addUser : 911
     * relName : 天天哈哈
     * sex : 1
     * imgUrl : /upload/shoper/2020050610045597.jpg
     * age : 0
     * infoType : 0
     */

    private int id;
    private int dataType;
    private int fromId;
    private String commentInfo;
    private String commentDesc;
    private int status;
    private String remark;
    private String addTime;
    private int addUser;
    private String relName;
    private String sex;
    private String imgUrl;
    private String age;
    private int infoType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
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

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public String getHead() {
        return XUtils.getImagePath(imgUrl);
    }
    public String getFriendAddTime(){
        String time = getAddTime().replace("/","-");
        return  TimeUtils.getFriendlyTimeSpanByNow(time);
    }
}
