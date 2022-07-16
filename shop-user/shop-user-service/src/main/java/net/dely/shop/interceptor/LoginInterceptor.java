package net.dely.shop.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import net.dely.shop.enums.BizCodeEnum;
import net.dely.shop.model.LoginUser;
import net.dely.shop.util.CommonUtil;
import net.dely.shop.util.JWTUtil;
import net.dely.shop.util.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author: dely
 * Date: 2022/7/17
 * Time: 0:03
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader("token");
        if (StringUtils.isBlank(accessToken)){
            accessToken = request.getParameter("token");
        }
        if (StringUtils.isNotEmpty(accessToken)){
            Claims claims = JWTUtil.checkJWT(accessToken);
            if (claims == null){
                //未登录
                CommonUtil.sendJsonMessage(response, ResultData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
                return false;
            }
            Integer userId = (Integer) claims.get("id");
            String headImg = (String)claims.get("head_img");
            String name = (String)claims.get("name");
            String mail = (String)claims.get("mail");


            LoginUser loginUser = LoginUser
                    .builder()
                    .headImg(headImg)
                    .name(name)
                    .id(Long.valueOf(userId))
                    .mail(mail).build();

            threadLocal.set(loginUser);

            return true;

        }
        //未登录
        CommonUtil.sendJsonMessage(response, ResultData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        threadLocal.remove();
    }
}
