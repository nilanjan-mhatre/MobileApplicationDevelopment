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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser fUser;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login);

        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
        database = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = findViewById(R.id.inbox_toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().show();
    }

    public void login() {

        EditText emailText = findViewById(R.id.email);
        EditText passwordText = findViewById(R.id.password);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        if(email == null || "".equals(email) || email.length() < 3 || !email.contains("@") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError(getString(R.string.email_error));
            return;
        }
        if(password == null || password.equals("")) {
            passwordText.setError(getString(R.string.password_error));
            return;
        }

        this.login(new User(email, password));
    }

    public void signup() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void startMessages(User user) {
        if(user != null) {
            Intent intent = new Intent(this, EmailActivity.class);
            intent.putExtra("username", user.getFullName());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
        }

    }

    public void login(final User user) {
        final Task<AuthResult> result = auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword());
        result.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                User loggedUser = null;
                if(result.isSuccessful()) {
                    FirebaseUser fUser = auth.getCurrentUser();
                    try {
                        loggedUser = (User) user.clone();
                        loggedUser.setFullName(fUser.getDisplayName());
                        loggedUser.setUserId(fUser.getUid());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                startMessages(loggedUser);
            }
        });
    }
}
