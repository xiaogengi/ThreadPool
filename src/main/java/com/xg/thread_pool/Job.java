package com.xg.thread_pool;

/**
 * @program: thread_pool
 * @description: 线程类
 * @author: gzk
 * @create: 2019-11-19 15:28
 **/

public class Job implements Runnable {
    @Override
    public void run() {
        System.out.println("当前线程名称:"+Thread.currentThread().getName()+";"+"job被指执行了 +time: "+System.currentTimeMillis());
    }
}
