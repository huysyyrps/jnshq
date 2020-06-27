package com.sdjnshq.circle.data.bean;

// 分享基础信息
public class BasePrice {
    /**
     * id : 1
     * proType :
     * proName :
     * proValue : 0.3
     * parentId :
     * status :
     * remark :
     */

    private int id;
    private String proType;
    private String proName;
    private String proValue;
    private String parentId;
    private String status;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProValue() {
        return proValue;
    }

    public void setProValue(String proValue) {
        this.proValue = proValue;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
