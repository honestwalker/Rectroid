package com.honestwalker.android.xview.components;

import android.content.Context;
import android.util.AttributeSet;

import com.honestwalker.android.commons.title.TitleArg;
import com.honestwalker.android.commons.title.TitleBuilder;
import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.R;

/**
 * Created by lanzhe on 16-11-16.
 */
public class TitleComponent extends Component {

    public TitleComponent(Context context) {
        super(context);
    }

    public TitleComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_title;
    }

    public void setTitle(TitleArg title) {
        new TitleBuilder(getContentView()).setTitle(title);
    }

}
