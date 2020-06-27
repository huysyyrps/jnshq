package com.sdjnshq.circle.bridge.login;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.sdjnshq.circle.CircleApplication;
import com.sdjnshq.circle.bridge.BaseViewModel;
import com.sdjnshq.circle.data.bean.LoginUser;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.data.http.ExceptionHandle;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.LoginRepository;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.EncodeUtils;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginViewModel extends BaseViewModel {
    private MutableLiveData<String> codeLive;
    private MutableLiveData<String> registerLive;
    private MutableLiveData<String> mobileLive;
    public static final int USER_INFO = 100;

    LoginRepository mLoginRepository;

    public LoginViewModel() {
        mLoginRepository = new LoginRepository();
        codeLive = new MutableLiveData<>();
        registerLive = new MutableLiveData<>();
        mobileLive = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMobiileLive() {
        return mobileLive;
    }

    public MutableLiveData<String> getCodeLive() {
        return codeLive;
    }

    public MutableLiveData<String> getRegisterive() {
        return registerLive;
    }

    // 注册
    public void register(String moblie, String password, String code, String tjcode) {
        RetrofitUtil.execute(mLoginRepository.register(moblie, password, code, tjcode), new SObserver<LoginUser>() {
            @Override
            public void onSuccess(LoginUser user) {
                AppSP.getInstance().login(user);
                getPagePostLive().setValue(new PagePost(USER_INFO));
            }

            @Override
            public void onError(String message) {
                Log.e("123", "出错了" + message);
                errorToast(message);
            }
        });
    }

    // 找回密码
    public void reFoundPwd(String moblie, String password, String code) {
        RetrofitUtil.execute(mLoginRepository.reFoundPwd(moblie, password, code), new SObserver<String>() {
            @Override
            public void onSuccess(String value) {
                getPagePostLive().setValue(new PagePost(PagePost.REFOUND_MOBILE_SUC));
            }

            @Override
            public void onError(String message) {
                errorToast(message);
            }
        });
    }

    public void loginByPwd(String mobile, String pwd) {
        RetrofitUtil.execute(mLoginRepository.loginByPwd(mobile, pwd), new SObserver<LoginUser>() {
            @Override
            public void onSuccess(LoginUser user) {
                AppSP.getInstance().login(user);
                loginTIM(mobile, user.getId());
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }

    // 验证码登录
    public void loginByCode(String code) {
        RetrofitUtil.execute(mLoginRepository.isRegisted(mobileLive.getValue()), new SObserver() {
            @Override
            public void onSuccess(Object s) {
                ToastUtil.toastLongMessage("您的手机号未注册");
            }

            @Override
            public void onError(String message) {
                super.onError(message);
                RetrofitUtil.execute(mLoginRepository.loginByCode(mobileLive.getValue(), code), new SObserver<LoginUser>() {
                    @Override
                    public void onSuccess(LoginUser user) {
                        AppSP.getInstance().login(user);
                        loginTIM(mobileLive.getValue(), user.getId());
                    }

                    @Override
                    public void onError(String message) {
                        super.onError(message);
                    }
                });
            }
        });
    }

    private void loginTIM(String mobile, String uid) {
        AppSP.getInstance().put("mobile", mobile);
        // 获取userSig函数
        String userSig = GenerateTestUserSig.genTestUserSig(uid);
        TUIKit.login(uid, userSig, new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {
                CircleApplication.getInstance().showToast("登录失败");
            }

            @Override
            public void onSuccess(Object data) {
                AppSP.getInstance().loginIM();
                if (mobile != null) {
                    RetrofitUtil.execute(mLoginRepository.isFullInfo(mobile), new SObserver() {

                        @Override
                        public void onSuccess(Object object) {
                            getPagePostLive().setValue(new PagePost(PagePost.PUSH_ACTIVITY));
                        }

                        @Override
                        public void onError(String message) {
                            super.onError(message);
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponseThrowable e) {
                            getPagePostLive().setValue(new PagePost(LoginViewModel.USER_INFO));
                        }
                    });
                } else {
                    getPagePostLive().setValue(new PagePost(PagePost.PUSH_ACTIVITY));
                }
            }
        });
    }

    // 注册验证码
    public void sendCode(String mobile) {
//        RetrofitUtil.execute(mLoginRepository.isRegisted(mobile), new SObserver() {
//            @Override
//            public void onSuccess(Object s) {
        RetrofitUtil.execute(mLoginRepository.sendMobileCode(mobile, false), new SObserver<String>() {
            @Override
            public void onSuccess(String s) {
                codeLive.setValue("registerCode");
            }


        });
//            }
//        });
    }

    public void sendCodeFound(String mobile) {
        RetrofitUtil.execute(mLoginRepository.sendMobileCode(mobile, false), new SObserver<String>() {
            @Override
            public void onSuccess(String s) {
                codeLive.setValue("registerCode");
            }
        });
    }

    public void sendLoginCode(String mobile) {
        RetrofitUtil.execute(mLoginRepository.sendMobileCode(mobile, true), new SObserver<String>() {
            @Override
            public void onSuccess(String s) {
                codeLive.setValue("loginCode");
            }

            @Override
            public void onError(ExceptionHandle.ResponseThrowable e) {
                //未注册
                if (e.getStatus().equals("2")) {
                    mobileLive.setValue("register");
                }
            }
        });
    }

    // 完善信息
    public void improveDate(List<MediaInfo> mediaInfos, String name, boolean isMale, String birthday, String sign) {
        // 上传照片
        Map<String, String> imgs = new HashMap<>();
        for (int i = 0; i < mediaInfos.size(); i++) {
            if (!TextUtils.isEmpty(mediaInfos.get(i).getPath())) {
                imgs.put("imgUrl" + (i + 1), EncodeUtils.encodeImageFile(mediaInfos.get(i).getPath()));
            }
        }

        RetrofitUtil.execute(mLoginRepository.improveDate(AppSP.getInstance().getUserId(), name, isMale ? "1" : "2", imgs, birthday, sign), new SObserver<String>() {
            @Override
            public void onSuccess(String o) {
                loginTIM(null, String.valueOf(AppSP.getInstance().getUserId()));
            }
        });
    }

}
