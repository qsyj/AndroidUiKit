package com.wqlin.android.uikit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wqlin.android.uikit.R;
import java.io.InputStream;

/**
 * PlaceholderDraweeView默认实现占位图 默认没有中间图片 只有背景色#e7e7e7
 * <p>
 *  属性placeholderIconId占位图drawableId 默认没有中间图片
 * <p>
 * 属性placeholderIconWidth-->default_logo的width 默认26dp
 * <p>
 * 属性placeholderIconHeight-->default_logo的height 默认20dp
 * <p>
 * 属性placeholderIconBgColor-->default_logo的背景色 默认#e7e7e7
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/5 10:34
 */
public class PlaceholderDraweeView extends SimpleDraweeView {
    private final String defaultColorStr = "#e7e7e7";
    private final float defaultWidthDP = 26;
    private final float defaultHeightDP = 20;
    private final int defaltIconResId = -1;
    private int mBackgroudColor;
    private float mBitmapWidth, mBitmapHeight;
    private int mIconResId;

    public PlaceholderDraweeView(Context context) {
        super(context);
    }
    public PlaceholderDraweeView(@NonNull Context context, @NonNull GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init(context,null);
        setPlaceholder(context,hierarchy);
        setHierarchy(hierarchy);
    }

    public PlaceholderDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceholderDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PlaceholderDraweeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPlaceholder(int placeholderIconId,int placeholderIconWidth,int placeholderIconHeight,int placeholderIconBgColor) {
        mBackgroudColor = placeholderIconBgColor;
        mBitmapWidth = placeholderIconWidth;
        mBitmapHeight = placeholderIconHeight;
        mBitmapWidth = mBitmapWidth < 0 ? 0 : mBitmapWidth;
        mBitmapHeight = mBitmapHeight < 0 ? 0 : mBitmapHeight;
        mIconResId = placeholderIconId;
        setHierarchy(getHierarchy());
    }

    public void setPlaceholderIconId(int placeholderIconId) {
        mIconResId = placeholderIconId;
        setHierarchy(getHierarchy());
    }

    public void setPlaceholderSize(int placeholderIconWidth,int placeholderIconHeight) {
        mBitmapWidth = placeholderIconWidth;
        mBitmapHeight = placeholderIconHeight;
        mBitmapWidth = mBitmapWidth < 0 ? 0 : mBitmapWidth;
        mBitmapHeight = mBitmapHeight < 0 ? 0 : mBitmapHeight;
        setHierarchy(getHierarchy());
    }

    public void setPlaceholderBgColor(int placeholderIconBgColor) {
        mBackgroudColor = placeholderIconBgColor;
        setHierarchy(getHierarchy());
    }

    @Override
    protected void inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        init(context,attrs);
        GenericDraweeHierarchyBuilder builder =
                GenericDraweeHierarchyInflater.inflateBuilder(context, attrs);
        setPlaceholder(context,builder);
        setAspectRatio(builder.getDesiredAspectRatio());
        setHierarchy(builder.build());
    }

    private void init(Context context, AttributeSet attrs) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBackgroudColor = Color.parseColor(defaultColorStr);
        mBitmapWidth = dip2px(context, defaultWidthDP);
        mBitmapHeight = dip2px(context, defaultHeightDP);
        mIconResId = defaltIconResId;
        TypedArray a = null;
        if (attrs != null) {
            a = context.obtainStyledAttributes(attrs, R.styleable.PlaceholderDraweeView);
        }
        if (a != null) {
            try {
                mBackgroudColor = a.getColor(R.styleable.PlaceholderDraweeView_placeholderIconBgColor,mBackgroudColor);
                mBitmapWidth = a.getDimensionPixelSize(R.styleable.PlaceholderDraweeView_placeholderIconWidth, (int) mBitmapWidth);
                mBitmapHeight = a.getDimensionPixelSize(R.styleable.PlaceholderDraweeView_placeholderIconHeight, (int) mBitmapHeight);
                mIconResId = a.getResourceId(R.styleable.PlaceholderDraweeView_placeholderIconId,defaltIconResId);
            } catch (Exception ep) {
                ep.printStackTrace();
            }finally {
                a.recycle();
            }
        }
    }

    private void setPlaceholder( Context context,GenericDraweeHierarchyBuilder builder) {
        if (context==null||builder==null) return;
        RoundingParams roundingParams = builder.getRoundingParams();
        PlaceholderDrawable.Params params = new PlaceholderDrawable.Params(mBackgroudColor, mBitmapWidth, mBitmapHeight);
        setParams(params,roundingParams);
        builder.setPlaceholderImage(getPlaceholderDrawable(context,params), ScalingUtils.ScaleType.FIT_XY);
    }

    private void setPlaceholder( Context context,GenericDraweeHierarchy hierarchy) {
        if (context==null||hierarchy==null) return;
        RoundingParams roundingParams = hierarchy.getRoundingParams();
        PlaceholderDrawable.Params params = new PlaceholderDrawable.Params(mBackgroudColor, mBitmapWidth, mBitmapHeight);
        setParams(params,roundingParams);
        hierarchy.setPlaceholderImage(getPlaceholderDrawable(context,params), ScalingUtils.ScaleType.FIT_XY);
    }

    private void setParams(PlaceholderDrawable.Params params,RoundingParams roundingParams) {
        if (roundingParams != null) {
            params.setCircle(roundingParams.getRoundAsCircle())
                    .setRadii(roundingParams.getCornersRadii())
                    .setBorderColor(roundingParams.getBorderColor())
                    .setBorderWidth(roundingParams.getBorderWidth())
                    .setPadding(roundingParams.getPadding());
        }
    }

    public Drawable getPlaceholderDrawable(Context context, PlaceholderDrawable.Params params) {
//        return new PlaceholderDrawable( readBitMap(context,mIconResId),params);
        return new PlaceholderDrawable( context.getResources(),mIconResId,params);
    }
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, final float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
