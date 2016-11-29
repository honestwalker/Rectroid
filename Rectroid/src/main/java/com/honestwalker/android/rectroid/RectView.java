package com.honestwalker.android.rectroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.honestwalker.androidutils.exception.ExceptionUtil;

/**
 * 组件容器对象，用于装载组件
 * Created by lanzhe on 16-11-16.
 */
public class RectView<V extends View> extends Component {

    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRegisterRectView() {}

    @Override
    protected void initComponent() {}

    @Override
    protected int contentViewLayout() {
        return 0;
    }

    /**
     * 装载组件
     * @param view 装在组件的view对象
     * @param <V>
     */
    public <V extends View> void view(V view) {
        this.addView(view);
    }

    /**
     * 组件View
     */
    private View componentView;

    /**
     * 拼装组件
     * @param componentClass  组件类型
     * @param <T>             组件类型
     * @return
     */
    public <T extends View> T inject(Class<T> componentClass) {
        try {
            T v = componentClass.getConstructor(Context.class).newInstance(getContext());
            componentView = v;
            view(v);
            return v;
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
        return null;
    }

    /**
     * 拼装组件
     * @param view 装在组件view
     * @return
     */
    public View inject(View view) {
        try {
            componentView = view;
            view(view);
            return view;
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
        return null;
    }

    /**
     * 获取容器中的组件
     * @param <V> 组件类
     * @return    组件类
     */
    public <V extends View> V getComponent() {
        return (V)componentView;
    }

}
