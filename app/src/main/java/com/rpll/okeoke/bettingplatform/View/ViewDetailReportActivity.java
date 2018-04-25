package com.rpll.okeoke.bettingplatform.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rpll.okeoke.bettingplatform.Model.Report;
import com.rpll.okeoke.bettingplatform.R;

import org.w3c.dom.Text;

public class ViewDetailReportActivity extends AppCompatActivity {

    EditText category, content;
    TextView sender, date;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
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
        myRef = database.getReference("Report").child(position);
        category.setText(myRef.child("category").toString());
        content.setText(myRef.child("content").toString());
        sender.setText(myRef.child("email").toString());
        date.setText(myRef.child("date").toString());
        category.setEnabled(false);
        content.setEnabled(false);
    }
}
