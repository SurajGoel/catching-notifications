package com.example.sgoel01.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;


public class IncomingCall extends BroadcastReceiver {

    private Context pcontext;
    public void onReceive(Context context, Intent intent) {
        pcontext=context;
        Log.d("global", "Using Global is active");
        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
            Log.d("usethis", "Atleast this is getting received");
            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("MyPhoneListener","   incoming no:"+incomingNumber);

            if (state == 1) {

                String msg = " Call with value:  " + InstantMessanger.gettheColor("dialer");
                Log.d("printout", msg);
                int duration = Toast.LENGTH_LONG;


            }
        }
    }
}