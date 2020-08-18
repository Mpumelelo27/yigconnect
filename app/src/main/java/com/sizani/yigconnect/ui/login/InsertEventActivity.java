package com.sizani.yigconnect.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.model.SQLiteHelper;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertEventActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabaseObj;
    EditText eventName, eventDate, eventTime;
    Button insertEvent;
    String sQLiteDataBaseQueryHolder;
    String eventNameHolder, eventDateHolder, eventTimeHolder;
    Boolean editTextEmptyHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_event);

        eventName = findViewById(R.id.eventName);
        eventDate = findViewById(R.id.eventDate);
        eventTime = findViewById(R.id.eventTime);
        insertEvent = findViewById(R.id.insertEvent);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Create Event");
        }

        insertEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sQLiteDataBaseBuild();
                sQLiteTableBuild();
                checkEditTextStatus();
                emptyEditTextAfterDataInsert();
                insertDataIntoSQLiteDatabase();
            }
        });
    }

    public void sQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void sQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL(
                "CREATE TABLE IF NOT EXISTS " + SQLiteHelper.EVENT_TABLE_NAME + "(" + SQLiteHelper.Table_Event_Id +
                        " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Event_Name + " VARCHAR, " +
                        SQLiteHelper.Table_Event_Date + " VARCHAR, " + SQLiteHelper.Table_Event_Time + " VARCHAR);");
    }

    public void insertDataIntoSQLiteDatabase() {

        if (editTextEmptyHolder) {
            sQLiteDataBaseQueryHolder =
                    "INSERT INTO " + SQLiteHelper.EVENT_TABLE_NAME + " (eventName,eventDate,eventTime) VALUES('" +
                            eventNameHolder + "', '" + eventDateHolder + "','" + eventTimeHolder + "');";

            sqLiteDatabaseObj.execSQL(sQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(InsertEventActivity.this, "Event Created Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(InsertEventActivity.this, AdministrativeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(InsertEventActivity.this, "Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void emptyEditTextAfterDataInsert() {
        eventName.getText().clear();
        eventDate.getText().clear();
        eventTime.getText().clear();
    }

    public void checkEditTextStatus() {
        eventNameHolder = eventName.getText().toString();
        eventDateHolder = eventDate.getText().toString();
        eventTimeHolder = eventTime.getText().toString();

        editTextEmptyHolder = !TextUtils.isEmpty(eventNameHolder) && !TextUtils.isEmpty(eventDateHolder) &&
                !TextUtils.isEmpty(eventTimeHolder);
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
