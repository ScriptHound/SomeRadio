package com.github.guwenk.smuradio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ListView listView = (ListView) findViewById(R.id.listView);

        String ip_port = "192.168.1.69:9001";
        String pass = "yHZDVtGwCC";
        RControl control = new RControl(ip_port, pass);
        control.takeXml();
        System.out.println("Done");
        //control.printTrackList();

        List<Tracks> trackList = new ArrayList<>();
        trackList = control.getTrackList();

        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < trackList.size(); i++){
            names.add(i + ". " + trackList.get(i).getArtist() + " - " + trackList.get(i).getTitle());
        }
        for (int i = 0; i < names.size(); i++) System.out.println(names.get(i));
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);

    }
}
