package com.gumi.freemarker.word;


import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XwpfTest {

    public static void main(String args[]) throws Exception {
        XwpfTest.testTemplateWrite();
//        DocxToPdf.convertPdf("C:\\freemarker\\1.docx", "C:\\freemarker\\1.pdf");
    }

    /**
     * 用一个docx文档作为模板，然后替换其中的内容，再写入目标文档中。
     *
     * @throws Exception
     */
    public static void testTemplateWrite() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("serialNumber", "WW2018000001");
        params.put("insuranceNumber", "100000001");
        params.put("carOwner", "测试9898");
        params.put("telephone", "13566993355");
        params.put("idNumber", "其他 222");
        params.put("acquisitionPrice", "0.01元");
        params.put("address", "上海市");
        params.put("carType", "ARCFOX/LITE/2017款 原力版");
        params.put("licenseNumber", "沪A123456");
        params.put("vin", "FGYKRVLM62XS295JS");
        params.put("engineNumber", "222");
        params.put("recordDate", "2018-06-05");
        params.put("imeis", "864298010048390");
        params.put("repayment", "测试9898");
        params.put("insuranceDate", "2018-06-05");
        params.put("no", "");
        String filePath = "C:\\freemarker\\temp\\1.docx";
        InputStream is = new FileInputStream(filePath);
        XWPFDocument doc = new XWPFDocument(is);
        //替换段落里面的变量
        replaceInPara(doc, params);
        //替换表格里面的变量
        replaceInTable(doc, params);
        OutputStream os = new FileOutputStream("C:\\freemarker\\1.docx");
        doc.write(os);
        close(os);
        close(is);
        doc.enforceReadonlyProtection("123", HashAlgorithm.sha512);
    }


    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    XWPFRun xwpfRun = para.insertNewRun(i);
                    xwpfRun.setText(runText);
                    xwpfRun.setFontSize(9);
                    xwpfRun.setFontFamily("宋体");//字体
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
