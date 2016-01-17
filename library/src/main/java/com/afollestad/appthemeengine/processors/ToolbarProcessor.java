package com.afollestad.appthemeengine.processors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEMenuPresenterCallback;
import com.afollestad.appthemeengine.ATEOnMenuItemClickListener;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.customizers.ATECollapsingTbCustomizer;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ToolbarProcessor implements Processor<Toolbar, Menu> {

    @SuppressWarnings("unchecked")
    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable Toolbar toolbar, @Nullable Menu menu) {
        if (toolbar == null && context instanceof AppCompatActivity)
            toolbar = Util.getSupportActionBarView(((AppCompatActivity) context).getSupportActionBar());

        final int toolbarColor = Config.toolbarColor(context, key, toolbar);
        if (toolbar == null) {
            // No toolbar view, Activity might have another variation of Support ActionBar (e.g. window decor vs toolbar)
            if (context instanceof AppCompatActivity) {
                final ActionBar ab = ((AppCompatActivity) context).getSupportActionBar();
                if (ab != null) ab.setBackgroundDrawable(new ColorDrawable(toolbarColor));
            }
            return;
        }

        if (menu == null)
            menu = toolbar.getMenu();
        final int tintColor = Config.getToolbarTitleColor(context, toolbar, key, toolbarColor);

        CollapsingToolbarLayout collapsingToolbar = null;
        if (toolbar.getParent() instanceof CollapsingToolbarLayout) {
            // Reset support action bar background to transparent in case it was set to something else previously
            if (context instanceof AppCompatActivity) {
                final ActionBar ab = ((AppCompatActivity) context).getSupportActionBar();
                if (ab != null) ab.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            collapsingToolbar = (CollapsingToolbarLayout) toolbar.getParent();
            collapsingToolbar.setStatusBarScrimColor(Config.statusBarColor(context, key));
            collapsingToolbar.setContentScrim(new ColorDrawable(toolbarColor));

            if (collapsingToolbar.getParent() instanceof AppBarLayout) {
                AppBarLayout appbarLayout = (AppBarLayout) collapsingToolbar.getParent();
                try {
                    if (mCollapsingToolbarListener == null)
                        mCollapsingToolbarListener = new ScrimsOffsetListener(context, key, toolbar, collapsingToolbar, menu);
                    else appbarLayout.removeOnOffsetChangedListener(mCollapsingToolbarListener);
                    appbarLayout.addOnOffsetChangedListener(mCollapsingToolbarListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (toolbar.getParent() instanceof AppBarLayout) {
            Util.setBackgroundCompat((View) toolbar.getParent(), new ColorDrawable(toolbarColor));
        } else {
            Util.setBackgroundCompat(toolbar, new ColorDrawable(toolbarColor));
        }


        // Tint the toolbar title
        if (collapsingToolbar != null)
            collapsingToolbar.setCollapsedTitleTextColor(tintColor);
        else
            toolbar.setTitleTextColor(tintColor);

        // Tint the toolbar navigation icon (e.g. back, drawer, etc.), otherwise handled by CollapsingToolbarLayout listener above
        if (collapsingToolbar == null && toolbar.getNavigationIcon() != null)
            toolbar.setNavigationIcon(TintHelper.tintDrawable(toolbar.getNavigationIcon(), tintColor));

        if (collapsingToolbar == null) {
            // If collapsing toolbar didn't take care of menu tinting initially, do it now
            tintMenu(context, toolbar, key, menu, tintColor);
        }

        if (context instanceof Activity) {
            if (collapsingToolbar == null) {
                // Set color of the overflow icon, otherwise handled by CollapsingToolbarLayout listener above
                Util.setOverflowButtonColor((Activity) context, tintColor);
            }

            try {
                // Tint immediate overflow menu items
                final Field menuField = Toolbar.class.getDeclaredField("mMenuBuilderCallback");
                menuField.setAccessible(true);
                final Field presenterField = Toolbar.class.getDeclaredField("mActionMenuPresenterCallback");
                presenterField.setAccessible(true);
                final Field menuViewField = Toolbar.class.getDeclaredField("mMenuView");
                menuViewField.setAccessible(true);
                final MenuPresenter.Callback currentPresenterCb = (MenuPresenter.Callback) presenterField.get(toolbar);
                if (!(currentPresenterCb instanceof ATEMenuPresenterCallback)) {
                    final ATEMenuPresenterCallback newPresenterCb = new ATEMenuPresenterCallback(
                            (Activity) context, key, currentPresenterCb, toolbar);
                    final MenuBuilder.Callback currentMenuCb = (MenuBuilder.Callback) menuField.get(toolbar);
                    toolbar.setMenuCallbacks(newPresenterCb, currentMenuCb);
                    ActionMenuView menuView = (ActionMenuView) menuViewField.get(toolbar);
                    if (menuView != null)
                        menuView.setMenuCallbacks(newPresenterCb, currentMenuCb);
                }

                // OnMenuItemClickListener to tint submenu items
                final Field menuItemClickListener = Toolbar.class.getDeclaredField("mOnMenuItemClickListener");
                menuItemClickListener.setAccessible(true);
                Toolbar.OnMenuItemClickListener currentClickListener = (Toolbar.OnMenuItemClickListener) menuItemClickListener.get(toolbar);
                if (!(currentClickListener instanceof ATEOnMenuItemClickListener)) {
                    final ATEOnMenuItemClickListener newClickListener = new ATEOnMenuItemClickListener(
                            (Activity) context, key, currentClickListener, toolbar);
                    toolbar.setOnMenuItemClickListener(newClickListener);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void tintMenu(@NonNull Context context, @NonNull Toolbar toolbar, @Nullable String key, @Nullable Menu menu, @ColorInt int tintColor) {
        try {
            final Field field = Toolbar.class.getDeclaredField("mCollapseIcon");
            field.setAccessible(true);
            Drawable collapseIcon = (Drawable) field.get(toolbar);
            if (collapseIcon != null)
                field.set(toolbar, TintHelper.tintDrawable(collapseIcon, tintColor));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (menu != null && menu.size() > 0) {
            for (int i = 0; i < menu.size(); i++) {
                final MenuItem item = menu.getItem(i);
                if (item.getIcon() != null)
                    item.setIcon(TintHelper.tintDrawable(item.getIcon(), tintColor));
                // Search view theming
                if (item.getActionView() != null &&
                        (item.getActionView() instanceof android.widget.SearchView ||
                                item.getActionView() instanceof android.support.v7.widget.SearchView)) {
                    Processor processor = ATE.getProcessor(android.support.v7.widget.SearchView.class);
                    if (processor != null)
                        processor.process(context, key, item.getActionView(), tintColor);
                }
            }
        }
    }

    private static ScrimsOffsetListener mCollapsingToolbarListener = null;

    public static class ScrimsOffsetListener implements AppBarLayout.OnOffsetChangedListener {

        private final Paint mTextPaint;
        private final Field mScrimsAreShown;

        @NonNull
        private final Context mContext;
        @Nullable
        private final String mKey;
        private Toolbar mToolbar;
        private final CollapsingToolbarLayout mCollapsingToolbar;
        private Menu mMenu;

        private int mCollapsedColor;
        private int mExpandedColor;

        public ScrimsOffsetListener(@NonNull Context context, @Nullable String key, Toolbar toolbar,
                                    CollapsingToolbarLayout toolbarLayout, Menu menu) throws Exception {
            mContext = context;
            mKey = key;
            mToolbar = toolbar;
            mCollapsingToolbar = toolbarLayout;
            mMenu = menu;

            Field mCollapsingTextHelperField = CollapsingToolbarLayout.class.getDeclaredField("mCollapsingTextHelper");
            mCollapsingTextHelperField.setAccessible(true);
            Object textHelper = mCollapsingTextHelperField.get(mCollapsingToolbar);
            Field mTextPaintField = textHelper.getClass().getDeclaredField("mTextPaint");
            mTextPaintField.setAccessible(true);
            mTextPaint = (Paint) mTextPaintField.get(textHelper);

            if (context instanceof ATECollapsingTbCustomizer) {
                mScrimsAreShown = CollapsingToolbarLayout.class.getDeclaredField("mScrimsAreShown");
                mScrimsAreShown.setAccessible(true);

                final ATECollapsingTbCustomizer customizer = (ATECollapsingTbCustomizer) mContext;
                mCollapsedColor = customizer.getCollapsedTintColor();
                mExpandedColor = customizer.getExpandedTintColor();

                if (mCollapsedColor == ATE.USE_DEFAULT || mExpandedColor == ATE.USE_DEFAULT) {
                    final int tintColor = Config.getToolbarTitleColor(context, toolbar, key);
                    if (mCollapsedColor == ATE.USE_DEFAULT) mCollapsedColor = tintColor;
                    if (mExpandedColor == ATE.USE_DEFAULT) mExpandedColor = tintColor;
                }
            } else {
                mScrimsAreShown = null;
            }

            invalidateMenu();
        }

        private boolean scrimsAreShown() {
            try {
                return mScrimsAreShown.getBoolean(mCollapsingToolbar);
            } catch (IllegalAccessException e) {
                return true;
            }
        }

        private void invalidateMenu() {
            final int tintColor;
            if (mScrimsAreShown != null) {
                tintColor = scrimsAreShown() ? mCollapsedColor : mExpandedColor;
            } else {
                tintColor = mTextPaint.getColor();
            }

            if (mToolbar.getNavigationIcon() != null)
                mToolbar.setNavigationIcon(TintHelper.tintDrawable(mToolbar.getNavigationIcon(), tintColor));
            tintMenu(mContext, mToolbar, mKey, mMenu, tintColor);
            if (mContext instanceof Activity) {
                // Set color of the overflow icon
                Util.setOverflowButtonColor((Activity) mContext, tintColor);
            }
        }

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            invalidateMenu();
        }
    }
}