package net.dely.shop.aspectj;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一日志切面
 *
 * @author dely
 * @date 2022/6/30
 */
@Aspect
@Slf4j
@Component
public class RequestLogAspect {

    @Around("execution(public * net.dely.shop.controller.*.*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if ("GET".equalsIgnoreCase(request.getMethod())){
            String requestParam = getRequestParam(pjp);
            log.info("请求" + pjp.getSignature().getDeclaringTypeName()+"."+ pjp.getSignature().getName()+"()" + "方法,请求参数：" + requestParam);
        }else {
            // 获取请求参数
            Map<String, Object> requestParams = getRequestParams(pjp);
            log.info("请求" + pjp.getSignature().getDeclaringTypeName()+"."+ pjp.getSignature().getName()+"()" + "方法,请求参数：" + JSON.toJSONString(requestParams));
        }
        Long startTime = System.currentTimeMillis();
        // 执行方法
        Object result = pjp.proceed();
        Long endTime = System.currentTimeMillis();
        if (result==null){
            log.info("执行" + pjp.getSignature().getDeclaringTypeName()+"."+ pjp.getSignature().getName()+"()"  + "方法耗时为" + (endTime - startTime) + "ms,响应结果：null");
            return null;
        }
        log.info("执行" + pjp.getSignature().getDeclaringTypeName()+"."+ pjp.getSignature().getName()+"()"  + "方法耗时为" + (endTime - startTime) + "ms,响应结果：" + JSON.toJSONString(result));
        return result;
    }

    /**
     * 获取GET请求参数
     *
     * @param proceedingJoinPoint
     * @return
     */
    private String getRequestParam(ProceedingJoinPoint proceedingJoinPoint) {
        StringBuilder requestParams = new StringBuilder();
        // 参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            // 如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                // 获取文件名
                value = file.getOriginalFilename();
            }
            requestParams.append(paramNames[i]).append(":").append(value).append(" ");
        }

        return requestParams.toString();
    }

    /**
     * 获取POST请求参数
     *
     * @param proceedingJoinPoint
     * @return
     */
    private Map<String, Object> getRequestParams(ProceedingJoinPoint proceedingJoinPoint) {
        Map<String, Object> requestParams = new HashMap<>();
        // 参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            // 如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                // 获取文件名
                value = file.getOriginalFilename();
            }
            requestParams.put(paramNames[i], value);
        }

        return requestParams;
    }


    @AfterThrowing(value = "execution(public * net.dely.shop.controller.*.*(..))", throwing = "e")
    public void getErr(JoinPoint joinPoint, Exception e){
        String errorMsg = StringUtils.substring(e.toString(), 0, 2000);
        log.error("执行" + joinPoint.getSignature().getDeclaringTypeName()+"."+ joinPoint.getSignature().getName()+"()" +"异常信息:"+errorMsg);
    }
}
