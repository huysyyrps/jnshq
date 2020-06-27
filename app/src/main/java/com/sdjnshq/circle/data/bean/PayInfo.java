package com.sdjnshq.circle.data.bean;

public class PayInfo {
    private WXPay wx;
    private AliPay ali;

    public WXPay getWx() {
        return wx;
    }

    public void setWx(WXPay wx) {
        this.wx = wx;
    }

    public AliPay getAli() {
        return ali;
    }

    public void setAli(AliPay ali) {
        this.ali = ali;
    }
}
