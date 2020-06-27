package com.sdjnshq.circle.data.bean;

import com.sdjnshq.circle.utils.XUtils;

public class Near {


    /**
     * dis : 0.0143
     * userId : 910
     * relName : 混沌
     * sex : 2
     * imgUrl : /upload/erweiImg/0_user.jpg
     * age : 9
     */

    private double dis;
    private int userId;
    private String relName;
    private String sex;
    private String imgUrl;
    private String age;
    private String signName;

    // 1男2女
    public boolean isMale(){
        if(sex!=null) {
            return sex.equals("1");
        }
        return false;
    }

    public double getDis() {
        return dis;
    }
    // 距离
    public String getDistance(){
        if(dis>=1){
            return String.format("%.2f", dis)+"km";
        }else{
            return (int)(dis*1000)+"m";
        }
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public void setDis(double dis) {
        this.dis = dis;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    public String getHead(){
        return XUtils.getImagePath(imgUrl);
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
}
