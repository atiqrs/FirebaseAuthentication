package com.atiqrs.myauthenticationfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName,lastName,email,password;
    Button SignUponSignupPage,SignINonSignupPage;
    ProgressBar progressBar;
    private long backPressedTime;
    //public FirebaseAuth mAuth;

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

        progressBar = findViewById(R.id.progressbarId);
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

        progressBar.setVisibility(View.VISIBLE);
        FirebaseApp.initializeApp(Main2Activity.this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(rEmail,rPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Main2Activity.this, "Sign Up Succesfull", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(Main2Activity.this, "User is already registared", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Main2Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        return;
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            exitMe();
            return;
        } else
            Toast.makeText(getApplicationContext(), "Press Again to exit", Toast.LENGTH_SHORT).show();
        backPressedTime = System.currentTimeMillis();


    }

    private void exitMe() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
        finish();
    }
}
