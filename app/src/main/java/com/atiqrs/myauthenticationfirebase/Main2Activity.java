package com.atiqrs.myauthenticationfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName,lastName,email,password;
    Button SignUponSignupPage,SignINonSignupPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.setTitle("Sign Up");
        init();

        SignINonSignupPage.setOnClickListener(this);
        SignUponSignupPage.setOnClickListener(this);

    }

    private void init() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        SignINonSignupPage = findViewById(R.id.SignInonSignupPage);
        SignUponSignupPage = findViewById(R.id.SignUponSignupPage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.SignUponSignupPage:
                getRegistration();
             break;

             case R.id.SignInonSignupPage:
                 startActivity(new Intent(Main2Activity.this,MainActivity.class));
             break;

        }
    }

    private void getRegistration() {
        String rFirstName = firstName.getText().toString().trim();
        String rLastName = lastName.getText().toString().trim();
        String rEmail = email.getText().toString().trim();
        String rPassword = password.getText().toString().trim();

        if(rFirstName.isEmpty()){
            firstName.setError("Fill the field");
            firstName.requestFocus();
            return;
        }
        if(rLastName.isEmpty()){
            lastName.setError("Fill the field");
            lastName.requestFocus();
            return;
        }
        if(rEmail.isEmpty()){
            email.setError("Fill the field");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(rEmail).matches()){
            email.setError("Enter a valid Email Address");
            email.requestFocus();
            return;
        }
        if(rPassword.isEmpty()){
            password.setError("Fill the field");
            password.requestFocus();
            return;
        }
        if(rPassword.length()<6){
            password.setError("Enter password minimum 6 digit");
            password.requestFocus();
            return;
        }
    }
}
