package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.graphics.Color;
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
import com.rpll.okeoke.bettingplatform.Model.Match;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class BetDetailActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private String BID = getIntent().getStringExtra("BID");;
    private TextView txtPoint, txtTeam1, txtTeam2,txtOds1,txtOds2,txtBet1,txtBet2,txtStatus, txtInfo;
    private EditText inputPoint;
    private Button btnSubmit;
    private RadioButton rb1;
    private RadioButton rb2;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private DatabaseReference myRef3 = database.getReference();
    private DatabaseReference myRef4 = database.getReference();
    private DatabaseReference myRef5 = database.getReference();
    private DatabaseReference myRef6 = database.getReference();
    private User user;
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
                String fullname = dataSnapshot.child("fullname").getValue(String.class);
                String username = dataSnapshot.child("username").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);
                point = dataSnapshot.child("point").getValue(int.class);
                txtPoint.setText("Yout point: "+point);
                user = new User(username, fullname, password, point);
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
        txtInfo = (TextView) findViewById(R.id.txtInfo);
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
                int winner = dataSnapshot.child("winner").getValue(int.class);

                txtTeam1.setText(team1);
                txtTeam2.setText(team2);
                txtStatus.setText(status);
                if(status.equalsIgnoreCase("LIVE"))
                {
                    txtStatus.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                else if(status.equalsIgnoreCase("FINISHED"))
                {
                    txtStatus.setBackgroundColor(Color.parseColor("#0B6623"));
                }
                if(status.equalsIgnoreCase("Finished")||status.equalsIgnoreCase("Live"))
                {
                    btnSubmit.setEnabled(false);
                    inputPoint.setEnabled(false);
                    rb1.setEnabled(false);
                    rb2.setEnabled(false);
                }
                if(dataSnapshot.child("betting").exists())
                {
                    btnSubmit.setEnabled(false);
                    inputPoint.setEnabled(false);
                    txtInfo.setText("You already choose, please wait for the match.");
                    int selectedTeam = dataSnapshot.child("betting").child(encodedEmail).child("selected_team").getValue(int.class);
                    int poinBet=dataSnapshot.child("betting").child(encodedEmail).child("bet_value").getValue(int.class);
                    double ods = 0.0;
                    if(selectedTeam==1)
                    {
                        txtBet1.setText("P: "+poinBet);
                        ods = Double.parseDouble(txtOds1.getText().toString());
                        rb1.setChecked(true);
                    }
                    else if(selectedTeam==2)
                    {
                        txtBet2.setText("P: "+poinBet);
                        ods = Double.parseDouble(txtOds2.getText().toString());
                        rb2.setChecked(true);
                    }
                    Boolean rewardCollected = dataSnapshot.child("betting").child(encodedEmail).child("rewardCollected").getValue(Boolean.class);
                    int totalReward = (int)(poinBet * ods);
                    if(status.equalsIgnoreCase("Finished"))
                    {
                        if(rewardCollected==false&&winner==selectedTeam)
                        {
                            user.setPoint(user.getPoint() + (poinBet + totalReward));
                            myRef4.child("Users").child(encodedEmail).setValue(user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Toast.makeText(getApplicationContext(), "Something error.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        myRef5.child("Matches").child(BID).child("betting").child(encodedEmail).child("rewardCollected").setValue(true);
                                        Toast.makeText(getApplicationContext(), "You got your reward!.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });

                        }
                        else if(rewardCollected==false&&winner!=selectedTeam)
                        {
                            myRef5.child("Matches").child(BID).child("betting").child(encodedEmail).child("rewardCollected").setValue(true);
                        }
                        else if(winner==selectedTeam)
                        {
                            txtInfo.setText("(WIN) Your reward: "+totalReward);
                        }
                        else
                        {
                            txtInfo.setText("(LOSE) Your reward: -"+poinBet);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        myRef6 = database.getReference("Matches").child(BID).child("betting");
        myRef6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long totalBet = dataSnapshot.getChildrenCount();
                double team1=0;
                double team2=0;
                double ods1=0,ods2=0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.child("selected_team").getValue(int.class)==1)
                        {
                            team1+=child.child("bet_value").getValue(int.class);
                        }
                        else
                        {
                            team2+=child.child("bet_value").getValue(int.class);
                        }

                }
                if(team1>team2)
                {
                    ods1 = (team1/(team1+team2))*0.95;
                    ods2 = 0.95/ods1;
                }
                else if(team2>team1)
                {
                    ods2 = (team2/(team1+team2))*0.95;
                    ods1 = 0.95/ods2;
                }
                else{
                    ods1 = 0.95;
                    ods2 = 0.95;
                }
                NumberFormat formatter = new DecimalFormat("#0.00");
                txtOds1.setText(""+formatter.format(ods1));
                txtOds2.setText(""+formatter.format(ods2));

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
                final int input = Integer.parseInt(inputPoint.getText().toString());

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
                    Betting betting = new Betting(input,selected_team, false);
                    myRef3.child("Matches").child(BID).child("betting").child(encodedEmail).setValue(betting, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(getApplicationContext(), "Data could not be saved.", Toast.LENGTH_SHORT).show();
                            } else {
                                user.setPoint(user.getPoint() - input);
                                myRef4.child("Users").child(encodedEmail).setValue(user, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if (databaseError != null) {
                                            Toast.makeText(getApplicationContext(), "Betting failed.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Betting Successfull.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                                Toast.makeText(getApplicationContext(), "betting...", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not enough point!",Toast.LENGTH_SHORT).show();
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
