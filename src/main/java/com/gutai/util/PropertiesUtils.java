package com.gutai.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 82421 on 2017/10/17.
 */
public class PropertiesUtils {
    /**
     *  根据Properties 文件名获取文件中的配置数据
     *  Class.getResource(String path)
             path不以’/'开头时，默认是从此类所在的包下取资源；
             path  以’/'开头时，则是从ClassPath根下获取；
        Class.getClassLoader（）.getResource(String path)
             path不能以’/'开头时；
             path是从ClassPath根下获取；

     * @param name 文件名称
     * @return
     */
    public static Properties getPro(String name){
        Properties pro=new Properties();
        InputStream in =PropertiesUtils.class.getClassLoader().getResourceAsStream(name);
       /* System.out.println(PropertiesUtils.class.getResource("/"+name).toString());
        System.out.println(PropertiesUtils.class.getClassLoader().getResource(name).toString());*/
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pro;
    }
    public static void main(String[] arg){
        Properties p= PropertiesUtils.getPro("context.properties");
        System.out.println(p.getProperty("wordStorage"));

    }



}
