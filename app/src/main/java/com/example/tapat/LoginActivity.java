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

    private final String lecturerId = "L1000";
    private final String lecturerPassword = "abc";
    private final String adminId = "A1000";
    private final String adminPassword = "123";

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
                if(enteredId.equals(lecturerId) && enteredPassword.equals(lecturerPassword)) {
                    /// Navigate to the lecturer page
                    Intent lecturerIntent = new Intent(LoginActivity.this, LecturerActivity.class);
                    startActivity(lecturerIntent);
                } else if (enteredId.equals(adminId) && enteredPassword.equals(adminPassword)) {
                    // Navigate to the admin page
                    Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(adminIntent);
                } else {
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