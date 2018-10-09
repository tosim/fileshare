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
	 * 方法示例: 
	 * String sourcePath = "F:\\office\\source.doc"; 
	 * String destFile = "F:\\pdf\\dest.pdf"; 
	 * Converter.office2PDF(sourcePath, destFile); 
	 * </pre> 
	 *  
	 * @param sourceFile 
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc, 
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc 
	 * @param destFile 
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf 
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0, 
	 *         则表示操作成功; 返回1, 则表示转换失败 
	 */  
	public static int office2PDF(String sourceFile, String destFile) {  
		try {  
			File inputFile = new File(sourceFile);  
			if (!inputFile.exists()) {  
				return -1;// 找不到源文件, 则返回-1  
			}  

			// 如果目标路径不存在, 则新建该路径  
			File outputFile = new File(destFile);  
			if (!outputFile.getParentFile().exists()) {  
				outputFile.getParentFile().mkdirs();  
			}  

			//这里是OpenOffice的安装目录, 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的  
			String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";

			// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'  
			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {  
				OpenOffice_HOME += "\\";  
			}  

			// 启动OpenOffice的服务  
			String command = OpenOffice_HOME  
			+ "program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"-nofirststartwizard";
			Process pro = Runtime.getRuntime().exec(command);  
			// connect to an OpenOffice.org instance running on port 8100  
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(  
					"127.0.0.1", 8100);
			connection.connect();  

			//转换  
			DocumentConverter converter = new OpenOfficeDocumentConverter(  
					connection);  
			converter.convert(inputFile, outputFile);  

			//关闭链接  
			connection.disconnect();  
			// 关闭OpenOffice服务的进程  
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
//		String sourceFile = "D:\\课程\\大三\\大数据\\ppt\\大数据分析_1.pptx";
//		String destFile = "D:\\课程\\大三\\大数据\\ppt\\大数据分析_1.pdf";
//		office2PDF(sourceFile, destFile);
	}
}
