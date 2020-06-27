package com.sdjnshq.circle.ui.page.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.AppUtil;

import butterknife.BindView;

// 关于
public class AboutFragment extends BaseFragment {
    @BindView(R.id.tv_version)
    TextView tvVersion;


    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int resourceId() {
        return R.layout.fragment_about;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvVersion.setText(AppUtil.getVersionName());
    }
}
