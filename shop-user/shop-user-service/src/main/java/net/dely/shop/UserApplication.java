package net.dely.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by IntelliJ IDEA.
 * User: dengjiaxin
 * Date: 2022/6/25
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
@SpringBootApplication
@MapperScan("net.dely.shop.storage.mysql.mapper")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
