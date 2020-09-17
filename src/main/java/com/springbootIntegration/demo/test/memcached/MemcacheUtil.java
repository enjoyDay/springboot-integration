package com.springbootIntegration.demo.test.memcached;

import net.spy.memcached.*;
import net.spy.memcached.internal.OperationFuture;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author liukun
 * @description
 * @since 2020/9/17
 */
public class MemcacheUtil {
    MemcachedClient memcachedClient = null;

    public static void main(String[] args) throws Exception {
        MemcacheUtil memcacheUtil = new MemcacheUtil();
        memcacheUtil.createClient("192.168.52.134", 11211);

//        memcacheUtil.set("memcached", 900, "123");
//        memcacheUtil.decr("memcached", 100);
        memcacheUtil.createClusterClient();

        memcacheUtil.closeClient();
    }

    /**
     * 保存或更新值
     *
     * @param key     key
     * @param exptime 期望保存时间
     * @param value   值
     */
    public void set(String key, int exptime, String value) throws ExecutionException, InterruptedException {
        // 存储数据
        OperationFuture<Boolean> set = memcachedClient.set(key, exptime, value);

        // 查看存储状态
        System.out.println("set status:" + set.get());

        // 输出值
        System.out.println(key + " value in cache - " + memcachedClient.get(key));
    }

    /**
     * 只保存不更新值
     *
     * @param key     key
     * @param exptime 保存时间
     * @param value   值
     */
    public void add(String key, int exptime, String value) throws ExecutionException, InterruptedException {
        // 存储数据
        OperationFuture<Boolean> add = memcachedClient.add(key, exptime, value);

        // 查看存储状态
        System.out.println("add status:" + add.get());

        // 输出值
        System.out.println(key + " value in cache - " + memcachedClient.get(key));
    }

    /**
     * 只更新不保存值
     *
     * @param key     key
     * @param exptime 保存时间
     * @param value   值
     */
    public void replace(String key, int exptime, String value) throws ExecutionException, InterruptedException {
        // 存储数据
        OperationFuture<Boolean> replace = memcachedClient.replace(key, exptime, value);

        // 查看存储状态
        System.out.println("replace status:" + replace.get());

        // 输出值
        System.out.println(key + " value in cache - " + memcachedClient.get(key));
    }

    /**
     * 在原值后面添加字符串
     *
     * @param key   key
     * @param value 值
     */
    public void append(String key, String value) throws ExecutionException, InterruptedException {
        // 存储数据
        CASValue<Object> gets = memcachedClient.gets(key);
        OperationFuture<Boolean> append = memcachedClient.append(gets.getCas(), key, value);

        // 查看存储状态
        System.out.println("append status:" + append.get());

        // 输出值
        System.out.println(key + " value in cache - " + memcachedClient.get(key));
    }

    /**
     * 在原值后面添加字符串
     *
     * @param key   key
     * @param value 值
     */
    public void prepend(String key, String value) throws ExecutionException, InterruptedException {
        // 存储数据
        CASValue<Object> gets = memcachedClient.gets(key);
        OperationFuture<Boolean> prepend = memcachedClient.prepend(gets.getCas(), key, value);

        // 查看存储状态
        System.out.println("prepend status:" + prepend.get());

        // 输出值
        System.out.println(key + " value in cache - " + memcachedClient.get(key));
    }

    /**
     * 线程安全写入数据，如果在最后一次取值后另外一个用户也在更新该数据，则不写入
     *
     * @param key
     * @param value
     */
    public void cas(String key, String value) {
        // 存储数据
        CASValue<Object> gets = memcachedClient.gets(key);
        CASResponse cas = memcachedClient.cas(key, gets.getCas(), value);

        // 输出 CAS 响应信息
        System.out.println("CAS Response - " + cas);

        // 输出值
        System.out.println(key + " value in cache - " + memcachedClient.get(key));
    }

    /**
     * 根据key删除
     *
     * @param key key
     * @return 是否删除成功
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Boolean delete(String key) throws ExecutionException, InterruptedException {
        OperationFuture<Boolean> delete = memcachedClient.delete(key);
        Boolean status = delete.get();
        return status;
    }

    /**
     * 根据key获取值
     *
     * @param key 关键字
     * @return key对应的value
     */
    public Object get(String key) {
        return memcachedClient.get(key);
    }

    /**
     * 自增
     *
     * @param key       key
     * @param increment 增加数
     * @throws Exception
     */
    public void incr(String key, int increment) throws Exception {
        if (increment < 0) {
            throw new Exception("value less than 0");
        }

        long incr = memcachedClient.incr(key, increment);
        // 自增并输出
        System.out.println("value in cache after increment - " + incr);

    }

    /**
     * 自减
     *
     * @param key       key
     * @param decrement 自减
     * @throws Exception
     */
    public void decr(String key, int decrement) throws Exception {
        if (decrement < 0) {
            throw new Exception("value less than 0");
        }

        long decr = memcachedClient.decr(key, decrement);
        // 自增并输出
        System.out.println("value in cache after decrement  - " + decr);

    }

////////////////////////////////////////////////////////////////////////////////////

    /**
     * 创建客户端
     *
     * @param ip   服务器IP
     * @param port 服务器端口
     */
    public void createClient(String ip, int port) {
        MemcachedClient mc = null;
        try {
            mc = new MemcachedClient(new InetSocketAddress(ip, port));
            System.out.println("Connection to server sucessful.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        memcachedClient = mc;
    }

    /**
     * 测试集群客户端
     */
    public void createClusterClient() throws IOException {
        // Get a memcached client connected to several servers

        // over the binary protocol

        MemcachedClient c = new MemcachedClient(new BinaryConnectionFactory(),

                AddrUtil.getAddresses("192.168.52.134:11211 192.168.52.134:11212"));

        // Try to get a value, for up to 5 seconds, and cancel if it
        // doesn't return
        c.set("memcached2", 900, "234");

        Object myObj = null;

        Future<Object> f = c.asyncGet("memcached2");

        try {

            myObj = f.get(5, TimeUnit.SECONDS);

            // throws expecting InterruptedException, ExecutionException

            // or TimeoutException

            System.out.println("memcached2 value is ->"+ myObj);

        } catch (Exception e) {
            // Since we don't need this, go ahead and cancel the operation.
            // This is not strictly necessary, but it'll save some work on
            // the server.  It is okay to cancel it if running.
            f.cancel(true);
            // Do other timeout related stuff
        }

        c.shutdown();
    }

    /**
     * 关闭连接
     */
    public void closeClient() {
        memcachedClient.shutdown();
    }
}
