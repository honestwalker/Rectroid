package com.honestwalker.android.rectroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.honestwalker.android.commons.utils.StartActivityHelper;
import com.honestwalker.android.kc_commons.ui.utils.TranslucentStatus;
import com.honestwalker.android.rectroid.utils.ActivityUtil;
import com.honestwalker.androidutils.IO.LogCat;

/**
 *
 * 组件支持通用activity页面。
 *
 * Created by lanzhe on 16-11-17.
 */
public class ComponentActivity extends FragmentActivity {

    private RectView rectViewDemo;

    private View componentView;

    private Class mainComponent;

    private int backAnimCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.component_base);

        statusBarTheme();

        getMainComponent();

        if(mainComponent == null) return;

        rectViewDemo = (RectView) findViewById(R.id.content);

        componentView = rectViewDemo.inject(mainComponent);

        // 记录后退动画
        backAnimCode = getIntent().getIntExtra("backAnimCode", 0);

        if(componentView instanceof Component) {
            ((Component) componentView).setActivity(this);
            ((Component) componentView).setIntent(getIntent());
            ((Component) componentView).onCreate();
        }

    }

    /**
     * 设置通知栏样式
     */
    private void statusBarTheme() {
        try {
            String statusBarTranslucent = ActivityUtil.getMeta(this, "statusbar-translucent");
            if("enable".equalsIgnoreCase(statusBarTranslucent)) {
                TranslucentStatus.setEnable(this);
            }
        } catch (Exception e) {}
    }

    /**
     * 加载首组件
     * @return
     */
    private Class getMainComponent() {
        Intent intent = getIntent();
        if(intent.hasExtra("component")) {
            mainComponent = (Class) intent.getSerializableExtra("component");
        } else {
            try {
                String mainComponentPackage = ActivityUtil.getMeta(this, "main-component");
                mainComponent = Class.forName(mainComponentPackage);
            } catch (Exception e) {
                Log.e("AndroidRuntime", "Main component not found!");
            }
        }
        return mainComponent;
    }

    /// 组件生命周期相关

    @Override
    protected void onResume() {
        super.onResume();
        if(componentView instanceof Component) {
            ((Component) componentView).onResume();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(componentView instanceof Component) {
            ((Component) componentView).onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(componentView instanceof Component) {
            ((Component) componentView).onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(componentView instanceof Component) {
            ((Component) componentView).onPause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(componentView instanceof Component) {
            ((Component) componentView).onStart();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(backAnimCode != 0) {
            StartActivityHelper.activityAnim(this, getIntent(), backAnimCode);
            backAnimCode = 0;
        }
    }

    public static void startComponent(Activity context, Class<? extends Component> component) {
        Intent intent = new Intent(context, ComponentActivity.class);
        startComponent(context, component, intent);
    }

    public static void startComponent(Activity context, Class<? extends Component> component, Intent intent) {
        startComponent(context, component, intent, StartActivityHelper.ANIM_PUSHIN);
    }

    public static void startComponent(Activity context, Class<? extends Component> component, Intent intent, int animCode) {
        if(intent == null) intent = new Intent();
        intent.putExtra("component", component);
        intent.setClass(context, ComponentActivity.class);
        StartActivityHelper.toActivity(context, ComponentActivity.class, intent, animCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogCat.d("xview" , "onActivityResult in ComAct" );
        if(componentView instanceof Component) {
            LogCat.d("xview" , "onActivityResult in ComAct callback" );
            ((Component) componentView).onActivityResult(requestCode, resultCode, data);
        }
    }
}
