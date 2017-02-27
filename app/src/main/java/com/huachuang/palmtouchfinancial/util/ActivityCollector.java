package com.huachuang.palmtouchfinancial.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/2/27.
 */

public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    private ActivityCollector(){}

    public static void addActivity(Activity activity) {
        if (activities == null) {
            return;
        }
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        activities.remove(activity);
    }

    public static void removeActivityAndFinish(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activities.remove(activity) && !activity.isFinishing()) {
            activity.finish();
        }
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
