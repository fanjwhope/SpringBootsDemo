package com.gutai.util.poiUtils;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * poi 操作word
 * API:  http://poi.apache.org/apidocs/index.html
 * Created by 82421 on 2017/10/19.
 *
 *
 *  POI读word docx 07 文件的两种方法:
     POI在读写word docx文件时是通过xwpf模块来进行的，其核心是XWPFDocument。
     一个XWPFDocument代表一个docx文档，其可以用来读docx文档，也可以用来写docx文档。
     XWPFDocument中主要包含下面这几种对象：
     XWPFParagraph：代表一个段落。
     XWPFRun：代表具有相同属性的一段文本。
     XWPFTable：代表一个表格。
     XWPFTableRow：表格的一行。
     XWPFTableCell：表格对应的一个单元格。

 */
public class WordTest {
   // public static void

    public static void main(String[] args) throws Exception {
        //word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
       /* OPCPackage opcPackage = POIXMLDocument.openPackage("D:\\FdekFile\\word\\test\\111.docx");
        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        String text2007 = extractor.getText();
        System.out.println(text2007);*/

        //readByXWPFDocument();

        //http://elim.iteye.com/blog/2031335   博客网址：读取word文档。

        String file="D:\\FdekFile\\word\\test\\test.zip";
       // readZipFile(file);


        //File  io (普通Io方式)
        File file1= File.createTempFile("aaa",".txt");
        System.out.println(file1.toString()+file1.getPath()); // C:\Users\82421\AppData\Local\Temp\aaa8850229652778564051.txt
        //  Files nio (nio 方式)
        Path p1=Files.createTempFile("aaa",".txt");
        System.out.println(p1.toString());// C:\Users\82421\AppData\Local\Temp\aaa2104960626118241284.txt





    }


    /**
     * 在使用XWPFWordExtractor读取docx文档的内容时，我们只能获取到其文本，而不能获取到其文本对应的属性值
     */
    public static void readByXWPFWordExtractor(){
        try {
            InputStream is = new FileInputStream(new File("D:\\FdekFile\\word\\test\\111.docx"));
            XWPFDocument doc = new XWPFDocument(is);
            XWPFWordExtractor ex = new XWPFWordExtractor(doc);
            String text = ex.getText();
            System.out.println(text);
            POIXMLProperties.CoreProperties coreProps = ex.getCoreProperties();
            WordTest.printCoreProperter(coreProps);
            close(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 1.2 通过XWPFDocument读
     在通过XWPFDocument读取docx文档时，我们就可以获取到文本比较精确的属性信息了。
     比如我们可以获取到某一个XWPFParagraph、XWPFRun或者是某一个XWPFTable，包括它们对应的属性信息。下面是一个使用XWPFDocument读取docx文档的示例：
     */
   public static void readByXWPFDocument() throws Exception{
       InputStream is = new FileInputStream("D:\\FdekFile\\word\\test\\111.docx");
       XWPFDocument doc = new XWPFDocument(is);

       List<XWPFParagraph> paras = doc.getParagraphs();
       for (XWPFParagraph para : paras) {
           //当前段落的属性
           //CTPPr pr = para.getCTP().getPPr();
           System.out.println(para.getText());
       }
       //获取文档中所有的表格
       List<XWPFTable> tables = doc.getTables();
       List<XWPFTableRow> rows;
       List<XWPFTableCell> cells;
       for (XWPFTable table : tables) {
           //表格属性
  //       CTTblPr pr = table.getCTTbl().getTblPr();
           //获取表格对应的行
           rows = table.getRows();
           for (XWPFTableRow row : rows) {
               //获取行对应的单元格
               cells = row.getTableCells();
               for (XWPFTableCell cell : cells) {
                   System.out.println(cell.getText());;
               }
           }
       }
       close(is);
   }


    private static void  printCoreProperter(POIXMLProperties.CoreProperties coreProperties ){
        System.out.println("分类:"+coreProperties.getCategory());//分类
        System.out.println("创建者:"+coreProperties.getCreator());//创建者
        System.out.println("创建时间:"+coreProperties.getCreated());//创建时间
        System.out.println("标题:"+coreProperties.getTitle());//标题
    }

    private static void close(InputStream is){
        if (is!=null){
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取zip 文件。
     * 会遍历zip 中的所有文件 （注意：不支持读取含中文名称的文件---压缩包内部不能包含中文名称的文件）
     * 如果含有中文名称的文件，程序会报错【Exception in thread "main" java.lang.IllegalArgumentException: MALFORMED】。
     * @param file
     * @throws Exception
     */
    public static void readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file, Charset.forName("GBK"));
        //ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) { //是否文件夹

            } else {
                System.err.println("file - " + ze.getName() + " : " + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }

}
