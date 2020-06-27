package com.sdjnshq.circle.bridge.money;

import androidx.lifecycle.MutableLiveData;

import com.sdjnshq.circle.bridge.BaseViewModel;
import com.sdjnshq.circle.data.bean.BannerInfo;
import com.sdjnshq.circle.data.bean.BasePrice;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.Rank;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.MoneyRepository;
import com.sdjnshq.circle.utils.AppSP;

import java.util.ArrayList;
import java.util.List;

public class MoneyViewModel extends BaseViewModel {
    MoneyRepository moneyRepository = new MoneyRepository();
    public MutableLiveData<List<Rank>> rankLive = new MutableLiveData<>();
    public MutableLiveData<String> basePriceLive = new MutableLiveData<>();
    public MutableLiveData<Double> balanceLive = new MutableLiveData<>();
    public MutableLiveData<List<User>> inviteLive = new MutableLiveData<>();
    int invitePage = 1;


    public void initInviteRank() {
        RetrofitUtil.execute(moneyRepository.getInviteRank(), new SObserver<PageList<Rank>>() {
            @Override
            public void onSuccess(PageList<Rank> pageList) {
                rankLive.setValue(pageList.getCurrentPageData());
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }


    public void getBasePrice() {
        RetrofitUtil.execute(moneyRepository.getBasePrice(), new SObserver<PageList<BasePrice>>() {
            @Override
            public void onSuccess(PageList<BasePrice> pageList) {
                AppSP.getInstance().setBasePrice(pageList.getCurrentPageData());
                basePriceLive.postValue("");
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }

    // 可用余额
    public void getBalance() {
        RetrofitUtil.execute(moneyRepository.getBalance(), new SObserver<Double>() {
            @Override
            public void onSuccess(Double balance) {
                balanceLive.postValue(balance);
            }
        });
    }

    // 我的邀请
    public void getInvite() {
        RetrofitUtil.execute(moneyRepository.getMyInvite(invitePage), new SObserver<PageList<User>>() {
            @Override
            public void onSuccess(PageList<User> users) {
                inviteLive.postValue(users.getCurrentPageData());
                invitePage++;
            }
        });
    }
    // 我的邀请二级
    public void getInviteTwo() {
        RetrofitUtil.execute(moneyRepository.getTwoInvite(invitePage), new SObserver<PageList<User>>() {
            @Override
            public void onSuccess(PageList<User> users) {
                inviteLive.postValue(users.getCurrentPageData());
                invitePage++;
            }
        });
    }
}
