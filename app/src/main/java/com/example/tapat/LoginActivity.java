package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tapat.helpers.dbHelper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText emailInputField;
    EditText passwordInputField;
    String email;
    String password;
    Button buttonLogin;
    dbHelper db;
    String sessionID;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;

        if (checkPermissionForReadExtertalStorage() == false) {
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("CURRENT API LEVEL", currentApiVersion + "");

        emailInputField = (EditText) findViewById(R.id.loginIDInput);
        passwordInputField = (EditText) findViewById(R.id.loginPasswordInput);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        db = new dbHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailInputField.getText().toString();
                password = passwordInputField.getText().toString();
                /* change this to connect to the database when we implement database*/
                /* if correct send the user to the homepage*/
                sessionID = db.userAuthorization(email, password);
                if (sessionID.length() > 0) {
                    if (sessionID.startsWith("A")) {
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        intent.putExtra("sessionID", sessionID);
                        startActivity(intent);
                    }
                    if (sessionID.startsWith("L")) {
                        Intent intent = new Intent(getApplicationContext(), FragmentHolderActivity.class);
                        intent.putExtra("sessionID", sessionID);
                        startActivity(intent);
                    }
                } else {
                    /* toast to alert the user that the login has failed*/
                    String errorText = "";
                    if (email.equals("")) {
                        errorText = "Login Failed: Invalid Credentials";
                    } else if (password.equals("")) {
                        errorText = "Login Failed: Invalid Password";
                    }
                    Toast loginToastError = Toast.makeText(view.getContext(), errorText, Toast.LENGTH_SHORT);
                    View loginToastView = loginToastError.getView();
                    loginToastError.show();

                }
            }
        });
    }
    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}