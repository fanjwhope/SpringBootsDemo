package com.gutai.model.resp;

import com.gutai.model.resp.bo.Image;

/**
 *2.图片信息
 */
public class ImageMessage extends BaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
