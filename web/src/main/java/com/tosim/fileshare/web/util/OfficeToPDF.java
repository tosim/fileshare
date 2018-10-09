package com.tosim.fileshare.web.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class OfficeToPDF {
	/** 
	 * <pre> 
	 * ����ʾ��: 
	 * String sourcePath = "F:\\office\\source.doc"; 
	 * String destFile = "F:\\pdf\\dest.pdf"; 
	 * Converter.office2PDF(sourcePath, destFile); 
	 * </pre> 
	 *  
	 * @param sourceFile 
	 *            Դ�ļ�, ����·��. ������Office2003-2007ȫ����ʽ���ĵ�, Office2010��û����. ����.doc, 
	 *            .docx, .xls, .xlsx, .ppt, .pptx��. ʾ��: F:\\office\\source.doc 
	 * @param destFile 
	 *            Ŀ���ļ�. ����·��. ʾ��: F:\\pdf\\dest.pdf 
	 * @return �����ɹ�������ʾ��Ϣ. ������� -1, ��ʾ�Ҳ���Դ�ļ�, ��url.properties���ô���; ������� 0, 
	 *         ���ʾ�����ɹ�; ����1, ���ʾת��ʧ�� 
	 */  
	public static int office2PDF(String sourceFile, String destFile) {  
		try {  
			File inputFile = new File(sourceFile);  
			if (!inputFile.exists()) {  
				return -1;// �Ҳ���Դ�ļ�, �򷵻�-1  
			}  

			// ���Ŀ��·��������, ���½���·��  
			File outputFile = new File(destFile);  
			if (!outputFile.getParentFile().exists()) {  
				outputFile.getParentFile().mkdirs();  
			}  

			//������OpenOffice�İ�װĿ¼, ���ҵ���Ŀ��,Ϊ�˱�����չ�ӿ�,û��ֱ��д���������,���������Ǿ���û�����  
			String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";

			// ������ļ��ж�ȡ��URL��ַ���һ���ַ����� '\'��������'\'  
			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {  
				OpenOffice_HOME += "\\";  
			}  

			// ����OpenOffice�ķ���  
			String command = OpenOffice_HOME  
			+ "program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"-nofirststartwizard";
			Process pro = Runtime.getRuntime().exec(command);  
			// connect to an OpenOffice.org instance running on port 8100  
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(  
					"127.0.0.1", 8100);
			connection.connect();  

			//ת��  
			DocumentConverter converter = new OpenOfficeDocumentConverter(  
					connection);  
			converter.convert(inputFile, outputFile);  

			//�ر�����  
			connection.disconnect();  
			// �ر�OpenOffice����Ľ���  
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

	public static void main(String[] args) {
		String sourceFile = "C:\\Users\\SqList\\Documents\\Tencent Files\\574146616\\FileRecv\\�ĵ�.docx";
		String destFile = "C:\\Users\\SqList\\Documents\\Tencent Files\\574146616\\FileRecv\\�ĵ�.pdf";
		office2PDF(sourceFile, destFile);
	}
}