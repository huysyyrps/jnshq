package com.sdjnshq.circle.ui.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.HomeViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.MessageCount;
import com.sdjnshq.circle.im.helper.ConversationLayoutHelper;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.ui.page.friend.NewFriendFragment;
import com.sdjnshq.circle.ui.page.user.SystemInfoFragment;
import com.sdjnshq.circle.ui.page.user.UserVisitorFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendPendencyRequest;
import com.tencent.imsdk.friendship.TIMFriendPendencyResponse;
import com.tencent.imsdk.friendship.TIMPendencyType;
import com.tencent.qcloud.tim.demo.DemoApplication;
import com.tencent.qcloud.tim.demo.chat.ChatActivity;
import com.tencent.qcloud.tim.demo.utils.Constants;
import com.tencent.qcloud.tim.uikit.component.action.PopActionClickListener;
import com.tencent.qcloud.tim.uikit.component.action.PopDialogAdapter;
import com.tencent.qcloud.tim.uikit.component.action.PopMenuAction;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.utils.PopWindowUtil;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// 消息列表
public class MessageFragment extends BaseFragment {
    @BindView(R.id.conversation_layout)
    ConversationLayout mConversationLayout;

    private ListView mConversationPopList;
    private PopDialogAdapter mConversationPopAdapter;
    private PopupWindow mConversationPopWindow;
    private List<PopMenuAction> mConversationPopActions = new ArrayList<>();
    HomeViewModel mHomeViewModel;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mHomeViewModel = getActivityViewModelProvider((AppCompatActivity) getActivity()).get(HomeViewModel.class);
        initView();
        mHomeViewModel.messageCountListLive.observe(getViewLifecycleOwner(), new Observer<MessageCount>() {
            @Override
            public void onChanged(MessageCount messageCount) {
                setMessageCount(messageCount);
            }
        });
        initNewFriendCount();
    }

    // 未读好友添加
    public void initNewFriendCount() {
        mHomeViewModel.getMessageCount();

        final TIMFriendPendencyRequest timFriendPendencyRequest = new TIMFriendPendencyRequest();
        timFriendPendencyRequest.setTimPendencyGetType(TIMPendencyType.TIM_PENDENCY_COME_IN);
        TIMFriendshipManager.getInstance().getPendencyList(timFriendPendencyRequest, new TIMValueCallBack<TIMFriendPendencyResponse>() {
            @Override
            public void onError(int i, String s) {
                ToastUtil.toastShortMessage("Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(TIMFriendPendencyResponse timFriendPendencyResponse) {
                if (timFriendPendencyResponse.getItems() != null) {
                    int pendingRequest = timFriendPendencyResponse.getItems().size();
                    try {
                        mConversationLayout.getConversationList().getAdapter().getItem(0).setUnRead(pendingRequest);
                        AppSP.getInstance().put("new_friend", pendingRequest);
                        mHomeViewModel.getMessageCount();
                    } catch (Exception ex) {

                    }
                }
            }
        });
    }


    private void initView() {
        // 隐藏原有的titlebar
        mConversationLayout.getTitleBar().setVisibility(View.GONE);

        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
        ConversationLayoutHelper.customizeConversation(mConversationLayout);
        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConversationInfo conversationInfo) {
                if (conversationInfo.getType() == ConversationInfo.TYPE_CUSTOM) {
                    if (conversationInfo.getTitle().equals("新的朋友")) {
                        ((MainFragment) getParentFragment()).startBrotherFragment(NewFriendFragment.newInstance());
                    } else if (conversationInfo.getTitle().equals("最近访客")) {
                        ((MainFragment) getParentFragment()).startBrotherFragment(UserVisitorFragment.newInstance());
                    } else if (conversationInfo.getTitle().equals("系统消息")) {
                        ((MainFragment) getParentFragment()).startBrotherFragment(SystemInfoFragment.newInstance());
                    }
                } else {
                    startChatActivity(conversationInfo);
                }
            }
        });
        mConversationLayout.getConversationList().setOnItemLongClickListener(new ConversationListLayout.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position, ConversationInfo conversationInfo) {
                startPopShow(view, position, conversationInfo);
            }
        });
        initPopMenuAction();
    }

    private void setMessageCount(MessageCount messageCount) {
        AppSP.getInstance().messageCount(messageCount);
        try {
            mConversationLayout.getConversationList().getAdapter().getItem(0).setUnRead(AppSP.getInstance().getInt("new_friend", 0));
            mConversationLayout.getConversationList().getAdapter().getItem(1).setUnRead(Integer.parseInt(messageCount.getNewSysCount()));
            mConversationLayout.getConversationList().getAdapter().getItem(2).setUnRead(Integer.parseInt(messageCount.getNewVisitCount()));
            mConversationLayout.getConversationList().getAdapter().notifyDataSetChanged();
            ((MainFragment) getParentFragment()).setMessageValue();
        } catch (Exception ex) {

        }
    }

    private void initPopMenuAction() {

        // 设置长按conversation显示PopAction
        List<PopMenuAction> conversationPopActions = new ArrayList<PopMenuAction>();
        PopMenuAction action = new PopMenuAction();
        action.setActionName(getResources().getString(com.tencent.qcloud.tim.demo.R.string.chat_top));
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mConversationLayout.setConversationTop(position, (ConversationInfo) data);
            }
        });
        conversationPopActions.add(action);
        action = new PopMenuAction();
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int position, Object data) {
                mConversationLayout.deleteConversation(position, (ConversationInfo) data);
            }
        });
        action.setActionName(getResources().getString(com.tencent.qcloud.tim.demo.R.string.chat_delete));
        conversationPopActions.add(action);
        mConversationPopActions.clear();
        mConversationPopActions.addAll(conversationPopActions);
    }

    /**
     * 长按会话item弹框
     *
     * @param index            会话序列号
     * @param conversationInfo 会话数据对象
     * @param locationX        长按时X坐标
     * @param locationY        长按时Y坐标
     */
    private void showItemPopMenu(final int index, final ConversationInfo conversationInfo, float locationX, float locationY) {
        if (mConversationPopActions == null || mConversationPopActions.size() == 0)
            return;
        View itemPop = LayoutInflater.from(getActivity()).inflate(com.tencent.qcloud.tim.demo.R.layout.pop_menu_layout, null);
        mConversationPopList = itemPop.findViewById(com.tencent.qcloud.tim.demo.R.id.pop_menu_list);
        mConversationPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuAction action = mConversationPopActions.get(position);
                if (action.getActionClickListener() != null) {
                    action.getActionClickListener().onActionClick(index, conversationInfo);
                }
                mConversationPopWindow.dismiss();
            }
        });

        for (int i = 0; i < mConversationPopActions.size(); i++) {
            PopMenuAction action = mConversationPopActions.get(i);
            if (conversationInfo.isTop()) {
                if (action.getActionName().equals(getResources().getString(com.tencent.qcloud.tim.demo.R.string.chat_top))) {
                    action.setActionName(getResources().getString(com.tencent.qcloud.tim.demo.R.string.quit_chat_top));
                }
            } else {
                if (action.getActionName().equals(getResources().getString(com.tencent.qcloud.tim.demo.R.string.quit_chat_top))) {
                    action.setActionName(getResources().getString(com.tencent.qcloud.tim.demo.R.string.chat_top));
                }

            }
        }
        mConversationPopAdapter = new PopDialogAdapter();
        mConversationPopList.setAdapter(mConversationPopAdapter);
        mConversationPopAdapter.setDataSource(mConversationPopActions);
        mConversationPopWindow = PopWindowUtil.popupWindow(itemPop, mBaseView, (int) locationX, (int) locationY);
        mBaseView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConversationPopWindow.dismiss();
            }
        }, 10000); // 10s后无操作自动消失
    }

    private void startPopShow(View view, int position, ConversationInfo info) {
        showItemPopMenu(position, info, view.getX(), view.getY() + view.getHeight() / 2);
    }

    private void startChatActivity(ConversationInfo conversationInfo) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? TIMConversationType.Group : TIMConversationType.C2C);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        Intent intent = new Intent(DemoApplication.instance(), ChatActivity.class);
        intent.putExtra(Constants.CHAT_INFO, chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DemoApplication.instance().startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent.getPage().equals("visitor")||messageEvent.getPage().equals("system")){
            initNewFriendCount();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
