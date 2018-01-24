package com.wqlin.android.uikit.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 解决ViewPager配合使用TabLayout时 多页面切换 只调用ViewPager.setCurrentItem(int item) 方法，就会严重闪动的bug
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/24 17:14
 */
public class TabViewPager extends ViewPager {
    public TabViewPager(Context context) {
        this(context,null);
    }

    public TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
