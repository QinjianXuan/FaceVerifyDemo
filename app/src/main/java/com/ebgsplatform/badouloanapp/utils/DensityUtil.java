package com.ebgsplatform.badouloanapp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.ebgsplatform.badouloanapp.BDApplication;

/**
 * dp px 转换工具
 *
 * @author XuanQinjian
 * @version 1.0
 */
public class DensityUtil {

    private DensityUtil() {}

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = BDApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = BDApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽, 单位 px
     * @return screenWidth;
     * */
    public static int getScreenWidth() {
        Resources resources = BDApplication.getAppContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    
    /**
     * 获取屏幕高， 单位 px
     * @return screenHeight;
     * */
    public static int getScreenHeight() {
        Resources resources = BDApplication.getAppContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * 判断条件： 1. google I/O 判断   2.屏幕尺寸大于6寸  3.手机屏幕宽高比小于1.6
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (BDApplication.getAppContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE&& getScreenSize() && (float)getScreenHeight() / (float) getScreenWidth() < 1.6  ;
    }


    /**
     * 判断是否为平板
     *
     * @return
     */
    private static boolean getScreenSize() {
        WindowManager wm = (WindowManager) BDApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = getScreenWidth();
        // 屏幕高度
        float screenHeight = getScreenHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }
}
