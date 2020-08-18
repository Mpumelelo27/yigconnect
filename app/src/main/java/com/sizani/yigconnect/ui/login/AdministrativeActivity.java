package com.sizani.yigconnect.ui.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.sizani.yigconnect.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdministrativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrative);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle("Admin Dashboard");
        }
    }

    public void insertEventActivity(View view) {
        Intent myIntent = new Intent(AdministrativeActivity.this, InsertEventActivity.class);
        AdministrativeActivity.this.startActivity(myIntent);
    }

    public void updateEventView(View view) {
        Intent myIntent = new Intent(AdministrativeActivity.this, UpdateEventActivity.class);
        AdministrativeActivity.this.startActivity(myIntent);
    }

    public void deleteEventActivity(View view) {
        Intent myIntent = new Intent(AdministrativeActivity.this, DeleteEventActivity.class);
        AdministrativeActivity.this.startActivity(myIntent);
    }
}
