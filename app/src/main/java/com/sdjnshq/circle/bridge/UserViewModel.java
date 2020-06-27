package com.sdjnshq.circle.bridge;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.sdjnshq.circle.data.bean.Circle;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.SystemInfo;
import com.sdjnshq.circle.data.bean.User;
import com.sdjnshq.circle.data.bean.Visitor;
import com.sdjnshq.circle.data.http.ExceptionHandle;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.UserRepository;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.EncodeUtils;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendAllowType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserViewModel extends BaseViewModel {
    public static final int EDIT_SUCCESS = 1;
    private static final String TAG = "USER_VIEW_MODEL";
    private User user;
    private UserRepository mUserRepository;
    private MutableLiveData<Integer> status;
    MutableLiveData<List<Circle>> circleListLive;
    MutableLiveData<List<User>> searchUserLive;
    public MutableLiveData<List<Visitor>> visitorLive;
    public MutableLiveData<List<SystemInfo>> systemLive;
    public MutableLiveData<Integer> deleteCircleLive;
    public MutableLiveData<Boolean> vipLive;


    private boolean isNeedUpdateImUserInfo = true;
    public int circleCurrenPage = 1;
    public int visitorCurrenPage = 1;
    public int systemCurrenPage = 1;
    private String mUserId;

    public UserViewModel() {
        user = new User();
        mUserRepository = new UserRepository();
        circleListLive = new MutableLiveData<>();
        status = new MutableLiveData<>();
        searchUserLive = new MutableLiveData<>();
        deleteCircleLive = new MutableLiveData<>();
        visitorLive = new MutableLiveData<>();
        systemLive = new MutableLiveData<>();
        vipLive = new MutableLiveData<>();
    }


    public MutableLiveData<List<Circle>> getCircleListLive() {
        return circleListLive;
    }

    public void setCircleListLive(MutableLiveData<List<Circle>> circleListLive) {
        this.circleListLive = circleListLive;
    }

    public MutableLiveData<List<User>> getSearchUserLive() {
        return searchUserLive;
    }

    public void setSearchUserLive(MutableLiveData<List<User>> searchUserLive) {
        this.searchUserLive = searchUserLive;
    }

    public User getUserLive() {
        return user;
    }

    public User getUser() {
        return user.getValue();
    }

    public MutableLiveData<Integer> getStatus() {
        return status;
    }

    public void init(String userId) {
        this.mUserId = userId;
        // 创建 网络请求接口 的实例
        RetrofitUtil.execute(mUserRepository.getUserInfo(userId), new SObserver<User>() {
            @Override
            public void onSuccess(User userBaseResponse) {
                user.refresh(userBaseResponse);
                if (mUserId == AppSP.getInstance().getUserId()) {
                    TUIKit.setUserName(userBaseResponse.getNickName());
                    if (isNeedUpdateImUserInfo) {
                        isNeedUpdateImUserInfo = false;
                        updateIMUserInfo(user.getValue());
                    }
                }
            }
        });
    }

    public void isVIP() {
        // 创建 网络请求接口 的实例
        RetrofitUtil.execute(mUserRepository.isVIP(), new SObserver(false) {
            @Override
            public void onSuccess(Object obj) {
                AppSP.getInstance().put("isVIP", false);
                vipLive.postValue(false);
            }

            @Override
            public void onError(ExceptionHandle.ResponseThrowable e) {
                if (e.getStatus().equals("0")) {
                    AppSP.getInstance().put("isVIP", true);
                    vipLive.postValue(true);
                } else {
                    AppSP.getInstance().put("isVIP", false);
                    vipLive.postValue(false);
                }
            }
        });
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public void editUser(List<MediaInfo> mediaInfos, User user,boolean isAuto) {
        // 上传照片
        Map<String, String> imgs = new HashMap<>();
        for (int i = 0; i < mediaInfos.size(); i++) {
            if (!TextUtils.isEmpty(mediaInfos.get(i).getPath())) {
                imgs.put("imgUrl" + (i + 1), EncodeUtils.encodeImageFile(mediaInfos.get(i).getPath()));
            }
        }

        RetrofitUtil.execute(mUserRepository.editUserInfo(user, imgs), new SObserver<String>() {
            @Override
            public void onSuccess(String o) {
                if(!isAuto) {
                    status.postValue(EDIT_SUCCESS);
                }
                isNeedUpdateImUserInfo = true;
                init(user.getValue().getId());
            }
        });
    }

    public void initCircleList() {
        RetrofitUtil.execute(mUserRepository.getDynamicList(mUserId, circleCurrenPage), new SObserver<PageList<Circle>>() {
            @Override
            public void onSuccess(PageList<Circle> pageList) {
                if (circleCurrenPage == 1) {
                    circleListLive.setValue(new ArrayList<>());
                }
                if (pageList.getCurrentPageData() != null && pageList.getCurrentPageData().size() != 0) {
                    circleListLive.getValue().addAll(pageList.getCurrentPageData());
                    circleListLive.setValue(circleListLive.getValue());
                    circleCurrenPage++;
                } else {
                    circleListLive.setValue(new ArrayList<>());
                }
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }


    public void removeHead(String imgId) {
        if (TextUtils.isEmpty(imgId)) {
            return;
        }
        RetrofitUtil.execute(mUserRepository.removeHeadImg(imgId), new SObserver<String>() {
            @Override
            public void onSuccess(String o) {

            }
        });
    }


    private void updateIMUserInfo(User user) {
        HashMap<String, Object> hashMap = new HashMap<>();

        // 头像
        if (!TextUtils.isEmpty(user.getHead())) {
            hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_FACEURL, user.getHead());
        }

        // 昵称
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_NICK, user.getNickName());

        // 个性签名
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_SELFSIGNATURE, user.getSign());

        // 地区
//        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_LOCATION, "sz");

        // 加我验证方式-需要验证
        String allowType = TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM;
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_ALLOWTYPE, allowType);

        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess() {
                DemoLog.i(TAG, "modifySelfProfile success");
            }
        });
    }

    // 根据手机号搜索
    public void searchUser(String mobile) {
        RetrofitUtil.execute(mUserRepository.searchUser(mobile), new SObserver<PageList<User>>() {
            @Override
            public void onSuccess(PageList<User> pageList) {
                searchUserLive.postValue(pageList.getCurrentPageData());
            }
        });
    }

    // 删除动态
    public void deleteCircle(int id, int position) {
        RetrofitUtil.execute(mUserRepository.circleDelete(id), new SObserver<String>() {
            @Override
            public void onSuccess(String pageList) {
                deleteCircleLive.postValue(position);
            }
        });
    }


    //更换头像顺序
    public void updateHeadImgOrder(String imgId, String orderNum) {
        RetrofitUtil.execute(mUserRepository.updateUserHeadOrder(imgId, orderNum), new SObserver() {
            @Override
            public void onSuccess(Object o) {

            }
        });
    }

    // 访问
    public void visitAdd(String userId) {
        RetrofitUtil.execute(mUserRepository.visitAdd(userId), new SObserver() {
            @Override
            public void onSuccess(Object o) {

            }
        });
    }

    // 访问
    public void visitInfo() {
        RetrofitUtil.execute(mUserRepository.visitInfo(visitorCurrenPage), new SObserver<PageList<Visitor>>() {
            @Override
            public void onSuccess(PageList<Visitor> pageList) {
                if (visitorCurrenPage == 1) {
                    visitorLive.setValue(new ArrayList<>());
                }
                if (pageList.getCurrentPageData() != null && pageList.getCurrentPageData().size() != 0) {
                    visitorLive.getValue().addAll(pageList.getCurrentPageData());
                    visitorLive.setValue(visitorLive.getValue());
                    visitorCurrenPage++;
                } else {
                    visitorLive.setValue(new ArrayList<>());
                }
            }
        });
    }

    // 系统消息
    public void systemInfo() {
        RetrofitUtil.execute(mUserRepository.getSystemInfo(visitorCurrenPage), new SObserver<PageList<SystemInfo>>() {
            @Override
            public void onSuccess(PageList<SystemInfo> pageList) {
                if (systemCurrenPage == 1) {
                    systemLive.setValue(new ArrayList<>());
                }
                if (pageList.getCurrentPageData() != null && pageList.getCurrentPageData().size() != 0) {
                    systemLive.getValue().addAll(pageList.getCurrentPageData());
                    systemLive.setValue(systemLive.getValue());
                    systemCurrenPage++;
                } else {
                    systemLive.setValue(new ArrayList<>());
                }
            }
        });
    }


}
