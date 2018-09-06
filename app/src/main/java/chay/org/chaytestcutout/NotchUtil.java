package chay.org.chaytestcutout;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

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
    public static void setImmersiveBarsMode(Context context, boolean isUseImmersiveBars,
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
    public static void setNotchMode(AppCompatActivity app, boolean isUseCutout, boolean isLightMode, int cutoutMode) {
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
