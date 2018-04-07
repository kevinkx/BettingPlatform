package com.rpll.okeoke.bettingplatform.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.rpll.okeoke.bettingplatform.R;

public class FirstActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button btnLogin;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(FirstActivity.this, ChatActivity.class));
            finish();
        }
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(FirstActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
