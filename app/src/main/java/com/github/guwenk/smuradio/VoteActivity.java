package com.github.guwenk.smuradio;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
    String filename;

    String urlXML = "http://192.168.1.69:9001/?pass=yHZDVtGwCC&action=getplaylist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        new DownloadXML().execute(urlXML);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, names);
        listView.setAdapter(adapter);

        Button btnVote = (Button)findViewById(R.id.buttonVote);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    filename = trackList.get(listView.getCheckedItemPosition()).getFilename();
                } catch (ArrayIndexOutOfBoundsException e){
                    filename = "";
                }

                if (filename != "" && filename != null) {
                    new VoteRequest().execute();
                    Toast.makeText(getApplicationContext(), "Выполнено: " + trackList.get(listView.getCheckedItemPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Ничего не выбрано", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected class VoteRequest extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            try {
                Log.d("FilePath", filename);
                new URL("http://192.168.1.69:9001/?pass=yHZDVtGwCC&action=songrequest&filename=" + filename).openConnection().getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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
