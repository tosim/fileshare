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
		//OfficeԴ�ļ�(word,excel,ppt)
		String inputFile = "d:\\resource\\So.doc";
		//Ŀ���ļ���׼��������
		String destFile = "d:\\resource\\17.pdf";
		//ImgĿ���ļ���ͼƬ�洢�ط���
		String ImgdestFile = "d:\\s\\";

		OfficeToPDF toPDF = new OfficeToPDF();
		//��ȡ������
		int Return = toPDF.office2PDF(inputFile, destFile);
		if (Return == -1) {
			System.out.println("�Ҳ���Դ�ļ��� ");
		}else if (Return == 1) {
			System.out.println("ת��ʧ��");
		}else if(Return == 0){
			System.out.println("ת���ɹ�");
			System.out.println("��ʼ��pdfתͼƬ...");
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
