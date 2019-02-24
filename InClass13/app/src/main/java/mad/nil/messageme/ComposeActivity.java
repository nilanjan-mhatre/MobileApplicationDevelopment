package mad.nil.messageme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class ComposeActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseUser fUser;
    DatabaseReference messagesDatabase;

    EditText messageText;
    AutoCompleteTextView toContact;
    Button sendButton;
    ImageButton contactsButton;

    List<String> usernameList;
    List<String> userIdList;

    private ArrayAdapter<String> textAdapter;
    private String[] contacts;
    private String to;
    private Integer index;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        setupUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setupData();
    }

    private void setupData() {


        EmailObj email = (EmailObj) getIntent().getSerializableExtra("email");
        if(email != null) {
            contactsButton.setVisibility(View.INVISIBLE);
            toContact.setEnabled(false);
            toContact.setText(email.getFromUsername());
            to = email.getFromUserId();
            setTo();
            flag = false;
        } else {
            usernameList = (List<String>) getIntent().getSerializableExtra("usernameList");
            userIdList = (List<String>) getIntent().getSerializableExtra("userIdList");
            contacts = usernameList.toArray(new String[usernameList.size()]);
            textAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.select_dialog_singlechoice, contacts);
            toContact.setThreshold(0);
            toContact.setAdapter(textAdapter);
            toContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setTo();
                }
            });
            flag = true;
        }
    }

    private void setupUI() {
        Toolbar toolbar = findViewById(R.id.inbox_toolbar);
        toolbar.setTitle(R.string.compose_message);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().show();

        usernameList = new ArrayList<>();
        userIdList = new ArrayList<>();
        index = 0;
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();

        messageText = findViewById(R.id.message_text);
        sendButton = findViewById(R.id.send_button);
        contactsButton = findViewById(R.id.contact_image);
        toContact = findViewById(R.id.auto_contact_text);
        toContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setTo();
            }
        });
        toContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTo();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContacts();
            }
        });

    }

    private void showContacts() {
        new AlertDialog.Builder(this).setItems(contacts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toContact.setText(usernameList.get(which));
                setTo();
            }
        }).show();
    }

    public void sendEmail() {

        String message = messageText.getText().toString();

        if(message == null || message.equals("")) {
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if(to == null || to.equals("")) {
            setTo();
        }*/
        if(to == null || to.equals("")) {
            toContact.setError("Select a contact");
            return;
        }

        EmailObj emailObj = new EmailObj(message, fUser.getUid(), fUser.getDisplayName(), to);
        emailObj.setCurrentDate();
        emailObj.setKey(index);
        messagesDatabase.child(index.toString()).setValue(emailObj);
        Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void setTo() {

        toContact.setError(null);
        if(flag) {
            String toStr = toContact.getText().toString();
            if (toStr != null && !toStr.equals("") && userIdList != null) {
                try {
                    to = userIdList.get(usernameList.indexOf(toStr));
                } catch (Exception e) {
                    to = null;
                }
            }
        }
        if(to != null) {
            messagesDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("messages")
                    .child(to);
            messagesDatabase.keepSynced(true);
            index = 0;
            messagesDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    index = Integer.parseInt(dataSnapshot.getKey()) + 1;
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out) {
            auth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
