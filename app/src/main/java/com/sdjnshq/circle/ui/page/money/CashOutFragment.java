package com.sdjnshq.circle.ui.page.money;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.money.MoneyViewModel;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

//提现
public class CashOutFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.et_money)
    EditText etMoney;

    MoneyViewModel moneyViewModel;
    @BindView(R.id.iv_wechat_check)
    ImageView ivWechatCheck;
    @BindView(R.id.iv_ali_check)
    ImageView ivAliCheck;

    public static CashOutFragment newInstance() {

        Bundle args = new Bundle();

        CashOutFragment fragment = new CashOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_cash_out;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moneyViewModel = getFragmentViewModelProvider(this).get(MoneyViewModel.class);
        titleBar.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(CashOutRecordFragment.newInstance());
            }
        });
    }

    @OnClick({R.id.bt_cashOut, R.id.tv_all,R.id.ll_wechat, R.id.ll_ali})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cashOut:
                if (TextUtils.isEmpty(etMoney.getText())) {
                    showLongToast("提现金额需要大于10元");
                }
//                double money = Double.parseDouble(etMoney.getText().toString());

                break;
            case R.id.tv_all:
                break;
            case R.id.tv_friend_reward:
                break;
            case R.id.ll_wechat:
                ivWechatCheck.setVisibility(View.VISIBLE);
                ivAliCheck.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_ali:
                ivWechatCheck.setVisibility(View.INVISIBLE);
                ivAliCheck.setVisibility(View.VISIBLE);
                break;
        }
    }

}
