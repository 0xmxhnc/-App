package com.example.efficientpomodoro;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

public class VibrateHelper {
    public static void Vibrate(final Activity activity, long milliseconds){
        Vibrator vibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat){
        Vibrator vibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, isRepeat ? 1 : -1);
    }

    //取消震动
    public static void VibrateCancle(final Activity activity){
        Vibrator vibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.cancel();
    }
}
