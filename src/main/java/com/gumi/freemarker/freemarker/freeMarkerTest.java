package com.gumi.freemarker.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyang on 2018/6/5.
 */
public class freeMarkerTest {
    public static void main(String args[]) throws IOException, TemplateException {
        //1.创建配置实例Cofiguration
        Configuration cfg = new Configuration();

        //2.设置模板文件目录
        //（1）src目录下的目录（template在src下）
        //cfg.setDirectoryForTemplateLoading(new File("src/template"));
        //（2）完整路径（template在src下）
        //cfg.setDirectoryForTemplateLoading(new File(
        //      "D:/cpic-env/workspace/javaFreemarker/src/template"));
        //cfg.setDirectoryForTemplateLoading(new File("src/template"));
        //（3）工程目录下的目录（template/main在工程下）--推荐
        cfg.setDirectoryForTemplateLoading(new File("C:\\freemarker"));
        //cfg.setObjectWrapper(new DefaultObjectWrapper());
        //获取模板（template）
        Template template = cfg.getTemplate("1.flt");
        //建立数据模型（Map）
        Map<String, String> root = new HashMap<String, String>();
        root.put("insuranceNumber", "100000001");
        root.put("carOwner", "测试9898");
        root.put("telephone", "13566993355");
        root.put("idNumber", "其他 222");
        root.put("acquisitionPrice", "0.01元");
        root.put("address", "上海市宝山区亿博大厦");
        root.put("carType", "ARCFOX/LITE/2017款 原力版");
        root.put("licenseNumber", "沪A123456");
        root.put("vin", "FGYKRVLM62XS295JS");
        root.put("engineNumber", "222");
        root.put("recordDate", "2018-06-05");
        root.put("imeis", "864298010048390,864298010048408");
        root.put("repayment", "测试9898");
        root.put("insuranceDate", "2018-06-06");
        //获取输出流（指定到控制台（标准输出））
//        Writer out = new OutputStreamWriter(System.out);
        // 输出文档路径及名称
        File outFile = new File("C:\\freemarker\\freemarkertest.doc");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        //StringWriter out = new StringWriter();
        //System.out.println(out.toString());
        //数据与模板合并（数据+模板=输出）
        template.process(root, out);
        out.flush();
    }
}
