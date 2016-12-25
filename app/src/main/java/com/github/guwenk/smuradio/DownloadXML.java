package com.github.guwenk.smuradio;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;



public class DownloadXML extends Thread{
	protected String downloadedXml;

	public String getDownloadedXml() {
		return downloadedXml;
	}

	@Override
	public void run() {
		try {
			URLConnection connection = new URL("http://192.168.1.69:9001/?pass=yHZDVtGwCC&action=library&filename=Base").openConnection();
			InputStream is = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);
			char[] buffer = new char[256];
			int rc;
			StringBuilder sb = new StringBuilder();
			while ((rc = reader.read(buffer)) != -1)
				sb.append(buffer, 0, rc);
			reader.close();
			sb.delete(0, 39); //Паганая XML, из-за одного невидимого символа крашилась!!!!!
			downloadedXml = sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
