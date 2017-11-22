package com.gutai.model.resp;


import com.gutai.Annotation.XStreamCDATA;

/**
 * 响应文本消息
 *<xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>12345678</CreateTime>
 <MsgType><![CDATA[text]]></MsgType>
 <Content><![CDATA[你好]]></Content>
 </xml>

 参数     是否必须     描述
 ToUserName     是     接收方帐号（收到的OpenID）
 FromUserName     是     开发者微信号
 CreateTime     是     消息创建时间 （整型）
 MsgType     是     text
 Content     是     回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
 */
public class TextMessage extends BaseMessage {

    // 回复的消息内容
    @XStreamCDATA
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}