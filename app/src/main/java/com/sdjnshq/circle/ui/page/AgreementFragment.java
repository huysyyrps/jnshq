package com.sdjnshq.circle.ui.page;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.sdjnshq.circle.R;
import com.sdjnshq.circle.data.bean.LoginUser;
import com.sdjnshq.circle.data.bean.PageList;
import com.sdjnshq.circle.data.bean.Protocol;
import com.sdjnshq.circle.data.http.APIManager;
import com.sdjnshq.circle.data.http.RetrofitUtil;
import com.sdjnshq.circle.data.http.SObserver;
import com.sdjnshq.circle.ui.base.BaseFragment;
import com.sdjnshq.circle.utils.AppSP;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;

public class AgreementFragment extends BaseFragment {
    @BindView(R.id.titleBar)
    CommonTitleBar titleBar;
    @BindView(R.id.webview)
    WebView webView;
    private int mType;

    public static AgreementFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);

        AgreementFragment fragment = new AgreementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int resourceId() {
        return R.layout.fragment_webview;
    }


    private void initData() {
        RetrofitUtil.execute(APIManager.getAPI().getProtocol(String.valueOf(mType)), new SObserver<PageList<Protocol>>() {
            @Override
            public void onSuccess(PageList<Protocol> data) {
                String encode;
                try {
                    encode = URLEncoder.encode(data.getCurrentPageData().get(0).getProValue(), "utf-8");
                    webView.loadData(encode, "text/html", "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                super.onError(message);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getInt("type");
        titleBar.getCenterTextView().setText(mType == 100 ? "用户协议" : "隐私协议");

        WebSettings webSettings = webView.getSettings();
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //特别注意，5.1以上默认禁止了https和http的混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        initData();
    }
}
