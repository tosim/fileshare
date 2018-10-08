package com.tosim.fileshare.web.util.Tojpg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

public class Pdf2Jpg {
	public static final String FILETYPE_PNG = "png";

	public static final String SUFF_IMAGE = "." + FILETYPE_PNG;

	public int tranfer(String sourceFile, String destFile)throws PDFException, PDFSecurityException, IOException {

		// ICEpdf document class
		
		String FileName = sourceFile.substring(sourceFile.lastIndexOf("\\")+1, sourceFile.lastIndexOf("."));
		
		Document document = null;
		BufferedImage img = null;
		float rotation = 0f;
		float zoom = 1.5f;
		
		//�ж�Ŀ¼�Ƿ���ڣ���������ڵĻ��򴴽�
		File file = new File(destFile);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		File inputFile = new File(sourceFile);  
		if (!inputFile.exists()) {  
			System.out.println("�Ҳ���Դ�ļ�");
			return -1;// �Ҳ���Դ�ļ�, �򷵻�-1  
		}  
		document = new Document();

		document.setFile(sourceFile);

		// maxPages = document.getPageTree().getNumberOfPages();

		int len = document.getNumberOfPages() < 3 ? document.getNumberOfPages() : 3;
		//����pdf�ļ�ͼƬ��ת��
		for (int i = 0; i < len; i++) {
			img = (BufferedImage) document.getPageImage(i,GraphicsRenderingHints.SCREEN,
					Page.BOUNDARY_CROPBOX,rotation,zoom);
			//����ͼƬ�ĺ�׺��
			Iterator iter = ImageIO.getImageWritersBySuffix(FILETYPE_PNG);
			
			ImageWriter writer = (ImageWriter) iter.next();
			
			File outFile = new File(destFile+FileName+"_"+(i+1)+".png");
			
			FileOutputStream out = new FileOutputStream(outFile);
			
			ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
			
			writer.setOutput(outImage);
			
			writer.write(new IIOImage(img, null, null));
		}
		img.flush();
		document.dispose();
		System.out.println("ת���ɹ������� ");
		return 0;
	}
	public static void main(String[] args) {
		try {
			String sourceFile ="C:\\Users\\SqList\\Documents\\Tencent Files\\574146616\\FileRecv\\�ĵ�.pdf";
			String destFile="d:\\tmp\\";
			Pdf2Jpg pdf = new Pdf2Jpg();
			pdf.tranfer(sourceFile,destFile);
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
}
