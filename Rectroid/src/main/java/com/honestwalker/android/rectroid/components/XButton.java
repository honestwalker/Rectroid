package com.honestwalker.android.rectroid.components;

import android.content.Context;
import android.widget.Button;

import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.R;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XButton extends Component {

    private Button button;

    public XButton(Context context) {
        super(context);
        initComponent();
    }

    @Override
    protected void onRegisterRectView() {
    }

    @Override
    protected void initComponent() {
        button = (Button) findViewById(R.id.btn1);
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_commont_button;
    }

    public void setText(String text) {
        button.setText(text);
    }

    public void setOnClickListener(OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
