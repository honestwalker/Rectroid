package com.honestwalker.android.rectroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XTextView extends TextView {

    public XTextView(Context context) {
        super(context);
        init();
    }

    public XTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setTextSize(getResources().getDimension(com.honestwalker.android.fastroid.R.dimen.text_size));
        this.setTextColor(getResources().getColor(android.R.color.black));
    }

}
