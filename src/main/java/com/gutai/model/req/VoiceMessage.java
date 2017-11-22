package com.gutai.model.req;

/**
 * Created by 82421 on 2017/8/22.
 */
/**
 * 音频消息
     <xml>
     <ToUserName><![CDATA[toUser]]></ToUserName>
     <FromUserName><![CDATA[fromUser]]></FromUserName>
     <CreateTime>1357290913</CreateTime>
     <MsgType><![CDATA[voice]]></MsgType>
     <MediaId><![CDATA[media_id]]></MediaId>
     <Format><![CDATA[Format]]></Format>
     <MsgId>1234567890123456</MsgId>
     </xml>
 *
 */
public class VoiceMessage extends BaseMessage {
    // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
