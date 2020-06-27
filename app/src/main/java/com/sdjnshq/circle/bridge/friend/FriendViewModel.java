package com.sdjnshq.circle.bridge.friend;

import androidx.lifecycle.MutableLiveData;

import com.sdjnshq.circle.bridge.BaseViewModel;
import com.sdjnshq.circle.data.bean.AddFriend;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.UserRepository;

import java.util.List;

public class FriendViewModel extends BaseViewModel {
    private UserRepository mUserRepository;
    public MutableLiveData<List<AddFriend>> addFriendLive;
    public MutableLiveData<Integer> agreeFriendLive;
    public MutableLiveData<List<User>> myFriendLive;
    public MutableLiveData<List<User>> searchUserLive;
    private int page = 1;

    public FriendViewModel() {
        mUserRepository = new UserRepository();
        addFriendLive = new MutableLiveData<>();
        agreeFriendLive = new MutableLiveData<>();
        myFriendLive = new MutableLiveData<>();
        searchUserLive = new MutableLiveData<>();
    }

    //好友请求列表
    public void addFriendList() {
//        RetrofitUtil.execute(mUserRepository.addFriendList(page), new SObserver<PageList<AddFriend>>() {
//            @Override
//            public void onSuccess(PageList<AddFriend> pageList) {
//                addFriendLive.postValue(pageList.getCurrentPageData());
//            }
//        });
    }

    public void agreeAdd(int id, int position) {
//        RetrofitUtil.execute(mUserRepository.agressFriend(id), new SObserver<String>() {
//            @Override
//            public void onSuccess(String pageList) {
//                agreeFriendLive.postValue(position);
//            }
//        });
    }

    public void getFriendList() {
//        RetrofitUtil.execute(mUserRepository.getFriendList(page), new SObserver<PageList<User>>() {
//            @Override
//            public void onSuccess(PageList<User> pageList) {
//                myFriendLive.postValue(pageList.getCurrentPageData());
//            }
//        });
    }

    // 根据手机号搜索
    public void searchUser(String mobile){
        RetrofitUtil.execute(mUserRepository.searchUser(mobile), new SObserver<PageList<User>>() {
            @Override
            public void onSuccess(PageList<User> pageList) {
                searchUserLive.postValue(pageList.getCurrentPageData());
            }
        });
    }

}
