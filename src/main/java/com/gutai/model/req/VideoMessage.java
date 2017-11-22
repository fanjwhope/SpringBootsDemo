package com.gutai.model.req;

/**
 * Created by 82421 on 2017/9/28.
 */

/**
 * 视屏信息
 *
 MediaId	视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
 ThumbMediaId	视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
 *
 */
public class VideoMessage extends com.gutai.model.resp.BaseMessage {
    // 媒体ID
    private String MediaId;
    // 语音格式
    private String ThumbMediaId;

    public String getMediaId() {
        return MediaId;
    }
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    public String getThumbMediaId() {
        return ThumbMediaId;
    }
    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

}
