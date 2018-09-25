package chay.org.chaytestcutout.i;

/**
 * Author:Chay
 * Time:2018/9/25 0025
 * <p>
 * 针对API>= 28时，判断手机是否含有刘海区
 * </p>
 **/
public interface OnCutoutListener {

    // is has cutout?
    void isHasCutout(boolean isHas);
}
