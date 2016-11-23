package com.honestwalker.android.rectroid;

import android.view.View;

import java.util.HashMap;

public class ComponentBind {

    private HashMap<Integer, Class> componentMapping = new HashMap<>();

    public ComponentBind bind(int resId, Class<? extends View> componentClass) {
        componentMapping.put(resId, componentClass);
        return this;
    }

    public Class getComponentClass(int key) {
        return componentMapping.get(key);
    }

    public HashMap<Integer, Class> getComponentBind() {
        return componentMapping;
    }

}
