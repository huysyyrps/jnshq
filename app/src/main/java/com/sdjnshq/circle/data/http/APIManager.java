package com.sdjnshq.circle.data.http;


import com.sdjnshq.circle.data.http.api.API;

/**
 * Created by lixiaoqi on 2018/8/31.
 */

public class APIManager {
    public static API getAPI(){
        return RetrofitUtil.getInstance().createApi(API.class);
    }
}
