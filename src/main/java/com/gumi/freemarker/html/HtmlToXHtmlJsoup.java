package com.gumi.freemarker.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import java.io.File;
import java.io.FileInputStream;

public class HtmlToXHtmlJsoup {

    public static String html2xhtml(String html) {

        Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);

        return doc.html();
    }

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\freemarker\\1.html");
        FileInputStream input = new FileInputStream(file);
        int size = input.available();
        byte[] buff = new byte[size];
        input.read(buff);
        input.close();
        String html = new String(buff, "utf-8");
        System.out.println("============html===================");
        System.out.println(html);
        String xhtml = HtmlToXHtmlJsoup.html2xhtml(html);
        System.out.println("============xhtml===================");
        System.out.println(xhtml);
    }

}
