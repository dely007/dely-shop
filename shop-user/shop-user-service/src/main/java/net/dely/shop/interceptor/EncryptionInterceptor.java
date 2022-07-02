package net.dely.shop.interceptor;

/**
 * @author: dely
 * Date: 2022/6/28
 * Time: 19:40
 */

import lombok.extern.slf4j.Slf4j;
import net.dely.shop.annotation.Encrypt;
import net.dely.shop.storage.mysql.entity.Encrypted;
import net.dely.shop.util.AesUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
@Intercepts({
        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})
})
public class EncryptionInterceptor implements Interceptor {

    public EncryptionInterceptor() {

    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        SqlCommandType sqlCommandType = null;

        for (Object object : args) {
            // 从MappedStatement参数中获取到操作类型
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                sqlCommandType = ms.getSqlCommandType();
                log.debug("Encryption interceptor 操作类型： {}", sqlCommandType);
                continue;
            }
            log.debug("Encryption interceptor 操作参数：{}",object);

            System.out.println(object.getClass().toString());
            // 判断参数
            if (object instanceof Encrypted) {
                System.out.println(object.getClass().toString());
                if (SqlCommandType.INSERT == sqlCommandType) {
                    encryptField((Encrypted)object);
                    continue;
                }
                if (SqlCommandType.UPDATE == sqlCommandType) {
                    encryptField((Encrypted)object);
                    log.debug("Encryption interceptor update operation,encrypt field: {}",object.toString());
                }
            }else {
                if (SqlCommandType.UPDATE == sqlCommandType) {

                    if (object instanceof MapperMethod.ParamMap) {
                        List<Object> list = new ArrayList<>();
                        MapperMethod.ParamMap map = (MapperMethod.ParamMap) object;
                        for (int i = 0; i < map.size(); i++) {
                            String key = "param" + String.valueOf(i + 1);
                            String et = "et";
                            if (map.containsKey(key)) {
                                list.add(map.get(key));
                            }
                            if (map.containsKey(et)) {
                                list.add(map.get(et));
                            }
                        }

                        for (Object o : list) {
                            if (o instanceof Encrypted) {
                                if (SqlCommandType.UPDATE == sqlCommandType) {
                                    encryptField((Encrypted)o);
                                    map.put("param1",o);
                                    map.put("et",o);
                                    System.out.println(map);
                                    log.info("Encryption interceptor update operation,encrypt field: {}",object.toString());
                                }
                            }
                        }
                    }
//                    encryptField((Encrypted)object);
                    log.debug("Encryption interceptor update operation,encrypt field: {}",object.toString());
                }
            }
        }
        return invocation.proceed();
    }


    /**
     * @param object 待检查的对象
     * @throws IllegalAccessException
     * 通过查询注解@Encrypt或者Encrypted返回的字段,进行动态加密
     * 两种方式互斥
     */
    private void encryptField(Encrypted object) throws IllegalAccessException, NoSuchFieldException {
        String[] encryptFields = object.getEncryptFields();
        String factor = "xu.dm118dAADF!@$";
        Class<?> clazz = object.getClass();

        if(encryptFields.length==0){
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Encrypt encrypt = field.getAnnotation(Encrypt.class);
                if(encrypt!=null) {
                    String encryptString = AesUtils.encrypt(field.get(object).toString(), factor);
                    field.set(object,encryptString);
                    log.debug("Encryption interceptor，encrypt field: {}",field.getName());
                }
            }
        }else {
            for (String fieldName : encryptFields) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String encryptString = AesUtils.encrypt(field.get(object).toString(), factor);
                field.set(object,encryptString);
                log.debug("Encryption interceptor，encrypt field: {}",field.getName());
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
