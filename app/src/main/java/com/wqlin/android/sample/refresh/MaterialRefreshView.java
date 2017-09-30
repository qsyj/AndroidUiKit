package com.wqlin.android.sample.refresh;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewParent;

import com.wqlin.android.uikit.refresh.CircleImageView;
import com.wqlin.android.uikit.refresh.IRefreshTrigger;
import com.wqlin.android.uikit.refresh.ISwipeRefreshLayout;
import com.wqlin.android.uikit.refresh.MaterialProgressDrawable;

/**
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2017/9/29 9:17
 */
public class MaterialRefreshView extends CircleImageView implements IRefreshTrigger {
    private  MaterialProgressDrawable mProgressDrawable;
    private boolean isDragRotation = false;
    private ISwipeRefreshLayout mRefreshLayout;

    public MaterialRefreshView(Context context) {
        this(context, 0xFFFAFAFA);
    }

    MaterialRefreshView(Context context, int color) {
        super(context, color);

    }

    @Override
    public void onPullDownState(float progress) {
        isDragRotation = true;
        mProgressDrawable.setOk(false);
        setRefreshViewScale(false);
    }

    @Override
    public void onRefreshing() {
        setRefreshViewScale(false);
        isDragRotation = false;
        mProgressDrawable.setOk(false);
        mProgressDrawable.showArrow(false);
        mProgressDrawable.start();
    }

    @Override
    public void onReleaseToRefresh() {
        isDragRotation = true;
        mProgressDrawable.setOk(false);
        setRefreshViewScale(false);
    }

    @Override
    public void onComplete() {
        setRefreshViewScale(true);
        mRefreshLayout.clearAnimation();
        mProgressDrawable.setOk(true);
        isDragRotation = false;
    }

    @Override
    public void onTransYChange(float tranY) {
        if (!isDragRotation) {
            return;
        }
        int h=getMeasuredHeight();
        float startY = h * 0.2f;
        float enY = h * 1.5f;

        int alpha = (int) (((tranY-startY) / getMeasuredHeight()) * 255);
        alpha = alpha < 0 ? 0 : alpha;
        if (tranY == h) {
            mProgressDrawable.setAlpha(255);
            mProgressDrawable.start();
        } else {
            float trim=(tranY-startY) / h;
            trim =trim< 0 ? 0 : trim;
            mProgressDrawable.showArrow(true);
            if (alpha > 255) {
                alpha = 255;
            }
            mProgressDrawable.setAlpha(alpha);
            mProgressDrawable.setArrowScale(Math.min(1f, trim));
            mProgressDrawable.setStartEndTrim(0f,Math.min(0.8f, trim));
            mProgressDrawable.setProgressRotation(trim);
            /*mProgressDrawable.setStartEndTrim(0f,0.9f);
            mProgressDrawable.setProgressRotation(0);*/
        }
    }

    @Override
    public void init() {
        ViewParent parent = getParent();
        if (parent instanceof ISwipeRefreshLayout) {
            mRefreshLayout = (ISwipeRefreshLayout) parent;
            mProgressDrawable = new MaterialProgressDrawable(getContext(),mRefreshLayout);
            mProgressDrawable.setBackgroundColor(0xFFFAFAFA);
            mProgressDrawable.setAlpha(255);
            int[] colors = new int[]{Color.RED};
            mProgressDrawable.setColorSchemeColors(colors);
            mProgressDrawable.setOk(false);
            setImageDrawable(mProgressDrawable);
        }

    }

    private void setRefreshViewScale(boolean isScale) {
        if (mRefreshLayout==null)
            return;
//        mRefreshLayout.setRefreshViewScale(isScale);
    }
}
