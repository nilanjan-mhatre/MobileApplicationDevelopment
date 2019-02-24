package mad.nil.messageme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser fUser;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.sign_up);

        Button cancel = findViewById(R.id.cancel);
        Button signup = findViewById(R.id.signup);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("users");

        Toolbar toolbar = findViewById(R.id.inbox_toolbar);
        toolbar.setTitle(getString(R.string.app_name) + " (" + getString(R.string.sign_up) + " )");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().show();
    }

    public void cancel() {
        finish();
    }

    public void signup() {
        EditText firstText = findViewById(R.id.first_name);
        EditText lastText = findViewById(R.id.last_name);
        EditText emailText = findViewById(R.id.email);
        EditText passwordText = findViewById(R.id.password);
        EditText repeatPasswordText = findViewById(R.id.repeat_password);

        String firstName = firstText.getText().toString();
        String lastName = lastText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String repeatPassword = repeatPasswordText.getText().toString();

        if(firstName == null || firstName.equals("")) {
            firstText.setError("Enter a valid first name");
            return;
        }
        if(lastName == null || lastName.equals("")) {
            lastText.setError("Enter a valid last name");
            return;
        }
        if(email == null || "".equals(email) || email.length() < 3 || !email.contains("@") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid EmailObj");
            return;
        }
        if(password == null || password.equals("")) {
            passwordText.setError("Enter a valid password");
            return;
        }
        if(repeatPassword == null || repeatPassword.equals("")) {
            repeatPasswordText.setError("Enter a valid password");
            return;
        }
        if(!repeatPassword.equals(password)) {
            repeatPasswordText.setError("Passwords do not match");
            return;
        }
        User user = new User(email, password);
        user.setFullName(firstName + " " + lastName);

        this.signup(user);
    }

    public void startMessages(User user) {
        if(user != null) {

            Intent intent = new Intent(this, EmailActivity.class);
            intent.putExtra("username", user.getFullName());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.signup_error, Toast.LENGTH_LONG).show();
        }
    }

    public void signup(final User user) {
        final Task<AuthResult> result = auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());

        result.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                User loggedUser = null;
                if(result.isSuccessful()) {
                    FirebaseUser fUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getFullName()).build();

                    fUser.updateProfile(profileUpdates);
                    try {
                        loggedUser = (User) user.clone();
                        loggedUser.setUserId(fUser.getUid());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    database.child(fUser.getUid()).setValue(user.getFullName());
                }

                startMessages(loggedUser);
            }
        });
    }
}
