package com.sizani.yigconnect.ui.login;

import androidx.appcompat.app.ActionBar;
import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.model.SQLiteHelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class RegistrationActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    EditText email, password, name;
    Spinner spinner;
    Button register;
    String nameHolder, emailHolder, passwordHolder;
    Boolean editTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String sQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    String roleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("User Registration");
        }

        register = findViewById(R.id.buttonRegister);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        name = findViewById(R.id.editName);
        spinner = findViewById(R.id.spinner);
        sqLiteHelper = new SQLiteHelper(this);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.UserRole, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sQLiteDataBaseBuild();
                sQLiteTableBuild();
                checkEditTextStatus();
                checkingEmailAlreadyExistsOrNot();
                emptyEditTextAfterDataInsert();
            }
        });
    }

    public void sQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void sQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL(
                "CREATE TABLE IF NOT EXISTS " + SQLiteHelper.USER_TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name +
                        " VARCHAR, " + SQLiteHelper.Table_Column_2_Email + " VARCHAR, " +
                        SQLiteHelper.Table_Column_3_Password + " VARCHAR," + SQLiteHelper.Table_Column_4_Type_Of_User +
                        " VARCHAR);");
    }

    public void insertDataIntoSQLiteDatabase() {

        if (editTextEmptyHolder) {
            sQLiteDataBaseQueryHolder =
                    "INSERT INTO " + SQLiteHelper.USER_TABLE_NAME + " (name,email,password,typeOfUser) VALUES('" +
                            nameHolder + "', '" + emailHolder + "','" + passwordHolder + "', '" + getRole() + "');";

            sqLiteDatabaseObj.execSQL(sQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            showSuccessIonDialog("SignUp", "User Registered Successfully");
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            showErrorIonDialog("SignUp", "Please Fill All The Required Fields.");
        }
    }

    public void emptyEditTextAfterDataInsert() {
        name.getText().clear();
        email.getText().clear();
        password.getText().clear();
    }

    public void checkEditTextStatus() {
        nameHolder = name.getText().toString();
        emailHolder = email.getText().toString();
        passwordHolder = password.getText().toString();

        editTextEmptyHolder =
                !TextUtils.isEmpty(nameHolder) && !TextUtils.isEmpty(emailHolder) && !TextUtils.isEmpty(passwordHolder);
    }

    public void checkingEmailAlreadyExistsOrNot() {
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj
                .query(SQLiteHelper.USER_TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?",
                        new String[] {emailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        checkFinalResult();
    }

    public void checkFinalResult() {
        if (F_Result.equalsIgnoreCase("Email Found")) {
            showErrorIonDialog("SignUp", "Email Already Exists");
        } else {
            insertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found";
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roleText = parent.getItemAtPosition(position).toString();
    }

    private int getRole() {
        if (roleText.equalsIgnoreCase("Admin")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
