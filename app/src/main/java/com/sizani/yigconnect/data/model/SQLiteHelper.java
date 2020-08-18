package com.sizani.yigconnect.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "YIGConnectDataBase";

    public static final String USER_TABLE_NAME = "UserTable";
    public static final String Table_Column_ID = "id";
    public static final String Table_Column_1_Name = "name";
    public static final String Table_Column_2_Email = "email";
    public static final String Table_Column_3_Password = "password";
    public static final String Table_Column_4_Type_Of_User = "typeOfUser";

    public static final String EVENT_TABLE_NAME = "EventTable";
    public static final String Table_Event_Name = "eventName";
    public static final String Table_Event_Date = "eventDate";
    public static final String Table_Event_Time = "eventTime";
    public static final String Table_Event_Id = "id";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE =
                "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" + Table_Column_ID + " INTEGER PRIMARY KEY, " +
                        Table_Column_1_Name + " VARCHAR, " + Table_Column_2_Email + " VARCHAR, " +
                        Table_Column_3_Password + " VARCHAR," + Table_Column_4_Type_Of_User + " INTEGER)";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_EVENT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + EVENT_TABLE_NAME + " (" + Table_Event_Id + " INTEGER PRIMARY KEY, " +
                        Table_Event_Name + " VARCHAR, " + Table_Event_Time + " VARCHAR, " + Table_Event_Date +
                        " VARCHAR )";
        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
}
