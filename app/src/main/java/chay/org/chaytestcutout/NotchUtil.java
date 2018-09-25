package chay.org.chaytestcutout;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import chay.org.chaytestcutout.i.OnCutoutListener;

/**
 * Author:Chay
 * Time:2018/9/5 0005
 * <p>
 * 刘海屏适配工具类
 * 建议在相关的页面上使用属性android:fitsSystemWindows="true"，
 * 或者在style里面声明这个属性.
 * PS:如果未使用沉浸式状态栏，并且fitsSystemWindows="false"，
 * 有需要全屏显示，那么需要针对相关页面进行适配，例如SplashActivity
 * </p>
 **/
public class NotchUtil {

    /**
     * 设置有沉浸式状态栏无刘海区
     * PS:此效果和有刘海区效果一样
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    @Deprecated
    public static void setImmersiveNoneNotch(Context context, boolean isLightMode, int cutoutMode) {
        setImmersiveMode(context, false, isLightMode, cutoutMode);
    }

    /**
     * 设置有沉浸式状态栏有刘海区
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    public static void setImmersiveWithNotch(Context context, boolean isLightMode, int cutoutMode) {
        setImmersiveMode(context, true, isLightMode, cutoutMode);
    }

    /**
     * 设置没有沉浸式状态栏无刘海区
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    public static void setNoneImmersiveNoneNotch(Context context, boolean isLightMode, int cutoutMode) {
        setNoneImmersiveMode(context, false, isLightMode, cutoutMode);
    }

    /**
     * 设置没有沉浸式状态栏有刘海区
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    public static void setNoneImmersiveWithNotch(Context context, boolean isLightMode, int cutoutMode) {
        setNoneImmersiveMode(context, true, isLightMode, cutoutMode);
    }

    /**
     * 设置沉浸式状态栏情况，设置刘海策略
     *
     * @param context     context
     * @param isUseCutout 是否使用刘海区
     * @param isLightMode 状态栏颜色状态
     * @param cutout      刘海使用策略
     */
    public static void setImmersiveMode(Context context, boolean isUseCutout, boolean isLightMode, int cutout) {
        setImmersiveBarsMode(context, true, isUseCutout, isLightMode, cutout);
    }

    /**
     * 不支持沉浸式状态栏情况，设置刘海策略
     *
     * @param context     context
     * @param isUseCutout 是否使用刘海区
     * @param isLightMode 状态栏颜色状态
     * @param cutout      刘海使用策略
     */
    public static void setNoneImmersiveMode(Context context, boolean isUseCutout, boolean isLightMode, int cutout) {
        setImmersiveBarsMode(context, false, isUseCutout, isLightMode, cutout);
    }

