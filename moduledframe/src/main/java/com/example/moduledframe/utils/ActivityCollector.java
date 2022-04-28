package com.example.moduledframe.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 向List中添加一个活动
     *
     * @param activity 活动
     */
    public static void addActivity(Activity activity) {

        activitys.add(activity);
    }

    /**
     * 从List中移除活动
     *
     * @param activity 活动
     */
    public static void removeActivity(Activity activity) {

        activitys.remove(activity);
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    public static void finishAll() {

        for (Activity activity : activitys) {

            if (!activity.isFinishing()) {

                activity.finish();
            }
        }
    }
}