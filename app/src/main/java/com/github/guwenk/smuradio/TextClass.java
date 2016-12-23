package com.github.guwenk.smuradio;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class TextClass {

    public List<Tracks> parsing(String xmlfile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlfile)));

        NodeList trackNodeList = document.getElementsByTagName("TRACK");

        List<Tracks> trackList = new ArrayList<>();

        for (int i = 0; i < trackNodeList.getLength(); i++){
            Element trackElement = (Element) trackNodeList.item(i);
            Tracks tracks = new Tracks();
            tracks.setNum(i+1);
            tracks.setArtist(trackElement.getAttribute("ARTIST"));
            tracks.setTitle(trackElement.getAttribute("TITLE"));
            tracks.setDuration(trackElement.getAttribute("DURATION"));
            tracks.setFilename(trackElement.getAttribute("FILENAME"));
            trackList.add(tracks);
        }
        //for (Tracks tr: trackList) {
            //System.out.println(tr.getNum()+ ". " + tr.getArtist()+ " - " + tr.getTitle());
            //System.out.println("\tDuration: " + tr.getDuration());
            //System.out.println("\tFilename: " + tr.getFilename());
            //System.out.println();
        //}
        return trackList;
    }
}
/*
public class TextClass {
    List<Tracks> trackList = new ArrayList<>();
    void parsing(String xmlfile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlfile)));

        NodeList trackNodeList = document.getElementsByTagName("TRACK");

        for (int i = 0; i < trackNodeList.getLength(); i++){
            Element trackElement = (Element) trackNodeList.item(i);
            Tracks tracks = new Tracks();
            tracks.setNum(i+1);
            tracks.setArtist(trackElement.getAttribute("ARTIST"));
            tracks.setTitle(trackElement.getAttribute("TITLE"));
            tracks.setDuration(trackElement.getAttribute("DURATION"));
            tracks.setFilename(trackElement.getAttribute("FILENAME"));
            trackList.add(tracks);
        }
        for (Tracks tr: trackList) {
            System.out.println(tr.getNum()+ ". " + tr.getArtist()+ " - " + tr.getTitle());
            //System.out.println("\tDuration: " + tr.getDuration());
            //System.out.println("\tFilename: " + tr.getFilename());
            //System.out.println();
        }
    }
    public List<Tracks> getTrackList(){
        List<Tracks> trackList = this.trackList;
        return trackList;
    }
}

 */
