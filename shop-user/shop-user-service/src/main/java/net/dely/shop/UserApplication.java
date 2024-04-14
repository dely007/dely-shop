package net.dely.shop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: dengjiaxin
 * Date: 2022/6/25
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 * @author admin
 */
@SpringBootApplication
@MapperScan("net.dely.shop.storage.mysql.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class UserApplication {

    public static void main(String[] args) throws UnknownHostException {
//        SpringApplication.run(UserApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(UserApplication.class, args);

        // 获取app的 访问地址
        Environment environment = context.getBean(Environment.class);
        //environment.getProperty("server.servlet.context-path") 应用的上下文路径，也可以称为项目路径
        String host = InetAddress.getLocalHost().getHostAddress();
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        if (StringUtils.isEmpty(path)) {
            path = "";
        }
        log.info("\n访问地址：http://{}:{}{}", host, port, path);
    }
}
