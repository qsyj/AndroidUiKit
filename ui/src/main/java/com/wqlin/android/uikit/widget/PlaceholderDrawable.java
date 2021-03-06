package com.wqlin.android.uikit.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.wqlin.android.uikit.util.Preconditions;

import java.util.Arrays;

/**
 *  背景图+中间图片组成的Drawable,可以作为图片加载时的占位图使用
 * <p>
 * 不使用Bitmap.createBitmap()缩放图片 可能造成内存泄漏,最好用BitmapFactory.decodeResource()获取Bitmap前缩放到最合适的大小,
 * 再draw()中canvas.drawBitmap(Bitmap, Matrix, Paint)缩放图片到具体大小
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/5 9:48
 */
public class PlaceholderDrawable extends Drawable {
    private Params mParams;
    private Bitmap mBitmap;
    private int mWidth = 0;
    private int mHeight = 0;

    @VisibleForTesting
    final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private final Path mBorderPath = new Path();
    private final RectF mTempRectangle = new RectF();


    @Deprecated
    public PlaceholderDrawable(Bitmap bitmap, Params params){
        setParams(params);
        mBitmap = bitmap;
    }

    public PlaceholderDrawable(Resources resources, int drawaleId, Params params){
        setParams(params);
        mBitmap = createBitmap(resources, drawaleId, params);

    }
    private Bitmap createBitmap(Resources resources,int resId,Params params) {
        if (resources==null||params==null||resId<0) return null;
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, resId,options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            int wS = (int) Math.round((imageWidth /params.getBitmapWidth() )+0.6);
            int hS= (int) Math.round((imageHeight /params.getBitmapHeight() )+0.6);
            options.inSampleSize = 1;
            if (wS > 1 || hS > 1) {
                options.inSampleSize = wS > hS ? hS : wS;
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(resources, resId,options);
        } catch (Exception ep) {
            ep.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 不使用Bitmap.createBitmap()缩放图片 可能造成内存泄漏
     * @param bitmap
     * @param params
     */
    @Deprecated
    private void setBitmap(Bitmap bitmap,Params params) {
        if (params==null) return;
        mBitmap = bitmap;
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        // 缩放原图
        matrix.postScale(params.getBitmapWidth()/bitmap.getWidth(), params.getBitmapHeight()/bitmap.getHeight());
        mBitmap= Bitmap.createBitmap(bitmap, 0, 0, bitmap.getHeight(), bitmap.getWidth(),matrix, true);
        bitmap.recycle();
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updatePath();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // clip, note: doesn't support anti-aliasing
        mPath.setFillType(Path.FillType.WINDING);
        canvas.clipPath(mPath);

        mPaint.setColor(mParams.mBackgroudColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(mPath, mPaint);

        Rect rect = getBounds();
        if (mBitmap != null) {
            float halfW = (mWidth - mParams.mBitmapWidth) * 1f / 2;
            float halfH = (mHeight - mParams.mBitmapHeight) * 1f / 2;
            float left = rect.left + halfW;
            float top = rect.top + halfH;
            //缩放 移动
            Matrix matrix = new Matrix();
            matrix.setScale(mParams.getBitmapWidth()/mBitmap.getWidth(), mParams.getBitmapHeight()/mBitmap.getHeight());
            matrix.postTranslate(left, top);
            canvas.drawBitmap(mBitmap, matrix, null);
        }
        if (mParams.mBorderColor != Color.TRANSPARENT) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mParams.mBorderColor);
            mPaint.setStrokeWidth(mParams.mBorderWidth);
            mPath.setFillType(Path.FillType.EVEN_ODD);
            canvas.drawPath(mBorderPath, mPaint);
        }
    }

    public void setParams(Params params) {
        if (params==null) return;
        mParams = params;
        updatePath();
    }

    private void updatePath() {
        if (mParams==null) return;
        mPath.reset();
        mBorderPath.reset();
        mTempRectangle.set(getBounds());

        mTempRectangle.inset(mParams.mPadding, mParams.mPadding);
        if (mParams.mIsCircle) {
            mPath.addCircle(
                    mTempRectangle.centerX(),
                    mTempRectangle.centerY(),
                    Math.min(mTempRectangle.width(), mTempRectangle.height())/2,
                    Path.Direction.CW);
        } else {
            mPath.addRoundRect(mTempRectangle, mParams.mRadii, Path.Direction.CW);
        }
        mTempRectangle.inset(-mParams.mPadding, -mParams.mPadding);

        mTempRectangle.inset(mParams.mBorderWidth/2, mParams.mBorderWidth/2);
        if (mParams.mIsCircle) {
            float radius = Math.min(mTempRectangle.width(), mTempRectangle.height())/2;
            mBorderPath.addCircle(
                    mTempRectangle.centerX(), mTempRectangle.centerY(), radius, Path.Direction.CW);
        } else {
            for (int i = 0; i < mParams.mBorderRadii.length; i++) {
                mParams.mBorderRadii[i] = mParams.mRadii[i] + mParams.mPadding - mParams.mBorderWidth/2;
            }
            mBorderPath.addRoundRect(mTempRectangle, mParams.mBorderRadii, Path.Direction.CW);
        }
        mTempRectangle.inset(-mParams.mBorderWidth/2, -mParams.mBorderWidth/2);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        mWidth =right-left;
        mHeight = bottom - top;
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    public int getIntrinsicHeight() {
        return mHeight;
    }

    public  static class Params{
        private int mBackgroudColor;
        private float mBitmapWidth, mBitmapHeight;
        private boolean mIsCircle = false;
        private float mBorderWidth = 0;
        private int mBorderColor = Color.TRANSPARENT;
        private float mPadding = 0;
        private final float[] mRadii= new float[8];;
        private final float[] mBorderRadii = new float[8];

        public Params(int backgroudColor, float bitmapWidth, float bitmapHeight) {
            this.mBackgroudColor = backgroudColor;
            this.mBitmapWidth = bitmapWidth;
            this.mBitmapHeight = bitmapHeight;
        }

        public int getBackgroudColor() {
            return mBackgroudColor;
        }

        public Params setBackgroudColor(int backgroudColor) {
            this.mBackgroudColor = backgroudColor;
            return this;
        }

        public float getBitmapWidth() {
            return mBitmapWidth;
        }

        public Params setBitmapWidth(float bitmapWidth) {
            this.mBitmapWidth = bitmapWidth;
            return this;
        }

        public float getBitmapHeight() {
            return mBitmapHeight;
        }

        public Params setBitmapHeight(float bitmapHeight) {
            this.mBitmapHeight = bitmapHeight;
            return this;
        }

        public boolean isCircle() {
            return mIsCircle;
        }

        public Params setCircle(boolean circle) {
            mIsCircle = circle;
            return this;
        }

        public float getBorderWidth() {
            return mBorderWidth;
        }

        public Params setBorderWidth(float borderWidth) {
            mBorderWidth = borderWidth;
            return this;
        }

        public int getBorderColor() {
            return mBorderColor;
        }

        public Params setBorderColor(int borderColor) {
            mBorderColor = borderColor;
            return this;
        }

        public float getPadding() {
            return mPadding;
        }

        public void setPadding(float padding) {
            mPadding = padding;
        }
        public Params setRadii(float[] radii) {
            if (radii == null) {
                Arrays.fill(mRadii, 0);
            } else {
                Preconditions.checkArgument(radii.length == 8, "radii should have exactly 8 values");
                System.arraycopy(radii, 0, mRadii, 0, 8);
            }
            return this;
        }

        public float[] getRadii() {
            return mRadii;
        }

        public float[] getBorderRadii() {
            return mBorderRadii;
        }
    }
}
