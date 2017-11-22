package com.gutai.util;

import com.gutai.Annotation.FieldNodeName;
import com.gutai.Annotation.XStreamCDATA;
import com.gutai.model.resp.NewsMessage;
import com.gutai.model.resp.TextMessage;
import com.gutai.model.resp.bo.Article;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 82421 on 2017/9/28.
 * 使用注解方式实现微信响应消息转xml .有待优化。。。
 */
public class AnMessageUtils {

    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)and 未关注群体扫描二维码
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：已关注群体扫描二维码
     */
    public static final String EVENT_TYPE_SCAN="SCAN";
    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";
    /**
     * 事件类型：VIEW(点击自定义菜单跳转链接时的事件)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型：transfer_customer_service(把消息推送给客服)
     */
    public static final String EVENT_TYPE_TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";


    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    //屏蔽某些编译时的警告信息(在强制类型转换的时候编译器会给出警告)
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
            System.out.println("Key:value-->  "+e.getName()+":" + e.getText());
        }
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }




    /**
     *
     * @param clazz
     * @param fieldAlias
     * @return
     */
    private static boolean existsCDATA1(Class<?> clazz, String fieldAlias) {
        if ("MediaId".equals(fieldAlias)) {
            return true; // 特例添加 morning99
        }
        // scan fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 1. exists XStreamCDATA
            if (field.getAnnotation(XStreamCDATA.class) != null) {
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                // 2. exists XStreamAlias
                if (null != xStreamAlias) {
                    if (fieldAlias.equals(xStreamAlias.value()))// matched
                        return true;
                } else {// not exists XStreamAlias
                    if (fieldAlias.equals(field.getName()))
                        return true;
                }
            }
        }
        return false;
    }


    /**
     *判断是否添加CDATA （是否含有CDATA 注解）
     * 默认字段命名为MediaId 则需要CDATA
     *     字段命名为CreateTime则不需要CDATA
     * @param field
     * @return
     */
    private static boolean existsCDATA(Field field) {
        if ("MediaId".equals(field.getName())) {
            return true; // 特例添加 morning99
        }
        if("CreateTime".equals(getFieldtNodeName(field))){
            return false;
        }
        if (field.getAnnotation(XStreamCDATA.class) != null) {
            return true;
        }else{
            return false;
        }

    }



    /**
     * 根据属性名称获取属性值
     * @param fieldName
     * @param obj
     * @return
     */
    private static Object getFieldValueByName(String fieldName,Object obj){
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(obj, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 属性FieldNodeName 注解值
     * @param field
     * @return
     */
    private static String getFieldtNodeName(Field field){
        FieldNodeName annotation = field.getAnnotation(FieldNodeName.class);
        if (null != annotation) {
            return annotation.name();
        } else {
            return field.getName();
        }
    }

    /**
     * 属性FieldNodeName 注解值 item
     * @param field
     * @return
     */
    private static String getFieldtNodeNameItem(Field field){
        FieldNodeName annotation = field.getAnnotation(FieldNodeName.class);
        if (null != annotation) {
            return annotation.item();
        } else {
            return field.getName();
        }
    }

    /**
     * 根据属性获取 注解值
     * @param field
     * @return
     */
    private static Map<String,String> getFieldAnnotation(Field field){
        Map<String,String> map=new HashMap<String,String>();
        FieldNodeName fannotation = field.getAnnotation(FieldNodeName.class);
        XStreamCDATA xan=field.getAnnotation(XStreamCDATA.class);
        if(null!=xan) {
            map.put("cdata","CDATA");
        }else
        {
            map.put("cdata","");
        }
        if (null != fannotation) {
            map.put("item",fannotation.item());
            map.put("name",fannotation.name());
        } else {
            map.put("item",field.getName());
            map.put("name",fannotation.name());
        }
        return map;
    }



    /**
     * 判断类型是否为基础类型 （包含 String）
     * @param clz
     * @return
     */
    private static boolean isWrapClass(Class<?> clz){
        try {
            if(!String.class.equals(clz)){
                if(clz.isPrimitive()){
                    return true;
                }else
                    return ((Class) clz.getField("TYPE").get(null)).isPrimitive();

            }else return true;
        } catch (Exception e) {
           return  false;
        }
    }


    private static <T> String fieldToXml(T obj, Field field) {
        if (!field.getName().startsWith("this")) {
            StringBuffer xml = new StringBuffer();
            // 属性值
            Object value = getFieldValueByName(field.getName(), obj);
            // 属性节点名 (默认属性名字)
            String nodeName = getFieldtNodeName(field);
            if (null != value) {
                // 是否为基本类型
                if (isWrapClass(field.getType())) {
                    xml.append("<").append(nodeName).append(">").append(existsCDATA(field)?"<![CDATA["+value+"]]>":value).append("</").append(nodeName)
                            .append(">");
                    return xml.toString();
                    // List
                } else if (field.getType().isAssignableFrom(List.class)) {
                    List list = (List) value;
                    String itemNode=getFieldtNodeNameItem(field);
                    xml.append("<"+nodeName+">");
                    for (Object o:list ) {
                       xml.append(beanToXml(o, itemNode));
                    }
                    xml.append("</"+nodeName+">");
                    return xml.toString();
                } else { //bean
                    return beanToXml(value, nodeName);
                }
            }
        }
        return "";
    }

    public static<T> String beanToXml(T obj,String rootName){
        if (rootName == null) {
            rootName = obj.getClass().getSimpleName();
        }
        StringBuilder resp=new StringBuilder("<").append(rootName).append(">");
        Class<? extends Object> userCla = obj.getClass();
        // 追溯其父对象。（不包含 Object.class ）
        for (; userCla != Object.class; userCla = userCla.getSuperclass()) {
			/* 得到类的所有属性集合 */
            Field[] fs = userCla.getDeclaredFields();
            for (Field field : fs) {
                resp.append(fieldToXml(obj, field));
            }
        }
        resp.append("</" + rootName + ">");
        return resp.toString();
    }


    /**
     * 文本消息对象转换成xml
     * alias 起别名,如果没有,根节点将面为com.pacage.textMessage的形式
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        return  AnMessageUtils.beanToXml(textMessage,"xml");
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        return  AnMessageUtils.beanToXml(newsMessage,"xml");
    }



    public static void main(String[] args) {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName("aaa");
        textMessage.setFromUserName("bbb");
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent("hello");
        String xml =AnMessageUtils.beanToXml(textMessage,"xml");
        System.out.println(xml);
        System.out.println("***********************************************");

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName("aaa");
        newsMessage.setFromUserName("bbb");
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("微信公众帐号开发教程Java版");
        // 图文消息中可以使用QQ表情、符号表情
        article.setDescription("这是测试有没有换行\n\n如果有空行就代表换行成功\n\n点击图文可以跳转到百度首页");
        // 将图片置为空
        article.setPicUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        article.setUrl("http://www.baidu.com");
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        System.out.println(AnMessageUtils.newsMessageToXml(newsMessage));

        System.out.println(MessageUtil.newsMessageToXml(newsMessage));

    }




}
