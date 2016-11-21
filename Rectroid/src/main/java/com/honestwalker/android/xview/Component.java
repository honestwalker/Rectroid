package com.honestwalker.android.xview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.honestwalker.androidutils.equipment.DisplayUtil;
import com.honestwalker.androidutils.exception.ExceptionUtil;
import com.lidroid.xutils.ViewUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * =====================================================================<br />
 *                                                                      <br />
 *    组件对象。                                                         <br />
 *    一个组建可以继承android 内置的view， 也可以继承该类，统一设计。         <br />
 *    组件带有生命周期，跟随activity生命周期一同被调用。                     <br />
 *    Created by lanzhe on 16-11-16.                                    <br />
 *                                                                      <br />
 * =====================================================================<br />
 */
public abstract class Component extends FrameLayout {

    protected LayoutInflater inflater;

    private View contentView;

    private List<View> components = new ArrayList<>();

    private Intent intent;

    private Activity activity;

    public Component(Context context) {
        super(context);
        initParam();
    }

    public Component(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParam();
    }

    public Component(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParam();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        // 为子组件设置activity
        Iterator<View> childComponents = getCompoents();
        while(childComponents.hasNext()) {
            View view = childComponents.next();
            if(view instanceof Component) {
                ((Component) view).setActivity(activity);
            }
        }
    }

    /**
     * 初始化组件，已经拼装子组件
     */
    private void initParam() {
        inflater = ((Activity)getContext()).getLayoutInflater();
        if(contentViewLayout() > 0) {
            contentView = inflater.inflate(contentViewLayout(), this);
            ViewUtils.inject(this , contentView);
            ComponentUtil.componentInject(this);
            initComponent();
        }
    }

    /**
     * 绑定组件容器和组件类
     * @param resId           组件容器id
     * @param componentClass
     */
    protected <T extends View> T bind(int resId, Class<T> componentClass) {
        return ComponentUtil.getComponent(this, getContentView(), resId, componentClass);
    }

    /**
     * 获取当前组件的View
     * @return 当前组件的View 对象
     */
    protected View getContentView() {
        return contentView;
    }

    /**
     * 注册组件，所有下一级组件将注册到components中。
     * @param component
     */
    void registerComponents(View component) {
        components.add(component);
    }

    /**
     * 获取已注册的组件的迭代器
     * @return
     */
    public Iterator<View> getCompoents() {
        return components.iterator();
    }

    /**
     * 将在组件初始化时被调用
     */
    protected abstract void initComponent();

    /**
     * 组件的布局
     * @return
     */
    protected abstract int contentViewLayout();

    /**
     * 执行所包含的组件的生命周期
     * @param method
     */
    private void evalLifeCycle(String method) {
        Iterator<View> iterator = getCompoents();
        while(iterator.hasNext()) {
            View view = iterator.next();
            if(view instanceof Component) {
                try {

                    // find method
                    boolean foundMethod = false;
                    Method[] ms = view.getClass().getDeclaredMethods();
                    for (Method m : ms) {
                        if(m.getName().equals(method)) {
                            foundMethod = true;
                            break;
                        }
                    }

                    Class lifeCycle = null;

                    if(!foundMethod) {
                        lifeCycle = view.getClass().getSuperclass();
                    } else {
                        lifeCycle = view.getClass();
                    }

                    Method lifeCyle = lifeCycle.getDeclaredMethod(method);
                    lifeCyle.invoke(view);

                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }
            }
        }
    }

    public Intent getIntent() {
        if(intent == null) {
            intent = new Intent();
        }
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
        Iterator<View> iter = getCompoents();
        while(iter.hasNext()) {
            View entry = iter.next();
            if(entry instanceof Component) {
                ((Component)entry).setIntent(intent);
            }
        }
    }

    /** 屏幕宽度 */
    public int screenWidth() {
        return DisplayUtil.getWidth(getContext());
    }

    /** 屏幕高对 */
    public int screenHeight() {
        return DisplayUtil.getHeight(getContext());
    }

    /// 生命周期相关

    public void onCreate () {
        evalLifeCycle("onCreate");
    }
    public void onResume () {
        evalLifeCycle("onResume");
    }
    public void onPause  () {
        evalLifeCycle("onPause");
    }
    public void onStart  () {
        evalLifeCycle("onStart");
    }
    public void onRestart() {
        evalLifeCycle("onRestart");
    }
    public void onDestroy() {
        evalLifeCycle("onDestroy");
    }

}
