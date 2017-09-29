package com.wqlin.android.sample.refresh;

import android.content.Context;

import com.wqlin.android.uikit.refresh.CircleImageView;
import com.wqlin.android.uikit.refresh.IRefreshTrigger;
import com.wqlin.android.uikit.refresh.MaterialProgressDrawable;

/**
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2017/9/29 9:17
 */
public class MaterialRefreshView extends CircleImageView implements IRefreshTrigger {

    MaterialRefreshView(Context context, int color) {
        super(context, color);
        MaterialProgressDrawable drawable = new MaterialProgressDrawable(getContext(),this);
        setImageDrawable(drawable);
    }

    @Override
    public void onPullDownState(float progress) {

    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void onReleaseToRefresh() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onTransYChange(float tranY) {

    }

    @Override
    public void init() {

    }
}
