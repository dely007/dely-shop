package net.dely.shop.config;

import lombok.extern.slf4j.Slf4j;
import net.dely.shop.interceptor.LogInterceptor;
import net.dely.shop.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 * @author: dely
 * Date: 2022/7/16
 * Time: 23:49
 */
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //拦截的路径
                .addPathPatterns("/api/user/*/**")
                //排查不拦截的路径
                .excludePathPatterns("/user/login/login","/api/user/redission/");

        //TODO 多线程 如何去解决
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");

    }
}
