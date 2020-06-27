package com.sdjnshq.circle.data.repository;

import com.sdjnshq.circle.data.bean.LoginUser;
import com.sdjnshq.circle.data.config.Configs;
import com.sdjnshq.circle.data.http.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;

public class LoginRepository extends BaseRepository {


    // 发送验证码
    public Observable<BaseResponse<String>> sendMobileCode(String mobile, boolean isLogin) {
        if (isLogin) {
            return apiService.sendLoginMobileCode(mobile, 1);
        } else {
            return apiService.sendMobileCode(mobile);
        }
    }


    public Observable<BaseResponse<LoginUser>> register(String mobile, String password, String mcode, String tjcode) {
        return apiService.register(mobile, password, mcode, tjcode);
    }


    public Observable<BaseResponse<String>> reFoundPwd(String mobile, String password, String mcode) {
        return apiService.reFoundPwd(mobile, password, mcode);
    }

    public Observable<BaseResponse<LoginUser>> loginByCode(String mobile, String mcode) {
        return apiService.login(mobile, null, mcode, Configs.LOGIN_TYPE_CODE, Configs.LOCATION_ALLOW, "");
    }

    public Observable<BaseResponse<LoginUser>> loginByPwd(String mobile, String pwd) {
        return apiService.login(mobile, pwd, null, Configs.LOGIN_TYPE_PWD, Configs.LOCATION_ALLOW, "");
    }

    public Observable<BaseResponse<String>> improveDate(String id,String name,String sexType, Map<String,String> imgs,String birthday,String sign){
        return  apiService.improveDate(id,name,sexType,imgs,birthday,sign);
    }

    public Observable<BaseResponse> isRegisted(String mobile){
        return  apiService.isRegisted(mobile);
    }
    public Observable<BaseResponse> isFullInfo(String mobile){
        return  apiService.isFullInfo(mobile);
    }
}
