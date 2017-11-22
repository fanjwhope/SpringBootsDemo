package com.gutai.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 82421 on 2017/8/14.
 */
@SpringBootApplication
@ComponentScan(value = "com.gutai.*")
@MapperScan(basePackages = "com.gutai.mapper")
public class StartApp  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartApp.class);
    }
    /**
     * SpringApplication 则是用于从main方法启动Spring应用的类。默认，它会执行以下步骤：
     1.创建一个合适的ApplicationContext实例 （取决于classpath）。
     2.注册一个CommandLinePropertySource，以便将命令行参数作为Spring properties。
     3.刷新application context，加载所有单例beans。
     4.激活所有CommandLineRunner beans。
     默认，直接使用SpringApplication 的静态方法run()即可。但也可以创建实例，并自行配置需要的设置。
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(StartApp.class);
        app.run(args);
        //SpringApplication.run(sampleControler.class, args);
    }

    /*
       http://blog.csdn.net/qq_24084925/article/details/55049300 跨域请求
       若是SpringBoot则简单的多：在application.java文件下添加：
    * */
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

}
