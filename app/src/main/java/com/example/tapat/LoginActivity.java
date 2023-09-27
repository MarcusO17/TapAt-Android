package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText emailInputField;
    EditText passwordInputField;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailInputField = (EditText) findViewById(R.id.loginEmailInput);
        passwordInputField = (EditText) findViewById(R.id.loginPasswordInput);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* change this to connect to the database when we implement database*/
                if(emailInputField.getText().toString() == "1234" && passwordInputField.getText().toString() == "1234") {
                    /* if correct send the user to the homepage*/

                    /* Intent intent = new Intent(this, Homepage.class);
                    startActivity(intent);*aaaaaaaaaaaaaaaaaaa/
                }
                else {
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