package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.rpll.okeoke.bettingplatform.Model.Report;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText category, content;
    String encodedEmail;
    long totalreport;
    DatabaseReference myref = database.getReference();
    DatabaseReference myref2 = database.getReference("Report");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        auth = FirebaseAuth.getInstance();
        String email = auth.getCurrentUser().getEmail();
        encodedEmail = User.encodeUserEmail(email);

        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalreport = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        category = findViewById(R.id.editCategory);
        content = findViewById(R.id.editreportContent);
    }

    public void submitReport(View view){
        String scategory = category.getText().toString();
        String scontent = content.getText().toString();
        DateFormat df = new SimpleDateFormat("EEEE, MM/dd/yyyy HH:mm");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        Report report = new Report(encodedEmail, scontent, scategory, reportDate);

        myref.child("Report").child(Long.toString(totalreport+1)).setValue(report, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), "Failed to send report.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Report Submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
