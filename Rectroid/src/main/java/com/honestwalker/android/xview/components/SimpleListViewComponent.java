package com.honestwalker.android.xview.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;

import com.honestwalker.android.xview.Component;
import com.honestwalker.android.xview.ComponentUtil;
import com.honestwalker.android.xview.components.adapter.SimpleListViewAdapter;
import com.honestwalker.android.xview.R;
import com.stay.pull.lib.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanzhe on 16-11-18.
 */
public class SimpleListViewComponent extends Component {

    private PullToRefreshListView listView;

    private SimpleListViewAdapter adapter;

    private List<String> dataSource = new ArrayList<>();

    public SimpleListViewComponent(Context context) {
        super(context);
    }

    public SimpleListViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleListViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initComponent() {
        listView = ComponentUtil.getComponent(this, getContentView(), R.id.content, PullToRefreshListView.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(this.dataSource == null) this.dataSource = new ArrayList<>();
        adapter = new SimpleListViewAdapter(getContext(), this.dataSource);
        listView.getRefreshableView().setAdapter(adapter);
        listView.getRefreshableView().setDividerHeight(0);
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.component_simple_listview;
    }

    public List<String> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<String> dataSource) {
        if(dataSource == null) {
            this.dataSource.clear();
        } else {
            this.dataSource.clear();
            this.dataSource.addAll(dataSource);
        }
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.getRefreshableView().setOnItemClickListener(listener);
    }

}
