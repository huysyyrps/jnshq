package com.sdjnshq.circle.data.repository;

import android.text.TextUtils;

import com.sdjnshq.circle.data.bean.Comment;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.http.BaseResponse;
import com.sdjnshq.circle.utils.AppSP;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CircleRepository extends BaseRepository {

    // 创建动态
    public Observable<BaseResponse<String>> createCircle(int doneType, int infoType, String dynamicContent, String imgStr, String video, String placeName, double lat, double lon) {

        Map<String, RequestBody> map = new HashMap<>();
        map.put("userId", toRequestBody(AppSP.getInstance().getUserId() + ""));
        map.put("doneType", toRequestBody(doneType + ""));
        map.put("infoType", toRequestBody(infoType + ""));
        map.put("dynamicContent", toRequestBody(dynamicContent + ""));
        map.put("imgStr", toRequestBody(imgStr + ""));
        map.put("placeName", toRequestBody(placeName + ""));
        map.put("latitude", toRequestBody(lat + ""));
        map.put("longitude", toRequestBody(lon + ""));
        if (!TextUtils.isEmpty(video)) {
            File file = new File(video);
            map.put(String.format("file\"; filename=\"" + file.getName()), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        return apiService.circleCreate(map);
    }


    private RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    // 评论
    public Observable<BaseResponse<String>> comment(String resourceId, String content) {
        return apiService.commentAdd(AppSP.getInstance().getUserId(), resourceId, content);
    }

    // 评论列表
    public Observable<BaseResponse<PageList<Comment>>> commentList(String resourceId) {
        return apiService.commentList(resourceId);
    }

    // 点赞 动态
    public Observable<BaseResponse<String>> thumbUp(String resourceId, boolean isThumb) {
        return apiService.thumbUp(AppSP.getInstance().getUserId(),isThumb ? 1 : 2, resourceId,1);
    }
}
