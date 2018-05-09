package com.ehang.commonutils.io;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义单线程的线程池,调用shutdownNow会直接中断线程，注意捕获{@link InterruptedException}
 */
public class SingleThreadExecutor {
    private ThreadPoolExecutor threadPoolExecutor;

    public SingleThreadExecutor() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.SECONDS,
                queue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 立即执行Runnable，可调用{@link #shutdownNow()}强制中断线程，此时注意捕获{@link InterruptedException}
     */
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 立即中断正在执行的线程，注意捕获{@link InterruptedException}
     */
    public void shutdownNow() {
        threadPoolExecutor.shutdownNow();
    }
}
