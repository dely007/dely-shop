package net.dely.shop.controller;


import lombok.extern.slf4j.Slf4j;
import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        userService.updateById(byId);
        return byId;
    }

    @PostMapping("/add/")
    public UserDO add(@RequestBody UserDO UserDO) {
        boolean success = userService.save(UserDO);
        UserDO byId = userService.getById(UserDO.getId());
        System.out.println(byId);
        //测试操作数据库
        return byId;
    }

}

