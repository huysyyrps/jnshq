package com.sdjnshq.circle.ui.page;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;
import com.blankj.utilcode.util.UriUtils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.circle.CircleViewModel;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.ui.adapter.SelectMediaAdapter;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.sdjnshq.circle.ui.page.circle.PoiActivity;
import com.sdjnshq.circle.utils.AppSP;
import com.sdjnshq.circle.utils.FullyGridLayoutManager;
import com.sdjnshq.circle.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.uikit.component.face.Emoji;
import com.tencent.qcloud.tim.uikit.component.face.FaceFragment;
import com.tencent.qcloud.tim.uikit.component.face.FaceManager;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;
import com.tencent.qcloud.tim.uikit.utils.TUIKitLog;
import com.wang.avi.AVLoadingIndicatorView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

public class SendCircleActivity extends BaseActivity {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_media)
    RecyclerView mRVMedia;
    @BindView(R.id.ll_circle_tip)
    LinearLayout llTip;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.jz_video)
    JzvdStd jzvdStd;
    @BindView(R.id.fl_video)
    FrameLayout flVideo;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.f_progress)
    FrameLayout fProgress;
    @BindView(R.id.more_face)
    FrameLayout fFace;


    private CircleViewModel mCircleViewModel;
    private SelectMediaAdapter mMediaAdapter;
    private List<MediaInfo> selectList = new ArrayList();
    private ViewTreeObserver.OnGlobalLayoutListener layoutListener;
    private boolean wasOpened = false;
    String TAG = "123";
    private String path;
    private PoiItem poiItem;
    private FragmentManager mFragmentManager;
    private FaceFragment mFaceFragment;
    private EditText mTextInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_circle);
        ButterKnife.bind(this);
        mTextInput =etContent;

        mCircleViewModel = getActivityViewModelProvider(this).get(CircleViewModel.class);
        mCircleViewModel.getPagePostLive().observe(this, new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if (pagePost.isSucess()) {
                    mSharedViewModel.circleToAddListener.postValue(true);
                    disProgress();
                    finish();
                }
            }
        });
        init();
        View back = titleBar.getLeftTextView();
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disProgress();
                    finish();
                }
            });
        }
    }



    private void showProgress(){
        fProgress.setVisibility(View.VISIBLE);
        avi.show();
    }
    private void disProgress(){
        avi.hide();
        fProgress.setVisibility(View.GONE);
    }

    private void init() {
        tvLocation.setText(AppSP.getInstance().getAoiName());

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        mRVMedia.setLayoutManager(manager);

        mRVMedia.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(this, 4), false));
        SelectMediaAdapter.onAddPicClickListener clickListener = new SelectMediaAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                openGallery();
            }
        };
        mMediaAdapter = new SelectMediaAdapter(selectList, clickListener);
        mMediaAdapter.bindToRecyclerView(mRVMedia);

        // 发布动态
        titleBar.findViewById(R.id.tv_circle_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etContent.getText().toString()) && mMediaAdapter.getData().size() == 0 && TextUtils.isEmpty(path)) {
                    showLongToast("请输入动态内容");
                    return;
                }
                double lat = AppSP.getInstance().getLat();
                double lon = AppSP.getInstance().getLon();
                String location = tvLocation.getText().toString();
                if(location.equals("你在哪里？")){
                    location = "";
                }
                showProgress();
                mCircleViewModel.create(etContent.getText().toString(), mMediaAdapter.getData(), path, location,lat,lon);
            }
        });


    }

    @OnClick({R.id.iv_camera, R.id.iv_pic, R.id.iv_video,R.id.iv_emoji, R.id.iv_video_delete, R.id.ll_location, R.id.iv_location_delete})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_camera:
                openCamera();
                break;
            case R.id.iv_pic:
                openGallery();
                break;
            case R.id.iv_video:
                openVideo();
                break;
            case R.id.iv_video_delete:
                jzvdStd.backPress();
                mRVMedia.setVisibility(View.VISIBLE);
                flVideo.setVisibility(View.GONE);
                break;
            case R.id.ll_location:
                Intent intent = new Intent(this, PoiActivity.class);
                startActivityForResult(intent, 111);
                break;
            case R.id.iv_location_delete:
                tvLocation.setText("你在哪里？");
                poiItem = null;
                break;
            case  R.id.iv_emoji:
                if(fFace.getVisibility() == View.VISIBLE){
                    fFace.setVisibility(View.GONE);
                }else {
                    showFaceViewGroup();
                }
                break;

        }
    }

    private void openGallery() {
        fFace.setVisibility(View.GONE);
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .minimumCompressSize(1024)
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void openCamera() {
        fFace.setVisibility(View.GONE);
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .compress(true)
                .minimumCompressSize(1024)
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    private void openVideo() {
        fFace.setVisibility(View.GONE);
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofVideo())
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .forResult(PictureConfig.PREVIEW_VIDEO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 111:
                    if (data.getParcelableExtra("location") != null) {
                        PoiItem item = data.getParcelableExtra("location");
                        tvLocation.setText(item.getTitle());
                    }
                    break;
                case PictureConfig.CHOOSE_REQUEST:
                case PictureConfig.REQUEST_CAMERA:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media;
                    for (int i = 0; i < localMedia.size(); i++) {
                        media = localMedia.get(i);
                        String path;
                        if (media.isCut() && !media.isCompressed()) {
                            // 裁剪过
                            path = media.getCutPath();
                            mMediaAdapter.addData(MediaInfo.newPathMediaInfo(path));
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            path = media.getCompressPath();
                            mMediaAdapter.addData(MediaInfo.newPathMediaInfo(path,true));
                        } else {
                            // 原图
                            path = media.getPath();
                            mMediaAdapter.addData(MediaInfo.newPathMediaInfo(path));
                        }
                    }

                    break;
                case PictureConfig.PREVIEW_VIDEO_CODE:
                    List<LocalMedia> videoMedia = PictureSelector.obtainMultipleResult(data);
                    LocalMedia video = videoMedia.get(0);
                    if (SdkVersionUtils.checkedAndroid_Q() && PictureMimeType.isContent(video.getPath())) {
                        path = UriUtils.uri2File(Uri.parse(video.getPath())).getAbsolutePath();
                    } else {
                        path = video.getPath();
                    }
                    mRVMedia.setVisibility(View.INVISIBLE);
                    flVideo.setVisibility(View.VISIBLE);
                    Log.e("123", path);
                    jzvdStd.setUp(path, "");
                    jzvdStd.startVideo();
                    break;
            }
        }
    }


    private void showFaceViewGroup() {
        TUIKitLog.i(TAG, "showFaceViewGroup");
        if (mFragmentManager == null) {
            mFragmentManager = this.getFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        mTextInput.requestFocus();
        mFaceFragment.setListener(new FaceFragment.OnEmojiClickListener() {
            @Override
            public void onEmojiDelete() {
                int index = mTextInput.getSelectionStart();
                Editable editable = mTextInput.getText();
                boolean isFace = false;
                if (index <= 0) {
                    return;
                }
                if (editable.charAt(index - 1) == ']') {
                    for (int i = index - 2; i >= 0; i--) {
                        if (editable.charAt(i) == '[') {
                            String faceChar = editable.subSequence(i, index).toString();
                            if (FaceManager.isFaceChar(faceChar)) {
                                editable.delete(i, index);
                                isFace = true;
                            }
                            break;
                        }
                    }
                }
                if (!isFace) {
                    editable.delete(index - 1, index);
                }
            }

            @Override
            public void onEmojiClick(Emoji emoji) {
                int index = mTextInput.getSelectionStart();
                Editable editable = mTextInput.getText();
                editable.insert(index, emoji.getFilter());
                FaceManager.handlerEmojiText(mTextInput, editable.toString(), true);
            }

            @Override
            public void onCustomFaceClick(int groupIndex, Emoji emoji) {
//                mMessageHandler.sendMessage(MessageInfoUtil.buildCustomFaceMessage(groupIndex, emoji.getFilter()));
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.more_face, mFaceFragment).commitAllowingStateLoss();
    }

    private void showSoftInput() {
//        TUIKitLog.v(TAG, "showSoftInput");
//        hideInputMoreLayout();
//        mAudioInputSwitchButton.setImageResource(com.tencent.qcloud.tim.uikit.R.drawable.action_audio_selector);
//        mEmojiInputButton.setImageResource(com.tencent.qcloud.tim.uikit.R.drawable.ic_input_face_normal);
//        mTextInput.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(mTextInput, 0);
//        if (mChatInputHandler != null) {
//            postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mChatInputHandler.onInputAreaClick();
//                }
//            }, 200);
//        }
    }

    public void hideSoftInput() {
        TUIKitLog.i(TAG, "hideSoftInput");
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextInput.getWindowToken(), 0);
        mTextInput.clearFocus();
        fFace.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
