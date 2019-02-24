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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    private FirebaseAuth auth;
    private FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.signup);
        auth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email == null || email.equals("")){
                    Toast.makeText(SignUpActivity.this, "Enter Email !!", Toast.LENGTH_SHORT).show();
                } else if(password == null || password.equals("")){
                    Toast.makeText(SignUpActivity.this, "Enter Password !!", Toast.LENGTH_SHORT).show();
                } else{
                    final Task<AuthResult> result = auth.createUserWithEmailAndPassword(email, password);

                    result.addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            User loggedUser = null;
                            if(result.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this, ChristmasListActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, "SignUp Failed !!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
