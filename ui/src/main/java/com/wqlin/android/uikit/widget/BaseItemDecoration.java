package com.wqlin.android.uikit.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.wqlin.android.uikit.R;

/**
 * Created by wqlin on 2018/2/4.
 */

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration {
    private boolean isDebug;
    private ItemDecorationDrawable mDivider;

    public BaseItemDecoration() {
        init();
    }

    private void init() {
        createItemDecorationDrawable();
    }

    private ItemDecorationDrawable createItemDecorationDrawable() {
        if (mDivider == null)
            mDivider = new ItemDecorationDrawable();
        return mDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        ItemDecorationConfig config = getItemDecorationConfig(view, parent);
        if (config==null)
            config = new ItemDecorationConfig();
        int left = config.getLeftWidth();
        int right = config.getRightWidth();
        int top = config.getTopHeight();
        int bottom = config.getBottomHeight();
        view.setTag(R.id.item_decoration_view_tag,config);
        outRect.set(left, top, right, bottom);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (c == null || parent == null) return;
        c.save();
        createItemDecorationDrawable();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            drawChild(c, child, parent);
        }
        c.restore();
    }

    public void drawChild(Canvas c, View child, RecyclerView parent) {
        if (c == null || child == null || parent == null) return;
        Object tag = child.getTag(R.id.item_decoration_view_tag);
        if (tag == null) return;
        ItemDecorationConfig config = (ItemDecorationConfig) tag;
        Rect bounds = new Rect();
        RecyclerView.LayoutParams childParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        parent.getDecoratedBoundsWithMargins(child, bounds);
        drawChild(c, Math.round(ViewCompat.getTranslationX(child)), Math.round(ViewCompat.getTranslationY(child)), config, bounds, childParams);
    }

    public void drawChild(Canvas c, int translationX, int translationY, ItemDecorationConfig config, Rect bounds, RecyclerView.LayoutParams childParams) {
        if (config == null) return;
        if (config.getLeftWidth() > 0)
            drawChildLeft(c, translationX, translationY, config, bounds, childParams);
        if (config.getTopHeight() > 0)
            drawChildTop(c, translationX, translationY, config, bounds, childParams);
        if (config.getRightWidth() > 0)
            drawChildRight(c, translationX, translationY, config, bounds, childParams);
        if (config.getBottomHeight() > 0)
            drawChildBottom(c, translationX, translationY, config, bounds, childParams);
    }

    public void drawChildLeft(Canvas c, int translationX, int translationY, ItemDecorationConfig config, Rect bounds, RecyclerView.LayoutParams childParams) {
        if (config == null) return;
        int left, right, top, bottom;
        left = bounds.left + translationX;
        right = left + config.getLeftWidth();
        top = bounds.top + translationY;
        bottom = bounds.bottom + translationX;
        mDivider.setBounds(left, top, right, bottom);
        mDivider.setColor(config.getLeftColor());
        mDivider.setPadding(config.getLeftPaddingLeft(), config.getLeftPaddingTop(), config.getLeftPaddingRight(), config.getLeftPaddingBottom());
        mDivider.draw(c);
    }

    public void drawChildTop(Canvas c, int translationX, int translationY, ItemDecorationConfig config, Rect bounds, RecyclerView.LayoutParams childParams) {
        if (config == null) return;
        int left, right, top, bottom;
        left = bounds.left + translationX;
        right = bounds.right + translationX;
        top = bounds.bottom + translationY;
        bottom = top + config.getTopHeight();

        mDivider.setBounds(left, top, right, bottom);
        mDivider.setColor(config.getTopColor());
        mDivider.setPadding(config.getTopPaddingLeft(), config.getTopPaddingTop(), config.getTopPaddingRight(), config.getTopPaddingBottom());
        mDivider.draw(c);
    }

    public void drawChildRight(Canvas c, int translationX, int translationY, ItemDecorationConfig config, Rect bounds, RecyclerView.LayoutParams childParams) {
        if (config == null) return;
        int left, right, top, bottom;
        right = bounds.right + translationX;
        left = right - config.getRightWidth();
        top = bounds.top + translationY;
        bottom = bounds.bottom + translationX;
        mDivider.setBounds(left, top, right, bottom);
        mDivider.setColor(config.getRightColor());
        mDivider.setPadding(config.getRightPaddingLeft(), config.getRightPaddingTop(), config.getRightPaddingRight(), config.getRightPaddingBottom());
        mDivider.draw(c);
    }

    public void drawChildBottom(Canvas c, int translationX, int translationY, ItemDecorationConfig config, Rect bounds, RecyclerView.LayoutParams childParams) {
        if (config == null) return;
        int left, right, top, bottom;
        left = bounds.left + translationX;
        right = bounds.right + translationX;
        bottom = bounds.bottom + translationY;
        top = bottom-config.getBottomHeight();

        mDivider.setBounds(left, top, right, bottom);
        mDivider.setColor(config.getBottomColor());
        mDivider.setPadding(config.getBottomPaddingLeft(), config.getBottomPaddingBottom(), config.getBottomPaddingRight(), config.getBottomPaddingBottom());
        mDivider.draw(c);
    }

    protected void log(String msg) {
        if (isDebug)
            Log.e("BaseItemDecoration", msg);
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public int getPosition(View view,RecyclerView recyclerView) {
        if (view==null||recyclerView==null) return -1;
        return recyclerView.getChildAdapterPosition(view);
    }

    public int getItemViewType(View view, RecyclerView recyclerView) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(view);
        return viewHolder.getItemViewType();
    }

    public abstract ItemDecorationConfig getItemDecorationConfig(View view, RecyclerView rv);

}
