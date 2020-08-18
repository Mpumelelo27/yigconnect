package com.sizani.yigconnect.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.adapter.AffiliateAdapter;
import com.sizani.yigconnect.data.adapter.UpdateListAdapter;
import com.sizani.yigconnect.data.model.SQLiteHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AffiliateActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    AffiliateAdapter listAdapter;
    ListView listView;

    ArrayList<String> id;
    ArrayList<String> eventName;
    ArrayList<String> eventDate;
    ArrayList<String> eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiliate);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("Affiliate Dashboard");
        }

        listView = findViewById(R.id.affiliatLlistUpdateEvent);

        sqLiteHelper = new SQLiteHelper(this);

        id = new ArrayList<>();
        eventName = new ArrayList<>();
        eventDate = new ArrayList<>();
        eventTime = new ArrayList<>();
        displayEvents();
    }

    private void displayEvents() {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.EVENT_TABLE_NAME + "", null);

        id.clear();
        eventName.clear();
        eventDate.clear();
        eventTime.clear();

        if (cursor.moveToFirst()) {
            do {

                id.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Event_Id)));
                eventName.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Event_Name)));
                eventDate.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Event_Date)));
                eventTime.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Event_Time)));
            } while (cursor.moveToNext());
        }
        listAdapter = new AffiliateAdapter(AffiliateActivity.this, id, eventName, eventDate, eventTime);
        listView.setAdapter(listAdapter);
        cursor.close();
    }
}
