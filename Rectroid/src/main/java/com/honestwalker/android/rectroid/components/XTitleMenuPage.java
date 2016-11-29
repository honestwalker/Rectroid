package com.honestwalker.android.rectroid.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.honestwalker.android.commons.title.TitleArgBuilder;
import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.ComponentBind;
import com.honestwalker.android.rectroid.R;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XTitleMenuPage extends Component {

    private TitleComponent titleComponent;

    private MenubarComponent menubarComponent;

    public XTitleMenuPage(Context context) {
        super(context);
    }

    public XTitleMenuPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XTitleMenuPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRegisterRectView() {
        registerRectView(R.id.title);
        registerRectView(R.id.body);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initComponent() {
        titleComponent   = bind(R.id.title,TitleComponent.class);
        menubarComponent = bind(R.id.body, MenubarComponent.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        titleComponent.setTitle(TitleArgBuilder.getTitle(getIntent().getStringExtra("title")));
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.component_common_title_menu_page;
    }

    public TitleComponent getTitleView() {
        return titleComponent;
    }

}
