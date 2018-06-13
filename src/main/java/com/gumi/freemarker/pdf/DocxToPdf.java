package com.gumi.freemarker.pdf;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class DocxToPdf {

    public static void convertPdf(String docxFilePath, String pdfFilePath) throws Exception {
        File docxFile = new File(docxFilePath);
        File pdfFile = new File(pdfFilePath);


//转换pdf文件
        if (docxFile.exists()) {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(new File(docxFilePath)));
            //    document.setParagraph(new Pa );
            File outFile = new File(pdfFilePath);
            outFile.getParentFile().mkdirs();
            OutputStream out = new FileOutputStream(outFile);
            //    IFontProvider fontProvider = new AbstractFontRegistry();
            PdfOptions options = PdfOptions.create();  //gb2312
//            PdfOptions options = PdfOptions.getDefault();
            PdfConverter.getInstance().convert(doc, out, options);


        } else {
        }
    }

    public static void main(String args[]) throws Exception {
        DocxToPdf.convertPdf("C:\\freemarker\\1.docx", "C:\\freemarker\\1.pdf");
    }

}
