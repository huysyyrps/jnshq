package com.sdjnshq.circle.data.repository;

import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.data.bean.BasePrice;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.Rank;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.data.config.Configs;
import com.sdjnshq.circle.data.http.BaseResponse;
import com.sdjnshq.circle.utils.AppSP;

import io.reactivex.Observable;

// 邀请、提现
public class MoneyRepository extends BaseRepository {

    private static final MoneyRepository instance = new MoneyRepository();

    public static MoneyRepository getInstance() {
        return instance;
    }


    // 获取邀请排行
    public Observable<BaseResponse<PageList<Rank>>> getInviteRank() {
        return apiService.getInviteRank();
    }

    // 首页banner
    public Observable<BaseResponse<PageList<BannerInfo>>> getBanner() {
        return apiService.getBanner();
    }

    // 基础价格
    public Observable<BaseResponse<PageList<BasePrice>>> getBasePrice() {
        return apiService.getBasePrice();
    }

    // 余额
    public Observable<BaseResponse<Double>> getBalance() {
        return apiService.getMoneyInfo(AppSP.getInstance().getUserId(), 1);
    }

    // 我的邀请
    public Observable<BaseResponse<PageList<User>>> getMyInvite(int page) {
        return apiService.getUserFans(AppSP.getInstance().getUserId(), 1, page, 10);
    }

    // 二级邀请
    public Observable<BaseResponse<PageList<User>>> getTwoInvite(int page) {
        return apiService.getUserFans(AppSP.getInstance().getUserId(), 2, page, 10);
    }
}
