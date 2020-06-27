package com.sdjnshq.circle.data.bean;

import com.sdjnshq.circle.utils.XUtils;

// 用户头像
public class UserHead {

    /**
     * headimg1ID : 3
     * headimg1URL : /upload/shoper/2020042809182364.jpg
     * headimg1OrderNum : 0
     */

    private String headimgID;
    private String headimgURL;
    private String headimgOrderNum;

    public UserHead(String headimg1ID, String headimg1URL, String headimg1OrderNum) {
        this.headimgID = headimg1ID;
        this.headimgURL = headimg1URL;
        this.headimgOrderNum = headimg1OrderNum;
    }

    public String getHeadimgID() {
        return headimgID;
    }

    public void setHeadimgID(String headimgID) {
        this.headimgID = headimgID;
    }

    public String getHeadimgURL() {
        return XUtils.getImagePath(headimgURL);
    }

    public void setHeadimgURL(String headimgURL) {
        this.headimgURL = headimgURL;
    }

    public String getHeadimgOrderNum() {
        return headimgOrderNum;
    }

    public void setHeadimgOrderNum(String headimgOrderNum) {
        this.headimgOrderNum = headimgOrderNum;
    }
}
