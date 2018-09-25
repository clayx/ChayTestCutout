package chay.org.chaytestcutout.i;

import android.view.DisplayCutout;

/**
 * Author:Chay
 * Time:2018/9/25 0025
 * <p>
 * {@link OnCutoutListener}child class，为了获取相关刘海信息
 * </p>
 **/
public interface OnCutoutDetailListener extends OnCutoutListener {

    // has cutout
    void onCutout(DisplayCutout cutout);

}
