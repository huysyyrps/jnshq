package com.sdjnshq.circle.data.http.api;

import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.data.bean.BasePrice;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.Comment;
import com.sdjnshq.circle.data.bean.LoginUser;
import com.sdjnshq.circle.data.bean.MessageCount;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.Order;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.PayInfo;
import com.sdjnshq.circle.data.bean.Protocol;
import com.sdjnshq.circle.data.bean.Rank;
import com.sdjnshq.circle.data.bean.SystemInfo;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.data.http.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by lixiaoqi on 2018/8/30.
 */

public interface API {
    String HOST = "http://www.sdjnshq.com/";

    @GET("getmobilecode.aspx")
    Observable<BaseResponse<String>> sendMobileCode(@Query("mobile") String mobile);

    //登录验证码
    @GET("getmobilecode.aspx")
    Observable<BaseResponse<String>> sendLoginMobileCode(@Query("mobile") String mobile, @Query("isLoginPage") int isLoginPage);

    // 登录 loginType 1密码登录 2验证码登录  allowPosition 1 允许  2拒绝
    @GET("login.aspx")
    Observable<BaseResponse<LoginUser>> login(@Query("mobile") String mobile, @Query("password") String pwd, @Query("mcode") String mcode, @Query("loginType") int loginType, @Query("allowPosition") int allowPosition, @Query("longitude_latiude") String longitude_latiude);

    // 注册
    @GET("reg.aspx")
    Observable<BaseResponse<LoginUser>> register(@Query("mobile") String mobile, @Query("password") String pwd, @Query("mcode") String mcode, @Query("tjcode") String tjcode);

    // 找回密码
    @GET("setpass.aspx")
    Observable<BaseResponse<String>> reFoundPwd(@Query("mobile") String mobile, @Query("password") String pwd, @Query("mcode") String mcode);

    @POST("dynamicadd.aspx")
    @Multipart
    Observable<BaseResponse<String>> circleCreate(@PartMap Map<String, RequestBody> files);

    // 动态列表
    @GET("getdynamicinfo.aspx")
    Observable<BaseResponse<PageList<Circle>>> getDynamicList(@Query("userId") String userId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize, @Query("latitude") double latitude, @Query("longitude") double longitude);


    // 附近动态列表
    @GET("getneardynamic.aspx")
    Observable<BaseResponse<PageList<Circle>>> getNearDynamicList(@Query("userId") String userId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize, @Query("latitude") double latitude, @Query("longitude") double longitude);


