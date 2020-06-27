package com.sdjnshq.circle.data.repository;

import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.data.bean.BasePrice;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.MessageCount;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.config.Configs;
import com.sdjnshq.circle.data.http.BaseResponse;
import com.sdjnshq.circle.utils.AppSP;

import io.reactivex.Observable;

public class HomeRepository extends BaseRepository {

    private static  final HomeRepository instance = new HomeRepository();

    public static HomeRepository getInstance(){
        return instance;
    }

    // 获取附近动态
    public Observable<BaseResponse<PageList<Circle>>> getDynamicList(String userId,int currentPage) {
        return apiService.getNearDynamicList(userId,currentPage,Configs.PAGE_SIZE, AppSP.getInstance().getLat(),AppSP.getInstance().getLon());
    }

    // 更新定位
    public Observable<BaseResponse<String>> updateLocation(String userId,int allow,double lat,double lon,String aoiName){
        return apiService.updateLocation(userId,1,lat,lon,aoiName);
    }

    // 附近的人
    public Observable<BaseResponse<PageList<Near>>> getNearUser(String userId,int page , double lat, double lon){
        return apiService.getNearUser(userId,page,10,lat,lon);
    }
    // 首页banner
    public Observable<BaseResponse<PageList<BannerInfo>>> getBanner(){
        return apiService.getBanner();
    }
    // 未读消息
    public Observable<BaseResponse<MessageCount>> getMessageCount(){
        return apiService.getMessageCount(AppSP.getInstance().getUserId());
    }


}
