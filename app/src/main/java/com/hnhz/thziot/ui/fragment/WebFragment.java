package com.hnhz.thziot.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hnhz.thziot.R;
import com.hnhz.thziot.event.EventFireMessage;
import com.hnhz.thziot.global.Constant;
import com.hnhz.thziot.ui.activity.LocationActivity;
import com.hnhz.thziot.ui.activity.WebActivity;
import com.hnhz.thziot.ui.webview.WVJBWebView;
import com.kq.platform.kq_common.ui.LazyBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class WebFragment extends LazyBaseFragment {
    private final String TAG = WebFragment.class.getSimpleName();

    @BindView(R.id.web_progress_bar)
    ProgressBar webProgressBar;
    @BindView(R.id.web_error_container)
    LinearLayout webErrorContainer;
    @BindView(R.id.btn_restart_web)
    Button btnRestartWeb;
    private String url;

    public WebFragment() {
    }

    public static WebFragment newInstance(String name,String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_KEY_PRODUCT_NAME, name);
        args.putString(Constant.BUNDLE_KEY_WEB_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.webView)
    WVJBWebView webView;

    private String menuName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        url = getArguments().getString(Constant.BUNDLE_KEY_WEB_URL);

        menuName = getArguments().getString(Constant.BUNDLE_KEY_PRODUCT_NAME);
        Log.i(this.getClass().getSimpleName(), menuName);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        webView.loadUrl(url);

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void bindLogic() {
        registerWebMethod();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webProgressBar.setVisibility(View.GONE);
                }
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("errorFragment", menuName);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.i("errorFragment", menuName);
                webView.setVisibility(View.GONE);
                webErrorContainer.setVisibility(View.VISIBLE);
            }
        });

        btnRestartWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webErrorContainer.setVisibility(View.GONE);
                String url = getArguments().getString(Constant.BUNDLE_KEY_WEB_URL);
                webView.loadUrl(url);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventFireMessage event) {
        webView.loadUrl(url);
    }

    public void registerWebMethod() {
        webView.registerHandler("newNativePageHandler", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("webData", data.toString());
                startActivity(intent);
            }
        });

        webView.registerHandler("callMapHandler", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                Log.d(TAG, "callMapHandler");
                Intent intent = new Intent(getContext(), LocationActivity.class);
                intent.putExtra("webData", data.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
