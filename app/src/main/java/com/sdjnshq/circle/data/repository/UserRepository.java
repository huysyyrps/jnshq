package com.sdjnshq.circle.data.repository;

import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.Order;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.PayInfo;
import com.sdjnshq.circle.data.bean.SystemInfo;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.data.config.Configs;
import com.sdjnshq.circle.data.http.BaseResponse;
import com.sdjnshq.circle.utils.AppSP;

import java.util.Map;

import io.reactivex.Observable;

public class UserRepository extends BaseRepository {

    public Observable<BaseResponse<User>> getUserInfo(String userId) {
        return apiService.getUserInfo(userId, AppSP.getInstance().getLat(),AppSP.getInstance().getLon());
    }
    public Observable<BaseResponse<String>> editUserInfo(User user, Map<String,String> imgs){
        return  apiService.editUserInfo(user.getId(),user.getNickName(),user.getBirthday(),user.getSex(),user.getEmotion(),user.getHomeTown(),user.getJob(),user.getSign(),imgs);
    }

    // 获取动态
    public Observable<BaseResponse<PageList<Circle>>> getDynamicList(String userId, int currentPage) {
        return apiService.getDynamicList(userId,currentPage, Configs.PAGE_SIZE,AppSP.getInstance().getLat(), AppSP.getInstance().getLon());
    }

    // 移除头像
    public Observable<BaseResponse<String>> removeHeadImg(String imgId) {
        return apiService.removeHeadImg(AppSP.getInstance().getUserId(),imgId);
    }

//    //加好友
//    public Observable<BaseResponse<String>> addFriend(String friend,String remark) {
//        return apiService.addFriend(AppSP.getInstance().getUserId(),friend,remark);
//    }

    //搜索好友
    public Observable<BaseResponse<PageList<User>>> searchUser(String mobile) {
        return apiService.searchUser(mobile);
    }
//
//    //好友请求列表
//    public Observable<BaseResponse<PageList<AddFriend>>> addFriendList(int page) {
//        return apiService.addFriendList(AppSP.getInstance().getUserId(),page,1000);
//    }
//    //审核好友请求
//    public Observable<BaseResponse<String>> agressFriend(int id) {
//        return apiService.agreeFriend(id,1);
//    }
//    //好友列表
//    public Observable<BaseResponse<PageList<User>>> getFriendList(int page) {
//        return apiService.getUserFriend(AppSP.getInstance().getUserId(),page,1000);
//    }
    //删除动态
    public Observable<BaseResponse<String>> circleDelete(int id) {
        return apiService.circleDelete(id,3);
    }
    //pay
    public Observable<BaseResponse<PageList<Order>>> createOrder() {
        return apiService.createOrder(AppSP.getInstance().getUserId());
    }
    //更换头像顺序
    public Observable<BaseResponse<String>> updateUserHeadOrder(String imgId,String orderNum) {
        return apiService.updateHeadImgOrder(AppSP.getInstance().getUserId(),imgId,orderNum);
    }
    //访问
    public Observable<BaseResponse<String>> visitAdd(String userId) {
        return apiService.visitMessageAdd(AppSP.getInstance().getUserId(),userId);
    }
    //访问
    public Observable<BaseResponse<PageList<Visitor>>> visitInfo(int page ) {
        return apiService.getVisitInfo(AppSP.getInstance().getUserId(),page,10);
    }
    //访问
    public Observable<BaseResponse<PageList<SystemInfo>>> getSystemInfo(int page ) {
        return apiService.getSystemInfo(AppSP.getInstance().getUserId(),page,10);
    }

    //pay status
    public Observable<BaseResponse> paySucdess(String orderNo) {
        return apiService.isPaySuccess(orderNo);
    }
    //pay status
    public Observable<BaseResponse<PayInfo>> pay(String orderNo, int type) {
        return apiService.orderPay(AppSP.getInstance().getUserId(),orderNo,type);
    }
    //pay status
    public Observable<BaseResponse> isVIP() {
        return apiService.isValid(AppSP.getInstance().getUserId());
    }


}
