package android.support.v4.animation;

import android.os.Build;
import android.view.View;

/**
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/12 16:33
 */
public class AnimatorCompatHelper {

    private final static AnimatorProvider IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 12) {
            IMPL = new HoneycombMr1AnimatorCompatProvider();
        } else {
            IMPL = new DonutAnimatorCompatProvider();
        }
    }

    public static ValueAnimatorCompat emptyValueAnimator() {
        return IMPL.emptyValueAnimator();
    }

    private AnimatorCompatHelper() {}

    public static void clearInterpolator(View view) {
        IMPL.clearInterpolator(view);
    }
}
