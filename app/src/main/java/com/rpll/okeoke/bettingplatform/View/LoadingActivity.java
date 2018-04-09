package com.rpll.okeoke.bettingplatform.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rpll.okeoke.bettingplatform.R;

public class LoadingActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent start;
                start = new Intent(LoadingActivity.this, FirstActivity.class);
                startActivity(start);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
