package com.sdjnshq.circle.bridge.circle;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.UriUtils;
import com.sdjnshq.circle.bridge.BaseViewModel;
import com.sdjnshq.circle.data.bean.Comment;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.data.config.Configs;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.data.repository.CircleRepository;
import com.sdjnshq.circle.utils.EncodeUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;

import java.util.List;

public class CircleViewModel extends BaseViewModel {
    public static final int THUMB_UP = 999;
    public static final int THUMB_UP_NO = 998;
    MutableLiveData<List> commentLiveData;
    CircleRepository mCircleRepository;

    public int page;


    public CircleViewModel() {
        mCircleRepository = new CircleRepository();
        commentLiveData = new MutableLiveData<>();
        page = 1;
    }

    public MutableLiveData<List> getCommentLiveData() {
        return commentLiveData;
    }

    public void setCommentLiveData(MutableLiveData<List> commentLiveData) {
        this.commentLiveData = commentLiveData;
    }

    public void comment(String resourceId, String content) {
        RetrofitUtil.execute(mCircleRepository.comment(resourceId, content), new SObserver<String>() {
            @Override
            public void onSuccess(String stringBaseResponse) {
                getPagePostLive().postValue(PagePost.sucess());
            }
        });
    }

    public void commentList(String resourceId) {
        RetrofitUtil.execute(mCircleRepository.commentList(resourceId), new SObserver<PageList<Comment>>() {
            @Override
            public void onSuccess(PageList<Comment> commentPageList) {
                commentLiveData.setValue(commentPageList.getCurrentPageData());
            }
        });
    }

    // 点赞
    public void thumbUp(String resourceId, boolean isThumb) {
        RetrofitUtil.execute(mCircleRepository.thumbUp(resourceId, isThumb), new SObserver<String>() {
            @Override
            public void onSuccess(String data) {
                getPagePostLive().postValue(new PagePost(data.equals("exists") ? THUMB_UP : THUMB_UP_NO));
            }
        });
    }


    //    token：token
//    信息类型：infoType（注：1图片类型，2视频类型）
//    操作类型：doneType （注：1添加，2修改，3删除）
//    动态信息ID：dynamicId
//    内容：dynamicContent
//    位置名称：placeName
//    图片信息：imgStr（注：多个图片用逗号【,】隔开,图片格式：data:image/png;base64）
//    视频参数：videoFile

    public void create(String content, List<MediaInfo> imageList, String video, String placeName, double lat, double lon) {
        int infoType = Configs.CIRCLE_INFO_TYPE_IMAGE;
        if (video != null) {
            infoType = Configs.CIRCLE_INFO_TYPE_VEDIO;
        }
        StringBuffer imgStr = new StringBuffer();
        if (imageList != null && imageList.size() > 0) {
            imgStr = new StringBuffer("");
            for (int i = 0; i < imageList.size(); i++) {
                if (i > 0) {
                    imgStr.append(',');
                }
                String path;
                if (SdkVersionUtils.checkedAndroid_Q() && !imageList.get(i).isCompress()) {
                    path = UriUtils.uri2File(Uri.parse(imageList.get(i).getPath())).getAbsolutePath();
                } else {
                    path = imageList.get(i).getPath();
                }
                imgStr.append(EncodeUtils.encodeImageFile(path));
            }
        }
        RetrofitUtil.execute(mCircleRepository.createCircle(Configs.CIRCLE_DONE_TYPE_ADD, infoType, content, imgStr.toString(), video, placeName, lat, lon), new SObserver<String>() {
            @Override
            public void onSuccess(String stringBaseResponse) {
                getPagePostLive().postValue(PagePost.sucess());
            }
        });
    }


    public void refreshComment(String resourceId) {
        page = 1;
        commentList(resourceId);
    }
}
