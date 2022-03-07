package org.future.source.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhpj
 * @date: 2022-03-07 9:51
 */
@Slf4j
public class TestThread {

    @Test
    public void testThreadGroupConstruct() {
        ThreadGroup group = new ThreadGroup("thread-group-");
        log.info("{},{},{}", group.getParent(), group.getParent().getParent(), group.getParent().getParent().getParent());
    }

    @Test
    public void testThreadGroupMethods() throws InterruptedException {
        ThreadGroup group = new ThreadGroup("thread-group-");
        ThreadGroup tempGroup = new ThreadGroup(group, "thread-group-");
        // 获取当前线程组的名称
        String name = group.getName();
        // 获取父线程组
        ThreadGroup parent = group.getParent();
        // 线程组的优先级
        int maxPriority = group.getMaxPriority();
        // 获取活跃的线程组数量,即：所属的子线程组
        int activeGroupCount = group.activeGroupCount();
        // 是否是守护线程组，false不是，true是
        boolean daemon = group.isDaemon();
        // 获取线程组中活跃的线程数量，死亡的不算
        int activeCount = group.activeCount();
        Thread.sleep(1000);
        // 将线程组中所有线程全部中断
         group.interrupt();
         // 列出线程组树，以及对应的线程
        group.list();
        Thread thread = new Thread(group, () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int i = 1 / 0;
        });
        group.uncaughtException(thread, new Exception());
        log.info("name:{},parent:{},maxPriority:{},activeGroupCount:{}", name, parent, maxPriority, activeGroupCount);
        log.info("daemon:{},activeCount:{},maxPriority:{},activeGroupCount:{}", daemon, activeCount, maxPriority, activeGroupCount);
    }

    @Test
    public void testThreadGroup() {
        ThreadGroup group = new ThreadGroup("thread-group-");
        Thread thread = new InnerThreadGroup(group, () -> {
            log.info("123");
        });
        log.info("线程组：{}, 线程id：{}, 线程：{}", thread.getThreadGroup(), thread.getId(), thread);

    }

    class InnerThreadGroup extends Thread {

        public InnerThreadGroup(ThreadGroup group, Runnable target) {
            super(group, target);
        }

        @Override
        public void run() {
            super.run();
        }

        @Override
        public String toString() {
            ThreadGroup group = getThreadGroup();
            if (group != null) {
                return "Thread[" + getName() + "," + getPriority() + "," +
                        group.getName() + getId() + "]";
            } else {
                return "Thread[" + getName() + "," + getPriority() + "," +
                        "" + "]";
            }
        }
    }



    @Test
    public void testThreadMethods() throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.info("创建的线程:" + Thread.currentThread().getId());
        });
        thread.setDaemon(true);
        log.info(thread.getName() + "开始执行," + (thread.isDaemon() ? "我是守护线程" : "我是用户线程"));
        // 标记线程是否是守护线程，当用户线程结束时，守护线程也会被中断,默认是用户进程
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        log.info("aaa");
    }


    // 创建线程
    @Test
    public void testCreateThread() throws InterruptedException {
        new Thread(() -> log.info("创建的线程" + Thread.currentThread().getId())).start();
        TimeUnit.SECONDS.sleep(1);
    }



}
