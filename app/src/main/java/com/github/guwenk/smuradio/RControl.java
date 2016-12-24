package com.github.guwenk.smuradio;


import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class RControl {
    private String ip_port;
    private String password;
    private List<Tracks> trackList;


    RControl(String ip_port, String password){
        this.ip_port = ip_port;
        this.password = password;
    }

    public List<Tracks> getTrackList() {
        return trackList;
    }

    private void request(String command) {
        try {
            URLConnection connection = new URL("http://" + ip_port + "/?pass=" + password + "&" + command).openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            char[] buffer = new char[256];
            int rc;
            StringBuilder sb = new StringBuilder();
            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);
            reader.close();
            System.out.println(sb);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void next() throws IOException {
        request("cmd=next");
    }
    void prev() throws IOException {
        request("cmd=prev");
    }
    void vote(String file_path) throws IOException {
        request("action=songrequest&filename="+ file_path);
    }
    void xmlDownload(){
        TextClass textClass;
        try {
            URLConnection connection = new URL("http://" + ip_port + "/?pass=" + password + "&action=getplaylist").openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            char[] buffer = new char[256];
            int rc;
            StringBuilder sb = new StringBuilder();
            while ((rc = reader.read(buffer)) != -1)
                sb.append(buffer, 0, rc);
            reader.close();
            //System.out.println(sb.toString());
            sb.delete(0, 39); //Паганая XML, из-за одного невидимого символа крашилась!!!!!
            textClass = new TextClass();
            trackList = textClass.parsing(sb.toString());
        } catch (SAXException e) {
            System.out.println("Wrong IP:PORT or PASS");
        } catch (ConnectException e){
            System.out.println("Unable to connect to server");
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
    public void printTrackList() {
        for (Tracks tr: trackList) {
            System.out.println(tr.getNum()+ ". " + tr.getArtist()+ " - " + tr.getTitle());
        }
    }
}
