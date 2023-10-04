package com.example.tapat;


import androidx.appcompat.app.AppCompatActivity;
import com.example.tapat.helpers.dbHelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailInputField;
    String email;
    String password;
    EditText passwordInputField;
    Button buttonLogin;
    dbHelper db;
    String sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailInputField = (EditText) findViewById(R.id.loginEmailInput);
        passwordInputField = (EditText) findViewById(R.id.loginPasswordInput);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        db = new dbHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    String errorText = "Wrong Credentials";
                    if(emailInputField.getText().toString().equals("")) {
                        errorText = "Login Failed: Empty Credentials";
                    } else if (passwordInputField.getText().toString().equals("")) {
                        errorText = "Login Failed: Empty Password";
                    }
                    emailInputField.setText("");
                    passwordInputField.setText("");
                    Toast loginToastError = Toast.makeText(view.getContext(), errorText, Toast.LENGTH_SHORT);
                    View loginToastView = loginToastError.getView();
                    //loginToastView.setBackgroundColor(Color.parseColor("#ffc6c4"));

                    loginToastError.show();
                }
            }
        });
    }
}