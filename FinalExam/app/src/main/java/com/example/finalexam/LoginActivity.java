package com.example.finalexam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    private FirebaseAuth auth;
    private FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch (Exception e){
        }
        FirebaseApp.initializeApp(this.getApplicationContext());
        auth = FirebaseAuth.getInstance();
        fuser = auth.getCurrentUser();

        setTitle(R.string.login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email == null || email.equals("")){
                    Toast.makeText(LoginActivity.this, "Enter Email !!", Toast.LENGTH_SHORT).show();
                } else if(password == null || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Enter Password !!", Toast.LENGTH_SHORT).show();
                } else{
                    final Task<AuthResult> result = auth.signInWithEmailAndPassword(email, password);
                    result.addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            User loggedUser = null;
                            if(result.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, ChristmasListActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fuser = auth.getCurrentUser();
        if(fuser != null) {
            Intent intent = new Intent(LoginActivity.this, ChristmasListActivity.class);
            startActivity(intent);
            finish();
        }
        // include code to handle the case if the user is already logged in,
        // which should take the user to main activity and finish this activity.
    }
}

