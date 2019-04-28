package com.atiqrs.myauthenticationfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText firstName,lastName,email,password;
    Button signinOnSignInPage;
    Button signupOnSignInPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In");

        init();

        signinOnSignInPage.setOnClickListener(this);
        signupOnSignInPage.setOnClickListener(this);
    }

    private void init() {
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupOnSignInPage = findViewById(R.id.signup);
        signinOnSignInPage = findViewById(R.id.signinOnSignInPage);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup){
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }
        /*switch (v.getId()){

            case R.id.signinOnSignInPage:
            break;

            case R.id.signupOnSignInPage:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            break;

        }*/
    }
}
