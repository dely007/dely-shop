package net.dely.shop.util;

import org.slf4j.MDC;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.*;
/**
 * 线程池包装类
 */
public class ThreadPoolExecutorMdcUtil extends ThreadPoolExecutor {
    public ThreadPoolExecutorMdcUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorMdcUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorMdcUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorMdcUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                        RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
