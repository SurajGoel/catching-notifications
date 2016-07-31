package com.example.sgoel01.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryLevelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == Intent.ACTION_BATTERY_LOW) {
            String value = InstantMessanger.gettheColor("batterylow");
            Log.d("batteryl", "The Value is : " + value);
        }
    }
}
