package com.sdjnshq.circle.bridge;

import androidx.lifecycle.MutableLiveData;

import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.data.bean.BasePrice;
import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.MessageCount;
import com.sdjnshq.circle.data.bean.Near;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.HomeRepository;
import com.sdjnshq.circle.utils.AppSP;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends BaseViewModel {
    public int circleCurrenPage = 1;
    public int nearCurrentPage = 1;
    MutableLiveData<List<Circle>> circleListLive;
    MutableLiveData<List<Near>> nearListLive;
    MutableLiveData<List<BannerInfo>> bannerListLive;
    public MutableLiveData<MessageCount> messageCountListLive;
    HomeRepository mHomeRepository;

    public HomeViewModel() {
        this.circleListLive = new MutableLiveData<>();
        this.nearListLive = new MutableLiveData<>();
        this.bannerListLive = new MutableLiveData<>();
        this.mHomeRepository = new HomeRepository();
        this.messageCountListLive = new MutableLiveData<>();
    }

    public MutableLiveData<List<BannerInfo>> getBannerListLive() {
        return bannerListLive;
    }

    public MutableLiveData<List<Circle>> getCircleListLive() {
        return circleListLive;
    }

    public MutableLiveData<List<Near>> getNearListLive() {
        return nearListLive;
    }

    public void getBanner() {
        RetrofitUtil.execute(mHomeRepository.getBanner(), new SObserver<PageList<BannerInfo>>() {
            @Override
            public void onSuccess(PageList<BannerInfo> pageList) {
                bannerListLive.setValue(pageList.getCurrentPageData());
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }

    public void refreshCircleList() {
        circleCurrenPage = 1;
        getCircleList();
    }

    public void getCircleList() {
        if (AppSP.getInstance().getLat() == 0) {
            return;
        }
        RetrofitUtil.execute(mHomeRepository.getDynamicList(AppSP.getInstance().getUserId(), circleCurrenPage), new SObserver<PageList<Circle>>() {
            @Override
            public void onSuccess(PageList<Circle> pageList) {
                circleListLive.setValue(pageList.getCurrentPageData());
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }

    public void getNearList() {
        if (AppSP.getInstance().getLat() == 0) {
            return;
        }
        RetrofitUtil.execute(mHomeRepository.getNearUser(AppSP.getInstance().getUserId(), nearCurrentPage, AppSP.getInstance().getLat(), AppSP.getInstance().getLon()),
                new SObserver<PageList<Near>>() {
                    @Override
                    public void onSuccess(PageList<Near> pageList) {
                        nearListLive.setValue(pageList.getCurrentPageData());
                        nearCurrentPage++;
                    }

                    @Override
                    public void onError(String message) {
                        super.onError(message);
                    }
                });
    }


    // 未读消息数量
    public void getMessageCount() {
        RetrofitUtil.execute(mHomeRepository.getMessageCount(),
                new SObserver<MessageCount>() {
                    @Override
                    public void onSuccess(MessageCount messageCount) {
                        messageCountListLive.postValue(messageCount);
                    }

                });
    }
}
