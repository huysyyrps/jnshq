package com.sdjnshq.circle.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sdjnshq.circle.R;
//import com.tencent.mm.sdk.constants.ConstantsAPI;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity{}
//
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
//
//    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//
////    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_basepickerview);
//
//        api = WXAPIFactory.createWXAPI(this, "wx25f0d0a93d9b3002");
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        Log.e("123", "onPayFinish, errCode = " + resp.errCode);
//        finish();
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////            builder.setTitle(R.string.app_tip);
////            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
////            builder.show();
//        }
//    }
//}