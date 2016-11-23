package com.honestwalker.android.rectroid;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.exception.ExceptionUtil;

import java.lang.reflect.Field;

/**
 * Created by lanzhe on 16-11-16.
 */
public class ComponentUtil {

    public static void componentInject(Activity context) {
        injectView(context);
    }

    /**
     * 在Activity中装配组件
     * @param activity 指定activity
     */
    private static void injectView(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ComponentInject componentInject = field.getAnnotation(ComponentInject.class);

            if(componentInject != null) {
                int res = componentInject.value();

                try {
                    RectView rectView = (RectView) activity.findViewById(res);
                    View view = rectView.inject(field.getType());
                    field.set(activity, view);
                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }

            }

        }
    }

    /**
     * 在Fragment中装配组件
     * @param fragment 指定fragment
     */
    private static void injectView(Fragment fragment) {
        Field[] fields = fragment.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ComponentInject componentInject = field.getAnnotation(ComponentInject.class);

            if(componentInject != null) {
                int res = componentInject.value();

                try {
                    RectView rectView = (RectView) fragment.getView().findViewById(res);
                    View view = rectView.inject(field.getType());
                    field.set(fragment, view);
                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }

            }

        }
    }

    /**
     * 在View中装配组件
     * @param contentView 指定view
     */
    public static void componentInject(View contentView) {
        Field[] fields = contentView.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ComponentInject componentInject = field.getAnnotation(ComponentInject.class);

            if(componentInject != null) {
                int res = componentInject.value();
                try {
                    RectView rectView = (RectView) contentView.findViewById(res);
                    View view = rectView.inject(field.getType());
                    field.set(contentView, view);
                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }

            }

        }
    }

//    public static <V extends View> V getComponent(View contentView, int resId, Class<V> componentClass) {
//        XView xView = (XView) contentView.findViewById(resId);
//        V view = (V) xView.inject(componentClass);
//        return view;
//    }

    /**
     * 从 XView 组件容器装配组件
     * @param component      父组件
     * @param resId          子组件的id
     * @param componentClass 子组件类型
     * @param <V>            子组件类型
     * @return
     */
    public static <V extends View> V getComponent(Component component, int resId, Class<V> componentClass) {

        // 取得XView 容器
        RectView rectView = (RectView) component.getContentView().findViewById(resId);

        LogCat.d("xview", component.getContentView() + "      " + rectView + "   inject");

        // 创建组件
        V view = (V) rectView.inject(componentClass);

        // 注册组件到父组件，如果子组件是Component类型的才能注册
        if(view instanceof Component) {
            registerComponent(component, (Component) view);
        }

        return view;
    }

    /**
     * 从 XView 组件容器装配组件
     * @param component      父组件
     * @param xview          子组件的id
     * @param componentClass 子组件类型
     * @param <V>            子组件类型
     * @return
     */
    public static <V extends View> V getComponent(Component component, RectView xview, Class<V> componentClass) {

        LogCat.d("xview", component.getContentView() + "      " + xview + "   inject");

        // 创建组件
        V view = (V) xview.inject(componentClass);

        // 注册组件到父组件，如果子组件是Component类型的才能注册
        if(view instanceof Component) {
            registerComponent(component, (Component) view);
        }

        return view;
    }

    /**
     * 注册组件到父组件
     * @param ownerComponent 父组件
     * @param view           注册的子组件
     */
    private static void registerComponent(Component ownerComponent, Component view) {
        if(ownerComponent != null && view != null) {
            ownerComponent.registerComponents(view);
        }
    }

}
