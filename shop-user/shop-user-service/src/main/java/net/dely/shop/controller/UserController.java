package net.dely.shop.controller;


import lombok.extern.slf4j.Slf4j;
import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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

    @GetMapping("/test")
    public UserDO test(long id) {
//        //测试日志 脱敏
//        String msg="1176322485@QQ.COM";
//        String tel="13144127277";
//        log.info(msg);
//        log.info(tel);
//        System.out.println(msg);
//        System.out.println(tel);
        //测试操作数据库
        UserDO byId = userService.getById(id);
        UserDO byId1 = userService.getById(47);

        byId.setPwd("1234567890");
        byId1.setPwd("1234567890");
//        userService.saveOrUpdate(byId);
        userService.updateBatchById(Arrays.asList(byId,byId1));
        UserDO userDO = userService.getById(id);
        UserDO userDO1 = userService.getById(47);
        return userDO;
    }

    @GetMapping("/add/")
    public UserDO add(String a) {
//        boolean success = userService.saveBatch(Arrays.asList(UserDO));
//        userService.saveOrUpdate(UserDO);
//        UserDO byId = userService.getById(UserDO.getId());
//        System.out.println(byId);
        //测试操作数据库

        try {
            int i=1/0;
            System.out.println("111111111111");
        } catch (Exception e) {
            log.error("e:",e);
            throw new RuntimeException("出现异常了哦哦哦哦");
        }

       /* LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(net.dely.shop.storage.mysql.entity.UserDO::getPwd, "123456");
        updateWrapper.set(net.dely.shop.storage.mysql.entity.UserDO::getMail, "1176322485@qq.com");
        updateWrapper.eq(net.dely.shop.storage.mysql.entity.UserDO::getId, 47);
        updateWrapper.last("limit 1");
        userService.update(updateWrapper);
        UserDO byId = userService.getById(47);*/
        return null;
    }

}

