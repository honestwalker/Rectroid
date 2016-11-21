package com.honestwalker.android.xview;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.honestwalker.androidutils.exception.ExceptionUtil;

import java.lang.reflect.Field;

/**
 * Created by lanzhe on 16-11-16.
 */
public class ComponentUtil {

    public static void componentInject(Activity context) {
        injectView(context);
    }

    private static void injectView(Activity context) {
        Field[] fields = context.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ComponentInject componentInject = field.getAnnotation(ComponentInject.class);

            if(componentInject != null) {
                int res = componentInject.value();

                try {
                    XView xView = (XView) context.findViewById(res);
                    View view = xView.inject(field.getType());
                    field.set(context, view);
                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }

            }

        }
    }

    private static void injectView(Fragment context) {
        Field[] fields = context.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ComponentInject componentInject = field.getAnnotation(ComponentInject.class);

            if(componentInject != null) {
                int res = componentInject.value();

                try {
                    XView xView = (XView) context.getView().findViewById(res);
                    View view = xView.inject(field.getType());
                    field.set(context, view);
                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }

            }

        }
    }

    public static void componentInject(View contentView) {
        Field[] fields = contentView.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            ComponentInject componentInject = field.getAnnotation(ComponentInject.class);

            if(componentInject != null) {
                int res = componentInject.value();
                try {
                    XView xView = (XView) contentView.findViewById(res);
                    View view = xView.inject(field.getType());
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
     * @param component
     * @param contentView
     * @param resId
     * @param componentClass
     * @param <V>
     * @return
     */
    public static <V extends View> V getComponent(Component component, View contentView, int resId, Class<V> componentClass) {

        // 去的XView 容器
        XView xView = (XView) contentView.findViewById(resId);

        // 创建组件
        V view = (V) xView.inject(componentClass);

        /**
         *  注册组件到父组件
         */
        addComponent(component, view);

        return view;
    }

    /**
     * 注册组件到父组件
     * @param ownerComponent
     * @param view
     */
    private static void addComponent(Component ownerComponent, View view) {
        try {
            ownerComponent.registerComponents(view);
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

}
