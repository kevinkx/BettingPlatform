package com.rpll.okeoke.bettingplatform.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Model.Match;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

public class EditMatchActivity extends AppCompatActivity {
    public static String BID;
    private EditText inputTeam1,inputTeam2,inputStatus,inputWinner;
    private Button btnSave;
    private TextView btnDelete;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match);
        inputTeam1 = (EditText) findViewById(R.id.inputTeam1);
        inputTeam2 = (EditText) findViewById(R.id.inputTeam2);
        inputStatus = (EditText) findViewById(R.id.inputStatus);
        inputWinner = (EditText) findViewById(R.id.inputWinner);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (TextView) findViewById(R.id.btnDelete);
        DatabaseReference myRef = database.getReference("Matches").child(BID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String team1 = dataSnapshot.child("team_1").getValue(String.class);
                String team2 = dataSnapshot.child("team_2").getValue(String.class);
                String status = dataSnapshot.child("status").getValue(String.class);
                int winner = 0;
                if(dataSnapshot.child("winner").getValue(int.class)!=null){
                    winner =  dataSnapshot.child("winner").getValue(int.class);
                }
                inputTeam1.setText(team1);
                inputTeam2.setText(team2);
                inputStatus.setText(status);
                inputWinner.setText(""+winner);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String team1 = inputTeam1.getText().toString().trim();
                String team2 = inputTeam2.getText().toString().trim();
                String status = inputStatus.getText().toString().trim();
                int winner = Integer.parseInt(inputWinner.getText().toString());

                if (TextUtils.isEmpty(team1)) {
                    Toast.makeText(getApplicationContext(), "Enter Team 1!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(team2)) {
                    Toast.makeText(getApplicationContext(), "Enter Team 2!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(status)) {
                    Toast.makeText(getApplicationContext(), "Enter Status!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(inputWinner.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter Winner!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(status.equalsIgnoreCase("Finished")&&(winner!=1||winner!=2))
                {
                    Toast.makeText(getApplicationContext(), "Who's the winner?", Toast.LENGTH_SHORT).show();
                    return;
                }
                Match match = new Match();
                match.setTeam_1(team1);
                match.setTeam_2(team2);
                match.setStatus(status);
                match.setWin(winner);
                DatabaseReference myRef = database.getReference("Matches").child(BID);
                myRef.setValue(match, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getApplicationContext(), "Data could not be saved.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference("Matches").child(BID);
                myRef.removeValue();
                finish();
            }
        });
    }
}
