package com.honestwalker.android.xview.components;

import android.content.Context;
import android.webkit.WebView;

import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.R;

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
