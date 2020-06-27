package com.sdjnshq.circle.im.helper;

import android.os.Handler;
import android.os.Looper;

import com.sdjnshq.architecture.utils.ImageUtils;
import com.sdjnshq.circle.R;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;

import java.util.ArrayList;
import java.util.List;

public class ConversationLayoutHelper {

    public static void customizeConversation(final ConversationLayout layout) {
        ConversationListLayout listLayout = (ConversationListLayout) layout.getConversationList();

        listLayout.setItemTopTextSize(16); // 设置adapter item中top文字大小
        listLayout.setItemBottomTextSize(12);// 设置adapter item中bottom文字大小
        listLayout.setItemDateTextSize(10);// 设置adapter item中timeline文字大小
        listLayout.setItemAvatarRadius(5);// 设置adapter item头像圆角大小
        listLayout.setItemAvatarRadius(99999999);
        listLayout.disableItemUnreadDot(false);// 设置adapter item是否不显示未读红点，默认显示

        // 动态插入，删除Item，包括自定义会话s
        final ConversationInfo customInfo = new ConversationInfo();
        customInfo.setType(ConversationInfo.TYPE_CUSTOM);
        customInfo.setId("自定义会话");
        customInfo.setGroup(false);
        customInfo.setTitle("新的朋友");
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsgType(MessageInfo.MSG_TYPE_CUSTOM);
        messageInfo.setCustomText("");
        customInfo.setLastMessage(new MessageInfo());
        customInfo.setIcon(ImageUtils.getBitmap(R.mipmap.ic_message_new));

        final ConversationInfo customInfo2 = new ConversationInfo();
        customInfo2.setType(ConversationInfo.TYPE_CUSTOM);
        customInfo2.setId("自定义会话");
        customInfo2.setGroup(false);
        customInfo2.setTitle("系统消息");
        MessageInfo messageInfo2 = new MessageInfo();
        messageInfo2.setMsgType(MessageInfo.MSG_TYPE_CUSTOM);
        messageInfo2.setCustomText("");
        customInfo2.setLastMessage(new MessageInfo());
        customInfo2.setIcon(ImageUtils.getBitmap(R.mipmap.ic_system_message));

        final ConversationInfo customInfo3 = new ConversationInfo();
        customInfo3.setType(ConversationInfo.TYPE_CUSTOM);
        customInfo3.setId("自定义会话");
        customInfo3.setGroup(false);
        customInfo3.setTitle("最近访客");
        MessageInfo messageInfo3 = new MessageInfo();
        messageInfo3.setMsgType(MessageInfo.MSG_TYPE_CUSTOM);
        messageInfo3.setCustomText("");
        customInfo3.setLastMessage(new MessageInfo());
        customInfo3.setIcon(ImageUtils.getBitmap(R.mipmap.ic_message_who));

        List<ConversationInfo> conversationInfoList = new ArrayList<>();
        conversationInfoList.add(customInfo);
        conversationInfoList.add(customInfo2);
        conversationInfoList.add(customInfo3);
        layout.setHeader(conversationInfoList);

    }

}
