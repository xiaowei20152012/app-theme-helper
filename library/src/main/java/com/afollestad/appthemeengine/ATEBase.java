package com.afollestad.appthemeengine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.afollestad.appthemeengine.tagprocessors.BackgroundTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.FontTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TextColorTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TextShadowColorTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TextSizeTagProcessor;
import com.afollestad.appthemeengine.tagprocessors.TintTagProcessor;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.viewprocessors.DefaultProcessor;
import com.afollestad.appthemeengine.viewprocessors.ListViewProcessor;
import com.afollestad.appthemeengine.viewprocessors.NavigationViewProcessor;
import com.afollestad.appthemeengine.viewprocessors.NestedScrollViewProcessor;
import com.afollestad.appthemeengine.viewprocessors.RecyclerViewProcessor;
import com.afollestad.appthemeengine.viewprocessors.ScrollViewProcessor;
import com.afollestad.appthemeengine.viewprocessors.SearchViewProcessor;
import com.afollestad.appthemeengine.viewprocessors.TabLayoutProcessor;
import com.afollestad.appthemeengine.viewprocessors.ToolbarProcessor;
import com.afollestad.appthemeengine.viewprocessors.ViewPagerProcessor;
import com.afollestad.appthemeengine.viewprocessors.ViewProcessor;

import java.util.HashMap;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEBase {

    protected final static String DEFAULT_PROCESSOR = "[default]";

    private static HashMap<String, ViewProcessor> mViewProcessors;
    private static HashMap<String, TagProcessor> mTagProcessors;

    private static void initViewProcessors() {
        mViewProcessors = new HashMap<>();
        mViewProcessors.put(DEFAULT_PROCESSOR, new DefaultProcessor());

        mViewProcessors.put(ScrollView.class.getName(), new ScrollViewProcessor());
        mViewProcessors.put(ListView.class.getName(), new ListViewProcessor());
        mViewProcessors.put(SearchView.class.getName(), new SearchViewProcessor());
        mViewProcessors.put(Toolbar.class.getName(), new ToolbarProcessor());

        if (ATEUtil.isInClassPath(NestedScrollViewProcessor.MAIN_CLASS))
            mViewProcessors.put(NestedScrollViewProcessor.MAIN_CLASS, new NestedScrollViewProcessor());
        else Log.d("ATEBase", "NestedScrollView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(RecyclerViewProcessor.MAIN_CLASS))
            mViewProcessors.put(RecyclerViewProcessor.MAIN_CLASS, new RecyclerViewProcessor());
        else Log.d("ATEBase", "RecyclerView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(NavigationViewProcessor.MAIN_CLASS))
            mViewProcessors.put(NavigationViewProcessor.MAIN_CLASS, new NavigationViewProcessor());
        else Log.d("ATEBase", "NavigationView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(TabLayoutProcessor.MAIN_CLASS))
            mViewProcessors.put(TabLayoutProcessor.MAIN_CLASS, new TabLayoutProcessor());
        else Log.d("ATEBase", "TabLayout isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(SearchViewProcessor.MAIN_CLASS))
            mViewProcessors.put(SearchViewProcessor.MAIN_CLASS, new SearchViewProcessor());
        else Log.d("ATEBase", "SearchView isn't in the class path. Ignoring.");
        if (ATEUtil.isInClassPath(ViewPagerProcessor.MAIN_CLASS))
            mViewProcessors.put(ViewPagerProcessor.MAIN_CLASS, new ViewPagerProcessor());
        else Log.d("ATEBase", "ViewPager isn't in the class path. Ignoring.");
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends View> ViewProcessor<T, ?> getViewProcessor(@Nullable Class<T> viewClass) {
        if (mViewProcessors == null)
            initViewProcessors();
        if (viewClass == null)
            return mViewProcessors.get(DEFAULT_PROCESSOR);
        ViewProcessor viewProcessor = mViewProcessors.get(viewClass.getName());
        if (viewProcessor != null)
            return viewProcessor;
        Class<?> current = viewClass;
        while (true) {
            current = current.getSuperclass();
            if (current == null) break;
            viewProcessor = mViewProcessors.get(current.getName());
            if (viewProcessor != null) break;
        }
        return viewProcessor;
    }

    public static <T extends View> void registerViewProcessor(@NonNull Class<T> viewCls, @NonNull ViewProcessor<T, ?> viewProcessor) {
        if (mViewProcessors == null)
            initViewProcessors();
        mViewProcessors.put(viewCls.getName(), viewProcessor);
    }

    private static void initTagProcessors() {
        mTagProcessors = new HashMap<>();
        mTagProcessors.put(BackgroundTagProcessor.PREFIX, new BackgroundTagProcessor());
        mTagProcessors.put(FontTagProcessor.PREFIX, new FontTagProcessor());
        mTagProcessors.put(TextColorTagProcessor.PREFIX,
                new TextColorTagProcessor(TextColorTagProcessor.PREFIX, false, false));
        mTagProcessors.put(TextColorTagProcessor.LINK_PREFIX,
                new TextColorTagProcessor(TextColorTagProcessor.LINK_PREFIX, true, false));
        mTagProcessors.put(TextColorTagProcessor.LINK_PREFIX,
                new TextColorTagProcessor(TextColorTagProcessor.HINT_PREFIX, false, true));
        mTagProcessors.put(TextShadowColorTagProcessor.PREFIX, new TextShadowColorTagProcessor());
        mTagProcessors.put(TextSizeTagProcessor.PREFIX, new TextSizeTagProcessor());
        mTagProcessors.put(TintTagProcessor.PREFIX,
                new TintTagProcessor(TintTagProcessor.PREFIX, false, false, false));
        mTagProcessors.put(TintTagProcessor.BACKGROUND_PREFIX,
                new TintTagProcessor(TintTagProcessor.BACKGROUND_PREFIX, true, false, false));
        mTagProcessors.put(TintTagProcessor.SELECTOR_PREFIX,
                new TintTagProcessor(TintTagProcessor.SELECTOR_PREFIX, false, true, false));
        mTagProcessors.put(TintTagProcessor.SELECTOR_PREFIX_LIGHT,
                new TintTagProcessor(TintTagProcessor.SELECTOR_PREFIX_LIGHT, false, true, true));
    }

    @Nullable
    public static TagProcessor getTagProcessor(@NonNull String prefix) {
        if (mTagProcessors == null)
            initTagProcessors();
        return mTagProcessors.get(prefix);
    }

    public static void registerTagProcessor(@NonNull String prefix, @NonNull TagProcessor tagProcessor) {
        if (mTagProcessors == null)
            initTagProcessors();
        mTagProcessors.put(prefix, tagProcessor);
    }

    protected static Class<?> didPreApply = null;
}