package com.gutai.model.req;

/**
 * Created by 82421 on 2017/8/22.
 */
/**
 * 2. 图片消息
     <xml>
     <ToUserName><![CDATA[toUser]]></ToUserName>
     <FromUserName><![CDATA[fromUser]]></FromUserName>
     <CreateTime>1348831860</CreateTime>
     <MsgType><![CDATA[image]]></MsgType>
     <PicUrl><![CDATA[this is a url]]></PicUrl>
     <MediaId><![CDATA[media_id]]></MediaId>
     <MsgId>1234567890123456</MsgId>
     </xml>
 *
 */
public class ImageMessage extends BaseMessage {
    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
