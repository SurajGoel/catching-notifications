package com.example.sgoel01.myapplication;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;

public class SelectEvent extends ListActivity {

    private HashMap<String,String> currentlyDisabled = new HashMap<String, String>();
    private ListView list_disabled;
    private ActionCustomAdapter disabled_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentlyDisabled = (HashMap<String,String>) getIntent().getSerializableExtra("Disabled_List");
        setContentView(R.layout.select_event);
        list_disabled = getListView();
        disabled_adapter = new ActionCustomAdapter(this,currentlyDisabled);
        list_disabled.setAdapter(disabled_adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        HashMap.Entry item = (HashMap.Entry)l.getItemAtPosition(position);
        Bundle b = new Bundle();
        b.putString("result", (String)item.getKey());
        getIntent().putExtras(b);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }
}
