package com.github.guwenk.smuradio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> names = new ArrayList<>();


    }
}
