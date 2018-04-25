package com.rpll.okeoke.bettingplatform.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rpll.okeoke.bettingplatform.R;

public class HelpActivity extends AppCompatActivity {

    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        String[] question = {"How do i back up my history?", "What is Top Up?", "How to withdraw?"};
        listview = findViewById(R.id.listhelp);
        listview.setAdapter(new ArrayAdapter<String>(this, R.layout.list_black, R.id.list_content, question));

    }
}
