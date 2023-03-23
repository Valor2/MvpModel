package com.example.moduledframe.utils;

import android.content.res.Configuration;
import android.content.res.Resources;

public class FontCompatUtils {

    public static final float MAX_FONT_SCALE = 1.10F; //可自行修改最大缩放值
    public static final String TAG = "FontCompatUtils";

    private static Float fontScalePercent = null;

    public static Resources getResources(Resources res) {
        Configuration configuration = res.getConfiguration();
        if (fontScalePercent == null) {
            fontScalePercent = 1 / configuration.fontScale;
        }
        if (shouldChangeFontScale(configuration)) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            //configuration.fontScale = MAX_FONT_SCALE;
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 是否需要改变字体缩放级别
     *
     * @param configuration
     * @return
     */
    public static boolean shouldChangeFontScale(Configuration configuration) {
        return configuration.fontScale > MAX_FONT_SCALE;
    }

    /**
     * 字体缩放比例
     *
     * @return
     */
    public static Float getFontScalePercent() {
        if (fontScalePercent == null) {
            return 1F;
        }
        return fontScalePercent;
    }
}
