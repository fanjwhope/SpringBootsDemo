package com.gutai.controller;

import com.gutai.util.PropertiesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 82421 on 2017/10/17.
 */
@Controller
public class FileDUController {
    /**
     * 单文件上传
     * @param req
     * @param multiReq
     */
    @RequestMapping(value = "/testUploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> testUploadFile(HttpServletRequest req,MultipartHttpServletRequest multiReq,HttpServletResponse response) {
        Map<String,String> result=new HashMap<String,String>();
        // 获取上传文件的路径
        String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
        System.out.println("uploadFlePath:getOriginalFilename " + uploadFilePath);
        System.out.println("uploadFlePath:getName " +  multiReq.getFile("file1").getName());
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        System.out.println("multiReq.getFile()" + uploadFileName);
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        System.out.println("uploadFileSuffix:" + uploadFileSuffix);

        //上传文件路径
        String fileURL=PropertiesUtils.getPro("context.properties").getProperty("wordStorage")+ uploadFileName
                + "."+ uploadFileSuffix;


        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) multiReq.getFile("file1").getInputStream();
            fos = new FileOutputStream(new File(fileURL));
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1){
                fos.write(temp,0,temp.length);
                fos.flush();
                i = fis.read(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    result.put("fileurl",fileURL);
                    result.put("message","上传成功！");
                } catch (IOException e) {
                    e.printStackTrace();
                    result.put("fileurl",fileURL);
                    result.put("message","上传失败！");
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                    result.put("fileurl",fileURL);
                    result.put("message","上传成功！");
                } catch (IOException e) {
                    e.printStackTrace();
                    result.put("fileurl",fileURL);
                    result.put("message","上传失败！");
                }
            }
        }
        return result;
    }

    /**
     * 实现多个文件上传
     * @param request
     * @return
     */
    @RequestMapping(value = "testUploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> handleFileUpload(HttpServletRequest request) {
        Map<String,Object> result=new HashMap<String,Object>();
        List<String> filenames=new ArrayList<String>();

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file"); //获取上传的文件组
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        //遍历文件组
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    String uploadFilePath = file.getOriginalFilename();
                    System.out.println("uploadFlePath:" + uploadFilePath);
                    // 截取上传文件的文件名
                    String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1,uploadFilePath.indexOf('.'));
                    System.out.println("multiReq.getFile()" + uploadFileName);
                    // 截取上传文件的后缀
                    String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
                    System.out.println("uploadFileSuffix:" + uploadFileSuffix);
                    //创建文件输出流，写文件（上传服务器的路径）
                    stream = new BufferedOutputStream(new FileOutputStream(new File(PropertiesUtils.getPro("context.properties").getProperty("wordStorage")+ uploadFileName + "." + uploadFileSuffix)));

                    byte[] bytes = file.getBytes();
                    stream.write(bytes,0,bytes.length); //将bytes 字节数组中的数据从0 开始写入bytes.length个长度的字节到stream 中

                    //记录文件名称。
                    filenames.add(uploadFilePath);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (stream != null) {
                            stream.close();
                            result.put("info","上传成功");
                        }
                    } catch (IOException e) {
                        result.put("info","上传失败");
                        e.printStackTrace();
                    }
                }
            } else {
                result.put("info","上传文件为空");
                System.out.println("上传文件为空");
            }
        }
        result.put("data",filenames);
        System.out.println("文件接受成功了");
        return result;
    }

    @RequestMapping(value = "/testDownload", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testDownload(String fileName,HttpServletResponse res) {

        System.out.println("文件路径："+fileName);

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
        return "success";
    }
}


