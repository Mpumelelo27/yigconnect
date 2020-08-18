package com.sizani.yigconnect.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sizani.yigconnect.R;

import java.util.ArrayList;

public class AffiliateAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> id;
    private ArrayList<String> eventName;
    private ArrayList<String> eventDate;
    private ArrayList<String> eventTime;

    public AffiliateAdapter(Context context, ArrayList<String> id, ArrayList<String> eventName,
            ArrayList<String> eventDate, ArrayList<String> eventTime) {
        this.context = context;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.id = id;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AffiliateHolder holder;
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.normal_user_list, parent, false);

            holder = new AffiliateHolder();

            holder.affiliteEventName = convertView.findViewById(R.id.affiliteEventName);
            holder.affiliteEventDate = convertView.findViewById(R.id.affiliteEventDate);
            holder.affiliteEventTime = convertView.findViewById(R.id.affiliteEventTime);
            convertView.setTag(holder);
        } else {
            holder = (AffiliateHolder) convertView.getTag();
        }
        holder.affiliteEventName.setText(eventName.get(position));
        holder.affiliteEventDate.setText(eventDate.get(position));
        holder.affiliteEventTime.setText(eventTime.get(position));

        return convertView;
    }

    public static class AffiliateHolder {

        TextView affiliteEventName;
        TextView affiliteEventDate;
        TextView affiliteEventTime;
    }
}
