package com.honestwalker.android.xview.components;

import android.content.Context;
import android.util.AttributeSet;

import com.honestwalker.android.commons.menu.BlurViewPager;
import com.honestwalker.android.commons.menu.IMenuContext;
import com.honestwalker.android.commons.menu.MenuBuilder;
import com.honestwalker.android.commons.menu.TabPageIndicator;
import com.honestwalker.android.commons.title.TitleArg;
import com.honestwalker.android.commons.title.TitleBuilder;
import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.R;
import com.honestwalker.androidutils.IO.LogCat;
import com.honestwalker.androidutils.activity.fragment.menubar.MenubarItemBean;

/**
 * Created by lanzhe on 16-11-16.
 */
public class MenubarComponent extends Component implements IMenuContext {

    protected BlurViewPager     pager;
    protected TabPageIndicator  indicator;

    private MenuBuilder menuBuilder = new MenuBuilder();

    public MenubarComponent(Context context) {
        super(context);
    }

    public MenubarComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenubarComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initComponent() {
        pager     = (BlurViewPager) getContentView().findViewById(R.id.pager);
        indicator = (TabPageIndicator) getContentView().findViewById(R.id.indicator);
        LogCat.d("xview", "pager=" + pager + "   indicator=" + indicator);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildMenu();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void buildMenu() {
        int menuTabResId    = getMenuTabResId();
        int getMenubarResId = getMenubarResId();
        LogCat.d("xview", "getMenubarResId=" + getMenubarResId + "   getRClass()=" + getRClass() + "  act=" + getActivity());
        menuBuilder.build(this, pager, indicator, menuTabResId, getMenubarResId, getRClass() );
    }

    /**
     * 可以通过重写该方法来重新指定menu tab 样式 , 可选
     */
    protected int getMenuTabResId() {
        return getIntent().getIntExtra("menubar_tab_res_id", 0);
    }

    private int getMenubarResId() {
        return getIntent().getIntExtra("menubar_res_id", 0);
    }

    private Class getRClass() {
        return (Class) getIntent().getSerializableExtra("RClass");
    }


    @Override
    protected int contentViewLayout() {
        return R.layout.component_menu;
    }

    public void setTitle(TitleArg title) {
        new TitleBuilder(getContentView()).setTitle(title);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int i) {}

    @Override
    public void onPageScrollStateChanged(int i) {}

    @Override
    public TitleArg onTitleChange(int position, String title, MenubarItemBean menubarItemBean) {
        return null;
    }

}
