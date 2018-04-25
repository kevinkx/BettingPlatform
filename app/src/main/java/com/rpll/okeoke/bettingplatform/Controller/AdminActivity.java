package com.rpll.okeoke.bettingplatform.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.rpll.okeoke.bettingplatform.R;

public class AdminActivity extends AppCompatActivity {
    private Button btnAdd,btnMatch,btnReport, btnLogout;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        auth = FirebaseAuth.getInstance();
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnMatch = (Button) findViewById(R.id.btnMatch);
        btnReport = (Button) findViewById(R.id.btnReport);
        btnLogout = (Button) findViewById(R.id.btnLogout);
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
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(AdminActivity.this, FirstActivity.class));
                finish();
            }
        });
    }
}
