package com.springbootIntegration.demo.test.concurrent;

import com.alibaba.druid.support.json.JSONUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author liukun
 * @description
 * @since 2020/7/25
 */
public class CompletableFutureTest {

    /**
     * 最原始的创建异步操作，并且能解决并发线程中出现的异常
     *
     * @return
     */
    public Future<Double> getPrice() {
        CompletableFuture completableFuture = new CompletableFuture();

        // 调用另一个线程执行计算任务
        new Thread(() -> {
            try {

                // 模拟长时间的计算任务
                Thread.sleep(5000);
                // 假设发生异常
                throw new Exception("获取价格时发生异常");
            } catch (Exception e) {
                e.printStackTrace();
                // 将线程内发生的异常抛出
                completableFuture.completeExceptionally(e);
            }
            // 计算结束后，结束completableFuture对象的运行，并设置返回的变量值。
            completableFuture.complete(1.0);
        }).start();

        return completableFuture;
    }

    /**
     * 对上述的步骤使用简化版本
     *
     * @return
     */
    public Future<Double> getPrice2() {
        return CompletableFuture.supplyAsync(() -> calculatePrice());
    }

    /**
     * 并发获取多个商家的价格，每个商家模拟都会有延迟，
     *
     * @return
     */
    public List<String> getPrice3() {
        List<String> shops = Arrays.asList("aa", "bb", "cc");
        // 并发获取每个商品价格
        List<CompletableFuture<String>> shopPrice = shops.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(
                                () -> shop + "，价格：" + calculatePrice()))
                .collect(Collectors.toList());

        return shopPrice.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 测试并行
     *
     * @return
     */
    public List<String> getPrice4() {
        List<String> shops = Arrays.asList("aa", "bb", "cc", "aa", "bb", "cc", "aa", "bb", "cc");
        return shops.parallelStream()
                .map(shop -> shop + "，价格：" + calculatePrice())
                .collect(Collectors.toList());
    }

    /**
     * 对getPrice3()方法的优化
     * <p>
     * 添加定制执行器
     *
     * @return
     */
    public List<String> getPrice5() {
        List<String> shops = Arrays.asList("aa", "bb", "cc", "aa", "bb", "cc", "aa", "bb", "cc");
        // 创建一个右守护线程构成的执行器
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });

        // 并发获取每个商品价格
        List<CompletableFuture<String>> shopPrice = shops.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(
                                () -> shop + "，价格：" + calculatePrice(), executorService))
                .collect(Collectors.toList());

        return shopPrice.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    /**
     * 异步流水线
     *
     * @return
     */
    public List<String> getPrice6() {
        List<String> shops = Arrays.asList("aa", "bb", "cc", "aa", "bb", "cc", "aa", "bb", "cc");
        // 创建一个右守护线程构成的执行器
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    }
                });

        // 示例，将两个相互依赖的异步任务串联起来
        List<CompletableFuture> collect = shops.stream()
                // 异步方式获取每个商品的原始价格
                .map(shop -> CompletableFuture.supplyAsync(() -> calculatePrice(), executorService))
                // 对第一步生成的CompletableFuture对象调用直接采取同步操作
                .map(future -> future.thenApply(this::parse))
                // 使用另一个异步任务，计算在获取原始价格后异步计算各自商品的折后价格
                // 将两个异步操作级联起来串联工作，使用thenCompose
                .map(future -> ((CompletableFuture) future).thenCompose(quote ->
                        CompletableFuture.supplyAsync(() -> discount())))
                .collect(Collectors.toList());

        // 示例，将两个不相关的异步任务整合起来
        shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> calculatePrice(), executorService)
                        .thenCombine(CompletableFuture.supplyAsync(() -> discount(), executorService),
                                // 这里将两个不相关的任务的结果结合
                                (price, discount) -> price * discount))
                .collect(Collectors.toList());

        // 示例，对异步任务中每一个获取的价格结果立即进行输出操作（还可以进行其他操作，比如保存）
        // 再输出后，然后这里使用allOf是等待获取所有价格结果
        CompletableFuture[] completableFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> calculatePrice(), executorService))
                .map(f -> f.thenAccept(System.out::println))
                .toArray(size -> new CompletableFuture[size]);
        // 上面会使用一个线程池异步执行，主线程还会直接往下执行，如果没有allOf这个程序，
        // 主线程会运行结束，异步返回。这里加上allOf后，会阻塞再这里,直到获取所有价格后再返回
        CompletableFuture.allOf(completableFutures).join();

        // 等待流中的所有future都执行完毕，提取各自的返回值
//        return collect.stream()
//                .map(CompletableFuture::join)
//                .collect(this::toList);
        return null;
    }

    private Double discount() {
        return 1.0;
    }

    private List<String> toList() {
        return null;
    }

    private Object parse(Double s) {
        System.out.println("解析");
        return null;
    }

    // 模拟长时间的计算任务
    private Double calculatePrice() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 1.0;
    }

    public static class Quote {
        public static Quote parse(String s) {
            return new Quote();
        }
    }

    /**
     * 基本异步操作使用
     */
    public static void test1() {
        long start = System.currentTimeMillis();
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        Future<Double> price = completableFutureTest.getPrice2();


        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("可以使用的处理器：" + i);
        System.out.println("做其他事情1...");
        System.out.println("做其他事情2...");

        try {
            // 这里阻塞获取值
            Double aDouble = price.get();

            System.out.println("获取价格：" + aDouble);
            // 如果价格未知，则会发生异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            // 这个异常时线程运行时发生的异常
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("消耗时间：" + (end - start));
    }

    /**
     * 测试并发
     */
    public static void test2() {
        long start = System.currentTimeMillis();
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        // 这里没有阻塞，说明是异步的
        List<String> price3 = completableFutureTest.getPrice3();


        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("可以使用的处理器：" + i);
        System.out.println("做其他事情1...");
        System.out.println("做其他事情2...");

        price3.stream().forEach(price -> {
            System.out.println(price);
        });

        long end = System.currentTimeMillis();

        System.out.println("消耗时间：" + (end - start));
    }

    /**
     * 测试并行
     */
    public static void test3() {
        long start = System.currentTimeMillis();
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        // 这里没有阻塞，说明是异步的
        List<String> price3 = completableFutureTest.getPrice4();


        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("可以使用的处理器：" + i);
        System.out.println("做其他事情1...");
        System.out.println("做其他事情2...");

        price3.stream().forEach(System.out::println);

        long end = System.currentTimeMillis();

        System.out.println("消耗时间：" + (end - start));
    }

    /**
     * 测试带有执行器的并发
     */
    public static void test4() {
        long start = System.currentTimeMillis();
        CompletableFutureTest completableFutureTest = new CompletableFutureTest();
        // 这里没有阻塞，说明是异步的
        List<String> price3 = completableFutureTest.getPrice5();


        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("可以使用的处理器：" + i);
        System.out.println("做其他事情1...");
        System.out.println("做其他事情2...");

        price3.stream().forEach(price -> {
            System.out.println(price);
        });

        long end = System.currentTimeMillis();

        System.out.println("消耗时间：" + (end - start));
    }

    public static void main(String[] args) {
//        test1();
        System.out.println("======================");
//        test2();
        System.out.println("======================");
//        test3();

        System.out.println("======================");
        test4();
    }
}
