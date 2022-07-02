package net.dely.shop.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.dely.shop.annotation.Encrypt;
import net.dely.shop.util.AesUtils;
import net.dely.shop.storage.mysql.entity.Encrypted;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author: dely
 * Date: 2022/6/28
 * Time: 19:42
 * 解密数据，返回结果为list集合时，应保证集合里都是同一类型的元素。
 * 解密失败时返回为null，或者返回为空串时，不对原数据操作。
 **/
@Component
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class DecryptionInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if(result instanceof ArrayList) {
            @SuppressWarnings("rawtypes")
            ArrayList list = (ArrayList) result;
            if(list.size() == 0) {
                return result;
            }
            if(list.get(0) instanceof Encrypted) {
                for (Object item : list) {
                    decryptField((Encrypted) item);
                }
            }
            return result;
        }
        if(result instanceof Encrypted) {
            decryptField((Encrypted) result);
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * @param object 待检查的对象
     * @throws IllegalAccessException
     * 通过查询注解@Encrypt或者Encrypted返回的字段,进行解密
     * 两种方式互斥
     */
    private void decryptField(Encrypted object) throws IllegalAccessException, NoSuchFieldException {
        String[] encryptFields = object.getEncryptFields();
        String factor = "xu.dm118dAADF!@$";
        Class<?> clazz = object.getClass();

        if(encryptFields.length==0){
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Encrypt encrypt = field.getAnnotation(Encrypt.class);
                if(encrypt!=null) {
                    String encryptString = AesUtils.decrypt(field.get(object).toString(), factor);
                    if(encryptString!=null){
                        field.set(object,encryptString);
                        log.debug("Encryption interceptor，encrypt field: {}",field.getName());
                    }
                }
            }
        }else {
            for (String fieldName : encryptFields) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String encryptString = AesUtils.decrypt(field.get(object).toString(), factor);
                if(encryptString!=null && encryptString.length() > 0){
                    field.set(object,encryptString);
                    log.debug("Encryption interceptor，encrypt field: {}",field.getName());
                }
            }
        }
    }
}
