package net.dely.shop.service;

import java.util.function.Supplier;

/**
 * @author: dely
 * Date: 2022/7/5
 * Time: 22:05
 */
public interface Locker {

    /**
     * 一直等待直到获取锁，无返回值
     * 默认使用的租期为30秒
     * @param name key
     * @param runnable 业务
     */
    void lock(String name,Runnable runnable);

    /**
     * 一直等待直到获取锁，有返回值-
     * 默认使用的租期为30秒
     * @param name key
     * @param supplier 业务
     * @param <T> 泛型
     * @return 返回值
     */
    <T> T lock(String name, Supplier<T> supplier);

    /**
     * 一直等待直到获取锁，无返回值
     * 默认使用的租期为30秒
     * 获取锁之后，默认30秒后自动释放锁
     * @param name key
     * @param runnable 业务
     */
    void lockRelease(String name,Runnable runnable);

    /**
     * 一直等待直到获取锁，有返回值-
     * 默认使用的租期为30秒
     * 获取锁之后，默认30秒后自动释放锁
     * @param name key
     * @param supplier 业务
     * @param <T> 泛型
     * @return 返回值
     */
    <T> T lockRelease(String name, Supplier<T> supplier);
}
