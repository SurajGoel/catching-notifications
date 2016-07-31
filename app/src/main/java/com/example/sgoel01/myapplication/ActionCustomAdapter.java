package com.example.sgoel01.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionCustomAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList mData;
    private static LayoutInflater inflater=null;

    public ActionCustomAdapter(Activity a, HashMap<String,String> d) {
        activity = a;
        mData = new ArrayList();
        mData.addAll(d.entrySet());
        inflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HashMap.Entry<String, String> getItem(int position) {
        return (HashMap.Entry) mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_notification, null);

        HashMap.Entry<String, String> item = getItem(position);

        TextView text=(TextView)vi.findViewById(R.id.textAction);;
        ImageView image=(ImageView)vi.findViewById(R.id.imageAction);

        text.setText(item.getValue());
        return vi;
    }


}
