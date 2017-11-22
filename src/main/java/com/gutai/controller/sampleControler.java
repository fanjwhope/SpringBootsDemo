package com.gutai.controller;

import com.gutai.model.User;
import com.gutai.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 82421 on 2017/8/14.
 * @EnableAutoConfiguration 用于自动配置。简单的说，它会根据你的pom配置（实际上应该是根据具体的依赖）来判断这是一个什么应用，并创建相应的环境。
    在上面这个例子中，@EnableAutoConfiguration 会判断出这是一个web应用，所以会创建相应的web环境。

注解：
 @Configuration：指出该类是 Bean 配置的信息源，相当于XML中的<beans></beans>，一般加在主类上。
 @EnableAutoConfiguration：让 SpringBoot 根据应用所声明的依赖来对 Spring 框架进行自动配置，由于 spring-boot-starter-web 添加了Tomcat和Spring MVC，
                            所以auto-configuration将假定你正在开发一个web应用并相应地对Spring进行设置.
 @ComponentScan：表示将该类自动发现（扫描）并注册为Bean，可以自动收集所有的Spring组件
                （@Component , @Service , @Repository , @Controller 等），包括@Configuration类。
 @SpringBootApplication： @EnableAutoConfiguration、@ComponentScan和@Configuration的合集。
 @EnableTransactionManagement：启用注解式事务。
 @RestController: ??
 */
@Controller
public class sampleControler {
    @Autowired
    BlogService service;

     @RequestMapping(value = "/home",method = RequestMethod.GET)
     //@ResponseBody    --- http://localhost:8081/home/ 才可以请求到页面。否则就是下载页面了。
     //@GetMapping("/home")
    public String home() {
         System.out.println("hello11");
        return "home";
    }

    @RequestMapping("/nihao1")
    @ResponseBody
    public String  index(){

       return "nihao1";
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<User>  getBlogList(){
        List<User> list=service.getAllBlogList();
        return list;
    }

    @RequestMapping("/mybatis")
    @ResponseBody
    public List<User>  getuserList(){
        List<User> list= null;
        try {
            list = service.getAllList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
