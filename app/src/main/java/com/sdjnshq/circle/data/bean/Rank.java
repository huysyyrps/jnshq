package com.sdjnshq.circle.data.bean;

public class Rank {
    private int userId;
    private int num;
    private String relName;
    private String imgUrl;
    private String signName;
    private double doneMoney;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getDoneMoney() {
        return doneMoney;
    }

    public void setDoneMoney(double doneMoney) {
        this.doneMoney = doneMoney;
    }

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

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
