package com.honestwalker.android.rectroid.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

/**
 * Created by lanzhe on 16-11-17.
 */
public class ActivityUtil {

    public static String getMeta(Activity context, String key) throws PackageManager.NameNotFoundException {
        ActivityInfo info = context.getPackageManager().getActivityInfo(context.getComponentName(), PackageManager.GET_META_DATA);
        String value = info.metaData.getString(key);
        return value;
    }

}
