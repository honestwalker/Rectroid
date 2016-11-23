package com.honestwalker.android.rectroid.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.ComponentInject;
import com.honestwalker.android.rectroid.ComponentUtil;
import com.honestwalker.android.rectroid.R;
import com.honestwalker.androidutils.ViewUtils.ViewSizeHelper;
import com.honestwalker.androidutils.equipment.DisplayUtil;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XSearchBar extends Component {

    private XButton xButton;
    private XEditText xEditText;

    private OnSearch onSearch;

    public XSearchBar(Context context) {
        super(context);
    }

    public XSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRegisterRectView() {
        registerRectView(R.id.btn1);
        registerRectView(R.id.edittext1);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initComponent() {

        xButton   = bind(R.id.btn1, XButton.class);
        xEditText = bind(R.id.edittext1, XEditText.class);

        ViewSizeHelper.getInstance(getContext()).setWidth(xButton,
                (int) (DisplayUtil.getWidth(getContext()) * 0.15));

        xButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSearch != null) {
                    onSearch.onSearch(xEditText.getText().toString());
                }
            }
        });

    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_searchbar;
    }

    /**
     * 设置按钮内容
     * @param text
     */
    public void setButtonText(String text) {
        xButton.setText(text);
    }

    /**
     * 设置输入框内容
     * @param text
     */
    public void setSearchText(String text) {
        xEditText.setText(text);
    }

    public Editable getSearchText() {
        return xEditText.getText();
    }

    /**
     * 设置输入框hint文字
     * @param text
     */
    public void setEditHintText(String text) {
        xEditText.setHint(text);
    }

    /**
     * 搜索回调
     */
    public interface OnSearch {
        void onSearch(String text);
    }

    public void onSearch(OnSearch onSearch) {
        this.onSearch = onSearch;
    }

}
