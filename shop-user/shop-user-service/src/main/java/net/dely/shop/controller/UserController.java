package net.dely.shop.controller;


import lombok.extern.slf4j.Slf4j;
import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dely
 * @since 2022-06-25
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test/")
    public UserDO test() {
        //测试日志 脱敏
        log.info("1176322485@QQ.COM");
        log.info("13144127277");
        //测试操作数据库
        return userService.getById(3);
    }

}

