package com.wqlin.android.sample.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.wqlin.android.sample.R;
import com.wqlin.widget.irecyclerview.ALoadMoreFooterLayout;

/**
 * Created by 汪倾林 on 2018/2/3.
 */

public class LoadMoreFooterLayout extends ALoadMoreFooterLayout {
    public LoadMoreFooterLayout(@NonNull Context context) {
        this(context,null);
    }

    public LoadMoreFooterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.item_text, this);
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onReleaseToRefresh() {

    }

    @Override
    public void onGone() {

    }

    @Override
    public void onLoding() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onNull() {

    }
}
