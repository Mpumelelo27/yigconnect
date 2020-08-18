package com.sizani.yigconnect.data.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.model.SQLiteHelper;

import java.util.ArrayList;

public class DeleteEventsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> id;
    private ArrayList<String> eventName;
    private ArrayList<String> eventDate;
    private ArrayList<String> eventTime;

    public DeleteEventsAdapter(Context context, ArrayList<String> id, ArrayList<String> eventName,
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.listviewdelete_event, parent, false);

            holder = new Holder(convertView.getContext());

            holder.textViewDeleteEventName = convertView.findViewById(R.id.textViewDeleteEventName);
            holder.textViewDeleteEventDate = convertView.findViewById(R.id.textViewDeleteEventDate);
            holder.textViewDeleteEventTime = convertView.findViewById(R.id.textViewDeleteEventTime);
            holder.deleteButton = convertView.findViewById(R.id.deleteButton);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.textViewDeleteEventName.setText(eventName.get(position));
        holder.textViewDeleteEventDate.setText(eventDate.get(position));
        holder.textViewDeleteEventTime.setText(eventTime.get(position));

        holder.deleteButton.setOnClickListener(view -> holder.deleteEvent(Integer.parseInt(id.get(position))));
        return convertView;
    }

    static class Holder {

        Context context;

        Holder(Context context) {
            this.context = context;
            sqLiteHelper = new SQLiteHelper(context);
        }

        EditText textViewDeleteEventName;
        EditText textViewDeleteEventDate;
        EditText textViewDeleteEventTime;
        Button deleteButton;
        SQLiteHelper sqLiteHelper;
        String sQLiteDataBaseQueryHolder;
        SQLiteDatabase sqLiteDatabaseObj;

        private void deleteEvent(int evenId) {
            openSQLiteDataBase();
            sQLiteDataBaseQueryHolder = "DELETE FROM " + SQLiteHelper.EVENT_TABLE_NAME + " WHERE id = " + evenId + "";

            sqLiteDatabaseObj.execSQL(sQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(context, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
        }

        private void openSQLiteDataBase() {
            sqLiteDatabaseObj = context.openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
        }
    }
}