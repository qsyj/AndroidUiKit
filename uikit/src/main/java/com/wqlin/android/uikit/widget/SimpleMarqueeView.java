package com.wqlin.android.uikit.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现走马灯效果，不是使用ViewFlipper来实现，若使用ViewFlipper会导致RecyclerView滑动到顶部
 * <p>
 * {@link #setData(List, View, View, OnDataAndClickListener)} 设置数据
 * <p>
 * {@link #startFlipping()} 开始走马灯
 * <p>
 * {@link #stopFlipping()} 开始走马灯
 * <p>
 * {@link #destory()} 销毁
 * {@link #setInterval(int)} 切换间隔时间
 * <p>
 * {@link #setAnimDuration(int)} 动画时长
 * <p>
 * {@link #setTimeInterpolator(TimeInterpolator)} 插值器 改变动画速度
 * <p>
 * {@link OnDataAndClickListener} 绑定数据 点击监听器
 * <p>
 * {@link OnAnimatorListener} 动画初始化 进行中 结束监听器
 * <p>
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2017/12/29 13:39
 */
public class SimpleMarqueeView extends FrameLayout implements View.OnClickListener{
    private final int WHAT_START_ANIM = 100;
    /**
     * 切换间隔时间
     */
    private int interval = 3000;
    /**
     * 动画时长
     */
    private int animDuration = 1000;
    /**
     *插值器 改变动画速度
     */
    private TimeInterpolator mTimeInterpolator;

    private boolean isFlipping = false;
    private boolean isAnim = false;

    private FrameLayout currentView;
    private FrameLayout hideView;


    private int currentPosition = -1;

    private float hideViewMarginTop;

    private Handler mHandler;

    private List<Object> mData = new ArrayList<>();

    private OnDataAndClickListener mListener;
    private ValueAnimator mValueAnimator;
    private OnAnimatorListener mAnimatorListener;

    public SimpleMarqueeView(Context context) {
        this(context,null);
    }

    public SimpleMarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleMarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTimeInterpolator = new AccelerateInterpolator(2f);

        hideView = createChildView();
        currentView = createChildView();
        hideView.setOnClickListener(this);
        currentView.setOnClickListener(this);

        addView(hideView);
        addView(currentView);
        initHanlder();
    }

    private void initHanlder() {
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    case WHAT_START_ANIM:
                        startAnim();
                        break;
                }
                return false;
            }
        });
    }

    public <T,K extends View> void setData(List<T> list, K showChildView, K hideChildView, OnDataAndClickListener<T,K> listener) {
        if (currentView == null || hideView == null) return;
        if (list == null || showChildView == null || hideChildView == null||listener==null) return;
        if (mData != null) {
            mListener = listener;
            currentView.removeAllViews();
            hideView.removeAllViews();
//            if (showChildView.getLayoutParams() == null)
                showChildView.setLayoutParams(createContentParams());

//            if (hideChildView.getLayoutParams()==null)
                hideChildView.setLayoutParams(createContentParams());

            currentView.addView(showChildView);
            hideView.addView(hideChildView);
            mData.clear();
            mData.addAll(list);
            showData();
        }
    }

    public void setAnimatorListener(OnAnimatorListener animatorListener) {
        mAnimatorListener = animatorListener;
    }

    private LayoutParams createContentParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        return params;
    }
    /**
     * 切换间隔时间
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 动画时长
     */
    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    /**
     * 插值器 改变动画速度
     */
    public void setTimeInterpolator(TimeInterpolator interpolator) {
        if (interpolator==null) return;
        mTimeInterpolator = interpolator;
    }

    private void correctionPosition() {
        if (currentPosition<0)
            currentPosition = 0;
        if (currentPosition>=getCount())
            currentPosition = 0;
    }

    private void setHideViewMarginTop() {
        float showTop = getHeight();
        if (currentView!=null&&currentView.getChildCount() == 1) {
            showTop=(getHeight()*1f/2)+(currentView.getChildAt(0).getHeight()*1f/2);
        }
        hideViewMarginTop = getHeight();
        if (hideView!=null&&hideView.getChildCount() == 1) {
            hideViewMarginTop=getHeight()-((hideView.getHeight()-hideView.getChildAt(0).getHeight())*1f/2f);
        }
        hideViewMarginTop = showTop > hideViewMarginTop ? showTop : hideViewMarginTop;
    }

    private void showData() {
        if (currentView==null||hideView==null) return;
        if (mData==null) return;
        if (mData.size()==0) return;
        correctionPosition();
        if (mListener != null) {
            View showChildView=null;
            View hideChildView = null;
            Object showData = null;
            Object hideData = null;
            if (currentView.getChildCount() == 1) {
                showChildView = currentView.getChildAt(0);
                showData = mData.get(currentPosition);
                currentView.setTag(showData);
            }
            if (hideView.getChildCount() == 1) {
                hideChildView = hideView.getChildAt(0);
                int hidePosition = currentPosition + 1;
                if (hidePosition >= getCount()) {
                    hidePosition = 0;
                }
                hideData=mData.get(hidePosition);
                hideView.setTag(hideData);
            }
            mListener.onBindData(showChildView,hideChildView,showData,hideData);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentView==null||hideView==null) return;
                setHideViewMarginTop();
                ViewCompat.setTranslationY(currentView, 0);
                ViewCompat.setTranslationY(hideView, hideViewMarginTop);
                ViewCompat.setAlpha(currentView,1);
                ViewCompat.setAlpha(hideView,0);
            }
        }, 0);

    }

    private int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void startFlipping() {
        isFlipping = true;
        if (mHandler != null && !isAnim) {
            mHandler.removeMessages(WHAT_START_ANIM);
            mHandler.sendEmptyMessageDelayed(WHAT_START_ANIM, interval);

        }
    }

    public boolean isFlipping() {
        return isFlipping;
    }

    public void stopFlipping() {
        isFlipping = false;
    }

    public void destory() {
        isFlipping = false;
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        if (mHandler != null) {
            mHandler.removeMessages(WHAT_START_ANIM);
            mHandler = null;
        }
        mTimeInterpolator = null;
        currentView = null;
        hideView = null;
        mData = null;
        mListener = null;
        mAnimatorListener = null;
    }

    private boolean isStartAnim() {
        if (isAnim||!isFlipping||getCount()<=0) return false;
        return true;
    }

    private float getAnimEnd() {
        return hideViewMarginTop;
    }

    private ValueAnimator getAnimator() {
        if (mValueAnimator == null) {
            final float end = getAnimEnd();
            mValueAnimator = ObjectAnimator.ofFloat(0, end).setDuration(animDuration);
            mValueAnimator.setInterpolator(mTimeInterpolator);
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (currentView==null ||hideView==null) return;
                    isAnim = false;
                    currentPosition++;
                    correctionPosition();
                    switchView();
                    if (mHandler!=null&&isFlipping)
                        mHandler.sendEmptyMessageDelayed(WHAT_START_ANIM, interval);
                    if (mAnimatorListener!=null)
                        mAnimatorListener.onAnimationEnd(animation);
                }
            });
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (currentView==null ||hideView==null) return;
                    boolean isSelf = true;
                    if (mAnimatorListener != null) {
                        isSelf = !mAnimatorListener.onAnimationUpdate(animation);
                    }
                    if (isSelf) {
                        float f = (float) animation.getAnimatedValue();
                        float height = end;
                        float dd = height-f;
                        ViewCompat.setTranslationY(currentView, -f);
                        ViewCompat.setTranslationY(hideView, dd);
                        ViewCompat.setAlpha(currentView,dd/height);//1-->0
                        ViewCompat.setAlpha(hideView, f / height);//0-->1
                    }
                }
            });
        }
        return mValueAnimator;
    }

    private void startAnim() {
        if (!isStartAnim()) return;
        setHideViewMarginTop();

        ValueAnimator valueAnimator = getAnimator();
        if (valueAnimator.getInterpolator() != mTimeInterpolator) {
            valueAnimator.setInterpolator(mTimeInterpolator);
        }
        boolean isSelf = true;
        if (mAnimatorListener != null) {
            isSelf = !mAnimatorListener.onAnimInit(valueAnimator);
        }
        if (isSelf) {
            final float end = getAnimEnd();
            valueAnimator.setFloatValues(0, end);
        }
        isAnim = true;
        correctionPosition();
        valueAnimator.start();
    }

    private void switchView() {
        if (currentView==null ||hideView==null) return;
        FrameLayout view = currentView;
        currentView = hideView;
        hideView = view;
        showData();
    }
    private FrameLayout createChildView() {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return frameLayout;
    }

    @Override
    public void onClick(View v) {
        if (currentView==null||hideView==null) return;
        if (mListener != null) {
            if (v instanceof ViewGroup &&((ViewGroup) v).getChildCount()>0) {
                Object tag = v.getTag();
                if (tag!=null)
                    mListener.onClick(((ViewGroup) v).getChildAt(0), v.getTag());
            }
        }
    }

    public interface OnDataAndClickListener<T,K extends View> {
        /**
         * 数据绑定
         * @param showView
         * @param hideView
         * @param showData
         * @param hideData
         */
        void onBindData(K showView, K hideView, T showData, T hideData);

        /**
         * 点击
         * @param view
         * @param data
         */
        void onClick(K view, T data);
    }

    public interface OnAnimatorListener {
        /**
         *
         * @param valueAnimator
         * @return true-->需要自己处理 ValueAnimator 初始化setFloatValues
         */
        boolean onAnimInit(ValueAnimator valueAnimator);

        /**
         *
         * @param animation rue-->需要自己处理
         * @return
         */
        boolean onAnimationUpdate(ValueAnimator animation);

        void onAnimationEnd(Animator animation);
    }
}
