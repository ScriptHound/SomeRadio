package com.github.guwenk.smuradio;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class RControl {
    String ip_port;
    String adress;
    String password;
    TextClass textClass;
    List<Tracks> trackList;
    RControl(String ip_port, String password){
        this.ip_port = ip_port;
        this.password = password;
    }
    private void request(String command) throws IOException{
        URLConnection connection = new URL("http://" + ip_port + "/?pass="+ password + "&" + command).openConnection();
        InputStream is = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();
        while ((rc = reader.read(buffer)) != -1)
            sb.append(buffer, 0, rc);
        reader.close();
        System.out.println(sb);
    }
    void next() throws IOException {
        request("cmd=next");
    }
    void prev() throws IOException {
        request("cmd=prev");
    }
    void vote(String adress) throws IOException {
        this.adress = adress;
        request("action=songrequest&filename="+ this.adress);
    }
    void takeXml() throws IOException, ParserConfigurationException, SAXException {
        URLConnection connection = new URL("http://192.168.1.69:9001/?pass="+ password + "&action=getplaylist").openConnection();
        InputStream is = connection.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();
        while ((rc = reader.read(buffer)) != -1)
            sb.append(buffer, 0, rc);
        reader.close();
        //System.out.println(sb.toString());
        sb.delete(0,39); //Паганая XML, из-за одного невидимого символа крашилась!!!!!
        textClass = new TextClass();
        trackList = textClass.parsing(sb.toString());
    }
    public void printTrackList() {
        for (Tracks tr: trackList) {
            System.out.println(tr.getNum()+ ". " + tr.getArtist()+ " - " + tr.getTitle());
        }
    }
}
