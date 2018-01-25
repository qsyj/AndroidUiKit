package com.wqlin.android.uikit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wqlin.android.uikit.R;

/**
 * PlaceholderImage默认实现占位图 使用R.drawable.default_drawee_logo
 * <p>
 *  占位图的默认R.drawable.default_drawee_logo
 * <p>
 * 属性iconWidth-->default_logo的width 默认26dp
 * <p>
 * 属性iconHeight-->default_logo的height 默认20dp
 * <p>
 * 属性iconBackgroudColor-->default_logo的背景色 默认#e7e7e7
 * <p>
 * {@link #setIcon(int, float, float)}-->修改占位图 图片 图片大小 背景
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/5 10:34
 */
public class DefaultDraweeView extends SimpleDraweeView {
    private final int defaultColor = Color.parseColor("#e7e7e7");
    private final float defaultWidthDP = 26;
    private final float defaultHeightDP = 20;
    private final int defaltIconResId = R.drawable.default_drawee_logo;
    private int mBackgroudColor=defaultColor;
    private float mBitmapWidth, mBitmapHeight;
    private int mIconResId;
    private Drawable mIconDrawable;

    public DefaultDraweeView(Context context) {
        super(context);
        init(context,null);
    }
    public DefaultDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context,null);
    }

    public DefaultDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DefaultDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    public DefaultDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    /**
     *
     * @param iconBackgroudColor 背景色 默认#e7e7e7
     * @param iconWidth 占位图 logo的width 默认26dp
     * @param iconHeight 占位图 logo的hegiht 默认26dp
     */
    public void setIcon(int iconBackgroudColor,float iconWidth,float iconHeight) {
        setIcon(iconBackgroudColor,0,iconWidth,iconHeight);
    }

    /**
     *
     * @param iconBackgroudColor 背景色 默认#e7e7e7
     * @param iconResId 占位图的资源id 默认R.drawable.default_drawee_logo
     * @param iconWidth 占位图 logo的width 默认26dp
     * @param iconHeight 占位图 logo的hegiht 默认26dp
     */
    public void setIcon(int iconBackgroudColor,int iconResId,float iconWidth,float iconHeight) {
        if (iconResId>0)
            mIconResId = iconResId;
        mBackgroudColor = iconBackgroudColor;
        mBitmapWidth = iconWidth;
        mBitmapHeight = iconHeight;
        setHierarchy(getContext());
    }

    private void init(Context context, AttributeSet attrs) {
        if (context==null) return;
        mBitmapWidth = dip2px(context, defaultWidthDP);
        mBitmapHeight = dip2px(context, defaultHeightDP);
        mIconResId = defaltIconResId;
        TypedArray a = null;
        if (attrs != null) {
            a = context.obtainStyledAttributes(attrs, R.styleable.DefaultDraweeView);
        }
        if (a != null) {
            mBackgroudColor = a.getColor(R.styleable.DefaultDraweeView_iconBackgroudColor, Color.parseColor("#cccccc"));
            mBitmapWidth = a.getDimensionPixelSize(R.styleable.DefaultDraweeView_iconWidth, (int) mBitmapWidth);
            mBitmapHeight = a.getDimensionPixelSize(R.styleable.DefaultDraweeView_iconHeight, (int) mBitmapHeight);
//            mIconDrawable = a.getDrawable(R.styleable.DefaultDraweeView_iconPlaceholderResId);
            a.recycle();
        }
        setHierarchy(context);
    }

    private void setHierarchy(Context context) {
        if (context==null) return;
        GenericDraweeHierarchy hierarchy = getHierarchy();
        if (hierarchy == null) {
            hierarchy = new GenericDraweeHierarchyBuilder(context.getResources()).build();
        }
        RoundingParams roundingParams = hierarchy.getRoundingParams();
        DefaultDrawable.Params params = new DefaultDrawable.Params(mBackgroudColor, mBitmapWidth, mBitmapHeight);
        if (roundingParams != null) {
            params.setCircle(roundingParams.getRoundAsCircle())
                    .setRadii(roundingParams.getCornersRadii())
                    .setBorderColor(roundingParams.getBorderColor())
                    .setBorderWidth(roundingParams.getBorderWidth())
                    .setPadding(roundingParams.getPadding());
        }
        hierarchy.setPlaceholderImage(getDefaultDrawable(context,params));
    }

    public Drawable getDefaultDrawable(Context context, DefaultDrawable.Params params) {
        Bitmap bitmap ;
        if (mIconDrawable != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) mIconDrawable;
            bitmap = bitmapDrawable.getBitmap();
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(),mIconResId);
        }
        mIconDrawable = null;
        return new DefaultDrawable(bitmap,params);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, final float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
