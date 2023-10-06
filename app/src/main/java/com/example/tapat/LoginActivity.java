package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailInputField;
    EditText passwordInputField;
    Button buttonLogin;
    dbHelper db;
    String sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailInputField = (EditText) findViewById(R.id.loginIDInput);
        passwordInputField = (EditText) findViewById(R.id.loginPasswordInput);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                String enteredId = emailInputField.getText().toString();
                String enteredPassword = passwordInputField.getText().toString();
                /* change this to connect to the database when we implement database*/
                 email = emailInputField.getText().toString();
                 password = passwordInputField.getText().toString();
                    /* if correct send the user to the homepage*/
                sessionID  = db.userAuthorization(email,password);
                if(sessionID.length()>0) {
                    if(sessionID.startsWith("A")){
                        Intent intent = new Intent(getApplicationContext(), LecturerHomepageActivity.class);
                        intent.putExtra("sessionID",sessionID);
                        startActivity(intent);
                    }
                }else {
                    /* toast to alert the user that the login has failed*/
                    String errorText = "";
                    if(emailInputField.getText().toString() == "") {
                        errorText = "Login Failed: Invalid Credentials";
                    } else if (passwordInputField.getText().toString()=="") {
                        errorText = "Login Failed: Invalid Password";
                    }
                    Toast loginToastError = Toast.makeText(view.getContext(), errorText, Toast.LENGTH_SHORT);
                    View loginToastView = loginToastError.getView();
                    loginToastView.setBackgroundColor(Color.parseColor("#ffc6c4"));

                    loginToastError.show();
                }
            }
        });
    }
}