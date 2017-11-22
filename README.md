# SpringBootsDemo
springboot 测试
微信开发信息：
网址：1. http://www.cnblogs.com/liuhongfeng/p/5057167.html
      2. 微信公众号： https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&token=2014690523&lang=zh_CN

      微信公众号【订阅号，未认证】
      AppId: wx66ec6f8824e6e730
      AppSecret： 3d229563246ba714ff1cbec997742196
      微信号：gh_f174fbb26719
      密码：qq邮箱

       微信公众号测试号：https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
       String AppID="wxe0c973705762576e";//"wx66ec6f8824e6e730";
       String AppSecret="eb9e873985b5893b64b31d08733fafa6";//"3d229563246ba714ff1cbec997742196";
 获取用户信息时：需要将ip 设置为白名单     ip 119.96.131.110

  ##获取access_token 凭证获取（GET）
  String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
  ###拉取用户信息


  ##第八篇 ：微信公众平台开发实战Java版之如何网页授权获取用户基本信息
   https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
       （1）替换自己的AppID
       （2）将redirect_url换成自己的授权请求链接URL。注意这个连接需要经过UTF-8编码。
       （3）需要修改scope。需要弹出页面则要修改为snsapi_userinfo 。
       scope参数的解释：
       1、以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）
       2、以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。

   ##文件流的学习

   ##1.文件上传的几种方式：http://www.cnblogs.com/sunliyuan/p/5737928.html  没有验证过。

   ##2.文件的上传与下载 （springBoot实现跨域文件的上传和下载），文件上传采用form表单提交的形式。
       1.ajax 跨域请求处理：
         /**
              http://blog.csdn.net/qq_24084925/article/details/55049300 跨域请求
              若是SpringBoot则简单的多：在application.java文件下添加：
           **/
           @Bean
           public WebMvcConfigurer corsConfigurer() {
               return new WebMvcConfigurerAdapter() {
                   @Override
                   public void addCorsMappings(CorsRegistry registry) {
                       registry.addMapping("/**")
                               .allowedOrigins("*")
                               .allowedMethods("PUT", "DELETE","GET","POST")
                               .allowedHeaders("*")
                               .exposedHeaders("access-control-allow-headers",
                                       "access-control-allow-methods",
                                       "access-control-allow-origin",
                                       "access-control-max-age",
                                       "X-Frame-Options")
                               .allowCredentials(false).maxAge(3600);
                   }
               };
           }
       2.文件上传后不跳转页面。 http://blog.csdn.net/u013125146/article/details/38703613 使用 jquery.form插件（预计于jQuery的）
        详见 fileUpDown_ajax.html
         Javaweb文件上传，对文件的一些细节处理： http://www.cnblogs.com/xdp-gacl/p/4200090.html

   ##3.怎么解析文件（word，exccel,pdf,zip）


   ##4.如何打包springboot项目。
   spring boot项目打包成war并在tomcat上运行的步骤   网址：http://blog.csdn.net/yalishadaa/article/details/70037846


   ##5.安装Maven。并且配置环境变量
   之前一直用的是idea自带的maven 插件。
   1.上网站http://maven.apache.org/download.cgi# 下载maven包
   注意：推荐只用后缀是 bin.zip 的文件。当然如果你对源码感兴趣并想自己构建Maven。
   可以下载 后缀为src.zip 的文件。下载页面还提供了MD5校验和checksum文件和asc 数字签名文件。用于校验maven 分发包的真确性和安全性。
   2.解压后，配置环境变量。
        2.1 在系统变量中创建一个新的变量 M2_HOME ,变量值 Maven的安装目录(解压目录)。 D:\Program Files (x86)\Maven\apache-maven-3.5.2-bin\apache-maven-3.5.2
        2.2 在系统变量中修改 path的变量值。 在值的尾部加上 %M2_HOME%\bin;
        2.3 单击确认。注意：多个值之间需要用 分号 隔开。
   3.打开cmd 窗体。 echo %M2_HOME%    和 mvn _v 检查是否配置好。

   注意：path环境变量。当我们在cmd 中输入命令时，Windows 首先会在当前目录中寻找可执行的文件或者脚本。
   如果没有找到，Windows 会遍历环境变量path 中定义的路径。并执行其中的脚本。








