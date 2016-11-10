package com.itchunyang.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by luchunyang on 2016/11/10.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: "+intent.getAction());
    }
}
