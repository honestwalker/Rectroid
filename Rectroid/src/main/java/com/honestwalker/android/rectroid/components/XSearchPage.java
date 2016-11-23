package com.honestwalker.android.rectroid.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.honestwalker.android.rectroid.Component;
import com.honestwalker.android.rectroid.ComponentUtil;
import com.honestwalker.android.rectroid.R;
import com.honestwalker.androidutils.IO.SharedPreferencesLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanzhe on 16-11-16.
 */
public class XSearchPage extends Component {

    private XSearchBar searchBar;

    private SimpleListViewComponent searchHistory;

    private final int MAX_SEARCH_HISTORY = 10;

    private XSearchBar.OnSearch onSearch;

    private OnSearchHistoryClickListener searchHistoryClickListener;

    public XSearchPage(Context context) {
        super(context);
    }

    public XSearchPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XSearchPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRegisterRectView() {
        registerRectView(R.id.searchbar);
        registerRectView(R.id.content);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initComponent() {
        searchBar = bind(R.id.searchbar, XSearchBar.class);
        searchHistory = bind(R.id.content, SimpleListViewComponent.class);
    }

    @Override
    protected int contentViewLayout() {
        return R.layout.view_searchpage;
    }

    /**
     * 设置搜索按钮内容
     * @param text 按钮文本
     */
    public void setSearchBtnText(String text) {
        searchBar.setButtonText(text);
    }

    /**
     * 设置输入框内容
     * @param text 输入框内容文本
     */
    public void setSearchText(String text) {
        searchBar.setSearchText(text);
    }

    /**
     * 获取搜索词内容
     * @return
     */
    public Editable getSearchText() {
        return searchBar.getSearchText();
    }

    /**
     * 设置输入框hint
     * @param text 输入框hint文本
     */
    public void setSearchHint(String text) {
        searchBar.setEditHintText(text);
    }

    public XSearchBar getSearchBar() {
        return searchBar;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        updateSearchHistoryList();

        searchBar.onSearch(new XSearchBar.OnSearch() {
            @Override
            public void onSearch(String text) {
                saveSearchHistories(text);
                updateSearchHistoryList();

                if(onSearch != null) {
                    onSearch.onSearch(text);
                }

            }
        });

        searchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(searchHistoryClickListener == null) return;
                List<String> searchHistories = getSearchHistories();
                if(searchHistories != null && searchHistories.size() > position) {
                    searchHistoryClickListener.onClick(searchHistories.get(position));
                    saveSearchHistories(searchHistories.get(position));
                    updateSearchHistoryList();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateSearchHistoryList() {
        searchHistory.setDataSource(getSearchHistories());
        searchHistory.notifyDataSetChanged();
    }

    /**
     * 保存搜索词
     * @param text
     */
    private void saveSearchHistories(String text) {
        List<String> searchHistories = getSearchHistories();
        searchHistories.remove(text);
        searchHistories.add(0, text.trim());
        if(searchHistories.size() > MAX_SEARCH_HISTORY) {
            searchHistories.remove(searchHistories.size() - 1);
        }
        SharedPreferencesLoader.putString("search_history", new Gson().toJson(searchHistories));
    }

    /**
     * 获取搜索记录
     * @return
     */
    private List<String> getSearchHistories() {
        String searchHistoriesJson = SharedPreferencesLoader.getString("search_history", "");
        try {
            List<String> searchHistoryList = new Gson().fromJson(searchHistoriesJson, List.class);
            if(searchHistoryList == null) {
                throw new Exception();
            }
            return searchHistoryList;
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    public void onSearch(XSearchBar.OnSearch onSearch) {
        this.onSearch = onSearch;
    }

    public interface OnSearchHistoryClickListener {
        void onClick(String item);
    }

    public void onSearchHistoryClick(OnSearchHistoryClickListener listener) {

        this.searchHistoryClickListener = listener;

    }

}
