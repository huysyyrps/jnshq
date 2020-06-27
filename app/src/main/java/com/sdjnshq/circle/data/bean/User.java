package com.sdjnshq.circle.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;

import com.google.gson.annotations.SerializedName;
import com.sdjnshq.circle.utils.TimeUtils;
import com.sdjnshq.circle.utils.XUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User extends LiveData<User> implements Parcelable {
    /**
     * id : 909
     * username :
     * mobile : 18615656904
     * isBindMobile : 0
     * email :
     * isBindEmail : 0
     * password : 12345678
     * md5Pass : 25d55ad283aa400af464c76d713c07ad
     * telephone :
     * bodyCode :
     * comName :
     * pid : 0
     * cid : 0
     * regionId : 0
     * address :
     * zipCode :
     * qq :
     * weixin :
     * isBindWeiXin : 0
     * weibo :
     * isBindWeiBo : 0
     * shopType : 1
     * shopTypeName : /upload/erweiImg/0_user.jpg
     * imgUrl :
     * comInfo :
     * remark :
     * status : 0
     * addTime : 2020/4/17 17:54:14
     * mobileCode :
     * activeCode :
     * infoType : 1
     * shopingTime :
     * mainBrand :
     * assessCount : 0
     * shareCount : 0
     * productType : 0
     * goodCount : 0
     * badCount : 0
     * viewsCount : 0
     * loginTime : 2020/4/17 17:54:14
     * loginIP : 222.175.152.82
     * relName :
     */

    private int id;
    private String username;
    private String mobile;
    private int isBindMobile;
    private String email;
    private int isBindEmail;
    private String password;
    private String md5Pass;
    private String telephone;
    private String comName;
    private int pid;
    private int cid;
    private int regionId;
    private String address;
    private String zipCode;
    private String weixin;
    private int isBindWeiXin;
    private int isBindWeiBo;
    private int shopType;
    private String shopTypeName;
    private String imgUrl;
    private String comInfo;
    private String remark;
    private int status;
    private String addTime;
    private int infoType;
    private String shopingTime;
    private int assessCount;
    private int shareCount;
    private int productType;
    private int goodCount;
    private int badCount;
    private int viewsCount;
    private String loginTime;
    private String loginIP;
    private String token;
    @SerializedName("activeCode")
    private String homeTown;
    @SerializedName("qq")
    private String emotion;
    @SerializedName("mainBrand")
    private String sex;
    @SerializedName("bodyCode")
    private String birthday;
    @SerializedName("relName")
    private String nickName;
    // 职业
    @SerializedName("mobileCode")
    private String job;
    // 职业
    @SerializedName("weibo")
    private String sign;
    private String dis;
    private String headimg1URL;
    private String headimg1ID;
    private String headimg1OrderNum;
    private String headimg2URL;
    private String headimg2ID;
    private String headimg2OrderNum;
    private String headimg3URL;
    private String headimg3ID;
    private String headimg3OrderNum;
    private String headimg4URL;
    private String headimg4ID;
    private String headimg4OrderNum;
    private String headimg5URL;
    private String headimg5ID;
    private String headimg5OrderNum;
    private String headimg6URL;
    private String headimg6ID;
    private String headimg6OrderNum;
    private String headimg7URL;
    private String headimg7ID;
    private String headimg7OrderNum;
    private String headimg8URL;
    private String headimg8ID;
    private String headimg8OrderNum;
    private String headimg9URL;
    private String headimg9ID;
    private String headimg9OrderNum;
    private String headimg10URL;
    private String headimg10ID;
    private String headimg10OrderNum;
    private String headimg11URL;
    private String headimg11ID;
    private String headimg11OrderNum;
    private String headimg12URL;
    private String headimg12ID;
    private String headimg12OrderNum;
    private String headimg13URL;
    private String headimg13ID;
    private String headimg13OrderNum;
    private String headimg14URL;
    private String headimg14ID;
    private String headimg14OrderNum;
    private String headimg15URL;
    private String headimg15ID;
    private String headimg15OrderNum;
    private String headimg16URL;
    private String headimg16ID;
    private String headimg16OrderNum;
    private boolean isCheck;
    private String moneyTotal;
    //1 是 2否
    private int isFriend;

    public String getMoneyTotal() {
        return moneyTotal;
    }

    public void setMoneyTotal(String moneyTotal) {
        this.moneyTotal = moneyTotal;
    }

    public boolean isFriend() {
        return isFriend == 1;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getHeadimg1URL() {
        return headimg1URL;
    }

    public void setHeadimg1URL(String headimg1URL) {
        this.headimg1URL = headimg1URL;
    }

    public String getHeadimg1ID() {
        return headimg1ID;
    }

    public void setHeadimg1ID(String headimg1ID) {
        this.headimg1ID = headimg1ID;
    }

    public String getHeadimg1OrderNum() {
        return headimg1OrderNum;
    }

    public void setHeadimg1OrderNum(String headimg1OrderNum) {
        this.headimg1OrderNum = headimg1OrderNum;
    }

    public String getHeadimg2URL() {
        return headimg2URL;
    }

    public void setHeadimg2URL(String headimg2URL) {
        this.headimg2URL = headimg2URL;
    }

    public String getHeadimg2ID() {
        return headimg2ID;
    }

    public void setHeadimg2ID(String headimg2ID) {
        this.headimg2ID = headimg2ID;
    }

    public String getHeadimg2OrderNum() {
        return headimg2OrderNum;
    }

    public void setHeadimg2OrderNum(String headimg2OrderNum) {
        this.headimg2OrderNum = headimg2OrderNum;
    }

    public String getHeadimg3URL() {
        return headimg3URL;
    }

    public void setHeadimg3URL(String headimg3URL) {
        this.headimg3URL = headimg3URL;
    }

    public String getHeadimg3ID() {
        return headimg3ID;
    }

    public void setHeadimg3ID(String headimg3ID) {
        this.headimg3ID = headimg3ID;
    }

    public String getHeadimg3OrderNum() {
        return headimg3OrderNum;
    }

    public void setHeadimg3OrderNum(String headimg3OrderNum) {
        this.headimg3OrderNum = headimg3OrderNum;
    }

    public String getHeadimg4URL() {
        return headimg4URL;
    }

    public void setHeadimg4URL(String headimg4URL) {
        this.headimg4URL = headimg4URL;
    }

    public String getHeadimg4ID() {
        return headimg4ID;
    }

    public void setHeadimg4ID(String headimg4ID) {
        this.headimg4ID = headimg4ID;
    }

    public String getHeadimg4OrderNum() {
        return headimg4OrderNum;
    }

    public void setHeadimg4OrderNum(String headimg4OrderNum) {
        this.headimg4OrderNum = headimg4OrderNum;
    }

    public String getHeadimg5URL() {
        return headimg5URL;
    }

    public void setHeadimg5URL(String headimg5URL) {
        this.headimg5URL = headimg5URL;
    }

    public String getHeadimg5ID() {
        return headimg5ID;
    }

    public void setHeadimg5ID(String headimg5ID) {
        this.headimg5ID = headimg5ID;
    }

    public String getHeadimg5OrderNum() {
        return headimg5OrderNum;
    }

    public void setHeadimg5OrderNum(String headimg5OrderNum) {
        this.headimg5OrderNum = headimg5OrderNum;
    }

    public String getHeadimg6URL() {
        return headimg6URL;
    }

    public void setHeadimg6URL(String headimg6URL) {
        this.headimg6URL = headimg6URL;
    }

    public String getHeadimg6ID() {
        return headimg6ID;
    }

    public void setHeadimg6ID(String headimg6ID) {
        this.headimg6ID = headimg6ID;
    }

    public String getHeadimg6OrderNum() {
        return headimg6OrderNum;
    }

    public void setHeadimg6OrderNum(String headimg6OrderNum) {
        this.headimg6OrderNum = headimg6OrderNum;
    }

    public String getHeadimg7URL() {
        return headimg7URL;
    }

    public void setHeadimg7URL(String headimg7URL) {
        this.headimg7URL = headimg7URL;
    }

    public String getHeadimg7ID() {
        return headimg7ID;
    }

    public void setHeadimg7ID(String headimg7ID) {
        this.headimg7ID = headimg7ID;
    }

    public String getHeadimg7OrderNum() {
        return headimg7OrderNum;
    }

    public void setHeadimg7OrderNum(String headimg7OrderNum) {
        this.headimg7OrderNum = headimg7OrderNum;
    }

    public String getHeadimg8URL() {
        return headimg8URL;
    }

    public void setHeadimg8URL(String headimg8URL) {
        this.headimg8URL = headimg8URL;
    }

    public String getHeadimg8ID() {
        return headimg8ID;
    }

    public void setHeadimg8ID(String headimg8ID) {
        this.headimg8ID = headimg8ID;
    }

    public String getHeadimg8OrderNum() {
        return headimg8OrderNum;
    }

    public void setHeadimg8OrderNum(String headimg8OrderNum) {
        this.headimg8OrderNum = headimg8OrderNum;
    }

    public String getHeadimg9URL() {
        return headimg9URL;
    }

    public void setHeadimg9URL(String headimg9URL) {
        this.headimg9URL = headimg9URL;
    }

    public String getHeadimg9ID() {
        return headimg9ID;
    }

    public void setHeadimg9ID(String headimg9ID) {
        this.headimg9ID = headimg9ID;
    }

    public String getHeadimg9OrderNum() {
        return headimg9OrderNum;
    }

    public void setHeadimg9OrderNum(String headimg9OrderNum) {
        this.headimg9OrderNum = headimg9OrderNum;
    }

    public String getHeadimg10URL() {
        return headimg10URL;
    }

    public void setHeadimg10URL(String headimg10URL) {
        this.headimg10URL = headimg10URL;
    }

    public String getHeadimg10ID() {
        return headimg10ID;
    }

    public void setHeadimg10ID(String headimg10ID) {
        this.headimg10ID = headimg10ID;
    }

    public String getHeadimg10OrderNum() {
        return headimg10OrderNum;
    }

    public void setHeadimg10OrderNum(String headimg10OrderNum) {
        this.headimg10OrderNum = headimg10OrderNum;
    }

    public String getHeadimg11URL() {
        return headimg11URL;
    }

    public void setHeadimg11URL(String headimg11URL) {
        this.headimg11URL = headimg11URL;
    }

    public String getHeadimg11ID() {
        return headimg11ID;
    }

    public void setHeadimg11ID(String headimg11ID) {
        this.headimg11ID = headimg11ID;
    }

    public String getHeadimg11OrderNum() {
        return headimg11OrderNum;
    }

    public void setHeadimg11OrderNum(String headimg11OrderNum) {
        this.headimg11OrderNum = headimg11OrderNum;
    }

    public String getHeadimg12URL() {
        return headimg12URL;
    }

    public void setHeadimg12URL(String headimg12URL) {
        this.headimg12URL = headimg12URL;
    }

    public String getHeadimg12ID() {
        return headimg12ID;
    }

    public void setHeadimg12ID(String headimg12ID) {
        this.headimg12ID = headimg12ID;
    }

    public String getHeadimg12OrderNum() {
        return headimg12OrderNum;
    }

    public void setHeadimg12OrderNum(String headimg12OrderNum) {
        this.headimg12OrderNum = headimg12OrderNum;
    }

    public String getHeadimg13URL() {
        return headimg13URL;
    }

    public void setHeadimg13URL(String headimg13URL) {
        this.headimg13URL = headimg13URL;
    }

    public String getHeadimg13ID() {
        return headimg13ID;
    }

    public void setHeadimg13ID(String headimg13ID) {
        this.headimg13ID = headimg13ID;
    }

    public String getHeadimg13OrderNum() {
        return headimg13OrderNum;
    }

    public void setHeadimg13OrderNum(String headimg13OrderNum) {
        this.headimg13OrderNum = headimg13OrderNum;
    }

    public String getHeadimg14URL() {
        return headimg14URL;
    }

    public void setHeadimg14URL(String headimg14URL) {
        this.headimg14URL = headimg14URL;
    }

    public String getHeadimg14ID() {
        return headimg14ID;
    }

    public void setHeadimg14ID(String headimg14ID) {
        this.headimg14ID = headimg14ID;
    }

    public String getHeadimg14OrderNum() {
        return headimg14OrderNum;
    }

    public void setHeadimg14OrderNum(String headimg14OrderNum) {
        this.headimg14OrderNum = headimg14OrderNum;
    }

    public String getHeadimg15URL() {
        return headimg15URL;
    }

    public void setHeadimg15URL(String headimg15URL) {
        this.headimg15URL = headimg15URL;
    }

    public String getHeadimg15ID() {
        return headimg15ID;
    }

    public void setHeadimg15ID(String headimg15ID) {
        this.headimg15ID = headimg15ID;
    }

    public String getHeadimg15OrderNum() {
        return headimg15OrderNum;
    }

    public void setHeadimg15OrderNum(String headimg15OrderNum) {
        this.headimg15OrderNum = headimg15OrderNum;
    }

    public String getHeadimg16URL() {
        return headimg16URL;
    }

    public void setHeadimg16URL(String headimg16URL) {
        this.headimg16URL = headimg16URL;
    }

    public String getHeadimg16ID() {
        return headimg16ID;
    }

    public void setHeadimg16ID(String headimg16ID) {
        this.headimg16ID = headimg16ID;
    }

    public String getHeadimg16OrderNum() {
        return headimg16OrderNum;
    }

    public void setHeadimg16OrderNum(String headimg16OrderNum) {
        this.headimg16OrderNum = headimg16OrderNum;
    }

    public int getAgeByBirth() {
        int age = 0;
        try {
            Date birthDay = TimeUtils.string2date(getBirthday());
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
                throw new IllegalArgumentException(
                        "The birthDay is before Now.It's unbelievable!");
            }
            int yearNow = cal.get(Calendar.YEAR);  //当前年份
            int monthNow = cal.get(Calendar.MONTH);  //当前月份
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
            cal.setTime(birthDay);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
        } catch (Exception ex) {
            return age;
        }
        return age;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    // 1男2女
    public boolean isMale() {
        return sex == null ? true : sex.equals("1");
    }

    public void change(int from,int to){
        String fromimgUrl = "";
        String fromimgId = "";
        if(from == 1){
            fromimgId = headimg1ID;
            fromimgUrl = headimg1URL;
        }else if(from == 2){
            fromimgId = headimg2ID;
            fromimgUrl = headimg2URL;
        }else if(from == 3){
            fromimgId = headimg3ID;
            fromimgUrl = headimg3URL;
        }
        String toimgUrl = "";
        String toimgId = "";
        if(to == 1){
            toimgId = headimg1ID;
            toimgUrl = headimg1URL;
        }else if(to == 2){
            toimgId = headimg2ID;
            toimgUrl = headimg2URL;
        }else if(to == 3){
            toimgId = headimg3ID;
            toimgUrl = headimg3URL;
        }
        String url =toimgId;
        String id = toimgUrl;
        toimgId = fromimgId;
        toimgId = fromimgUrl;
        fromimgId = id;
        fromimgUrl = url;
    }

    public List<UserHead> getBannerList() {
        List<UserHead> bannerList = new ArrayList<>();

        if (!TextUtils.isEmpty(headimg1URL)) {
            bannerList.add(new UserHead(headimg1ID, headimg1URL, headimg1OrderNum));
        }
        if (!TextUtils.isEmpty(headimg2URL)) {
            bannerList.add(new UserHead(headimg2ID, headimg2URL, headimg2OrderNum));
        }
        if (!TextUtils.isEmpty(headimg3URL)) {
            bannerList.add(new UserHead(headimg3ID, headimg3URL, headimg3OrderNum));
        }
        if (!TextUtils.isEmpty(headimg4URL)) {
            bannerList.add(new UserHead(headimg4ID, headimg4URL, headimg4OrderNum));
        }
        if (!TextUtils.isEmpty(headimg5URL)) {
            bannerList.add(new UserHead(headimg5ID, headimg5URL, headimg5OrderNum));
        }
        if (!TextUtils.isEmpty(headimg6URL)) {
            bannerList.add(new UserHead(headimg6ID, headimg6URL, headimg6OrderNum));
        }
        if (!TextUtils.isEmpty(headimg7URL)) {
            bannerList.add(new UserHead(headimg7ID, headimg7URL, headimg7OrderNum));
        }
        if (!TextUtils.isEmpty(headimg8URL)) {
            bannerList.add(new UserHead(headimg8ID, headimg8URL, headimg8OrderNum));
        }
        if (!TextUtils.isEmpty(headimg9URL)) {
            bannerList.add(new UserHead(headimg9ID, headimg9URL, headimg9OrderNum));
        }
        if (!TextUtils.isEmpty(headimg10URL)) {
            bannerList.add(new UserHead(headimg10ID, headimg10URL, headimg10OrderNum));
        }
        if (!TextUtils.isEmpty(headimg11URL)) {
            bannerList.add(new UserHead(headimg11ID, headimg11URL, headimg11OrderNum));
        }
        if (!TextUtils.isEmpty(headimg12URL)) {
            bannerList.add(new UserHead(headimg12ID, headimg12URL, headimg12OrderNum));
        }
        if (!TextUtils.isEmpty(headimg13URL)) {
            bannerList.add(new UserHead(headimg13ID, headimg13URL, headimg13OrderNum));
        }
        if (!TextUtils.isEmpty(headimg14URL)) {
            bannerList.add(new UserHead(headimg14ID, headimg14URL, headimg14OrderNum));
        }
        if (!TextUtils.isEmpty(headimg15URL)) {
            bannerList.add(new UserHead(headimg14ID, headimg14URL, headimg14OrderNum));
        }
        if (!TextUtils.isEmpty(headimg16URL)) {
            bannerList.add(new UserHead(headimg16ID, headimg16URL, headimg16OrderNum));
        }

        return bannerList;
    }

    public List<MediaInfo> getImageList() {
        List<MediaInfo> bannerList = new ArrayList<>();

        if (!TextUtils.isEmpty(headimg1URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg1ID, headimg1URL, headimg1OrderNum));
        }
        if (!TextUtils.isEmpty(headimg2URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg2ID, headimg2URL, headimg2OrderNum));
        }
        if (!TextUtils.isEmpty(headimg3URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg3ID, headimg3URL, headimg3OrderNum));
        }
        if (!TextUtils.isEmpty(headimg4URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg4ID, headimg4URL, headimg4OrderNum));
        }
        if (!TextUtils.isEmpty(headimg5URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg5ID, headimg5URL, headimg5OrderNum));
        }
        if (!TextUtils.isEmpty(headimg6URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg6ID, headimg6URL, headimg6OrderNum));
        }
        if (!TextUtils.isEmpty(headimg7URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg7ID, headimg7URL, headimg7OrderNum));
        }
        if (!TextUtils.isEmpty(headimg8URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg8ID, headimg8URL, headimg8OrderNum));
        }
        if (!TextUtils.isEmpty(headimg9URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg9ID, headimg9URL, headimg9OrderNum));
        }
        if (!TextUtils.isEmpty(headimg10URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg10ID, headimg10URL, headimg10OrderNum));
        }
        if (!TextUtils.isEmpty(headimg11URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg11ID, headimg11URL, headimg11OrderNum));
        }
        if (!TextUtils.isEmpty(headimg12URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg12ID, headimg12URL, headimg12OrderNum));
        }
        if (!TextUtils.isEmpty(headimg13URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg13ID, headimg13URL, headimg13OrderNum));
        }
        if (!TextUtils.isEmpty(headimg14URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg14ID, headimg14URL, headimg14OrderNum));
        }
        if (!TextUtils.isEmpty(headimg15URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg14ID, headimg14URL, headimg14OrderNum));
        }
        if (!TextUtils.isEmpty(headimg16URL)) {
            bannerList.add(MediaInfo.newURLMediaInfo(headimg16ID, headimg16URL, headimg16OrderNum));
        }

        return bannerList;
    }

    public void refresh(User user) {
        setValue(user);
//        postValue(this);
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
        postValue(this);
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
        postValue(this);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        postValue(this);
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        postValue(this);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        postValue(this);
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
        postValue(this);
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
        postValue(this);
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsBindMobile() {
        return isBindMobile;
    }

    public void setIsBindMobile(int isBindMobile) {
        this.isBindMobile = isBindMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(int isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMd5Pass() {
        return md5Pass;
    }

    public void setMd5Pass(String md5Pass) {
        this.md5Pass = md5Pass;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public int getIsBindWeiXin() {
        return isBindWeiXin;
    }

    public void setIsBindWeiXin(int isBindWeiXin) {
        this.isBindWeiXin = isBindWeiXin;
    }


    public int getIsBindWeiBo() {
        return isBindWeiBo;
    }

    public void setIsBindWeiBo(int isBindWeiBo) {
        this.isBindWeiBo = isBindWeiBo;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public String getShopTypeName() {
        return shopTypeName;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    // 获取头像
    public String getHead() {
        return XUtils.getImagePath(TextUtils.isEmpty(headimg1URL) ? imgUrl : headimg1URL);
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getComInfo() {
        return comInfo;
    }

    public void setComInfo(String comInfo) {
        this.comInfo = comInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getInfoType() {
        return infoType;
    }

    public void setInfoType(int infoType) {
        this.infoType = infoType;
    }

    public String getShopingTime() {
        return shopingTime;
    }

    public void setShopingTime(String shopingTime) {
        this.shopingTime = shopingTime;
    }


    public int getAssessCount() {
        return assessCount;
    }

    public void setAssessCount(int assessCount) {
        this.assessCount = assessCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }


    // 距离
    public String getDistance() {
        if (TextUtils.isEmpty(getDis())) {
            return "";
        }
        double dis = Double.parseDouble(getDis());
        if (dis >= 1) {
            return String.format("%.2f", dis) + "km";
        } else {
            return (int) (dis * 1000) + "m";
        }
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.mobile);
        dest.writeInt(this.isBindMobile);
        dest.writeString(this.email);
        dest.writeInt(this.isBindEmail);
        dest.writeString(this.password);
        dest.writeString(this.md5Pass);
        dest.writeString(this.telephone);
        dest.writeString(this.comName);
        dest.writeInt(this.pid);
        dest.writeInt(this.cid);
        dest.writeInt(this.regionId);
        dest.writeString(this.address);
        dest.writeString(this.zipCode);
        dest.writeString(this.weixin);
        dest.writeInt(this.isBindWeiXin);
        dest.writeInt(this.isBindWeiBo);
        dest.writeInt(this.shopType);
        dest.writeString(this.shopTypeName);
        dest.writeString(this.imgUrl);
        dest.writeString(this.comInfo);
        dest.writeString(this.remark);
        dest.writeInt(this.status);
        dest.writeString(this.addTime);
        dest.writeInt(this.infoType);
        dest.writeString(this.shopingTime);
        dest.writeInt(this.assessCount);
        dest.writeInt(this.shareCount);
        dest.writeInt(this.productType);
        dest.writeInt(this.goodCount);
        dest.writeInt(this.badCount);
        dest.writeInt(this.viewsCount);
        dest.writeString(this.loginTime);
        dest.writeString(this.loginIP);
        dest.writeString(this.token);
        dest.writeString(this.homeTown);
        dest.writeString(this.emotion);
        dest.writeString(this.sex);
        dest.writeString(this.birthday);
        dest.writeString(this.nickName);
        dest.writeString(this.job);
        dest.writeString(this.sign);
        dest.writeString(this.dis);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.mobile = in.readString();
        this.isBindMobile = in.readInt();
        this.email = in.readString();
        this.isBindEmail = in.readInt();
        this.password = in.readString();
        this.md5Pass = in.readString();
        this.telephone = in.readString();
        this.comName = in.readString();
        this.pid = in.readInt();
        this.cid = in.readInt();
        this.regionId = in.readInt();
        this.address = in.readString();
        this.zipCode = in.readString();
        this.weixin = in.readString();
        this.isBindWeiXin = in.readInt();
        this.isBindWeiBo = in.readInt();
        this.shopType = in.readInt();
        this.shopTypeName = in.readString();
        this.imgUrl = in.readString();
        this.comInfo = in.readString();
        this.remark = in.readString();
        this.status = in.readInt();
        this.addTime = in.readString();
        this.infoType = in.readInt();
        this.shopingTime = in.readString();
        this.assessCount = in.readInt();
        this.shareCount = in.readInt();
        this.productType = in.readInt();
        this.goodCount = in.readInt();
        this.badCount = in.readInt();
        this.viewsCount = in.readInt();
        this.loginTime = in.readString();
        this.loginIP = in.readString();
        this.token = in.readString();
        this.homeTown = in.readString();
        this.emotion = in.readString();
        this.sex = in.readString();
        this.birthday = in.readString();
        this.nickName = in.readString();
        this.job = in.readString();
        this.sign = in.readString();
        this.dis = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}