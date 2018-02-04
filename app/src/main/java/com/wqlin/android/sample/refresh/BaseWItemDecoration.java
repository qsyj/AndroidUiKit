package com.wqlin.android.sample.refresh;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wqlin.android.uikit.widget.BaseItemDecoration;
import com.wqlin.widget.irecyclerview.WrapperAdapter;

/**
 * Created by wqlin on 2018/2/4.
 */

public abstract class BaseWItemDecoration extends BaseItemDecoration {
    public BaseWItemDecoration() {
        super();
        setDebug(true);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && adapter instanceof WrapperAdapter) {
            int itemViewType = getItemViewType(view, parent);
            if (itemViewType==Integer.MIN_VALUE
                    ||itemViewType==Integer.MIN_VALUE+1
                    ||itemViewType==Integer.MAX_VALUE
                    ||itemViewType==Integer.MAX_VALUE-1
                    ||itemViewType==Integer.MAX_VALUE-2)
                return;
        }
        super.getItemOffsets(outRect, view, parent, state);
    }

    /**WRecyclerView真实的Adapter*/
    public RecyclerView.Adapter getAdapter(RecyclerView parent) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && adapter instanceof WrapperAdapter) {
            return ((WrapperAdapter) adapter).getAdapter();
        }
        return adapter;
    }
}
