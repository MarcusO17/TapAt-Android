package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText emailInputField;
    EditText passwordInputField;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    startActivity(intent);*/
                }
                else {
                    /* maybe something like a red alert telling the user login failed on the bottom of the screen*/
                }
            }
        });
    }
}