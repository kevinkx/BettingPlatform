package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Model.Betting;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

public class BetDetailActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    public static String BID;
    private TextView txtPoint, txtTeam1, txtTeam2,txtOds1,txtOds2,txtBet1,txtBet2,txtStatus;
    private EditText inputPoint;
    private Button btnSubmit;
    private RadioButton rb1;
    private RadioButton rb2;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private DatabaseReference myRef3;
    private String encodedEmail = "";
    private int point = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_detail);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setContentView(R.layout.activity_bet_detail);
        auth = FirebaseAuth.getInstance();
        txtPoint = (TextView) findViewById(R.id.txtPoint);
        String email = auth.getCurrentUser().getEmail();
        encodedEmail = User.encodeUserEmail(email);
        rb1 = (RadioButton) findViewById(R.id.selectedTeam1);
        rb1.setOnCheckedChangeListener(this);
        rb2 = (RadioButton) findViewById(R.id.selectedTeam2);
        rb2.setOnCheckedChangeListener(this);
        myRef = database.getReference("Users").child(encodedEmail);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                point = dataSnapshot.child("point").getValue(int.class);
                txtPoint.setText("Yout point: "+point);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });

        txtTeam1 = (TextView) findViewById(R.id.team_1);
        txtTeam1.setText(BID);
        txtTeam2 = (TextView) findViewById(R.id.team_2);
        txtOds1 = (TextView) findViewById(R.id.ods_1);
        txtOds2 = (TextView) findViewById(R.id.ods_2);
        txtStatus = (TextView) findViewById(R.id.status);
        txtBet1 = (TextView) findViewById(R.id.bet1);
        txtBet2 = (TextView) findViewById(R.id.bet2);
        myRef2 = database.getReference("Matches").child(BID);
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String team1 = dataSnapshot.child("team_1").getValue(String.class);
                String team2 = dataSnapshot.child("team_2").getValue(String.class);
                String status = dataSnapshot.child("status").getValue(String.class);
                txtTeam1.setText(team1);
                txtTeam2.setText(team2);
                txtStatus.setText(status);
                if(dataSnapshot.child("betting").exists())
                {
                    int selectedTeam = dataSnapshot.child("betting").child(encodedEmail).child("selected_team").getValue(int.class);
                    if(selectedTeam==1)
                    {
                        txtBet1.setText("P: "+dataSnapshot.child("betting").child(encodedEmail).child("bet_value").getValue(int.class));
                    }
                    else if(selectedTeam==2)
                    {
                        txtBet2.setText("P: "+dataSnapshot.child("betting").child(encodedEmail).child("bet_value").getValue(int.class));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        inputPoint = (EditText) findViewById(R.id.inputPoint);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int input = Integer.parseInt(inputPoint.getText().toString());

                if(rb2.isChecked()==false&&rb1.isChecked()==false)
                {
                    Toast.makeText(getApplicationContext(), "Select your choice!.", Toast.LENGTH_SHORT).show();
                }
                else if(point>=input)
                {
                    int selected_team;
                    if(rb1.isChecked())
                    {
                        selected_team = 1;
                    }
                    else
                    {
                        selected_team = 2;
                    }
                    Betting betting = new Betting(selected_team,input);
                    myRef.child("Matches").child(BID).child("betting").child(encodedEmail).setValue(betting, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(getApplicationContext(), "Data could not be saved.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    finish();
                }
                else
                {
                    Toast.makeText(BetDetailActivity.this,"Not enough point!",Toast.LENGTH_SHORT);
                }
            }
        });

        return true;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.selectedTeam1) {
                rb2.setChecked(false);
            }
            if (buttonView.getId() == R.id.selectedTeam2) {
                rb1.setChecked(false);
            }
        }
    }
}
