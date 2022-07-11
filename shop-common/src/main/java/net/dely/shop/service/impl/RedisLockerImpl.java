package net.dely.shop.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.dely.shop.service.Locker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author: dely
 * Date: 2022/7/5
 * Time: 22:05
 */
@Slf4j
@Component
public class RedisLockerImpl implements Locker {

    private final int timeout = 30;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 一直等待直到获取锁，无返回值
     * 默认使用的租期为30秒
     * @param name key
     * @param runnable 业务
     */
    @Override
    public void lock(String name, Runnable runnable) {
        RLock lock = redissonClient.getLock(name);
        try {
            if (lock.tryLock(timeout, TimeUnit.SECONDS)) {
                log.debug("get lock, name={}", name);
                try {
                    runnable.run();
                } finally {
                    log.debug("release lock, name={}", name);
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error("RedisLockerImpl.lock() 异常信息",e);
            throw new RuntimeException("服务器异常，请稍后再试");
        }
    }

    /**
     * 一直等待直到获取锁，有返回值-
     * 默认使用的租期为30秒
     * @param name key
     * @param supplier 业务
     * @param <T> 泛型
     * @return 返回值
     */
    @Override
    public <T> T lock(String name, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(name);
        try {
            if (lock.tryLock(timeout,TimeUnit.SECONDS)){
                log.debug("get lock, name={}", name);
                try {
                    return supplier.get();
                } finally {
                    log.debug("release lock, name={}", name);
                    lock.unlock();
                }
            } else {
                log.error("RedisLockerImpl.lock() 获取锁失败");
                throw new RuntimeException("网络异常，请稍后再试");
            }
        } catch (InterruptedException e) {
            log.error("RedisLockerImpl.lock() 异常信息",e);
            throw new RuntimeException("服务器异常，请稍后再试");
        }
    }

    /**
     * 一直等待直到获取锁，无返回值
     * 默认使用的租期为30秒
     * 获取锁之后，默认30秒后自动释放锁
     * @param name key
     * @param runnable 业务
     */
    @Override
    public void lockRelease(String name, Runnable runnable) {
        RLock lock = redissonClient.getLock(name);
        try {
            if (lock.tryLock(timeout, timeout, TimeUnit.SECONDS)) {
                log.debug("get lock, name={}", name);
                try {
                    runnable.run();
                } finally {
                    log.debug("release lock, name={}", name);
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error("RedisLockerImpl.lockRelease() 异常信息",e);
            throw new RuntimeException("服务器异常，请稍后再试");
        }
    }

    /**
     * 一直等待直到获取锁，有返回值-
     * 默认使用的租期为30秒
     * 获取锁之后，默认30秒后自动释放锁
     * @param name key
     * @param supplier 业务
     * @param <T> 泛型
     * @return 返回值
     */
    @Override
    public <T> T lockRelease(String name, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(name);
        try {
            if (lock.tryLock(timeout,timeout,TimeUnit.SECONDS)){
                log.debug("get lock, name={}", name);
                try {
                    T t = supplier.get();
                    return t;
                } finally {
                    log.debug("release lock, name={}", name);
                    lock.unlock();
                }
            } else {
                log.error("RedisLockerImpl.lockRelease() 获取锁失败");
                throw new RuntimeException("网络异常，请稍后再试");
            }
        } catch (InterruptedException e) {
            log.error("RedisLockerImpl.lockRelease() 异常信息",e);
            throw new RuntimeException("服务器异常，请稍后再试");
        }
    }
}
