package com.springbootIntegration.demo.util;

/**
 * @author liukun
 * @description 线程工具类
 * @date 2019/9/14
 */
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ThreadUtil {
    static Logger logger = LogManager.getLogger();
    static final Map<String, ExecutorService> EXECUTORS = InstanceUtil.newConcurrentHashMap();
    static final Map<String, List<Future<?>>> FUTURES = InstanceUtil.newConcurrentHashMap();
    static final Map<String, Lock> LOCKS = InstanceUtil.newConcurrentHashMap();

    public ThreadUtil() {
    }

    public static void sleep(int start, int end) {
        try {
            Thread.sleep(MathUtil.getRandom((double)start, (double)end).longValue());
        } catch (InterruptedException var3) {
            logger.error(ExceptionUtil.getStackTraceAsString(var3));
        }

    }

    public static void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException var3) {
            if (logger.isDebugEnabled()) {
                logger.debug(var3.getMessage());
            }
        }

    }

    public static ExecutorService threadPool(String threadName, int core, int seconds) {
        logger.info("Freed threadPoolExecutor " + threadName);
        return new ThreadPoolExecutor(core, core, (long)seconds, TimeUnit.SECONDS, new LinkedBlockingQueue(), (new ThreadFactoryBuilder()).setNameFormat(threadName + "-%d").build());
    }

    public static void execute(String threadName, Runnable runnable) {
        execute(threadName, 5, 60, runnable);
    }

    public static void execute(String threadName, int core, int seconds, Runnable runnable) {
        if (!LOCKS.containsKey(threadName)) {
            LOCKS.put(threadName, new ReentrantLock(true));
        }

        ((Lock)LOCKS.get(threadName)).lock();

        try {
            boolean first = threadPool(threadName, core, seconds) == null;
            if (first) {
                EXECUTORS.putIfAbsent(threadName, threadPool(threadName, core, seconds));
                if (FUTURES.get(threadName) == null) {
                    FUTURES.putIfAbsent(threadName, InstanceUtil.newArrayList());
                } else {
                    ((List)FUTURES.get(threadName)).clear();
                }
            }

            ExecutorService executorService = (ExecutorService)EXECUTORS.get(threadName);
            Future<?> future = executorService.submit(runnable);
            ((List)FUTURES.get(threadName)).add(future);
            if (first) {
                shutdown(threadName);
            }
        } finally {
            ((Lock)LOCKS.get(threadName)).unlock();
        }

    }

    private static void shutdown(final String threadName) {
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (((Lock)ThreadUtil.LOCKS.get(threadName)).tryLock()) {
                    try {
                        boolean done = true;
                        Iterator var2 = ((List)ThreadUtil.FUTURES.get(threadName)).iterator();

                        while(var2.hasNext()) {
                            Future<?> future = (Future)var2.next();
                            if (!future.isDone()) {
                                done = false;
                            }
                        }

                        if (done) {
                            ((ExecutorService)ThreadUtil.EXECUTORS.get(threadName)).shutdown();
                            ThreadUtil.EXECUTORS.remove(threadName);
                            ((List)ThreadUtil.FUTURES.get(threadName)).clear();
                            timer.cancel();
                            ThreadUtil.logger.info("Freed threadPoolExecutor " + threadName);
                        }
                    } finally {
                        ((Lock)ThreadUtil.LOCKS.get(threadName)).unlock();
                    }
                }

            }
        }, 60000L, 180000L);
    }
}