    /**
     * 设置沉浸式状态栏
     * PS:在Android P之前，只有设置为沉浸式状态栏，才会延伸到刘海区，否则
     * 设置getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
     * 是不会延伸到刘海区的
     * PS:针对不同需求，调整相关策略，主要策略为系统适应，如果需要特殊处理的一些需求，根据根据状态栏高度调整
     *
     * @param context            context
     * @param isUseImmersiveBars 是否使用沉浸式状态栏
     * @param isUseCutout        是否支持刘海屏
     * @param isLightMode        是否是浅色模式，如果为真，则修改状态栏字体颜色为深色
     * @param cutoutMode         Android P刘海区使用策略，-1为默认处理
     */
    private static void setImmersiveBarsMode(Context context, boolean isUseImmersiveBars,
                                             boolean isUseCutout, boolean isLightMode, int cutoutMode) {
        if (context instanceof AppCompatActivity) {
            AppCompatActivity app = (AppCompatActivity) context;
            if (isUseImmersiveBars) {
                //由于actionBar可能在style中设置隐藏，那么app.getSupportActionBar()可能为空，固做空判断
                if (app.getSupportActionBar() != null) {
                    app.getSupportActionBar().hide();
                }
                StatusBarUtil.transparencyBar(app);
                setLightMode(app, isLightMode);
                setNotchMode(app, true, isUseCutout, isLightMode, cutoutMode);
            } else {
                StatusBarUtil.restoreBar(app, R.color.colorAccent);
                setLightMode(app, isLightMode);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    NotchThirdUtil.setNotchModeforApi28(context, false, isUseCutout, isLightMode, cutoutMode);
                } else {
                    NotchThirdUtil.setNormalMode(app, true);
                }
            }
        }
    }

    /**
     * 设置使用浅色模式，即状态栏颜色为深色
     *
     * @param app         AppCompatActivity
     * @param isLightMode 是否浅色模式
     */
    private static void setLightMode(AppCompatActivity app, boolean isLightMode) {
        if (isLightMode) {
            StatusBarUtil.statusBarLightMode(app);
        } else {
            StatusBarUtil.statusBarDarkMode(app);
        }
    }

    /**
     * 设置刘海策略
     *
     * @param app         app
     * @param isUseCutout 是否使用刘海区域
     * @param isLightMode 是否显示浅模式
     * @param cutoutMode  刘海屏显示策略
     */
    private static void setNotchMode(AppCompatActivity app, boolean isUseImmersiveBars, boolean isUseCutout, boolean isLightMode, int cutoutMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotchThirdUtil.setNotchModeforApi28(app, true, isUseCutout, isLightMode, cutoutMode);
        } else {
            if (OSUtil.isEmui()) {
                if (NotchThirdUtil.hasNotchInScreenAtHuawei(app)) {
                    if (isUseCutout) {
                        NotchThirdUtil.setFullScreenWindowLayoutInDisplayCutout(app.getWindow());
                    } else {
                        NotchThirdUtil.setNotFullScreenWindowLayoutInDisplayCutout(app.getWindow());
                    }
                }
            } else if (OSUtil.isOppo()) {
                if (NotchThirdUtil.hasNotchInScreenAtOppo(app)) {
                    //OPPO系统默认处理
                }
            } else if (OSUtil.isVivo()) {
                if (NotchThirdUtil.hasNotchInScreenAtVoio(app)) {
                    //VIVO系统默认处理
                }
            } else if (OSUtil.isMiui()) {
                if (NotchThirdUtil.isHideNotchScreen4Xiaomi(app)) {
                    //如果用户把刘海屏关掉，那么直接设置正常模式
                    NotchThirdUtil.setNormalMode(app, true);
                    return;
                }
                if (NotchThirdUtil.hasNotchInScreenAtXiaomi()) {
                    //MIUI系统处理
                    if (isUseCutout) {
                        NotchThirdUtil.addExtraFlag(app);
                    } else {
                        NotchThirdUtil.clearExtraFlag(app);
                    }
                }
            }

        }
    }

    /**
     * 判断是否有刘海屏
     * PS：因为Android P是在回调中判断的，所以，使用listener形式
     * 并且，只有在切换时才会获取到，否则返回都是false（API 28）
     *
     * @param app      {@link AppCompatActivity}
     * @param listener {@link OnCutoutListener}
     */
    public static void isHasCutout(AppCompatActivity app, OnCutoutListener listener) {
        if (Build.VERSION.SDK_INT >= 28) {
            NotchThirdUtil.getNotchSize4Google(app, listener);
        } else {
            if (OSUtil.isEmui()) {
                listener.isHasCutout(NotchThirdUtil.hasNotchInScreenAtHuawei(app));
            } else if (OSUtil.isOppo()) {
                listener.isHasCutout(NotchThirdUtil.hasNotchInScreenAtOppo(app));
            } else if (OSUtil.isVivo()) {
                listener.isHasCutout(NotchThirdUtil.hasNotchInScreenAtVoio(app));
            } else if (OSUtil.isMiui()) {
                listener.isHasCutout(NotchThirdUtil.isHideNotchScreen4Xiaomi(app));
            }

        }
    }

    /**
     * 适配状态栏高度（一般也就是刘海高度）
     * PS：
     *
     * @param activity       activity
     * @param view           需要调整的view
     * @param originalMargin 初始的高度，单位dp/px
     * @param <T>            包裹view的ViewGroup的Layoutparams，
     *                       常用的{@link android.support.constraint.ConstraintLayout,android.widget.RelativeLayout,android.widget.LinearLayout...}
     */
    public static <T extends ViewGroup.MarginLayoutParams> void adaptStartusHeight(Activity activity,
                                                                                   View view, int originalMargin, boolean isDP) {
        int statusbarHeight = StatusBarUtil.getStatusBarHeight(activity);//单位px
        T lp = (T) view.getLayoutParams();
        if (isDP) {
            originalMargin = ScreenUtil.dip2px(activity, originalMargin);
        }
        lp.topMargin = statusbarHeight + originalMargin;
        view.setLayoutParams(lp);
    }

    /**
     * 恢复初始化的View
     *  @hide
     * @param activity       activity
     * @param view           需要恢复的View
     * @param originalMargin 初始的距离高度
     * @param isDP           单位是否为dp
     * @param <T>            包裹view的ViewGroup的Layoutparams
     */
    public static <T extends ViewGroup.MarginLayoutParams> void restoreMarginHeight(Activity activity,
                                                                                    View view, int originalMargin, boolean isDP) {
        T lp = (T) view.getLayoutParams();
        if (isDP) {
            originalMargin = ScreenUtil.dip2px(activity, originalMargin);
        }
        lp.topMargin = originalMargin;
        view.setLayoutParams(lp);
    }


}
