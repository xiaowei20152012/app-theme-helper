package com.afollestad.appthemeengine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.afollestad.appthemeengine.processors.DefaultProcessor;
import com.afollestad.appthemeengine.processors.DrawerLayoutProcessor;
import com.afollestad.appthemeengine.processors.ListViewProcessor;
import com.afollestad.appthemeengine.processors.MaterialDialogsProcessor;
import com.afollestad.appthemeengine.processors.NavigationViewProcessor;
import com.afollestad.appthemeengine.processors.NestedScrollViewProcessor;
import com.afollestad.appthemeengine.processors.Processor;
import com.afollestad.appthemeengine.processors.RecyclerViewProcessor;
import com.afollestad.appthemeengine.processors.ScrollViewProcessor;
import com.afollestad.appthemeengine.processors.SearchViewProcessor;
import com.afollestad.appthemeengine.processors.TabLayoutProcessor;
import com.afollestad.appthemeengine.processors.ToolbarProcessor;
import com.afollestad.appthemeengine.processors.ViewPagerProcessor;
import com.afollestad.appthemeengine.util.ATEUtil;

import java.util.HashMap;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEBase {

    protected final static String DEFAULT_PROCESSOR = "[default]";
    protected final static String MATERIALDIALOGS_PROCESSOR = "[material-dialogs]";

    private static HashMap<String, Processor> mProcessors;

    private static void initProcessors() {
        mProcessors = new HashMap<>();
        mProcessors.put(DEFAULT_PROCESSOR, new DefaultProcessor());
        if (ATEUtil.isInClassPath(MaterialDialogsProcessor.MAIN_CLASS))
            mProcessors.put(MATERIALDIALOGS_PROCESSOR, new MaterialDialogsProcessor());
        else Log.d("ATEBase", "MaterialDialogs isn't in the class path. Ignoring.");

        mProcessors.put(ScrollView.class.getName(), new ScrollViewProcessor());
        mProcessors.put(ListView.class.getName(), new ListViewProcessor());
        mProcessors.put(SearchView.class.getName(), new SearchViewProcessor());
        mProcessors.put(Toolbar.class.getName(), new ToolbarProcessor());

        if (ATEUtil.isInClassPath(NestedScrollViewProcessor.MAIN_CLASS))
            mProcessors.put(NestedScrollViewProcessor.MAIN_CLASS, new NestedScrollViewProcessor());
        else Log.d("ATEBase", "NestedScrollView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(RecyclerViewProcessor.MAIN_CLASS))
            mProcessors.put(RecyclerViewProcessor.MAIN_CLASS, new RecyclerViewProcessor());
        else Log.d("ATEBase", "RecyclerView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(NavigationViewProcessor.MAIN_CLASS))
            mProcessors.put(NavigationViewProcessor.MAIN_CLASS, new NavigationViewProcessor());
        else Log.d("ATEBase", "NavigationView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(DrawerLayoutProcessor.MAIN_CLASS))
            mProcessors.put(DrawerLayoutProcessor.MAIN_CLASS, new DrawerLayoutProcessor());
        else Log.d("ATEBase", "DrawerLayout isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(TabLayoutProcessor.MAIN_CLASS))
            mProcessors.put(TabLayoutProcessor.MAIN_CLASS, new TabLayoutProcessor());
        else Log.d("ATEBase", "TabLayout isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(SearchViewProcessor.MAIN_CLASS))
            mProcessors.put(SearchViewProcessor.MAIN_CLASS, new SearchViewProcessor());
        else Log.d("ATEBase", "SearchView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(ViewPagerProcessor.MAIN_CLASS))
            mProcessors.put(ViewPagerProcessor.MAIN_CLASS, new ViewPagerProcessor());
        else Log.d("ATEBase", "ViewPager isn't in the class path. Ignoring.");
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends View> Processor<T, ?> getProcessor(@Nullable Class<T> viewClass) {
        if (mProcessors == null)
            initProcessors();
        if (viewClass == null)
            return mProcessors.get(DEFAULT_PROCESSOR);
        Processor processor = mProcessors.get(viewClass.getName());
        if (processor != null)
            return processor;
        Class<?> current = viewClass;
        while (true) {
            current = current.getSuperclass();
            if (current == null) break;
            processor = mProcessors.get(current.getName());
            if (processor != null) break;
        }
        return processor;
    }

    public static HashMap<String, Processor> getProcessors() {
        if (mProcessors == null)
            initProcessors();
        return mProcessors;
    }

    public static <T extends View> void registerProcessor(@NonNull Class<T> viewCls, @NonNull Processor<T, ?> processor) {
        if (mProcessors == null)
            initProcessors();
        mProcessors.put(viewCls.getName(), processor);
    }

    protected static Class<?> didPreApply = null;
}