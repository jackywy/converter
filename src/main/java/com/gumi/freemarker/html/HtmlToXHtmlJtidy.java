package com.gumi.freemarker.html;

import org.w3c.tidy.Tidy;

import java.io.*;

public class HtmlToXHtmlJtidy {

    public static String html2xhtml(String html) throws FileNotFoundException {
        ByteArrayInputStream stream = new ByteArrayInputStream(html.getBytes());

//        ByteArrayOutputStream tidyOutStream = new ByteArrayOutputStream();
        OutputStream tidyOutStream = new FileOutputStream(new File("C:\\freemarker\\1.xhtml"));
        // 实例化Tidy对象
        Tidy tidy = new Tidy();
        // 设置输入
        tidy.setInputEncoding("utf-8");
        // 如果是true 不输出注释，警告和错误信息
        tidy.setQuiet(true);
        // 设置输出
        tidy.setOutputEncoding("utf-8");
        // 不显示警告信息
        tidy.setShowWarnings(false);
        // 缩进适当的标签内容。
        tidy.setIndentContent(true);
        // 内容缩进
        tidy.setSmartIndent(true);
        tidy.setIndentAttributes(false);
//        // 只输出body内部的内容
//        tidy.setPrintBodyOnly(true);
        // 多长换行
        tidy.setWraplen(1024);
        // 输出为xhtml
        tidy.setXHTML(true);
        // 去掉没用的标签
        tidy.setMakeClean(true);
        // 清洗word2000的内容
        tidy.setWord2000(true);
        // 设置错误输出信息
        tidy.setErrout(new PrintWriter(System.out));
        tidy.parse(stream, tidyOutStream);
        return tidyOutStream.toString();
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
        String xhtml = HtmlToXHtmlJtidy.html2xhtml(html);
        System.out.println("============xhtml===================");
        System.out.println(xhtml);
    }

}
