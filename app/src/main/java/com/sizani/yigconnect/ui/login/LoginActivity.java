package com.sizani.yigconnect.ui.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sizani.yigconnect.R;
import com.sizani.yigconnect.data.model.SQLiteHelper;

public class LoginActivity extends BaseActivity {
    EditText usernameEditText, passwordEditText;
    Button loginButton, signUpButton;
    ProgressBar loadingProgressBar;
    String emailHolder, passwordHolder;
    Boolean editTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    int role;
    String tempPassword = "NOT_FOUND";
    public static final String UserEmail = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.editEmail);
        passwordEditText = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.buttonLogin);
        signUpButton = findViewById(R.id.buttonRegister);
        loadingProgressBar = findViewById(R.id.loading);
        sqLiteHelper = new SQLiteHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                checkEditTextStatus();
                login();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login() {

        if (editTextEmptyHolder) {
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
            cursor = sqLiteDatabaseObj
                    .query(SQLiteHelper.USER_TABLE_NAME, null, " " + SQLiteHelper.Table_Column_2_Email + "=?",
                            new String[] {emailHolder}, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    tempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));
                    role = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_Type_Of_User));
                    cursor.close();
                }
            }
            checkFinalResult();
        } else {
            loadingProgressBar.setVisibility(View.GONE);
            showErrorIonDialog("Login", "Please Enter UserName or Password.");
        }
    }

    public void checkEditTextStatus() {
        emailHolder = usernameEditText.getText().toString();
        passwordHolder = passwordEditText.getText().toString();
        editTextEmptyHolder = !TextUtils.isEmpty(emailHolder) && !TextUtils.isEmpty(passwordHolder);
    }

    public void checkFinalResult() {
        if (tempPassword.equalsIgnoreCase(passwordHolder)) {
            loadingProgressBar.setVisibility(View.GONE);
            if (role == 0) {
                Intent intent = new Intent(LoginActivity.this, AdministrativeActivity.class);
                intent.putExtra(UserEmail, emailHolder);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, AffiliateActivity.class);
                intent.putExtra(UserEmail, emailHolder);
                startActivity(intent);
            }
        } else {
            loadingProgressBar.setVisibility(View.GONE);
            showErrorIonDialog("Login", "UserName or Password is Wrong, Please Try Again.");
        }
        tempPassword = "NOT_FOUND";
    }
}
