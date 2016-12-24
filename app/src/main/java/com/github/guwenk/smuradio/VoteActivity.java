package com.github.guwenk.smuradio;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;



public class VoteActivity extends AppCompatActivity {

    NodeList trackNodeList;
    ProgressDialog pDialog;
    List<Tracks> trackList = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    String urlXML = "http://192.168.1.69:9001/?pass=yHZDVtGwCC&action=getplaylist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ListView listView = (ListView) findViewById(R.id.listView);


        new DownloadXML().execute(urlXML);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
    }






    protected class DownloadXML extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VoteActivity.this);
            pDialog.setTitle("Загрузка плейлиста");
            pDialog.setMessage("Загрузка....");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... Url) {
            try {
                //URL url = new URL(Url[0]);
                InputStream stream = getAssets().open("base.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //Document doc = documentBuilder.parse(new InputSource(url.openStream()));
                Document doc = documentBuilder.parse(new InputSource(stream));
                trackNodeList = doc.getElementsByTagName("TRACK");
            } catch (SAXException e) {
                System.out.println("Wrong IP:PORT or PASS");
            } catch (ParserConfigurationException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (int i = 0; i < trackNodeList.getLength(); i++){
                Element trackElement = (Element) trackNodeList.item(i);
                Tracks tracks = new Tracks();
                tracks.setNum(i+1);
                tracks.setArtist(trackElement.getAttribute("ARTIST"));
                tracks.setTitle(trackElement.getAttribute("TITLE"));
                tracks.setDuration(trackElement.getAttribute("DURATION"));
                tracks.setFilename(trackElement.getAttribute("FILENAME"));
                names.add((i+1)+ ". " + trackElement.getAttribute("ARTIST") + " - " + trackElement.getAttribute("TITLE"));
                trackList.add(tracks);
            }
            pDialog.dismiss();
        }
    }
}
