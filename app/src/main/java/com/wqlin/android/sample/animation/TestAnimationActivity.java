package com.wqlin.android.sample.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wqlin.android.sample.R;

/**
 * Created by wqlin on 2017/7/30.
 */

public class TestAnimationActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View animView = findViewById(R.id.tv_view);
        animView.setBackgroundResource(R.drawable.test_frame_anim);
        ((AnimationDrawable) animView.getBackground()).start();
//经过运行查看内存比较。传统用法，一次性加载所有帧，内存消耗高，容易溢出导致奔溃。 FrameAnimDrawable 只加载当前动画帧，用完即释放，内存消耗少，动画质量媲美AnimationDrawable。
//        int[] RES_IDS = new int[]{
//          R.drawable.test_pic_01,
//          R.drawable.test_pic_02,
//          R.drawable.test_pic_03,
//          R.drawable.test_pic_04,
//          R.drawable.test_pic_05,
//          R.drawable.test_pic_06,
//          R.drawable.test_pic_07,
//          R.drawable.test_pic_08,
//          R.drawable.test_pic_09,
//          R.drawable.test_pic_10,
//        };

//        FrameAnimDrawable drawable = new FrameAnimDrawable(5, RES_IDS, getResources());
//        animView.setBackgroundDrawable(drawable);
//        drawable.start();
    }
}
