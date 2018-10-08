package com.g;

import java.io.IOException;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;

import com.g.Tojpg.Pdf2Jpg;
import com.sun.star.bridge.oleautomation.Date;

public class Entry {

	/**
	 * @param args
	 */
	public static void main(String[]arg0){
		//Office源文件(word,excel,ppt)
		String inputFile = "d:\\resource\\So.doc";
		//目标文件（准备存进哪里）
		String destFile = "d:\\resource\\17.pdf";
		//Img目标文件（图片存储地方）
		String ImgdestFile = "d:\\s\\";

		OfficeToPDF toPDF = new OfficeToPDF();
		//获取返回码
		int Return = toPDF.office2PDF(inputFile, destFile);
		if (Return == -1) {
			System.out.println("找不着源文件呢 ");
		}else if (Return == 1) {
			System.out.println("转换失败");
		}else if(Return == 0){
			System.out.println("转换成功");
			System.out.println("开始把pdf转图片...");
			try {
				Pdf2Jpg pdf = new Pdf2Jpg();
				pdf.tranfer(destFile,ImgdestFile);
			} catch (PDFException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PDFSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("4567");
	}
}
