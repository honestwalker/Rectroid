package com.honestwalker.android.rectroid.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.honestwalker.android.fastroid.R;
import com.systembartint.SystemBarTintManager;

/**
 * Created by honestwalker on 15-9-24.
 */
public class TranslucentStatus {

    private static boolean translucentStatus = false;

    public static boolean isEnable() {
        return translucentStatus;
    }

    public static void setEnable(Activity context) {

        if(!support()) return;

        // 旧的实现方法
//        activity.getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // 新的实现方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(context, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(context.getResources().getColor(R.color.none));

    }

    @TargetApi(19)
    protected static void setTranslucentStatus(Activity context, boolean on) {
        translucentStatus = on;
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static boolean support() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        } else {
            return false;
        }
    }

}
