package com.github.guwenk.smuradio;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;

import static android.R.attr.password;


public class DownloadXML {
	String downloadedXml;
	void xmlDownload(){
		try {
			URLConnection connection = new URL("http://192.168.1.69:9001/?pass=yHZDVtGwCC&action=getplaylist").openConnection();
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

	public void write (String fileName){
		File file = new File(fileName);
		try {
			//проверяем, что если файл не существует то создаем его
			if(!file.exists()){
				file.createNewFile();
			}

			//PrintWriter обеспечит возможности записи в файл
			PrintWriter out = new PrintWriter(file.getAbsoluteFile());

			try {
				//Записываем текст у файл
				out.print(downloadedXml);
			} finally {
				//После чего мы должны закрыть файл
				//Иначе файл не запишется
				out.close();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	private void exists(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.exists()){
			throw new FileNotFoundException(file.getName());
		}
	}
	public String read(String fileName) throws FileNotFoundException {
		//Этот спец. объект для построения строки
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder();

		exists(fileName);

		try {
			//Объект для чтения файла в буфер
			BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
			try {
				//В цикле построчно считываем файл
				String s;
				while ((s = in.readLine()) != null) {
					sb.append(s);
					sb.append("\n");
				}
			} finally {
				//Также не забываем закрыть файл
				in.close();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}

		//Возвращаем полученный текст с файла
		return sb.toString();
	}
}
