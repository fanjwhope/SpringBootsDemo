package com.gutai.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 82421 on 2017/8/14.
 */
@SpringBootApplication
@ComponentScan(value = "com.gutai.*")
@MapperScan(basePackages = "com.gutai.mapper")
public class StartApp {
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
}
