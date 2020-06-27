package com.sdjnshq.circle.ui.page.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.utils.SoftKeyBoardUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;

public class EditTextFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.et_input)
    EditText etInput;

    String name;

    @Override
    public int resourceId() {
        return R.layout.fragment_edit_text;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            name = arguments.getString("name");
            titleBar.getCenterTextView().setText(name);
            etInput.setText(arguments.getString("value"));
            // 多行
            int lines = arguments.getInt("lines", 1);
            etInput.setLines(lines);
            arguments.get("value");
        }

        titleBar.findViewById(R.id.iv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("value", etInput.getText().toString());
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        SoftKeyBoardUtil.hideKeyBoard(etInput);
    }

    public static EditTextFragment newInstance(String name, String value, int lines) {

        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("value", value);
        args.putInt("lines", lines);
        EditTextFragment fragment = new EditTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
