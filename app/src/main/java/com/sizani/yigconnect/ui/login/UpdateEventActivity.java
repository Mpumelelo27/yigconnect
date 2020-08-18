package com.sizani.yigconnect.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.adapter.UpdateListAdapter;
import com.sizani.yigconnect.data.model.SQLiteHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class UpdateEventActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    UpdateListAdapter listAdapter;
    ListView listView;

    ArrayList<String> id;
    ArrayList<String> eventName;
    ArrayList<String> eventDate;
    ArrayList<String> eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Update Event");
        }

        listView = findViewById(R.id.listUpdateEvent);

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
        listAdapter = new UpdateListAdapter(UpdateEventActivity.this, id, eventName, eventDate, eventTime);
        listView.setAdapter(listAdapter);
        cursor.close();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
