package com.sdjnshq.circle.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;
import com.sdjnshq.circle.utils.XUtils;

import java.util.ArrayList;
import java.util.List;

// 动态
public class Circle implements Parcelable {
    public static final int  TYPE_IMAGE =1;
    /**
     * id : 61
     * title :
     * pageName :
     * newsType : 1
     * newImg :
     * newsContent : 为人民服务，我幸福，我快乐，我开心
     * keyword :
     * newsDesc :
     * linkurl :
     * is_tj : 0
     * is_hot : 0
     * ordernum : 0
     * addTime : 2020/4/29 14:55:39
     * add_userid : 909
     * relName : TuTu
     * sex : 1
     * imgUrl : /upload/shoper/2020042809182364.jpg
     * age : 0
     * res_views : 0
     * areaid : 0
     * sid :
     * releaseTime : 2020/4/29 14:55:39
     * author :
     */

    private int id;
    private String title;
    private String pageName;
    private int newsType;
    private String newImg;
    private String newsContent;
    private String keyword;
    private String newsDesc;
    private String linkurl;
    private int is_tj;
    private int is_hot;
    // 点赞次数
    private int ordernum;
    private String addTime;
    private int add_userid;
    private String relName;
    private String sex;
    private String imgUrl;
    private String age;
    private int res_views;
    private int areaid;
    private String sid;
    private String releaseTime;
    private String author;
    private double dis;
    private String poiName;
    private int commentCount;
    // 1 是 2否
    private int currUserGood;
    // 是否已经点赞
    private boolean isThumb;
    private ArrayList<String> imageList;

    public ArrayList<String> getImageList() {
        if (imageList == null) {
            imageList = new ArrayList<>();
        }
        if (imageList.size() == 0 ) {
            if(!TextUtils.isEmpty(newImg)) {
                imageList.add(XUtils.getImagePath(newImg));
            }
            if(!TextUtils.isEmpty(pageName)) {
                imageList.add(XUtils.getImagePath(pageName));
            }
            if(!TextUtils.isEmpty(keyword)) {
                imageList.add(XUtils.getImagePath(keyword));
            }
        }

        return imageList;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public boolean isThumb() {
        return isThumb;
    }

    public void setThumb(boolean thumb) {
        isThumb = thumb;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    // 距离
    public String getDistance() {
        if (dis >= 1) {
            return String.format("%.2f", dis) + "km";
        } else {
            return (int) (dis * 1000) + "m";
        }
    }


    public int getCurrUserGood() {
        return currUserGood;
    }

    public void setCurrUserGood(int currUserGood) {
        this.currUserGood = currUserGood;
    }

    public double getDis() {
        return dis;
    }

    public void setDis(double dis) {
        this.dis = dis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getNewImg() {
        return newImg;
    }

    public void setNewImg(String newImg) {
        this.newImg = newImg;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public String getLinkurl() {
        return linkurl;
    }

    // 头像
    public String getHead() {
        return XUtils.getImagePath(imgUrl);
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public int getIs_tj() {
        return is_tj;
    }

    public void setIs_tj(int is_tj) {
        this.is_tj = is_tj;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getAdd_userid() {
        return add_userid;
    }

    public void setAdd_userid(int add_userid) {
        this.add_userid = add_userid;
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

    public int getRes_views() {
        return res_views;
    }

    public void setRes_views(int res_views) {
        this.res_views = res_views;
    }

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInfo() {
        String time = getAddTime().replace("/", "-");
        return TimeUtils.getFriendlyTimeSpanByNow(time) + "之前~" + getDistance();
    }


    public Circle() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.pageName);
        dest.writeInt(this.newsType);
        dest.writeString(this.newImg);
        dest.writeString(this.newsContent);
        dest.writeString(this.keyword);
        dest.writeString(this.newsDesc);
        dest.writeString(this.linkurl);
        dest.writeInt(this.is_tj);
        dest.writeInt(this.is_hot);
        dest.writeInt(this.ordernum);
        dest.writeString(this.addTime);
        dest.writeInt(this.add_userid);
        dest.writeString(this.relName);
        dest.writeString(this.sex);
        dest.writeString(this.imgUrl);
        dest.writeString(this.age);
        dest.writeInt(this.res_views);
        dest.writeInt(this.areaid);
        dest.writeString(this.sid);
        dest.writeString(this.releaseTime);
        dest.writeString(this.author);
        dest.writeDouble(this.dis);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.currUserGood);
        dest.writeByte(this.isThumb ? (byte) 1 : (byte) 0);
    }

    protected Circle(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.pageName = in.readString();
        this.newsType = in.readInt();
        this.newImg = in.readString();
        this.newsContent = in.readString();
        this.keyword = in.readString();
        this.newsDesc = in.readString();
        this.linkurl = in.readString();
        this.is_tj = in.readInt();
        this.is_hot = in.readInt();
        this.ordernum = in.readInt();
        this.addTime = in.readString();
        this.add_userid = in.readInt();
        this.relName = in.readString();
        this.sex = in.readString();
        this.imgUrl = in.readString();
        this.age = in.readString();
        this.res_views = in.readInt();
        this.areaid = in.readInt();
        this.sid = in.readString();
        this.releaseTime = in.readString();
        this.author = in.readString();
        this.dis = in.readDouble();
        this.commentCount = in.readInt();
        this.currUserGood = in.readInt();
        this.isThumb = in.readByte() != 0;
    }

    public static final Creator<Circle> CREATOR = new Creator<Circle>() {
        @Override
        public Circle createFromParcel(Parcel source) {
            return new Circle(source);
        }

        @Override
        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };

    public String getVideo() {
        return XUtils.getImagePath(linkurl);
    }
}
