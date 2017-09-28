package com.wqlin.android.uikit.tablayout;

import android.support.annotation.DrawableRes;

public interface CommonTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}