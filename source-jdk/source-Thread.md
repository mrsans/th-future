## 多线程

### java线程组

java的线程组,创建线程组可以通过两种构造器进行创建

ThreadGroup group = new ThreadGroup(String name); 传递一个线程组的名称

ThreadGroup group = new ThreadGroup(ThreadGroup group, String name);  传递一个父的线程组

我们可以看到，如果不传递，那么就以当前线程的线程组为基础，如下例子，新的线程组默认所属为main线程组，main线程组的父线程组为system线程组

```java
@Slf4j
public class TestThread {
    @Test
    public void testThreadGroupConstruct() {
        ThreadGroup group = new ThreadGroup("thread-group-");
        log.info("{},{},{}", group.getParent(), group.getParent().getParent(), group.getParent().getParent().getParent());
    }
}
```

### java多线程的创建

在java中创建线程是调用 Thread中的start()方法，会开启一个线程

```java
 public synchronized void start() {
        // 当调用start()方法时，首先判断线程的状态是否为0，如果为0，那么抛出异常，也就是说，一个线程对象只能调用一次start()方法
        if (threadStatus != 0)
            throw new IllegalThreadStateException();
        // 添加到线程组中
        group.add(this);
        // 
        boolean started = false;
        try {
            // java的JNI方法。
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
            }
        }
    }

    private native void start0();
```

```java
    // 创建线程
    @Test
    public void testCreateThread() throws InterruptedException {
        new Thread(() -> log.info("创建的线程" + Thread.currentThread().getId())).start();
        TimeUnit.SECONDS.sleep(1);
    }
```

### thread中的方法
Thread#setDaemon(boolean on): 当前线程是否是守护线程，默认是false，为用户线程。

守护线程： 当此线程所在的用户线程结束时，那么此线程也会停止

Thread#isDaemon(): 判断当前线程是否是守护线程，true为是，false为不是

Thread#getId(): 获取当前线程的id号





















































