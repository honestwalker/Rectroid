package com.honestwalker.android.rectroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;

import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.R;
import com.honestwalker.androidutils.commons.adapter.BaseArrayAdapter;
import com.stay.pull.lib.PullToRefreshBase;
import com.stay.pull.lib.PullToRefreshListView;

/**
 * 简单列表组件
 * Created by lanzhe on 16-11-18.
 */
public class RefreshListViewComponent extends Component {

    private PullToRefreshListView listView;

    private BaseArrayAdapter adapter;

    private PullToRefreshBase.OnRefreshListener onRefreshListener;

    public RefreshListViewComponent(Context context) {
        super(context);
    }

    public RefreshListViewComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRegisterRectView() {
        registerRectView(R.id.content);
    }

    @Override
    protected void initComponent() {
        listView = bind(R.id.content, PullToRefreshListView.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        listView.getRefreshableView().setDividerHeight(0);
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.component_simple_listview;
    }

    public void setAdapter(BaseArrayAdapter adapter) {
        listView.getRefreshableView().setAdapter(adapter);
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.getRefreshableView().setOnItemClickListener(listener);
    }

    /**
     * 设置下拉事件
     * @param listener
     */
    public void setOnRefreshListener(PullToRefreshBase.OnRefreshListener listener) {
        this.onRefreshListener = listener;
        listView.setOnRefreshListener(this.onRefreshListener);
    }

    /**
     * 设置是否允许下拉刷新
     * @param enabled
     */
    public void setPullToRefreshEnabled(boolean enabled) {
        listView.setPullToRefreshEnabled(enabled);

    }

    /**
     * 刷新完毕
     */
    public void onRefreshComplete() {
        listView.onRefreshComplete();
    }

    /**
     * 是否正在刷新
     * @return
     */
    public boolean isRefreshing() {
        return listView.isRefreshing();
    }

}
