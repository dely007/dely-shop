package net.dely.shop.controller;

import lombok.extern.slf4j.Slf4j;
import net.dely.shop.UserApplication;
import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: dely
 * Date: 2022/7/13
 * Time: 21:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddressDetail(){

        UserDO byId = userService.getById(47);
        log.info(byId.toString());
    }
}
