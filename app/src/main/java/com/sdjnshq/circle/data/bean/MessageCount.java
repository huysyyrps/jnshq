package com.sdjnshq.circle.data.bean;

public class MessageCount {

    /**
     * newSysCount : 0
     * newVisitCount : 0
     */

    private String newSysCount;
    private String newVisitCount;

    public String getNewSysCount() {
        return newSysCount;
    }

    public void setNewSysCount(String newSysCount) {
        this.newSysCount = newSysCount;
    }

    public String getNewVisitCount() {
        return newVisitCount;
    }

    public void setNewVisitCount(String newVisitCount) {
        this.newVisitCount = newVisitCount;
    }
    public int getCount(){
        return  Integer.parseInt(getNewSysCount())+Integer.parseInt(getNewVisitCount());
    }
}
