package com.example.tapat;


import androidx.appcompat.app.AppCompatActivity;
import com.example.tapat.helpers.dbHelper;
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
    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailInputField = (EditText) findViewById(R.id.loginEmailInput);
        passwordInputField = (EditText) findViewById(R.id.loginPasswordInput);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        db = new dbHelper(this);
        db.insertAdmin();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* change this to connect to the database when we implement database*/
                 email = emailInputField.getText().toString();
                 password = passwordInputField.getText().toString();
                    /* if correct send the user to the homepage*/
                 boolean authPass = db.authLogin(email,password);
                 userRole = db.userAuthControl(email,password);
                if(authPass) {
                    Toast.makeText(getApplicationContext(),userRole,Toast.LENGTH_SHORT).show();
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
                    //loginToastView.setBackgroundColor(Color.parseColor("#ffc6c4"));

                    loginToastError.show();
                }
            }
        });
    }
}