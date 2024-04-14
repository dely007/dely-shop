package net.dely.shop.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.dely.shop.OpenfeignTest;
import net.dely.shop.Request.UserLoginRequest;
import net.dely.shop.constants.Constants;
import net.dely.shop.model.LoginUser;
import net.dely.shop.storage.mysql.service.UserService;
import net.dely.shop.util.*;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: dely
 * Date: 2022/7/13
 * Time: 21:11
 */
@RestController
@RequestMapping("/user/login")
@Slf4j
@Api(tags = "登录相关接口")
@RefreshScope
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private OpenfeignTest openfeignTest;

    @Autowired
    private ThreadPoolComponent threadPoolComponent;

    @Autowired
    private ThreadPoolTaskExecutor userPoolTaskExecutor;

    @Value("${test.value}")
    private String testvalue;

    /**
     * 登录
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public ResultData register(@RequestBody UserLoginRequest loginRequest){

        //openfeign接口远程调用
        /*int i = openfeignTest.testOpenfeign(2);
        System.out.println("openfeign====="+i);*/
        LoginUser loginUser = LoginUser.builder().id(1L).name("DJX").mail("1176322485@QQ.COM").headImg("123.png").build();
        String token = JWTUtil.geneJsonWebToken(loginUser);
        return ResultData.buildSuccess(token);
    }

    /**
     * 测试feign
     * @param num
     * @return
     */
    @PostMapping("feign")
    @ApiOperation("测试feign")
    public ResultData feign(Integer num){

        //openfeign接口远程调用
        int i = openfeignTest.testOpenfeign(num);
        System.out.println("openfeign====="+i);
        return ResultData.buildSuccess(i);
    }

    /**
     * 测试TOKEN内容
     * @return
     */
    @GetMapping("getToken")
    @ApiOperation("测试TOKEN内容")
    public ResultData getToken(HttpServletRequest request){
        log.info("testvalue:{}",testvalue);
        String token = "AuthorizationeyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZWx5c2hvcCIsImhlYWRJbWciOiIxMjMucG5nIiwiaWQiOjEsIm5hbWUiOiJESlgiLCJtYWlsIjoiMTE3NjMyMjQ4NUBRUS5DT00iLCJpYXQiOjE3MTMwOTkzMzAsImV4cCI6MTcxNDg1MjM2M30.XCPbv8EIK1TY5lRGhBzDsif5m58QvwMwY5uBmiV-S-U";
        Claims claims = JWTUtil.checkJWT(token);
        String headImg = (String) claims.get("headImg");
        Integer id = (Integer) claims.get("id");
        String ipAddr = CommonUtil.getIpAddr(request);
        log.info("headImg:{},id:{},ipAddr:{}",headImg,id,ipAddr);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPoolComponent.execute(()->{
                log.info("threadPoolComponent thread running i:{}", finalI);
            });
        }

        String traceId = MDC.get(Constants.TRACE_ID);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            userPoolTaskExecutor.execute(() -> {
                MDC.put(Constants.TRACE_ID, traceId);
                try {
                    log.info("userPoolTaskExecutor thread running i:{}", finalI);
                } finally {
                    MDC.clear();
                }
            });
        }
        return ResultData.buildSuccess(testvalue);
    }


    /**
     * 测试MDC内容
     * @return
     */
    @GetMapping("testMDC")
    @ApiOperation("测试TOKEN内容")
    public ResultData testMDC(HttpServletRequest request){
        log.info("testvalue:{}",testvalue);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            threadPoolComponent.execute(()->{
                log.info("threadPoolComponent thread running i:{}", finalI);
            });
        }

        String traceId = MDC.get(Constants.TRACE_ID);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            userPoolTaskExecutor.execute(() -> {
                MDC.put(Constants.TRACE_ID, traceId);
                try {
                    log.info("userPoolTaskExecutor thread running i:{}", finalI);
                } finally {
                    MDC.clear();
                }
            });
        }
        return ResultData.buildSuccess(testvalue);
    }
}
