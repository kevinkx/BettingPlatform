package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rpll.okeoke.bettingplatform.R;

public class AdminActivity extends AppCompatActivity {
    private Button btnAdd,btnMatch,btnReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnMatch = (Button) findViewById(R.id.btnMatch);
        btnReport = (Button) findViewById(R.id.btnReport);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(AdminActivity.this, AddMatchActivity.class);
                startActivity(myIntent);
            }
        });
        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminActivity.this, ViewMatchActivity.class);
                startActivity(myIntent);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminActivity.this, ViewReportActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
