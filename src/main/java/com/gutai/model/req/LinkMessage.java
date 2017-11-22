package com.gutai.model.req;

/**
 * Created by 82421 on 2017/8/22.
 */

/**
 * 链接消息
     <xml>
     <ToUserName><![CDATA[toUser]]></ToUserName>
     <FromUserName><![CDATA[fromUser]]></FromUserName>
     <CreateTime>1351776360</CreateTime>
     <MsgType><![CDATA[link]]></MsgType>
     <Title><![CDATA[公众平台官网链接]]></Title>
     <Description><![CDATA[公众平台官网链接]]></Description>
     <Url><![CDATA[url]]></Url>
     <MsgId>1234567890123456</MsgId>
     </xml>
 */
public class LinkMessage extends BaseMessage{
    // 消息标题
    private String Title;
    // 消息描述
    private String Description;
    // 消息链接
    private String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
