package com.sdjnshq.circle.ui.page.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sdjnshq.architecture.ui.simple.SimpleTextWatcher;
import com.sdjnshq.architecture.utils.Utils;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.login.LoginViewModel;
import com.sdjnshq.circle.data.bean.MediaInfo;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sdjnshq.circle.utils.TimeUtils;
import com.tencent.qcloud.tim.uikit.utils.SoftKeyBoardUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ImproveDataFragment extends BaseFragment {
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.ll_male)
    LinearLayout llMale;
    @BindView(R.id.ll_female)
    LinearLayout llFemale;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sign)
    EditText etSign;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    private LoginViewModel mLoginViewModel;
    boolean isMale = true;
    List<MediaInfo> mediaInfoList = new ArrayList<>();

    public static ImproveDataFragment newInstance() {

        Bundle args = new Bundle();

        ImproveDataFragment fragment = new ImproveDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_improve_data;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(LoginViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.iv_head, R.id.bt_submit, R.id.ll_male,R.id.ll_female,R.id.tv_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                PictureSelector.create(getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java】
                        .compress(true)
                        .minimumCompressSize(1000)
                        .enableCrop(true)
                        .withAspectRatio(1,1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                // 选择头像
                break;
            case R.id.bt_submit:
                if(mediaInfoList.size() == 0){
                    showLongToast("请选择头像");
                    return;
                }
                if(TextUtils.isEmpty(etName.getText().toString())){
                    showLongToast("请输入昵称");
                    return;
                }
                if(TextUtils.isEmpty(etSign.getText().toString())){
                    showLongToast("请输入签名");
                    return;
                }
                if(tvBirthday.getText().toString().startsWith("请")){
                    showLongToast("请选择出生日期");
                    return;
                }
                mLoginViewModel.improveDate(mediaInfoList,etName.getText().toString(),isMale,tvBirthday.getText().toString(),etSign.getText().toString());
                break;
            case R.id.ll_male:
                isMale =true;
                llMale.setBackgroundResource(R.drawable.bg_male_radius2);
                llFemale.setBackgroundResource(R.drawable.bg_white_radius10);
                break;
            case R.id.ll_female:
                isMale = false;
                llMale.setBackgroundResource(R.drawable.bg_white_radius10);
                llFemale.setBackgroundResource(R.drawable.bg_female_radius2);
                break;
            case R.id.tv_birthday:
                SoftKeyBoardUtil.hideKeyBoard(etName);
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        tvBirthday.setText(TimeUtils.date2String(date));
                    }
                }).build();
                if(tvBirthday.getText().toString().startsWith("请")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //获取当前时间
                    Date date = new Date(System.currentTimeMillis());
                    pvTime.setDate(TimeUtils.string2Calendar(simpleDateFormat.format(date)));
                }else{
                    pvTime.setDate(TimeUtils.string2Calendar(tvBirthday.getText().toString()));
                }
                pvTime.show();
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    if (localMedia != null && localMedia.size() > 0) {
                        ivHead.setImageURI(Uri.parse(localMedia.get(0).getPath()));

                        LocalMedia media = localMedia.get(0);
                        String path;
                        if (media.isCut() && !media.isCompressed()) {
                            // 裁剪过
                            path = media.getCutPath();
                        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            path = media.getCompressPath();
                        } else {
                            // 原图
                            path = media.getPath();
                        }
                        mediaInfoList.clear();
                        mediaInfoList.add(MediaInfo.newPathMediaInfo(path));
                    }
                    break;
            }
        }
    }
}

