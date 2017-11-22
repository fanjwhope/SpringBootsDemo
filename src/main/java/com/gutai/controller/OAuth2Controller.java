package com.gutai.controller;

import com.gutai.model.pojo.SNSUserInfo;
import com.gutai.model.pojo.WeixinOauth2Token;
import com.gutai.service.OAuth2ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 *  授权后的回调请求处理
 */
@RestController
@RequestMapping("/oauth")
public class OAuth2Controller {

    @Autowired
    OAuth2ServiceImpl oAuth2Service;

    @RequestMapping("")
    public HashMap<String, Object> getOauth(String code, String statu) {
        HashMap<String, Object> rs = new HashMap<>();
        rs.put("flag", false);
        //用户同意授权
        if (!"authdeny".equals(code)) {
            WeixinOauth2Token oauth2AccessToken = oAuth2Service.getOauth2AccessToken(code);
            if (null != oAuth2Service) {
                SNSUserInfo snsUserInfo = oAuth2Service.getSNSUserInfo(oauth2AccessToken.getAccessToken(), oauth2AccessToken.getOpenId());
                rs.put("flag", true);
                rs.put("data", snsUserInfo);
            } else {
                rs.put("msg", "未知错误");
            }
        } else {
            rs.put("msg", "用户拒绝");
        }
        return rs;
    }
}