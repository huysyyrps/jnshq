package com.sdjnshq.circle.ui.page.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.Observer;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.UserViewModel;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.ui.base.BaseActivity;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendRequest;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMFriendStatus;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

// 添加朋友
public class FriendAddActivity extends BaseActivity {
    private static final String TAG = "friend——add";
    @BindView(R.id.titleBar)
    CommonTitleBar commonTitleBar;
    @BindView(R.id.et_message)
    EditText etMessage;
    String mUserId;
    UserViewModel userViewModel;


    public static void newIntent(Activity activity,String mUserId) {
        Intent intent = new Intent(activity,FriendAddActivity.class);
        intent.putExtra("userId",mUserId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add);
        ButterKnife.bind(this);

        userViewModel = getActivityViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getPagePostLive().observe(this, new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if(pagePost.getCode() == 998){
                    finish();
                }
            }
        });

        mUserId = getIntent().getStringExtra("userId");
        commonTitleBar.getRightCustomView().findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });
        commonTitleBar.getLeftTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void addFriend() {
        String id = mUserId;
        if (TextUtils.isEmpty(id)) {
            return;
        }
//        userViewModel.addFriend(id,etMessage.getText().toString());

        TIMFriendRequest timFriendRequest = new TIMFriendRequest(id);
        timFriendRequest.setAddWording(etMessage.getText().toString());
        timFriendRequest.setAddSource("android");
        TIMFriendshipManager.getInstance().addFriend(timFriendRequest, new TIMValueCallBack<TIMFriendResult>() {
            @Override
            public void onError(int i, String s) {
                DemoLog.e(TAG, "addFriend err code = " + i + ", desc = " + s);
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(TIMFriendResult timFriendResult) {
                DemoLog.i(TAG, "addFriend success result = " + timFriendResult.toString());
                switch (timFriendResult.getResultCode()) {
                    case TIMFriendStatus.TIM_FRIEND_STATUS_SUCC:
                        ToastUtil.toastShortMessage("发送成功");
                        break;
                    case TIMFriendStatus.TIM_FRIEND_PARAM_INVALID:
                        if (TextUtils.equals(timFriendResult.getResultInfo(), "Err_SNS_FriendAdd_Friend_Exist")) {
                            ToastUtil.toastShortMessage("对方已是您的好友");
                            break;
                        }
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_SELF_FRIEND_FULL:
                        ToastUtil.toastShortMessage("您的好友数已达系统上限");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_THEIR_FRIEND_FULL:
                        ToastUtil.toastShortMessage("对方的好友数已达系统上限");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                        ToastUtil.toastShortMessage("被加好友在自己的黑名单中");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                        ToastUtil.toastShortMessage("对方已禁止加好友");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                        ToastUtil.toastShortMessage("您已被被对方设置为黑名单");
                        break;
                    case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_PENDING:
                        ToastUtil.toastShortMessage("等待好友审核同意");
                        break;
                    default:
                        ToastUtil.toastLongMessage(timFriendResult.getResultCode() + " " + timFriendResult.getResultInfo());
                        break;
                }
                finish();
            }
        });
    }
}
