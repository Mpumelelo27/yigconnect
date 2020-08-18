package com.sizani.yigconnect.data.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.model.SQLiteHelper;

import java.util.ArrayList;

public class UpdateListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> id;
    private ArrayList<String> eventName;
    private ArrayList<String> eventDate;
    private ArrayList<String> eventTime;

    public UpdateListAdapter(Context context, ArrayList<String> id, ArrayList<String> eventName,
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
            convertView = layoutInflater.inflate(R.layout.listviewupdate_event, parent, false);

            holder = new Holder(convertView.getContext());

            holder.editTextEventName = convertView.findViewById(R.id.editTextEventName);
            holder.editTextEventDate = convertView.findViewById(R.id.editTextEventDate);
            holder.editTextEventTime = convertView.findViewById(R.id.editTextEventTime);
            holder.updateButton = convertView.findViewById(R.id.updateButton);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.editTextEventName.setText(eventName.get(position));
        holder.editTextEventDate.setText(eventDate.get(position));
        holder.editTextEventTime.setText(eventTime.get(position));

        holder.updateButton.setOnClickListener(view -> holder.updateEvent(holder.editTextEventName.getText().toString(),
                holder.editTextEventName.getText().toString(), holder.editTextEventName.getText().toString(),
                id.get(position)));
        return convertView;
    }

    static class Holder {

        Context context;

        Holder(Context context) {
            this.context = context;
            sqLiteHelper = new SQLiteHelper(context);
        }

        EditText editTextEventName;
        EditText editTextEventDate;
        EditText editTextEventTime;
        Button updateButton;
        SQLiteHelper sqLiteHelper;
        String sQLiteDataBaseQueryHolder;
        SQLiteDatabase sqLiteDatabaseObj;

        private void updateEvent(String eventName, String eventDate, String eventTime, String evenId) {
            openSQLiteDataBase();
            sQLiteDataBaseQueryHolder =
                    "UPDATE " + SQLiteHelper.EVENT_TABLE_NAME + " SET " + SQLiteHelper.Table_Event_Name + " = '" +
                            eventName + "' , " + SQLiteHelper.Table_Event_Date + " = '" + eventDate + "' , " +
                            SQLiteHelper.Table_Event_Time + " = '" + eventTime + "' WHERE id = " + evenId + "";

            sqLiteDatabaseObj.execSQL(sQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(context, "Data Edit Successfully", Toast.LENGTH_LONG).show();
        }

        private void openSQLiteDataBase() {
            sqLiteDatabaseObj = context.openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
        }
    }
}