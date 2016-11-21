package com.honestwalker.android.xview.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.ComponentUtil;
import com.honestwalker.android.xview.R;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XTitlePage<T extends View> extends Component {

    private TitleComponent title;

    private T content;

    public XTitlePage(Context context) {
        super(context);
    }

    public XTitlePage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XTitlePage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initComponent() {
        title = ComponentUtil.getComponent(this, getContentView(), R.id.title, TitleComponent.class);
//        content = ComponentUtil.getComponent(getContentView(), R.id.content, T);
    }

//    public T setContent(Class<T> contentClass) {
//        content = ComponentUtil.getComponent(this, getContentView(), R.id.content, contentClass);
//        return content;
//    }

    public T setContent(Component component, Class<T> contentClass) {
        content = ComponentUtil.getComponent(component, getContentView(), R.id.content, contentClass);
        return content;
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_common_titlepage;
    }

    public TitleComponent getTitleView() {
        return title;
    }

}
