package com.sizani.yigconnect.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import id.ionbit.ionalert.IonAlert;

abstract class BaseActivity extends AppCompatActivity {

    protected void showErrorIonDialog(String title, String message) {
        new IonAlert(this, IonAlert.ERROR_TYPE).
                setTitleText(title).
                setContentText(message).
                show();
    }

    protected void showSuccessIonDialog(String title, String message) {
        new IonAlert(this, IonAlert.
                SUCCESS_TYPE).
                setTitleText(title).
                setContentText(message).
                show();
    }
}