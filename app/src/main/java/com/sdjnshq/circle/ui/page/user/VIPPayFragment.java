package com.sdjnshq.circle.ui.page.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alipay.sdk.app.PayTask;
import com.sdjnshq.circle.MainActivity;
import com.sdjnshq.circle.R;
import com.sdjnshq.circle.bridge.OrderViewModel;
import com.sdjnshq.circle.data.MessageEvent;
import com.sdjnshq.circle.data.bean.AliPay;
import com.sdjnshq.circle.data.bean.Order;
import com.sdjnshq.circle.data.bean.PagePost;
import com.sdjnshq.circle.data.bean.PayInfo;
import com.sdjnshq.circle.data.bean.WXPay;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.Base64;
import com.sdjnshq.circle.utils.utils.WeChatUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.Url;

//  支付
public class VIPPayFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.ll_wechat)
    LinearLayout llMale;
    @BindView(R.id.ll_ali)
    LinearLayout llFemale;
    @BindView(R.id.cb_wechat)
    CheckBox cbWechat;
    @BindView(R.id.cb_ali)
    CheckBox cbAli;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    OrderViewModel mOrderViewModel;
    String mOrderId;
    String mOrderNo;
    boolean isPay = false;

    public static VIPPayFragment newInstance() {

        Bundle args = new Bundle();
        VIPPayFragment fragment = new VIPPayFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int resourceId() {
        return R.layout.fragment_vip_pay;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cbWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbAli.setChecked(!isChecked);
            }
        });
        cbAli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbWechat.setChecked(!isChecked);
            }
        });

        mOrderViewModel = getFragmentViewModelProvider(this).get(OrderViewModel.class);
        mOrderViewModel.getPagePostLive().observe(getViewLifecycleOwner(), new Observer<PagePost>() {
            @Override
            public void onChanged(PagePost pagePost) {
                if (pagePost.getValue().equals("0")) {
                    // 支付成功
                    showLongToast("支付成功");
                    pop();
                    EventBus.getDefault().post(new MessageEvent("vip"));
                } else {
                    // 支付失败
//                    showLongToast("支付失败");
                }
            }
        });
        mOrderViewModel.orderLive.observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                mOrderNo = order.getOrderNo();
                mOrderId = String.valueOf(order.getId());
                tvPrice.setText("￥" + String.valueOf(order.getOrderTotal()) + "/年");
            }
        });

        mOrderViewModel.payLive.observe(getViewLifecycleOwner(), new Observer<PayInfo>() {
            @Override
            public void onChanged(PayInfo payInfo) {
                isPay = true;
                if (payInfo.getWx() != null && payInfo.getWx().getAppid() != null) {
                    WXPay wxPay = payInfo.getWx();
                    Map<String, String> keys = new HashMap<>();
                    keys.put("appid", wxPay.getAppid());
                    keys.put("partnerid", wxPay.getMch_id());
                    keys.put("prepayid", wxPay.getPreId());
                    keys.put("package", "Sign=WXPay");
                    keys.put("noncestr", wxPay.getNonce_str());
                    keys.put("timestamp", wxPay.getTimestamp());
                    String sign = "";
                    try {
                        sign = WeChatUtil.getSign(keys);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    PayReq req = new PayReq();
                    req.appId = wxPay.getAppid();
                    req.partnerId = wxPay.getMch_id();
                    req.prepayId = wxPay.getPreId();
                    req.nonceStr = wxPay.getNonce_str();
                    req.timeStamp = wxPay.getTimestamp();
                    req.packageValue = "Sign=WXPay";
                    req.sign = sign;
                    req.extData = "app data"; // optional
                    IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), wxPay.getAppid());
                    api.sendReq(req);
                } else if (payInfo.getAli() != null) {
                    AliPay aliPay  = payInfo.getAli();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("app_id", aliPay.getApp_id());
                    params.put("biz_content", URLDecoder.decode(aliPay.getBiz_content()));
                    params.put("charset", aliPay.getCharset());
                    params.put("method",  aliPay.getMethod());
                    params.put("sign_type", aliPay.getSign_type());
                    params.put("timestamp",URLDecoder.decode( aliPay.getTimestamp()));
                    params.put("version", aliPay.getVersion());
                    params.put("notify_url",aliPay.getNotify_url());
                    String orderParam = buildOrderParam(params);
                    // 后台返回的sign 错误！！！
//                    String sign = getSign(params, "MIIEpAIBAAKCAQEArEy8NOpFQ3mrDoU6SyNKu30+hfmVAWtcUVGzfybIh1Yxw27IZUoatLaLQh4Gqn393H+cj+S4HCBmwG6iniu4QAEpyiDY1yMz8x5LWM0/b34l4iKrtK54qJkdPFmqKNt3LRWGHbn/wPU6elRbjqCF2ibD5MR1YN25WZ7vxS2OjP5e5wzpn+bQeHIYX2SDhtABgvDqe6wzuyJkix71F+4Q9iM7WATaVsWptINA5FdiKv0lQNq5geTtB2Lj79YDuecAz2Jw3meLJn4NN5xDVU7Z5fyH5+x8P2QLwJymQYUCvWDTtXPpcLJRaDYSJdlbcRv4NdBueUszAZUdaEqrybAk+wIDAQABAoIBABdJv95/E6GNHlu2Eiz5ti4lPYAqPcOVWUL8ggBbo5r3QX6iyJ84JufqDGSBXnFI2jklXmnshh33GyxDR+ONEThFb/qL+gMKFQl1PMXh6AMPz9sCyKaBNnnDSDpR/DfNggwr7pax/yl7e83w/PcIJ09XEf8ntp3DGjxMySejgrL3raBtbIasMPaD7UJk+DZAWiFJe2F3/rgHFKSsC82vpe6Oaa2fOtCO3vvhVzGxlotYJvQ3lql3Z3X91LxB2U6YdoLg5dIkHp/k5kdGdWSGMLeMAZzjeka6YKVZ3iP5V2rwIL0pCkeXWAwUBcE+7e5zKdjUZu5j083jqd4xeWun37ECgYEA5vD5n0DE/GsAlWGEt4AW9qF3KPrI6e6uZqOtJH2faHT5Kp7IMDpvyU1OtiJ7t25SOyMNV8OvEeuDYIEAyY8aWU0yWyZzPzEV3fD+76sy9tZMOwEFxSLrdF/rXzLi2rRBkZLq6SaL881T7QVAIMfl4xagDArgF8LPzaRUxzjQIx8CgYEAvv7UqiUUUD8CxZGqFRvrYENX+xp7lo66Qh3B6cD96+T5995C1ik77+4LloTMOiv40kqypwQIKpo/CYdEC0cg5V/fBx9rZvODZ2U6S22pSi+EG2YIFyuYksLwaSoLxrDfkjWscwGQGAeMBs2TpW/mIqW+AOX8hxwbCYqkHaP4PqUCgYBgkvjwPdeibuSpk0Q6sFfp/w+jlWwbV3Ka/WtUTSwyNGhWCFxSvr0sgyWdSMJPrNS6YigcQXqelcw0uP2pTCyKl5utPmEHVfF9BGC3vPHtAySRsBXHoJix68Kz5sWzC5o31CZkJXh1FbI49e8hRja+RiHbp1ia1x6rMkM5pGp1pQKBgQCMXvGhktrZF1ZMKgBcv0ZAW34/+Hi4cHGHwiMQT1zPkFv+xgVNLMT2U3eEyljmsAaRV4Dxqs7tsKt/r0BdIYuVyN47d+YRpgr/ZLBxW0k+Uqm/EUAE92+M/WR/hf1drATEiloRtCZY4sMRBOnsfs8ey71XH7B3BDsCyjhP6IC8WQKBgQCW+WgxQ+Rp/xPbTwm1n/U8Ln4PpYxBGGdaPTOIs/M6QWZGoGASmXtIbAw48D+/JmLYdLC79IdJaYfsCeH9qmy+dzH5BgobCmtOERRzRAHig6IqT4h4a7FcxupaFGk8XOD6Dfw5tCl6g0R/E0rBz+GFEPV4N0JOatztcygWyQk5bA==", true);
                    final String orderInfo = orderParam + "&" + aliPay.getSign();

                    final Runnable payRunnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask alipay = new PayTask(getActivity());
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                        }
                    };

                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }
            }
        });
        mOrderViewModel.createOrder();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(mOrderId) && isPay) {
            isPay = false;
            mOrderViewModel.paySuccess(mOrderNo);
        }
    }

    @OnClick({R.id.bt_submit, R.id.ll_ali, R.id.ll_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_ali:
                cbAli.setChecked(true);
                break;
            case R.id.ll_wechat:
                cbWechat.setChecked(true);
                break;
            case R.id.bt_submit:
                if (!TextUtils.isEmpty(mOrderId)) {
                    mOrderViewModel.pay(mOrderId, cbWechat.isChecked());
                }
                break;
        }

    }


    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 对支付参数信息进行签名
     *
     * @param map 待签名授权信息
     * @return
     */
    public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = sign(authInfo.toString(), rsaKey, true);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }


    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static String getAlgorithms(boolean rsa2) {
        return rsa2 ? SIGN_SHA256RSA_ALGORITHMS : SIGN_ALGORITHMS;
    }

    public static String sign(String content, String privateKey, boolean rsa2) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(getAlgorithms(rsa2));

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
