package chay.org.chaytestcutout;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Author:Chay
 * Time:2018/9/25 0025
 * <p>
 * 屏幕相关工具类
 * </p>
 **/
public class ScreenUtil {

    /**
     * 方法描述 判断是否锁屏
     *
     * @param context 描述 上下文
     * @return isLock
     */
    public static boolean isLockScreen(final Context context) {

        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);

        if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将dp转为px
     *
     * @param context 上下文
     * @param dpValue 需要转换的dp数值
     * @return px数值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px数值
     * @return dp数值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity activity
     * @return 屏幕宽度
     */
    public static int getSreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getSreenWidth(Context context) {
        WindowManager a = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d1 = a.getDefaultDisplay(); // 获取屏幕宽、高用
        Point point = new Point();
        d1.getSize(point);
        return point.x;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity activity
     * @return 屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getSreenHeight(Context context) {
        WindowManager a = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d1 = a.getDefaultDisplay(); // 获取屏幕宽、高用
        Point point = new Point();
        d1.getSize(point);
        return point.y;
    }

}
