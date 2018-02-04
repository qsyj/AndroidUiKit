package com.wqlin.android.uikit.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 汪倾林 on 2018/2/4.
 */

public class ItemDecorationDrawable extends Drawable {
    private Paint mPaint;

    private int color;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    public ItemDecorationDrawable() {
        this(Color.TRANSPARENT);
    }

    public ItemDecorationDrawable(int color) {
        this(color,0, 0);
    }

    public ItemDecorationDrawable(int color,int paddingLeft, int paddingTop) {
        this(color,paddingLeft, paddingTop, 0, 0);
    }

    public ItemDecorationDrawable(int color,int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        init(color,paddingLeft,paddingTop,paddingRight,paddingBottom);
    }

    private void init(int color,int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.color = color;
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;

        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        Rect rect = new Rect();
        rect.set(bounds.left + paddingLeft, bounds.top + paddingTop,
                bounds.right - paddingRight, bounds.bottom - paddingBottom);
        canvas.drawRect(rect,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return getBounds().right-getBounds().left;
    }

    @Override
    public int getIntrinsicHeight() {
        return getBounds().bottom-getBounds().top;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
    }

    public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }
}
