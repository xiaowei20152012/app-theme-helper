package com.kabouzeid.appthemehelpersample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
public class StatusbarLayout extends FrameLayout {

    private Drawable statusbarDrawable;

    private Rect statusbarBounds;

    private int statusbarInsetHeight;

    private WindowInsets windowInsets;

    public StatusbarLayout(Context context) {
        this(context, null);
    }

    public StatusbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(true); // No need to draw until the insets are adjusted
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusbarInsetHeight = insets.getSystemWindowInsetTop();
            setWillNotDraw(statusbarInsetHeight <= 0 || statusbarDrawable == null);
            ViewCompat.postInvalidateOnAnimation(StatusbarLayout.this);
            this.windowInsets = insets;
        }
        return super.onApplyWindowInsets(insets);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ImageView view = (ImageView) findViewById(R.id.image);
        if (view != null) {
            view.getDrawable().setBounds(0, 0, w, h);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        if (statusbarBounds == null) {
            statusbarBounds = new Rect();
        }
        statusbarBounds.set(0, 0, getWidth(), statusbarInsetHeight);

        if (statusbarBounds != null && statusbarDrawable != null) {
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());

            // Top
            statusbarDrawable.setBounds(statusbarBounds);
            statusbarDrawable.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (statusbarDrawable != null) {
            statusbarDrawable.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (statusbarDrawable != null) {
            statusbarDrawable.setCallback(null);
        }
    }

    public void setStatusbarColor(int color) {
        this.statusbarDrawable = new ColorDrawable(color);
        if (statusbarBounds != null) {
            invalidate(statusbarBounds);
        }
    }
}
