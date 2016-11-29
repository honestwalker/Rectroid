package com.honestwalker.android.rectroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.equipment.DisplayUtil;
import com.honestwalker.androidutils.exception.ExceptionUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.EventBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * =====================================================================<br />
 *                                                                      <br />
 *    组件对象。                                                         <br />
 *    组件可以是一个页面，一块试图，一个控件或者一组功能。                    <br />
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
        init();
    }

    public Component(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Component(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        initParam();

        // 这里是先找到组件容器， 然后在调用initComponent去渲染子组件
        // 不能在initComponent 指定的容器id中通过findViewById 去找容器，因为这样会让一个组件递归加载完子组件后，
        // 再去加载当前组件下一个容器，此时如果子组件有个view的id和容器组件id一样，会产生UI错误
        onRegisterRectView();    // 这里先加载容器，让入映射表
        initComponent();         // 这里开始加载子组件 ， 并依照上面步骤依次加载下去。
    }

    public Activity getActivity() {
        return activity;
    }

    /**
     * 把activity对象 设置到所有子组件
     * @param activity 持有组件的activity
     */
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
     * 初始化当前组件(不包含子组件)
     */
    private void initParam() {
        inflater = ((Activity)getContext()).getLayoutInflater();
        if(contentViewLayout() > 0) {
            contentView = inflater.inflate(contentViewLayout(), this);
//            ViewUtils.inject(this , contentView);
            ComponentUtil.componentInject(this);
        }
    }

    /**
     * 容器索引表
     */
    private HashMap<Integer, RectView> xviewMapping = new HashMap<>();

    /**
     * 注册RectView组件容器
     * @param resId 容器ID
     */
    protected void registerRectView(int resId) {
        View view = findViewById(resId);
        if(view != null && view instanceof Component) {
            xviewMapping.put(resId, (RectView) view);
        }
    }

    /**
     * 绑定组件容器和组件类
     * @param resId           组件容器id
     * @param componentClass  组件类
     */
    protected <T extends View> T bind(int resId, Class<T> componentClass) {
        try {
            return ComponentUtil.getComponent(this, xviewMapping.get(resId), componentClass);
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
        return null;
    }

    /**
     * 绑定组件容器和组件类
     * @param xview           组件容器id
     * @param componentClass  组件类
     */
    protected <T extends View> T bind(RectView xview, Class<T> componentClass) {
        return ComponentUtil.getComponent(this, xview, componentClass);
    }

    /**
     * 获取当前组件的View
     * @return 当前组件的View 对象
     */
    protected View getContentView() {
        if(contentView == null) {
            inflater = ((Activity)getContext()).getLayoutInflater();
            if(contentViewLayout() > 0) {
                contentView = inflater.inflate(contentViewLayout(), this);
            }
        }
        return contentView;
    }

    /**
     * 注册组件，所有下一级组件将注册到components中。
     * @param component 注册的组件
     */
    void registerComponents(View component) {
        components.add(component);
    }

    /**
     * 获取已注册的组件的迭代器
     * @return 组件迭代器
     */
    public Iterator<View> getCompoents() {
        return components.iterator();
    }

    /**
     * 注册容器回调
     * 在该方法的实现中 调用 registerRectView 方法 来注册组件容器，注册的组件必须是RectView或其子类。
     */
    protected abstract void onRegisterRectView();

    /**
     * 帮顶组件到容器
     */
    protected abstract void initComponent();

    /**
     * 组件的布局
     * @return 布局id索引
     */
    protected abstract int contentViewLayout();

    protected void bindEvent(View view, int id) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            // 查找view事件注解
            // 如果注解带有 EventBase 就是事件注解
            Annotation[] annos = method.getAnnotations();
            for (Annotation anno : annos) {
                // 查找事件注解
                EventBase eventBase = anno.annotationType().getAnnotation(EventBase.class);
                if(eventBase != null) {
                    try {
                        Method method1 = anno.getClass().getMethod("value");
                        int viewResId = ((int[])method1.invoke(anno))[0];
                        if(viewResId != id) {   // 如果事件注解id不匹配则跳过
                            break;
                        }
                    } catch (Exception e){
                        ExceptionUtil.showException(e);
                    }

                    String listenerSetter = eventBase.listenerSetter();
                    Class listenerType    = eventBase.listenerType();
                    String methodName     = eventBase.methodName();

                    LogCat.d("event", listenerSetter + "   " + methodName);

                    // 调用view设置事件方法
                    {
                        try {
                            Object listener = new ProxyEvent().newInstance(this, method, methodName, new Class[]{listenerType});
                            // 设置 view 的事件监听
                            view.getClass().getMethod(listenerSetter, listenerType).invoke(view, listener);
                        } catch (Exception e) {
                            ExceptionUtil.showException(e);
                        }
                    }

                }
            }

        }

    }

    /**
     * 执行所包含的组件的生命周期
     * @param method 待调用的生命周期方法名
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

                    Class lifeCycle;

                    if(!foundMethod) {
                        lifeCycle = view.getClass().getSuperclass();
                    } else {
                        lifeCycle = view.getClass();
                    }

                    Method lifeCycleMethod = lifeCycle.getDeclaredMethod(method);
                    lifeCycleMethod.invoke(view);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Iterator<View> views = getCompoents();
        while(views.hasNext()) {
            View component = views.next();
            if(component instanceof Component) {
                ((Component)component).onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
