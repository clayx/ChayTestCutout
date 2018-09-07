## Notch工具类

目前，市场上存在多种带有刘海的手机，需要对刘海区进行相应适配，而低于Android P版本的手机
都是各厂商自行研发的，并没有统一方法，固需要对不同厂商手机进行不同的处理。

而针对刘海区的适配，在低于Android P版本上，只需要注意的是当用户手机是刘海屏手机，并且使
用的APP为带有沉浸式状态栏的手机，才会出现重要View和相应的点击事件出现在刘海区，要避免这
种情况，需根据系统的不同，处理方式不同。

要做到适配方案，我们需要先判断当前手机的系统版本，如果系统版本大于等于28，那么我们就采用
系统提供的适配方案即可，如果系统版本低于28，那么我们需要判断当前系统是什么系统，是否展示
状态栏（或者使用沉浸式状态栏），然后在根据各厂商的适配方案进行
处理。

[适配总结](NotchIntroduce.md)

NotchUtil主要针对当前竖屏模式进行简单适配，其中包括沉浸式状态栏的使用，状态栏深浅色的状态
的修改，刘海区的适配做相关处理。

PS：
1.在使用NotchUtil的时候，在页面的xml根布局设置android:fitsSystemWindows="true"属性，或者全局
设置style设置相关属性。
2.如果App位置相关android:fitsSystemWindows="true"属性，开发人员在使用NotUtil的时候，需要在
后续的操作中，要针对危险区进行处理。
3.如果需要在页面动态修改状态栏的使用，需要添加
getWindowManager()
.updateViewLayout(getWindow().getDecorView(),getWindow().getDecorView().getLayoutParams());
刷新页面。
4.对于一些特殊情况，需要单独适配处理，获取相关状态栏高度和刘海区信息，对相关view进行位置偏移
操作等。

NotchUtil代码如下：

````
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
                    if (isUseCutout) {
                        NotchThirdUtil.addExtraFlag(app);
                    } else {
                        NotchThirdUtil.clearExtraFlag(app);
                    }
                }
            }

        }
    }

}
````