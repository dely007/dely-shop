package net.dely.shop.util;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Component
public class ThreadPoolComponent {

    private ThreadPoolExecutor threadPoolExecutor;

    private final static int THREAD_NUM = 2;

    @PostConstruct
    void init() {
        threadPoolExecutor = new ThreadPoolExecutor(THREAD_NUM,
                THREAD_NUM * 1,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(THREAD_NUM *1000),
                new SyncThreadFactory("USER-SAVE-"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return threadPoolExecutor.submit(task, result);
    }

    public void execute(Runnable command) {
        Runnable runnable = ThreadMdcUtil.wrap(command, MDC.getCopyOfContextMap());
        threadPoolExecutor.execute(runnable);
    }
}
