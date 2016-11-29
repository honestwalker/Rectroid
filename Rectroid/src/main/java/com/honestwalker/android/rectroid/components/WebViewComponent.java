package com.honestwalker.android.rectroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.honestwalker.android.commons.views.HtmlWebView.HtmlWebView;
import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.R;

/**
 * Created by hkyy on 16-11-24.
 */
public class WebViewComponent extends Component {

    WebView webView;

    public WebViewComponent(Context context) {
        super(context);
    }

    public WebViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initComponent() {
        webView = (WebView) findViewById(R.id.webview);

        getSettings().setSupportZoom(false);
        getSettings().setAllowFileAccess(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        getSettings().setCacheMode(getSettings().LOAD_DEFAULT);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });
    }

    private WebSettings getSettings() {
        return webView.getSettings();
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_commont_webview;
    }

    @Override
    protected void onRegisterRectView() {
    }

    public void loadUrl(String url){
        webView.loadUrl(url);
    }


    /* @Override
    protected void initComponent() {
        webView = (WebView) findViewById(R.id.webview);
        getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               *//* view.loadUrl(url);
                return true;*//*
                return false;
            }
        });
    }

    private WebSettings getSettings() {
        return webView.getSettings();
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_commont_webview;
    }

    @Override
    protected void onRegisterRectView() {
    }


    public void loadUrl(String url) {
        webView.loadUrl(url);
    }*/
}
