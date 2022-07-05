package net.dely.shop.util;

import net.dely.shop.service.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 分布式锁
 * @author: dely
 * Date: 2022/7/5
 * Time: 22:44
 */
@Component
public class LockUtil {

    private static Locker locker;

    @Autowired
    public void setLocker(Locker locker) {
        LockUtil.locker = locker;
    }

    /**
     * 普通加锁，无返回值
     * @param name key
     * @param runnable 业务
     */
    public static void lock(String name, Runnable runnable){
        locker.lock(name,runnable);
    }

    /**
     * 普通加锁，有返回值
     * @param name key
     * @param supplier 业务
     * @param <T> 泛型
     * @return 返回值
     */
    public static <T> T lock(String name, Supplier<T> supplier){
        return locker.lock(name,supplier);
    }
}
