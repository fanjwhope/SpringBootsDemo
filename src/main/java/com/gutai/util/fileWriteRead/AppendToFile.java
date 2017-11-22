package com.gutai.util.fileWriteRead;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by 82421 on 2017/10/19.
 */
public class AppendToFile {
    /**
     * A 方法追加文件：使用随机访问文件流 RandomAccessFile，按读写方式。
     * @param fileName
     * @param content
     */
    public static void appendMethodA(String fileName,String content){
        try {
            //打开一个随机访问文件流，按读写方式
            RandomAccessFile randomAccessFile=new RandomAccessFile(fileName,"rw");
            //文件长度，字节数
            long fileLength=randomAccessFile.length();
            //将写文件指针移到文件尾部
            randomAccessFile.seek(fileLength);
            randomAccessFile.writeBytes(content); //写入内容
            randomAccessFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * B 方法追加文件：使用fileWriter
     * @param fileName
     * @param content
     */
    public static void appendMethodB(String fileName,String content){
        try {
            //打开一个写文件器，构造函数中的第二个参数trueb表示以追加的形式写文件
            FileWriter writer=new FileWriter(fileName,true);
            writer.write(content);
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String filename="D:\\FdekFile\\word\\test\\反馈问题.txt";
        String content="hello ,nihao Append";
        //按A方法追加
        AppendToFile.appendMethodA(filename,content+"A.....");
        ReadFromFile.readFileByLines(filename);
        //按B方法追加
        AppendToFile.appendMethodB(filename,content+"B.....");
        ReadFromFile.readFileByLines(filename);


    }
}
