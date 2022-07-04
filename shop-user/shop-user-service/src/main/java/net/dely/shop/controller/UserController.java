package net.dely.shop.controller;


import lombok.extern.slf4j.Slf4j;
import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.service.UserService;
import net.dely.shop.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

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


    @GetMapping("/testRedis/")
    public Object testRedis() throws IOException {
//        UserDO userDO = new UserDO();
//        userDO.setPwd("123");
//        userDO.setName("djx");
//        redisUtil.set("shop:user", JSON.toJSONString(userDO));
//        String s = redisUtil.get("shop:user");
//        UserDO userDO1 = JSON.parseObject(s, UserDO.class);
//        redisTemplate.opsForValue().set("test",userDO);
//        TestDO testDO = new TestDO();
//        testDO.setHeadImg("1");
//        testDO.setSlogan("2");
//
//        Map<String, String> maps = new HashMap<>(8);
//        maps.put("1", JSON.toJSONString(testDO));
//        maps.put("2", JSON.toJSONString(testDO));
//        redisUtil.hPutAll("HAMAP",maps);
//
//        Map<Object, Object> map = redisUtil.hGetAll("HAMAP");
//        Set<Map.Entry<Object, Object>> entries = map.entrySet();
//        for (Map.Entry<Object, Object> entry : entries) {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());
//            Object value = entry.getValue();
//            TestDO testDO1 = JSON.parseObject((String) entry.getValue(), TestDO.class);
//            System.out.println(testDO1);
//        }
//        String hamap = (String) redisUtil.hGet("HAMAP", "1");
//        TestDO testDO2 = JSON.parseObject(hamap, TestDO.class);
//        System.out.println(testDO2);
//        Set<ZSetOperations.TypedTuple<String>> values = new HashSet<>();
////
////
////        DefaultTypedTuple member1 = new DefaultTypedTuple( "小明", 100.0);
////        DefaultTypedTuple member2 = new DefaultTypedTuple( "小王", 99.5);
////
////        values.add(member1);
////        values.add(member2);
////        Long zset = redisUtil.zAdd("zset", values);
////        System.out.println("zset"+zset);
////
////        Set<String> zset1 = redisUtil.zRange("zset", 0, -1);
////        System.out.println(zset1);

        redisUtil.set("expireTime","expireTime",60L);
        Long expireTime = redisUtil.getExpire("expireTime");
        System.out.println("expireTime=="+expireTime);
        return "success";
    }

}

