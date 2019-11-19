package com.xg.thread_pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: thread_pool
 * @description: 线程池默认实现类
 * @author: gzk
 * @create: 2019-11-19 15:33
 **/

public class DefaultThreadPool <Job extends Runnable>  {

    //线程最大上限
    private static final int MAX_THREAD_SIZE = 30;

    //线程任务列表
    private final LinkedList<Job> jobs = new LinkedList();

    //线程列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());


    //正在执行的线程数量
    private int workerNum;


    // 执行线程的编号
    private AtomicLong threadNum = new AtomicLong();


    public DefaultThreadPool(int workerNum){
        if(workerNum > MAX_THREAD_SIZE){
            this.workerNum = workerNum;
        }else{
            this.workerNum = MAX_THREAD_SIZE;
        }

        //初始化需要创建的线程
        initializeWorkers(workerNum);
    }

//  初始化需要创建的线程
    private void initializeWorkers(int workerNum) {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker();
            // 添加到自己的线程池里
            workers.add(worker);
            //启动这个线程
            Thread thread = new Thread(worker);
            thread.start();

        }

    }


//    @Override
    public void execute(Job job) {
        if(job == null){
            throw new NullPointerException("job is null");
        }
        if(job != null){
            synchronized (jobs){
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }


    class Worker implements Runnable{

        // 表示当前线程是否执行
        private volatile boolean runStatus = true;

        @Override
        public void run() {
            while (runStatus){
                Job job = null;
                synchronized (jobs){
                    if(jobs.isEmpty()){
                        try {
                            //线程等待唤醒
                            jobs.wait();
                        } catch (Exception e){
                            e.printStackTrace();
                            // 中断该线程
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if(job != null){
                    job.run();
                }
            }
        }

        // 结束该线程
        public void stop(){
            runStatus = false;
        }
    }



}
