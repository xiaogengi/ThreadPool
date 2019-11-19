package com.xg.thread_pool;

/**
 * @program: thread_pool
 * @description: 线程池测试
 * @author: gzk
 * @create: 2019-11-19 16:02
 **/

public class ThreadPoolTest {

    public static void main(String[] args) {
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool(10);
        for (int i = 0; i < 5; i++) {
            Job job = new Job();
            defaultThreadPool.execute(job);
        }
    }


}
