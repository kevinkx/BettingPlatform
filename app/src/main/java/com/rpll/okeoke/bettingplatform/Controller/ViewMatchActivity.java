package com.rpll.okeoke.bettingplatform.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Adapter.MatchAdapter;
import com.rpll.okeoke.bettingplatform.Model.Match;
import com.rpll.okeoke.bettingplatform.R;

import java.util.ArrayList;

public class ViewMatchActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Match> matches = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);
        mRecyclerView = (RecyclerView) findViewById(R.id.viewBet);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myRef = database.getReference("Matches");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // specify an adapter (see also next example)
                long totalBet = dataSnapshot.getChildrenCount();
                matches = new ArrayList<>();
                Match match;
                for (int i = 0; i < totalBet; i++) {
                    match = new Match();
                    match.setTeam_1(dataSnapshot.child(Integer.toString(i)).child("team_1").getValue(String.class));
                    match.setTeam_2(dataSnapshot.child(Integer.toString(i)).child("team_2").getValue(String.class));
                    match.setId_match(dataSnapshot.child(Integer.toString(i)).child("id_match").getValue(String.class));
                    match.setStatus(dataSnapshot.child(Integer.toString(i)).child("status").getValue(String.class));
                    matches.add(match);
                }
                mAdapter = new MatchAdapter(matches, ViewMatchActivity.this);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
    }
}
