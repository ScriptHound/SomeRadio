package com.github.guwenk.smuradio;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;


/**
 * Created by denis on 23.12.2016.
 */

public class JavaRunControl {
    public static void main(String args[]) throws IOException, ParserConfigurationException, SAXException {
        boolean xmldl = false;
        RControl control = new RControl("127.0.0.1:9000", "*pass*");
        Scanner in = new Scanner(System.in);
        String str = "%22D:\\Music\\Pet%20Shop%20Boys%20Discography\\Studio\\1987%20Actually\\B04%20Heart.flac%22";
        System.out.println("1. Next\n" +
                "2. Prev\n" +
                "3. Request\n" +
                "4. Download XML\n" +
                "5. Print TrackList\n" +
                "0. Exit");
        while (true) {
            int choose = in.nextInt();
            switch (choose) {
                case 1:
                    control.next();
                    break;
                case 2:
                    control.prev();
                    break;
                case 3:
                    control.vote(str);
                    break;
                case 4:
                    control.takeXml();
                    xmldl = true;
                    System.out.println("Done");
                    break;
                case 5:
                    if (xmldl) control.printTrackList();
                    else System.out.println("You should download XML");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Bad Choice");
                    break;
            }
        }
    }
}
