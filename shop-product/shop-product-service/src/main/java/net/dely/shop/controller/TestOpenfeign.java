package net.dely.shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: dely
 * Date: 2022/7/19
 * Time: 23:36
 */
@RestController
public class TestOpenfeign {

    /**
     * 测试 openfeing
     * @param num 数据
     * @return 返回值
     */
    @GetMapping("/api/product/v1/num")
    public int testOpenfeign(@RequestParam("num") int num){
        return num+8;
    }
}
