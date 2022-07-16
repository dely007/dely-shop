package net.dely.shop.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.dely.shop.storage.mysql.entity.UserDO;
import net.dely.shop.storage.mysql.service.UserService;
import net.dely.shop.util.CommonUtil;
import net.dely.shop.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dely
 * @since 2022-06-25
 */
@RestController
@RequestMapping("api/user")
@Slf4j
@Api(tags = "测试相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Qualifier("captchaProducer")
    @Autowired
    private Producer captchaProducer;

    /**
     * 图形验证码有效期10分钟
     */
    private static final long CAPTCHA_CODE_EXPIRED = 60 * 1000 * 10;

    /**
     * 获取图形验证码
     * @param request
     * @param response
     */
    @ApiOperation("获取图形验证码")
    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){

        String captchaText = captchaProducer.createText();
        log.info("图形验证码:{}",captchaText);

        //存储
        redisTemplate.opsForValue().set(getCaptchaKey(request),captchaText,CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("获取图形验证码异常:{}",e);
        }

    }

    /**
     * 获取缓存的key
     * @param request
     * @return
     */
    private String getCaptchaKey(HttpServletRequest request){

        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");

        String key = "user-service:captcha:"+ CommonUtil.MD5(ip+userAgent);

        log.info("ip={}",ip);
        log.info("userAgent={}",userAgent);
        log.info("key={}",key);

        return key;

    }

    @ApiOperation("测试数据库加解密")
    @GetMapping("/test")
    public UserDO test(@ApiParam(value = "运单id",required = true)long id, HttpServletRequest request) {
        String token = request.getHeader("token");
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

        byId.setPwd("12345678");
        byId1.setPwd("12345678");
//        userService.saveOrUpdate(byId);
        userService.updateBatchById(Arrays.asList(byId,byId1));
        UserDO userDO = userService.getById(id);
        UserDO userDO1 = userService.getById(47);
        return userDO;
    }

    @ApiOperation("测试AOP")
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

    @ApiOperation("测试redis")
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
        Object test = redisTemplate.opsForValue().get("test");
        UserDO object = JSONObject.parseObject(JSON.toJSONString(test), new TypeReference<UserDO>() {});
        System.out.println("object="+object);
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

//        redisUtil.set("expireTime","expireTime",60L);
//        Long expireTime = redisUtil.getExpire("expireTime");
//        System.out.println("expireTime=="+expireTime);
        return object;
    }

    @ApiOperation("测试分布式锁")
    @GetMapping("/redission/")
    public Object redission() throws InterruptedException {

        System.out.println("thread1="+Thread.currentThread().getName());
//        String lockName = "lockkey";
//        LockUtil.lock(lockName,()->{
//            log.info("普通加锁，无返回值");
//        });
//        String result = LockUtil.lock(lockName, () -> {
//            log.info("普通加锁，无返回值");
//            return "返回值";
//        });
        RLock rLock =  redissonClient.getLock("lockname5");
        if (rLock.tryLock()){
            try {
                System.out.println("时间为="+new Date());
                return "success";
            } finally {
                Thread.sleep(10000);
                System.out.println("thread2="+Thread.currentThread().getName());
                rLock.unlock();
            }
        }else {
            System.out.println("thread3="+Thread.currentThread().getName());
            System.out.println("时间为="+new Date());
            return "fail";
        }
    }

}

