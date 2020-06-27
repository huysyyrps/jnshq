package com.sdjnshq.circle.bridge;

import androidx.lifecycle.MutableLiveData;
import com.sdjnshq.circle.data.bean.Order;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.data.bean.PayInfo;
import com.sdjnshq.circle.data.http.ExceptionHandle;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.UserRepository;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

public class OrderViewModel extends BaseViewModel {
    private UserRepository mUserRepository;

    public MutableLiveData<Order> orderLive;
    public MutableLiveData<PayInfo> payLive;

    public OrderViewModel() {
        mUserRepository = new UserRepository();
        orderLive = new MutableLiveData<>();
        payLive = new MutableLiveData<>();
    }


    // 支付
    public void createOrder() {
        RetrofitUtil.execute(mUserRepository.createOrder(), new SObserver<PageList<Order>>() {
            @Override
            public void onSuccess(PageList<Order> data) {
                orderLive.postValue(data.getCurrentPageData().get(0));
            }
        });
    }

    // 支付
    public void paySuccess(String orderNo) {
        RetrofitUtil.execute(mUserRepository.paySucdess(orderNo), new SObserver() {
            @Override
            public void onSuccess(Object obj) {
                ToastUtil.toastShortMessage("订单不存在");
            }

            @Override
            public void onError(ExceptionHandle.ResponseThrowable e) {
                super.onError(e);
                getPagePostLive().postValue(new PagePost(0, e.getStatus()));
            }
        });
    }

    // 支付
    public void pay(String orderNo, boolean isWx) {
        RetrofitUtil.execute(mUserRepository.pay(orderNo, isWx ? 1 : 2), new SObserver<PayInfo>() {
            @Override
            public void onSuccess(PayInfo payInfo) {
                payLive.postValue(payInfo);
            }

            @Override
            public void onError(ExceptionHandle.ResponseThrowable e) {
                super.onError(e);
                getPagePostLive().postValue(new PagePost(0, e.getStatus()));
            }
        });
    }
}
