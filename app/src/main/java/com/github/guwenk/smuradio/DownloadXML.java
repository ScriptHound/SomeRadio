package com.github.guwenk.smuradio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class DownloadXML {
	public static void write (String fileName, String text){
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
				out.print(text);
			} finally {
				//После чего мы должны закрыть файл
				//Иначе файл не запишется
				out.close();
			}
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	private static void exists(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.exists()){
			throw new FileNotFoundException(file.getName());
		}
	}
	public static String read(String fileName) throws FileNotFoundException {
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
