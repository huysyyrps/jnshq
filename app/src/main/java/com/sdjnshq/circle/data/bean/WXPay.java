package com.sdjnshq.circle.data.bean;

public class WXPay {

    /**
     * timestamp : 1590220568
     * spbill_create_ip : 222.175.152.82
     * total_fee : 9800
     * trade_type : APP
     * appid : wx25f0d0a93d9b3002
     * body : 93920200523134318
     * device_info : WEB
     * mch_id : 1563516211
     * nonce_str : J3GKBXMBC3TO66HEWNBG
     * notify_url : http://www.sdjnshq.com/busi/payback.aspx
     * out_trade_no : 93920200523134318
     * preId : wx23155608994427c83107d11a1600546300
     * sign : CDE2A2F2D28465A366145E5324BBF6F8
     * recoderCount : 1
     * currentPageData : [{"id":307,"orderNo":"93920200523134318","orderName":"代言人会员订单","proTotal":98,"sendTotal":0,"orderTotal":98,"orderDesc":"济南生活圈代言人会员订单","remark":"","recieveUser":"广告语","pid":0,"PName":"","cid":0,"CName":"","regionId":0,"ReginName":"特价美甲体验88元（原价：198元）","address":"","mobile":"18615656904","tel":"","status":0,"addTime":"2020/5/23 13:43:18","infoType":1,"addUser":939,"relName":"广告语","sex":"1","imgUrl":"/upload/shoper/2020052313422591.jpg","age":"9"}]
     */

    private String timestamp;
    private String spbill_create_ip;
    private String total_fee;
    private String trade_type;
    private String appid;
    private String body;
    private String device_info;
    private String mch_id;
    private String nonce_str;
    private String notify_url;
    private String out_trade_no;
    private String preId;
    private String sign;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
