package com.example.sgoel01.myapplication;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Intent mailAccessabilityIntent;
    private Switch serviceSwitch;
    private ListView actioList;
    private ActionCustomAdapter adapter;
    private Button addEvent;
    private static final String DEFAULT_COLOR = "R:255,G:255,B:255";

    private static HashMap<String, String> finalpackages = new HashMap<String, String>();
    private static HashMap<String, String> appToRGB = new HashMap<String, String>();
    private static HashMap<String, String> newfinalpackages = new HashMap<String, String>();
    private static HashMap<String, String> newappToRGB = new HashMap<String, String>();

    static {
        finalpackages.put("com.android.dialer", "Calls");
        finalpackages.put("com.whatsapp", "WhatsApp");
        finalpackages.put("com.facebook.katana", "Facebook");
        finalpackages.put("com.twitter", "Twitter");
        finalpackages.put("com.instagram", "Instagram");
        finalpackages.put("com.gmail", "GMail");
        finalpackages.put("com.batterylow", "Low Battery");
        finalpackages.put("com.messenger", "Text Message");

        newfinalpackages.put("dialer", "Calls");
        newfinalpackages.put("whatsapp", "WhatsApp");
        newfinalpackages.put("facebook.katana", "Facebook");
        newfinalpackages.put("twitter", "Twitter");
        newfinalpackages.put("instagram", "Instagram");
        newfinalpackages.put("gmail", "GMail");
        newfinalpackages.put("batterylow", "Low Battery");
        newfinalpackages.put("messenger", "Text Message");
        newfinalpackages.put("calendar", "Calendar");
        newfinalpackages.put("deskclock", "Alarms");


        appToRGB.put("com.dialer", DEFAULT_COLOR);
        appToRGB.put("com.whatsapp", DEFAULT_COLOR);
        appToRGB.put("com.facebook.katana", DEFAULT_COLOR);
        appToRGB.put("com.twitter", DEFAULT_COLOR);
        appToRGB.put("com.instagram", DEFAULT_COLOR);
        appToRGB.put("com.gmail", null);
        appToRGB.put("com.batterylow", null);
        appToRGB.put("com.messenger", null);

        newappToRGB.put("dialer", DEFAULT_COLOR);
        newappToRGB.put("whatsapp", DEFAULT_COLOR);
        newappToRGB.put("facebook", DEFAULT_COLOR);
        newappToRGB.put("twitter", DEFAULT_COLOR);
        newappToRGB.put("instagram", DEFAULT_COLOR);
        newappToRGB.put("gmail", null);
        newappToRGB.put("batterylow", null);
        newappToRGB.put("messenger", null);
        newappToRGB.put("calendar", null);
        newappToRGB.put("deskclock", null);
    }


    private static HashMap<String, String> currentlyEnabled = new HashMap<String, String>();
    private HashMap<String, String> currentlyDisabled = new HashMap<String, String>();

    public static HashMap<String, String> getCurrentlyEnabled() {
        return currentlyEnabled;
    }

    public static HashMap<String, String> getAppToRGB() {
        return newappToRGB;
    }

    private void DrawSelected() {
        currentlyEnabled.put("dialer", "Calls");
        currentlyEnabled.put("whatsapp", "WhatsApp");
        currentlyEnabled.put("facebook", "Facebook");
        currentlyEnabled.put("twitter", "Twitter");
        currentlyEnabled.put("instagram", "Instagram");
        currentlyDisabled.put("gmail", "GMail");
        currentlyDisabled.put("batterylow", "Low Battery");
        currentlyDisabled.put("messenger", "Text Message");
        currentlyDisabled.put("calendar", "Calendar");
        currentlyDisabled.put("deskclock", "Alarms");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mailAccess = new Intent(MainActivity.this, InstantMessanger.class);
        startService(mailAccess);
        DrawSelected();
        serviceSwitch = (Switch) findViewById(R.id.serviceSwitch); //DIDN'T ADD FUNCTIONALITY YET.
        actioList = (ListView) findViewById(R.id.actionList);
        addEvent = new Button(this);
        addEvent.setText("Add New Event");
        actioList.addFooterView(addEvent);
        adapter = new ActionCustomAdapter(this, currentlyEnabled);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(MainActivity.this, SelectEvent.class);
                newIntent.putExtra("Disabled_List", (Serializable) currentlyDisabled);
                startActivityForResult(newIntent, 0);
            }
        });

        actioList.setAdapter(adapter);
        actioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String Item = ((HashMap.Entry<String, String>) adapterView.getItemAtPosition(i)).getKey();
                showColorPickerDialogDemo(Item);
            }
        });

    }


    private void showColorPickerDialogDemo(final String key) {

        int initialColor = Color.WHITE;

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                showToast(color);
                appToRGB.put(key, "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color));
                InstantMessanger.addEnabledActions(key, "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color));
            }

        });
        colorPickerDialog.show();

    }

    private void showToast(int color) {
        String rgbString = "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color);
        Toast.makeText(this, rgbString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            String pack = data.getStringExtra("result");
            currentlyDisabled.remove(pack);
            currentlyEnabled.put(pack,newfinalpackages.get(pack));
            Toast.makeText(this, "Coming Here " + pack + "and putting " +newfinalpackages.get(pack), Toast.LENGTH_SHORT).show();
            newappToRGB.put(pack, DEFAULT_COLOR);
            InstantMessanger.addEnabledActions(pack, DEFAULT_COLOR);

            // BAD PRACTICE. notifyDaraSetChanged() Not Working.
            adapter = new ActionCustomAdapter(this, currentlyEnabled);
            actioList.setAdapter(adapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
