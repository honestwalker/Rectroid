package com.honestwalker.android.xview.components.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.honestwalker.android.xview.R;
import com.honestwalker.androidutils.commons.adapter.BaseArrayAdapter;
import com.honestwalker.androidutils.commons.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lanzhe on 16-11-18.
 */
public class SimpleListViewAdapter extends BaseArrayAdapter<String> {

    public SimpleListViewAdapter(Context context, List<String> data) {
        super(context, R.layout.listitem_simple_text, data);
    }

    @Override
    protected void addItemData(View convertView, String item, int position) {
        ViewHolder viewHolder = getViewHolder(convertView, ViewHolder.class);
        viewHolder.textView.setText(item);
    }

    private class ViewHolder extends BaseViewHolder {

        public TextView textView;

        public ViewHolder(View baseView) {
            super(baseView);
            textView = (TextView) findViewById(android.R.id.text1);
            textView.setTextSize(12);
            textView.setTextColor(getContext().getResources().getColor(android.R.color.black));
        }

    }

}
