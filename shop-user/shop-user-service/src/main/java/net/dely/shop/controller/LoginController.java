package net.dely.shop.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.dely.shop.OpenfeignTest;
import net.dely.shop.Request.UserLoginRequest;
import net.dely.shop.model.LoginUser;
import net.dely.shop.storage.mysql.service.UserService;
import net.dely.shop.util.CommonUtil;
import net.dely.shop.util.JWTUtil;
import net.dely.shop.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
        int i = openfeignTest.testOpenfeign(2);
        System.out.println("openfeign====="+i);
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
        String token = "AuthorizationeyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZWx5c2hvcCIsImhlYWRJbWciOiIxMjMucG5nIiwiaWQiOjEsIm5hbWUiOiJESlgiLCJtYWlsIjoiMTE3NjMyMjQ4NUBRUS5DT00iLCJpYXQiOjE2NTc5ODQ5MDksImV4cCI6MTY1OTczNzk0MX0.Iof_PTvOL2rX_HkYeQpAm8TTS4vjtnJwZGilUYRN3Y8";
        Claims claims = JWTUtil.checkJWT(token);
        String headImg = (String) claims.get("headImg");
        Integer id = (Integer) claims.get("id");
        String ipAddr = CommonUtil.getIpAddr(request);
        return ResultData.buildSuccess(testvalue);
    }
}
