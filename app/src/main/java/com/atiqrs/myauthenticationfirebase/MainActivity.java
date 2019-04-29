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

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText username,password;
    private Button signinOnSignInPage;
    private Button signupOnSignInPage;
    private ProgressBar progressBar;
    private long backPressedTime;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In");

            FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();

        init();

        signinOnSignInPage.setOnClickListener(this);
        signupOnSignInPage.setOnClickListener(this);
        exitMeFromApps();
    }


    private void init() {

        username = findViewById(R.id.username);
        password = findViewById(R.id.pass);

        progressBar = findViewById(R.id.progressbarIdSignIn);

        signupOnSignInPage = findViewById(R.id.signup);
        signinOnSignInPage = findViewById(R.id.signinOnSignInPage);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signup:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            break;

            case R.id.signinOnSignInPage:
                getSigning();
            break;
        }
    }

    private void getSigning() {

        String rUserName = username.getText().toString().trim();
        String rPassword = password.getText().toString().trim();

        if(rUserName.isEmpty()){
            username.setError("Fill the field");
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(rUserName).matches()){
            username.setError("Enter a valid Email Address");
            username.requestFocus();
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

        mAuth.signInWithEmailAndPassword(rUserName, rPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(MainActivity.this,Home.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "Login Faild", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }



    private void exitMeFromApps() {
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return;
        }
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else
            Toast.makeText(getApplicationContext(), "Press Again to exit", Toast.LENGTH_SHORT).show();
        backPressedTime = System.currentTimeMillis();
    }

}
