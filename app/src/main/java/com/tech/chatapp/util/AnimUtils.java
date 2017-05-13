package com.tech.chatapp.util;


import android.app.Activity;
import android.content.Context;

import com.tech.chatapp.R;


public class AnimUtils {

    public static void activityenterAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);

    }

    public static void activityexitAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_right);
    }

    public static void activitySlideUpAnim(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.activity_slide_up, android.R.anim.fade_out);

    }

    public static void activitySlidedownAnim(Context context) {
        ((Activity) context).overridePendingTransition(android.R.anim.fade_in, R.anim.activity_slide_down);
    }


}
