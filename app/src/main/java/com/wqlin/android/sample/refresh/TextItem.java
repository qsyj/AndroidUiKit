package com.wqlin.android.sample.refresh;

import android.support.annotation.NonNull;

public class TextItem {

    @NonNull
    public String text;

    public TextItem(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }
}