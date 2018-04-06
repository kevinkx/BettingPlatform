package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView textView;
    private Button topup;
    private Button logout;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        String encodedEmail = User.encodeUserEmail(email);

        DatabaseReference myRef = database.getReference("Users").child(encodedEmail);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String fullname = dataSnapshot.child("fullname").getValue(String.class);
                String username = dataSnapshot.child("username").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);
                int point = dataSnapshot.child("point").getValue(int.class);
                textView = (TextView) findViewById(R.id.textView1);
                textView.setText("Fullname: "+fullname+"\n"+"Username: "+username+"\n"+"Password: "+password+"\n"+"Point: "+point+"\n");
                user = new User(username, fullname, fullname, point);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        logout = (Button) findViewById(R.id.btnLogout);
        topup = (Button) findViewById(R.id.btnTopUp);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, FirstActivity.class));
                finish();
            }
        });
        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String encodedEmail = User.encodeUserEmail(auth.getCurrentUser().getEmail());
                 user.setPoint(user.getPoint()+100);
                myRef2.child("Users").child(encodedEmail).setValue(user, new DatabaseReference.CompletionListener(){
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null)
                        {
                            //Toast.makeText(getApplicationContext(), "Data could not be saved.",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //Toast.makeText(getApplicationContext(), "Data saved successfully.",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });

    }

}
