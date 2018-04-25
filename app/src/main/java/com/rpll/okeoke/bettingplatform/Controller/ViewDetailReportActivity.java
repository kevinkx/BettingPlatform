package com.rpll.okeoke.bettingplatform.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.R;

public class ViewDetailReportActivity extends AppCompatActivity {

    EditText category, content;
    TextView sender, date;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_report);

        category = findViewById(R.id.reportCategory);
        content = findViewById(R.id.reportContent);
        sender = findViewById(R.id.reportSender);
        date = findViewById(R.id.reportDate);

        position = getIntent().getStringExtra("position");
        Log.d("posisi", position);
        myRef.child("Report").child(position).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                category.setText(dataSnapshot.child("category").getValue(String.class));
                content.setText(dataSnapshot.child("content").getValue(String.class));
                sender.setText(dataSnapshot.child("email").getValue(String.class));
                date.setText(dataSnapshot.child("date").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        category.setEnabled(false);
        content.setEnabled(false);
    }
}
