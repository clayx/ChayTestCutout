package chay.org.chaytestcutout;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Author:Chay
 * Time:2018/9/5 0005
 * <p>
 * 刘海屏适配
 * {@link https://blog.csdn.net/xiangzhihong8/article/details/80317682}
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
    public static void setImmersiveNoneNotch(Context context, boolean isLightMode) {
        setImmersiveMode(context, false, isLightMode, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
    }

    /**
     * 设置有沉浸式状态栏有刘海区
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    public static void setImmersiveWithNotch(Context context, boolean isLightMode) {
        setImmersiveMode(context, true, isLightMode, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
    }

    /**
     * 设置没有沉浸式状态栏无刘海区
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    public static void setNoneImmersiveNoneNotch(Context context, boolean isLightMode) {
        setNoneImmersiveMode(context, false, isLightMode, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT);
    }

    /**
     * 设置没有沉浸式状态栏有刘海区
     *
     * @param context     context
     * @param isLightMode 状态栏模式
     */
    public static void setNoneImmersiveWithNotch(Context context, boolean isLightMode) {
        setNoneImmersiveMode(context, true, isLightMode, WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
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
                app.getSupportActionBar().hide();
                StatusBarUtil.transparencyBar(app);
                if (isLightMode) {
                    StatusBarUtil.StatusBarLightMode(app);
                } else {
                    StatusBarUtil.StatusBarDarkMode(app);
                }
                setNotchMode(app, isUseCutout, isLightMode, cutoutMode);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    NotchThirdUtil.setNotchModeforApi28(context, isUseCutout, isLightMode, cutoutMode);
                }
            }
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
    private static void setNotchMode(AppCompatActivity app, boolean isUseCutout, boolean isLightMode, int cutoutMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            NotchThirdUtil.setNotchModeforApi28(app, isUseCutout, isLightMode, cutoutMode);
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
                    NotchThirdUtil.setNormalMode(app);
                    return;
                }
                if (NotchThirdUtil.hasNotchInScreenAtXiaomi()) {
                    //MIUI系统处理
                }
            }

        }
    }

}
