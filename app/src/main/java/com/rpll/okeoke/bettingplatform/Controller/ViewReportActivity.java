package com.rpll.okeoke.bettingplatform.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Adapter.ReportAdapter;
import com.rpll.okeoke.bettingplatform.Model.Report;
import com.rpll.okeoke.bettingplatform.R;

import java.util.ArrayList;

public class ViewReportActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Report> reports = new ArrayList<Report>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        mRecyclerView = findViewById(R.id.recyclerReport);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myRef = database.getReference("Report");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalreport = dataSnapshot.getChildrenCount();
                Log.d("totalreport",Long.toString(totalreport));
                for(int i=1;i<=totalreport;i++){
                    Report report = new Report();
                    report.setCategory(dataSnapshot.child(Integer.toString(i)).child("content").getValue(String.class));
                    report.setDate(dataSnapshot.child(Integer.toString(i)).child("date").getValue(String.class));
                    reports.add(report);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter = new ReportAdapter(this, reports);
        mRecyclerView.setAdapter(mAdapter);

    }
}
