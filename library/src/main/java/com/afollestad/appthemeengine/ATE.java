package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.appthemeengine.customizers.ATETaskDescriptionCustomizer;
import com.afollestad.appthemeengine.processors.Processor;
import com.afollestad.appthemeengine.util.ATEUtil;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.views.PostInflationApplier;
import com.afollestad.appthemeengine.views.ViewInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class ATE extends ATEBase {

    private static final String IGNORE_TAG = "ate_ignore";
    public static final int USE_DEFAULT = Integer.MAX_VALUE;

    protected static <T extends View & PostInflationApplier> void addPostInflationView(T view) {
        if (mPostInflationApply == null)
            mPostInflationApply = new ArrayList<>();
        mPostInflationApply.add(view);
    }

    private static ArrayList<View> mPostInflationApply;

    @SuppressWarnings("unchecked")
    private static void performDefaultProcessing(@NonNull Context context, @NonNull View current, @Nullable String key) {
        if (current.getTag() != null && current.getTag() instanceof String) {
            // Apply default processor to view if view's tag is a String
            Processor processor = getProcessor(null); // gets default processor
            if (processor != null)
                processor.process(context, key, current, null);
        }
    }

    public static Config config(@NonNull Context context, @Nullable String key) {
        return new Config(context, key);
    }

    @SuppressLint("CommitPrefEdits")
    public static boolean didValuesChange(@NonNull Context context, long updateTime, @Nullable String key) {
        return ATE.config(context, key).isConfigured() && Config.prefs(context, key).getLong(Config.VALUES_CHANGED, -1) > updateTime;
    }

    public static void preApply(@NonNull Activity activity, @Nullable String key) {
        didPreApply = activity.getClass();
        synchronized (IGNORE_TAG) {
            if (mPostInflationApply != null) {
                mPostInflationApply.clear();
                mPostInflationApply = null;
            }
        }
        int activityTheme = activity instanceof ATEActivityThemeCustomizer ?
                ((ATEActivityThemeCustomizer) activity).getActivityTheme() : Config.activityTheme(activity, key);
        if (activityTheme != 0) activity.setTheme(activityTheme);

        final LayoutInflater li = activity.getLayoutInflater();
        LayoutInflaterCompat.setFactory(li, new InflationInterceptor(
                activity instanceof ATEActivity ? (ATEActivity) activity : null,
                li,
                activity instanceof AppCompatActivity ? ((AppCompatActivity) activity).getDelegate() : null));
    }

    public static View getRootView(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    @SuppressWarnings("unchecked")
    private static void performMainTheming(@NonNull Activity activity, @Nullable String key) {
        final View rootView = getRootView(activity);
        final boolean rootSetsStatusBarColor = rootView != null && rootView instanceof ViewInterface &&
                ((ViewInterface) rootView).setsStatusBarColor();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            if (!rootSetsStatusBarColor) {
                if (Config.coloredStatusBar(activity, key))
                    window.setStatusBarColor(Config.statusBarColor(activity, key));
                else window.setStatusBarColor(Color.BLACK);
            }
            if (Config.coloredNavigationBar(activity, key))
                window.setNavigationBarColor(Config.navigationBarColor(activity, key));
            else window.setNavigationBarColor(Color.BLACK);
            applyTaskDescription(activity, key);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = activity.getWindow().getDecorView();
            boolean lightStatusEnabled = false;
            if (Config.coloredStatusBar(activity, key)) {
                final int lightStatusMode = Config.lightStatusBarMode(activity, key);
                switch (lightStatusMode) {
                    case Config.LIGHT_STATUS_BAR_OFF:
                    default:
                        break;
                    case Config.LIGHT_STATUS_BAR_ON:
                        lightStatusEnabled = true;
                        break;
                    case Config.LIGHT_STATUS_BAR_AUTO:
                        lightStatusEnabled = ATEUtil.isColorLight(Config.primaryColor(activity, key));
                        break;
                }
            }

            final int systemUiVisibility = decorView.getSystemUiVisibility();
            if (lightStatusEnabled) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        // MD integration
        if (Config.usingMaterialDialogs(activity, key)) {
            final Processor processor = getProcessors().get(MATERIALDIALOGS_PROCESSOR);
            if (processor != null) processor.process(activity, key, null, null);
        }
    }

    public static void apply(@NonNull View view, @Nullable String key) {
        if (view.getContext() == null)
            throw new IllegalStateException("View has no Context, use apply(Context, View, String) instead.");
        apply(view.getContext(), view, key);
    }

    @SuppressWarnings("unchecked")
    public static void apply(@NonNull Context context, @NonNull View view, @Nullable String key) {
        if (IGNORE_TAG.equals(view.getTag())) return;
        performDefaultProcessing(context, view, key);

        final Processor processor = getProcessor(view.getClass());
        if (processor != null) {
            // Apply view theming using processors, if any match
            processor.process(context, key, view, null);
        }
    }

    /**
     * @deprecated use postApply() instead.
     */
    @Deprecated
    public static void apply(@NonNull Activity activity, @Nullable String key) {
        postApply(activity, key);
    }

    @SuppressWarnings("unchecked")
    public static void postApply(@NonNull Activity activity, @Nullable String key) {
        if (didPreApply == null)
            preApply(activity, key);
        performMainTheming(activity, key);

        final View rootView = getRootView(activity);
        final boolean rootSetsToolbarColor = rootView != null && rootView instanceof ViewInterface &&
                ((ViewInterface) rootView).setsToolbarColor();

        if (!rootSetsToolbarColor && Config.coloredActionBar(activity, key)) {
            if (activity instanceof AppCompatActivity) {
                final AppCompatActivity aca = (AppCompatActivity) activity;
                if (aca.getSupportActionBar() != null) {
                    Processor toolbarProcessor = getProcessor(Toolbar.class);
                    if (toolbarProcessor != null) {
                        // The processor handles retrieving the toolbar from the support action bar
                        toolbarProcessor.process(activity, key, null, null);
                    }
                }
            } else if (activity.getActionBar() != null) {
                activity.getActionBar().setBackgroundDrawable(new ColorDrawable(Config.toolbarColor(activity, key, null)));
            }
        }

        if (mPostInflationApply != null) {
            synchronized (IGNORE_TAG) {
                for (View view : mPostInflationApply)
                    ((PostInflationApplier) view).postApply();
            }
        }
    }

//    public static void apply(@NonNull android.support.v4.app.Fragment fragment, @Nullable String key) {
//        if (fragment.getActivity() == null)
//            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
//        final View fragmentView = fragment.getView();
//        if (fragmentView == null)
//            throw new IllegalStateException("Fragment does not have a View yet.");
//        if (fragmentView instanceof ViewGroup)
//            apply(fragment.getActivity(), (ViewGroup) fragmentView, key);
//        else apply(fragment.getActivity(), fragmentView, key);
//        if (fragment.getActivity() instanceof AppCompatActivity)
//            apply(fragment.getActivity(), key);
//    }

//    public static void apply(@NonNull android.app.Fragment fragment, @Nullable String key) {
//        if (fragment.getActivity() == null)
//            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
//        else if (fragment.getView() == null)
//            throw new IllegalStateException("Fragment does not have a View yet.");
//        apply(fragment.getActivity(), (ViewGroup) fragment.getView(), key);
//        if (fragment.getActivity() instanceof AppCompatActivity)
//            apply(fragment.getActivity(), key);
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void applyTaskDescription(@NonNull Activity activity, @Nullable String key) {
        int color = 0;
        Bitmap icon = null;
        if (activity instanceof ATETaskDescriptionCustomizer) {
            final ATETaskDescriptionCustomizer customizer = (ATETaskDescriptionCustomizer) activity;
            color = customizer.getTaskDescriptionColor();
            icon = customizer.getTaskDescriptionIcon();
        }
        if (color == ATE.USE_DEFAULT)
            color = Config.primaryColor(activity, key);

        // Task description requires fully opaque color
        color = ATEUtil.stripAlpha(color);
        // Default is app's launcher icon
        if (icon == null)
            icon = ((BitmapDrawable) activity.getApplicationInfo().loadIcon(activity.getPackageManager())).getBitmap();

        // Sets color of entry in the system recents page
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(
                (String) activity.getTitle(), icon, color);
        activity.setTaskDescription(td);
    }

    @Nullable
    private static Toolbar getPostInflationToolbar() {
        synchronized (IGNORE_TAG) {
            for (View view : mPostInflationApply) {
                if (view instanceof Toolbar)
                    return (Toolbar) view;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static void applyMenu(@NonNull Activity activity, @Nullable String key, @Nullable Menu menu) {
        Processor toolbarProcessor = getProcessor(Toolbar.class);
        if (toolbarProcessor != null) {
            final Toolbar postInflationToolbar = getPostInflationToolbar();
            toolbarProcessor.process(activity, key, postInflationToolbar, menu);
        }
    }

    public static void applyOverflow(@NonNull AppCompatActivity activity, @Nullable String key) {
        final Toolbar postInflationToolbar = getPostInflationToolbar();
        final Toolbar toolbar = postInflationToolbar != null ?
                postInflationToolbar : ATEUtil.getSupportActionBarView(activity.getSupportActionBar());
        applyOverflow(activity, key, toolbar);
    }

    public static void applyOverflow(final @NonNull Activity activity, final @Nullable String key, final @Nullable Toolbar toolbar) {
        if (toolbar == null) return;
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field f1 = Toolbar.class.getDeclaredField("mMenuView");
                    f1.setAccessible(true);
                    ActionMenuView actionMenuView = (ActionMenuView) f1.get(toolbar);
                    Field f2 = ActionMenuView.class.getDeclaredField("mPresenter");
                    f2.setAccessible(true);

                    // Actually ActionMenuPresenter
                    BaseMenuPresenter presenter = (BaseMenuPresenter) f2.get(actionMenuView);
                    Field f3 = presenter.getClass().getDeclaredField("mOverflowPopup");
                    f3.setAccessible(true);
                    MenuPopupHelper overflowMenuPopupHelper = (MenuPopupHelper) f3.get(presenter);
                    setTintForMenuPopupHelper(activity, overflowMenuPopupHelper, key);

                    Field f4 = presenter.getClass().getDeclaredField("mActionButtonPopup");
                    f4.setAccessible(true);
                    MenuPopupHelper subMenuPopupHelper = (MenuPopupHelper) f4.get(presenter);
                    setTintForMenuPopupHelper(activity, subMenuPopupHelper, key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void setTintForMenuPopupHelper(final @NonNull Activity context, @Nullable MenuPopupHelper menuPopupHelper, final @Nullable String key) {
        if (menuPopupHelper != null) {
            final ListView listView = menuPopupHelper.getPopup().getListView();
            listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        Field checkboxField = ListMenuItemView.class.getDeclaredField("mCheckBox");
                        checkboxField.setAccessible(true);
                        Field radioButtonField = ListMenuItemView.class.getDeclaredField("mRadioButton");
                        radioButtonField.setAccessible(true);

                        final boolean isDark = !ATEUtil.isColorLight(ATEUtil.resolveColor(context, android.R.attr.windowBackground));

                        for (int i = 0; i < listView.getChildCount(); i++) {
                            View v = listView.getChildAt(i);
                            if (!(v instanceof ListMenuItemView)) continue;
                            ListMenuItemView iv = (ListMenuItemView) v;

                            CheckBox check = (CheckBox) checkboxField.get(iv);
                            if (check != null) {
                                TintHelper.setTint(check, Config.accentColor(context, key), isDark);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    check.setBackground(null);
                            }

                            RadioButton radioButton = (RadioButton) radioButtonField.get(iv);
                            if (radioButton != null) {
                                TintHelper.setTint(radioButton, Config.accentColor(context, key), isDark);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    radioButton.setBackground(null);
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        //noinspection deprecation
                        listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    private ATE() {
    }
}