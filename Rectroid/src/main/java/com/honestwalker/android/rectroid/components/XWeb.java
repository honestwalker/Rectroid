package com.honestwalker.android.rectroid.components;

import android.content.Context;
import android.webkit.WebView;

import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.R;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XWeb extends Component {

    private WebView webView;

    public XWeb(Context context) {
        super(context);
        initComponent();
    }

    @Override
    protected void onRegisterRectView() {
    }

    @Override
    protected void initComponent() {
        webView = (WebView) findViewById(R.id.webview);
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_commont_webview;
    }

    public WebView getWebView() {
        return webView;
    }

}
