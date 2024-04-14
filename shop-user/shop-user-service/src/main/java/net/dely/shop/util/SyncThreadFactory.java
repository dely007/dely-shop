package net.dely.shop.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncThreadFactory implements ThreadFactory {

    private AtomicInteger threadNumber = new AtomicInteger(1);

    private String prefix;

    public SyncThreadFactory(String prefix){
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        int index = threadNumber.getAndIncrement();
        StringBuilder prefixBuilder = new StringBuilder();
        String namePrefix = prefixBuilder.append(prefix).append("-").append(index).toString();
        Thread t = new Thread(r, namePrefix);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
