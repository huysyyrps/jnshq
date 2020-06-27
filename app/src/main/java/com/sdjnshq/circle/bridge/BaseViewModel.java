package com.sdjnshq.circle.bridge;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sdjnshq.circle.data.bean.PagePost;

public class BaseViewModel extends ViewModel {
    private MutableLiveData<PagePost> pagePostLive;

    public BaseViewModel() {
        this.pagePostLive = new  MutableLiveData<PagePost>();
    }


    public void errorToast(String msg){
        pagePostLive.setValue(PagePost.errorMsg(msg));
    }

    public MutableLiveData<PagePost> getPagePostLive() {
        return pagePostLive;
    }

}
