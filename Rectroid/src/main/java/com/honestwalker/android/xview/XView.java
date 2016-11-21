package com.honestwalker.android.xview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.honestwalker.androidutils.exception.ExceptionUtil;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XView<V extends View> extends Component {

    private View contentView;

    public XView(Context context) {
        super(context);
    }

    public XView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initComponent() {}

    @Override
    protected int contentViewLayout() {
        return 0;
    }

    /**
     * 添加组件布局
     * @param view
     * @param <V>
     */
    public <V extends View> void view(V view) {
        this.removeAllViews();
        this.contentView = view;
        this.addView(view);
    }

    private Object componentV;

    /**
     * 拼装组件
     * @param xView
     * @param <T>
     * @return
     */
    public <T extends View> T inject(Class<T> xView) {
        try {
            T v = xView.getConstructor(Context.class).newInstance(getContext());
            view(v);
            componentV = v;
            return v;
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
        return null;
    }

    public <V extends View> V getComponent(Class<? extends View> componentClass) {
        return (V)componentV;
    }

}
