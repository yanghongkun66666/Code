public class ProducerConsumerWithSync {
    private static final int BUFFER_SIZE = 10;
    private static final Object lock = new Object();

    private static final Integer[] buffer = new Integer[BUFFER_SIZE];
    private static int count = 0, in = 0, out = 0;

    // 毒丸
    private static final int POISON = Integer.MIN_VALUE;

    static class Producer extends Thread {
        @Override public void run() {
            try {
                for (int num = 0; num < 100; ++num) {
                    synchronized (lock) {
                        while (count == BUFFER_SIZE) {
                            System.out.println("队列已满，生产者等待=======");
                            lock.wait();
                        }
                        buffer[in] = num;
                        in = (in + 1) % BUFFER_SIZE;
                        count++;
                        System.out.println("生产数据-" + num + " | 缓冲区元素数量-" + count);
                        lock.notifyAll(); // 持锁内调用
                    }
                }

                // 结束：安全投递毒丸（也要遵守容量约束&持锁）
                synchronized (lock) {
                    while (count == BUFFER_SIZE) {
                        lock.wait();
                    }
                    buffer[in] = POISON;
                    in = (in + 1) % BUFFER_SIZE;
                    count++;
                    lock.notifyAll(); // 唤醒消费者去取毒丸
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer extends Thread {
        @Override public void run() {
            try {
                while (true) {
                    int data;
                    synchronized (lock) {
                        while (count == 0) {
                            System.out.println("队列为空，消费者等待====");
                            lock.wait();
                        }
                        data = buffer[out];
                        buffer[out] = null;                // 防止对象泄漏
                        out = (out + 1) % BUFFER_SIZE;
                        count--;                           // 先更新计数
                        lock.notifyAll();                  // 再通知生产者
                    }

                    if (data == POISON) {
                        System.out.println("收到毒丸，退出");
                        break;                             // 正常结束
                    } else {
                        System.out.println("消费数据-" + data + " | 缓冲区元素数量-" + count);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread p = new Producer();
        Thread c = new Consumer();
        p.start(); c.start();
        p.join();  c.join(); // 等待两个线程结束，优雅退出
    }
}
