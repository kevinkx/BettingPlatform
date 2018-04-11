package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Adapter.ChatAdapter;
import com.rpll.okeoke.bettingplatform.Model.Livechat;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Livechat> chats = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnSend;
    private EditText inputChat;
    private FirebaseAuth auth;
    long totalChat;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference myRef2 = database.getReference();
    DatabaseReference myRefUser;
    String username = "Anonymous";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        final String encodedEmail = User.encodeUserEmail(email);
        myRefUser = database.getReference("Users").child(encodedEmail);
        myRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("username").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.viewChat);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myRef = database.getReference("Livechat");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // specify an adapter (see also next example)
                boolean myChat = false;
                totalChat = dataSnapshot.getChildrenCount();
                chats = new ArrayList<>();
                Livechat chat;
                for (int i = 0; i < totalChat; i++) {
                    chat = new Livechat();
                    myChat = false;
                    chat.setUsername(dataSnapshot.child(Integer.toString(i)).child("username").getValue(String.class));
                    chat.setChat(dataSnapshot.child(Integer.toString(i)).child("chat").getValue(String.class));
                    chat.setDate(dataSnapshot.child(Integer.toString(i)).child("date").getValue(String.class));
                    if(dataSnapshot.child(Integer.toString(i)).child("email").getValue(String.class).equals(encodedEmail))
                    {
                        myChat = true;
                    }
                    chat.setMyChat(myChat);
                    chats.add(chat);
                }
                mAdapter = new ChatAdapter(chats);
                mRecyclerView.setAdapter(mAdapter);
                int position = (int) totalChat - 1;
                mRecyclerView.scrollToPosition(position);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        inputChat = (EditText) findViewById(R.id.inputChat);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String isiChat = inputChat.getText().toString();
                inputChat.setText("");
                inputChat.clearFocus();
                if (TextUtils.isEmpty(isiChat)) {
                    Toast.makeText(getApplicationContext(), "Enter the message!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Create an instance of SimpleDateFormat used for formatting
                // the string representation of date (month/day/year)
                DateFormat df = new SimpleDateFormat("EEEE, MM/dd/yyyy HH:mm:ss");
                // Get the date today using Calendar object.
                Date today = Calendar.getInstance().getTime();
                // Using DateFormat format method we can create a string
                // representation of a date with the defined format.
                String reportDate = df.format(today);
                Livechat livechat = new Livechat(username, encodedEmail,isiChat, reportDate);
                myRef2.child("Livechat").child(Long.toString(totalChat)).setValue(livechat, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getApplicationContext(), "Failed to sent the message.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id== R.id.action_logout)
        {
            auth.signOut();
            startActivity(new Intent(ChatActivity.this, FirstActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
