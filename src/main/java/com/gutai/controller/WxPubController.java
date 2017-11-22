package com.gutai.controller;

import com.gutai.service.core.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *   http://blog.csdn.net/h295928126/article/details/53490985
 *  @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
 *  只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，配置的视图解析器InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容。
 *
 * Created by 82421 on 2017/8/21.
 */
@RestController
public class WxPubController {
    //此处TOKEN即我们刚刚所填的token
    private String TOKEN = "mytestToken";
    @Autowired
    private CoreService coreService;


    /**
     * 接收并校验四个请求参数
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return echostr
     */
    @RequestMapping(value = "/weixin",method= RequestMethod.GET)
    public String checkName(@RequestParam(name="signature")String signature,
                            @RequestParam(name="timestamp")String timestamp,
                            @RequestParam(name="nonce")String nonce,
                            @RequestParam(name="echostr")String echostr){
        System.out.println("-----------------------开始校验------------------------");
        //排序
        String sortString = sort(TOKEN, timestamp, nonce);
        //加密
        String myString = sha1(sortString);
        //校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            System.out.println("签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            System.out.println("sortString:"+sortString);
            System.out.println("myString:"+myString);
            System.out.println(echostr);
            return echostr;
        } else {
            System.out.println("签名校验失败");
            return "";
        }
    }

    /**
     * 排序方法
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 调用核心业务类接收消息、处理消息跟推送消息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "weixin",method = RequestMethod.POST)
    public  String post(HttpServletRequest req){
        System.out.println("********************开始处理请求*****************************");
        try {
            System.out.println("接受消息内容："+req.getInputStream().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String respMessage = coreService.processRequest(req);
        System.out.println("推送消息内容： "+respMessage);
        return respMessage;
    }


    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/test",method= RequestMethod.GET,produces = "text/xml")
    public String test(){
        System.out.println("-----------------------开始校验------------------------");
        return "test";
    }



    @RequestMapping(value = "/test",method= RequestMethod.GET,produces = "application/xml")
    public String test2(){
        System.out.println("-----------------------开始校验------------------------");
        return "test2";
    }
}