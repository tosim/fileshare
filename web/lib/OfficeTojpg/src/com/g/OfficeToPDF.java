package com.g;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.sun.star.bridge.oleautomation.Date;

public class OfficeToPDF {

	public static int office2PDF(String sourceFile, String destFile) {  
		try {  
			File inputFile = new File(sourceFile);  
			if (!inputFile.exists()) {  
				return -1;
			}  

			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {  
				outputFile.getParentFile().mkdirs();  
			}  

			String OpenOffice_HOME = "C:\\Program Files\\OpenOffice.org 3\\App\\openoffice";

			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
				OpenOffice_HOME += "\\";  
			}  

			String command = OpenOffice_HOME
			+ "program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"-nofirststartwizard";  
			Process pro = Runtime.getRuntime().exec(command);  
			// connect to an OpenOffice.org instance running on port 8100  
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(  
					"127.0.0.1", 8100);  
			connection.connect();  

			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);  
			converter.convert(inputFile, outputFile);  
			connection.disconnect();
			pro.destroy();
			System.out.println("1234");
			return 0;  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
			return -1;  
		} catch (ConnectException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return 1;
	}  
}
