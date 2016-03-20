package ru.nbsp.pushka.presentation.subscription.params;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egor on 16.03.16.
 */
public class DictLayout extends LinearLayout {


    Map<String, View> views = new HashMap<>();

    public DictLayout(Context context) {
        this(context, null);
    }

    public DictLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public View get(String key) {
        return views.get(key);
    }

    public void addPair(String key, View view) {
        views.put(key, view);
        onViewsChanged();
    }

    public void onViewsChanged() {
        removeAllViews();
        for(View view : views.values()) {
            addView(view);
        }
    }
}
