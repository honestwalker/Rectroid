package com.honestwalker.android.xview.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.R;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XEditText extends Component {

    private EditText editText;

    private View cleanBTN;

    public XEditText(Context context) {
        super(context);
    }

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initComponent() {
        editText = (EditText) findViewById(R.id.edittext1);
        cleanBTN = findViewById(R.id.btn1);
        textChange();
        cleanText();
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_xedittext;
    }

    public void setText(String content) {
        editText.setText(content);
    }

    public void setHint(String content) {
        editText.setHint(content);
    }

    public Editable getText() {
        return editText.getText();
    }

    public CharSequence getHint() {
        return editText.getHint();
    }

    private void cleanText() {
        cleanBTN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setText("");
                cleanBTN.setVisibility(View.GONE);
            }
        });
    }

    private void textChange() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    cleanBTN.setVisibility(View.VISIBLE);
                } else {
                    cleanBTN.setVisibility(View.GONE);
                }
            }
        });
    }

}
