package com.sdjnshq.circle.ui.page.money;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.money.MoneyViewModel;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

//提现记录
public class CashOutRecordFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;

    MoneyViewModel moneyViewModel;

    public static CashOutRecordFragment newInstance() {

        Bundle args = new Bundle();

        CashOutRecordFragment fragment = new CashOutRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_cash_out_record;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moneyViewModel = getFragmentViewModelProvider(this).get(MoneyViewModel.class);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
    }
}
