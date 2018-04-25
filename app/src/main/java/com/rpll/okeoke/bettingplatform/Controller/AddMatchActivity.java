package com.rpll.okeoke.bettingplatform.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Model.Match;
import com.rpll.okeoke.bettingplatform.R;

public class AddMatchActivity extends AppCompatActivity {
    private EditText team1,team2;
    private Button btnAdd;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private DatabaseReference myRefInfo = database.getReference("Matches");
    long idMatch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        team1 = (EditText) findViewById(R.id.inputTeam1);
        team2 = (EditText) findViewById(R.id.inputTeam2);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        myRefInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               idMatch = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(team1.getText().toString().isEmpty()||team2.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMatchActivity.this,"Please fill all field!",Toast.LENGTH_SHORT);
                }
                else
                {
                    Match match = new Match();
                    match.setId_match(Long.toString(idMatch));
                    match.setTeam_1(team1.getText().toString());
                    match.setTeam_2(team2.getText().toString());
                    match.setStatus("Waiting");
                    match.setWin(0);
                    myRef.child("Matches").child(Long.toString(idMatch)).setValue(match, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(getApplicationContext(), "Data could not be saved.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    });
                }
            }
        });
    }
}
