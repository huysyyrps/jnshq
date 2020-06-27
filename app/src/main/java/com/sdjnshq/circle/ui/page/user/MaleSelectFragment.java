package com.sdjnshq.circle.ui.page.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.GlideEngine;
import com.tencent.qcloud.tim.uikit.utils.SoftKeyBoardUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

// 选择性别
public class MaleSelectFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.ll_male)
    LinearLayout llMale;
    @BindView(R.id.ll_female)
    LinearLayout llFemale;

    boolean isMale = true;
    String sexType;

    public static MaleSelectFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("sexType", name);
        MaleSelectFragment fragment = new MaleSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int resourceId() {
        return R.layout.fragment_male_select;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            sexType = arguments.getString("sexType");
            isMale = sexType.equals("1");
        }
        if(isMale){
            llMale.setBackgroundResource(R.drawable.bg_male_radius10);
            llFemale.setBackgroundResource(R.drawable.bg_white_radius10);
        }else{
            llMale.setBackgroundResource(R.drawable.bg_white_radius10);
            llFemale.setBackgroundResource(R.drawable.bg_male_radius10);
        }

        titleBar.findViewById(R.id.iv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value", isMale ? "1" : "2");
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
    }


    @OnClick({R.id.ll_male, R.id.ll_female})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_male:
                isMale = true;
                llMale.setBackgroundResource(R.drawable.bg_male_radius10);
                llFemale.setBackgroundResource(R.drawable.bg_white_radius10);
                break;
            case R.id.ll_female:
                isMale = false;
                llMale.setBackgroundResource(R.drawable.bg_white_radius10);
                llFemale.setBackgroundResource(R.drawable.bg_male_radius10);
                break;
        }

    }


}
