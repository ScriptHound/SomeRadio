package com.github.guwenk.smuradio;


import java.io.IOException;
import java.net.URL;

public class UrlRequest extends Thread{
    String command = "";
    UrlRequest(String command){
        this.command = command;
    }

    @Override
    public void run() {
        try {
            new URL("http://192.168.1.69:9001/?pass=yHZDVtGwCC&cmd="+command).openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