    // 获取用户列表信息
    @GET("getuserinfo.aspx")
    Observable<BaseResponse<PageList<User>>> getUserInfoList(@Query("userId") String userId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    // 获取用户信息
    @GET("getUserInfoModel.aspx")
    Observable<BaseResponse<User>> getUserInfo(@Query("userId") String userId, @Query("latitude") double latitude, @Query("longitude") double longitude);

    //修改用户信息
    @POST("userinfoedit.aspx")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse<String>> editUserInfo(@Field("userId") String userId, @Field("nicheng") String nick, @Field("birthday") String birthday, @Field("sexType") String sexType, @Field("marrige") String emotion,
                                                  @Field("hometown") String hometown, @Field("jobName") String jobName, @Field("signName") String signName, @FieldMap Map<String, String> imgs);

    // 完善信息
    @POST("userinfoedit.aspx")
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse<String>> improveDate(@Field("userId") String userId, @Field("nicheng") String nick, @Field("sexType") String sexType, @FieldMap Map<String, String> imgs, @Field("birthday") String birthday, @Field("signName") String signName);

    // 是否允许定位：allowPosition（1是，2否）
    // 定位
    @GET("UpdatePostion.aspx")
    Observable<BaseResponse<String>> updateLocation(@Query("userId") String userId, @Query("allowPosition") int allowPosition, @Query("latitude") double latitude, @Query("longitude") double longitud, @Query("poiName") String poiName);

    // 附近的人
    @GET("GetNearUser.aspx")
    Observable<BaseResponse<PageList<Near>>> getNearUser(@Query("userId") String userId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize, @Query("latitude") double latitude, @Query("longitude") double longitude);

    // 首页banner
    @GET("getBannerInfo.aspx")
    Observable<BaseResponse<PageList<BannerInfo>>> getBanner();

    // 评论
    @GET("commentAdd.aspx")
    Observable<BaseResponse<String>> commentAdd(@Query("userId") String userId, @Query("resourceId") String resourceId, @Query("commentContent") String content);

    // 动态点赞 1是动态 2是评论
    @GET("upResource.aspx")
    Observable<BaseResponse<String>> thumbUp(@Query("userId") String userId, @Query("doneType") int doneType, @Query("resourceId") String resourceId, @Query("resourceType") int type);

    // 动态评论列表
    @GET("GetCommentInfo.aspx")
    Observable<BaseResponse<PageList<Comment>>> commentList(@Query("resourceId") String resourceId);


    // 基本信息
    @GET("GetBasePrice.aspx")
    Observable<BaseResponse<PageList<BasePrice>>> getBasePrice();

    @GET("GetRanking.aspx")
    Observable<BaseResponse<PageList<Rank>>> getInviteRank();

    @GET("DelUserHeadImg.aspx")
    Observable<BaseResponse<String>> removeHeadImg(@Query("userId") String userId, @Query("imgId") String imgId);

    @GET("SearchUserInfo.aspx")
    Observable<BaseResponse<PageList<User>>> searchUser(@Query("mobile") String mobile);

    @GET("DynamicAdd.aspx")
    Observable<BaseResponse<String>> circleDelete(@Query("dynamicId") int id, @Query("doneType") int doneType);

    @GET("MemOrder.aspx")
    Observable<BaseResponse<PageList<Order>>> createOrder(@Query("userId") String userId);

    @GET("UpdateHeadImgOrder.aspx")
    Observable<BaseResponse<String>> updateHeadImgOrder(@Query("userId") String userId, @Query("imgId") String imgId, @Query("orderNum") String orderNum);

    @GET("VisitMessageAdd.aspx")
    Observable<BaseResponse<String>> visitMessageAdd(@Query("userId") String userId, @Query("resourceUserId") String resourceUserId);

    @GET("GetVisitInfo.aspx")
    Observable<BaseResponse<PageList<Visitor>>> getVisitInfo(@Query("userId") String userId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);//访问

    // 1用户协议2隐私协议
    @GET("GetProtocol.aspx")
    Observable<BaseResponse<PageList<Protocol>>> getProtocol(@Query("protocolid") String protocolid);

    // 是否已经注册
    @GET("IsExistsMobile.aspx")
    Observable<BaseResponse> isRegisted(@Query("mobile") String mobile);

    // 未读消息
    @GET("GetNewMessageCount.aspx")
    Observable<BaseResponse<MessageCount>> getMessageCount(@Query("userId") String userId);

    @GET("GetSysMessageInfo.aspx")
    Observable<BaseResponse<PageList<SystemInfo>>> getSystemInfo(@Query("userId") String userId, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);//访问

    // 信息是否完整
    @GET("IsFullInfo.aspx")
    Observable<BaseResponse> isFullInfo(@Query("mobile") String mobile);

    // 信息是否完整
    @GET("IsPaySuccess.aspx")
    Observable<BaseResponse> isPaySuccess(@Query("orderNo") String orderNo);

    // 信息是否完整
    @GET("OrderPay.aspx")
    Observable<BaseResponse<PayInfo>> orderPay(@Query("userId") String userId, @Query("orderId") String orderId, @Query("payType") int payType);

    // 是否是代言人
    @GET("IsValid.aspx")
    Observable<BaseResponse> isValid(@Query("userId") String userId);

    // 余额信息
    @GET("GetUserMoneyTotal.aspx")
    Observable<BaseResponse<Double>> getMoneyInfo(@Query("userId") String userId, @Query("moneyType") int moneyType);

    // 邀请
    @GET("GetUserFans.aspx")
    Observable<BaseResponse<PageList<User>>> getUserFans(@Query("userId") String userId, @Query("fansType") int fansType, @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

}
