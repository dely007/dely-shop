package net.dely.shop;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: dely
 * Date: 2022/7/19
 * Time: 23:31
 */
@FeignClient(name = "shop-product-server")
public interface OpenfeignTest {

    /**
     * 测试 openfeing
     * @param num 数据
     * @return 返回值
     */
    @GetMapping("/api/product/v1/num")
    int testOpenfeign(@RequestParam("num") int num);
}
