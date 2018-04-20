package com.ehang.commonutils.io;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : miaozh
 * @since : 2016/9/18  15:59
 */
public class ThreadHelper {
    private static ScheduledThreadPoolExecutor sScheduledThreadPoolExecutor;

    public static ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        if (sScheduledThreadPoolExecutor == null) {
            synchronized (ThreadHelper.class) {
                if (sScheduledThreadPoolExecutor == null) {
                    sScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10, r -> {
                        Thread thread = new Thread(r, "sdk Thread poll thread");
                        thread.setDaemon(true);
                        return thread;
                    });
                }
            }
        }
        return sScheduledThreadPoolExecutor;
    }

    /**
     * 添加一个调度任务，这里统一放在一个线程池中调用，需要停止该任务时，调用{@link ThreadHelper#removeScheduledThread(ScheduledFuture)}
     *
     * @param r        需执行的任务
     * @param delay    延时多久开始
     * @param period   执行周期
     * @param timeUnit delay和period的时间单位
     */
    public static ScheduledFuture scheduleWithFixedDelay(Runnable r, int delay, int period, TimeUnit timeUnit) {
        if (r == null) {
            return null;
        }
        return getScheduledThreadPoolExecutor().scheduleWithFixedDelay(r, delay, period, timeUnit);
    }

    /**
     * 添加一个调度任务，这里统一放在一个线程池中调用，需要停止该任务时，调用{@link ThreadHelper#removeScheduledThread(ScheduledFuture)}
     *
     * @param r        需执行的任务
     * @param delay    延时多久开始
     * @param timeUnit delay和period的时间单位
     */
    public static ScheduledFuture schedule(Runnable r, int delay, TimeUnit timeUnit) {
        if (r == null) {
            return null;
        }
        return getScheduledThreadPoolExecutor().schedule(r, delay, timeUnit);
    }

    /**
     * 移除调度任务
     */
    public static void removeScheduledThread(ScheduledFuture r) {
        if (r == null) {
            return;
        }
        r.cancel(false);
        r.cancel(true);
    }
}
