package com.example.sgoel01.myapplication;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class InstantMessanger extends AccessibilityService implements BluetoothSerialListener {

    private TextView text;
    private String packagename;
    private String selfname = "com.example.sgoel01.checkingevents",download = "com.android.providers.downloads";
    Context context = this;
    private BluetoothSerial mSerial;

    private static HashMap<String,String> enabledActions = new HashMap<String, String>();

    public static void addEnabledActions(String key, String value) {
        enabledActions.put(key, value);
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service is Started for the first time", Toast.LENGTH_LONG).show();
        enabledActions = MainActivity.getAppToRGB();
        for(HashMap.Entry<String,String> entry : enabledActions.entrySet()) {
            Log.d("enabledAct", entry.getKey() + " is equal to " + entry.getValue());
        }
        Toast.makeText(this, enabledActions.get("whatsapp"), Toast.LENGTH_SHORT).show();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service is Started on startcommand", Toast.LENGTH_LONG).show();
        enabledActions = MainActivity.getAppToRGB();
        return Service.START_STICKY;
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {

        //Toast.makeText(this,"Event Catched", Toast.LENGTH_LONG).show();
        if(event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            packagename = String.valueOf(event.getPackageName());
            Log.d("pack/call", packagename);
            parsePackage(packagename);
            if(packagename.equals(selfname)==false && packagename.equals("com.android.providers.downloads")==false) {
                Toast.makeText(this, "Package: "+packagename,Toast.LENGTH_LONG).show();
            }
            if(enabledActions.get(packagename) != null) {
                Toast.makeText(this, "This is Working Now " + packagename , Toast.LENGTH_LONG).show();
            }
        }

    }

    private void parsePackage (String pack) {
        String name = pack.substring(pack.indexOf(".")+1,pack.length());
        char[] array = pack.toCharArray();
        int prev=0;
        for(int i=0,n=pack.length() ; i<n ; i++) {
            String temp="";
            if(i==n-1) {
                temp = pack.substring(prev,i+1);
                Log.d("seetothis", "Name: " + temp +" has value= " + enabledActions.get(temp) );
            }
            else if(pack.charAt(i) == '.') {
                temp = pack.substring(prev,i);
                prev = i+1;
                Log.d("seetothis", "Name: " + temp +" has value= " + enabledActions.get(temp) );
            }
            if(enabledActions.get(temp) != null) {
                Log.d("printout", "Package grabbed is: "+temp+" With Value: "+enabledActions.get(temp));
            }
            mSerial.write(enabledActions.get(temp));
        }
    }

    @Override
    public void onInterrupt() {

    }

    public static String gettheColor(String packagen) {
        return enabledActions.get(packagen);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "hUA Nhi ",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.feedbackType = 1;
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.notificationTimeout = 100;
        setServiceInfo(info);
    }

    @Override
    public void onBluetoothNotSupported() {

    }

    @Override
    public void onBluetoothDisabled() {

    }

    @Override
    public void onBluetoothDeviceDisconnected() {

    }

    @Override
    public void onConnectingBluetoothDevice() {

    }

    @Override
    public void onBluetoothDeviceConnected(String name, String address) {

    }

    @Override
    public void onBluetoothSerialRead(String message) {

    }

    @Override
    public void onBluetoothSerialWrite(String message) {

    }
}
