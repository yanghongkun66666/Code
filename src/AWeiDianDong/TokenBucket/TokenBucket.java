package AWeiDianDong.TokenBucket;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucket {
    private final double capacity;      // 桶的最大容量
    private final double refillRate;    // 每秒填充的令牌数
    private double tokens;              // 当前令牌数
    private long lastRefillTime;        // 上次填充时间(毫秒)

    private final ReentrantLock lock = new ReentrantLock();

    // 构造函数
    public TokenBucket(double capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;  // 初始化时桶是满的
        this.lastRefillTime = System.currentTimeMillis();
    }

    // 令牌补充逻辑
    private void refill() {
        long now = System.currentTimeMillis();
        double elapsedSeconds = (now - lastRefillTime) / 1000.0;

        // 根据经过的时间补充令牌数
        double tokensToAdd = elapsedSeconds * refillRate;
        tokens = Math.min(capacity, tokens + tokensToAdd);

        lastRefillTime = now;  // 更新最后的填充时间
    }

    // 尝试消耗指定数量的令牌
    public boolean tryConsume(int tokensNeeded) {
        lock.lock();
        try {
            refill();
            if (tokens >= tokensNeeded) {
                tokens -= tokensNeeded;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // 获取当前桶中令牌数
    public double getTokens() {
        lock.lock();
        try {
            return tokens;
        } finally {
            lock.unlock();
        }
    }

    // 测试代码
    public static void main(String[] args) throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(10, 1); // 容量10，速率1 token/s

        for (int i = 0; i < 15; i++) {
            int tokensToConsume = (i % 3 == 0) ? 3 : 1;
            if (tokenBucket.tryConsume(tokensToConsume)) {
                System.out.println("Consumed " + tokensToConsume + " tokens");
            } else {
                System.out.println("Failed to consume " + tokensToConsume + " tokens");
            }
            Thread.sleep(1000); // 模拟等待 1 秒
        }
    }
}
