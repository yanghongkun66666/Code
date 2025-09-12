package AWeiDianDong.RateLimit;

public class CounterSildeWindowLimiter {

    private int windowSize; //总窗口大小，以毫秒为单位，比如 1000 毫秒（1秒）。
    private int limit;//总窗口内的最大请求数。
    private int splitNum;//切分小窗口的数目大小  将总窗口切分为多少个小窗口。
    private int[] counters;//一个数组，用于存储每个小窗口内的请求计数。
    private int index;//当前小窗口计数器的索引  当前请求所属小窗口在 counters 数组中的索引。
    private long startTime;//窗口开始时间  总窗口的起始时间戳。

    private CounterSildeWindowLimiter(){}

    public CounterSildeWindowLimiter(int windowSize, int limit, int splitNum){
        this.limit = limit;
        this.windowSize = windowSize;
        this.splitNum = splitNum;
        counters = new int[splitNum];
        index = 0;
        startTime = System.currentTimeMillis();
    }

    //请求到达后先调用本方法，若返回true，则请求通过，否则限流
    public synchronized boolean tryAcquire(){
        long curTime = System.currentTimeMillis(); //获取当前时间。
        long windowsNum = Math.max(curTime - windowSize - startTime,0) / (windowSize / splitNum);//计算滑动小窗口的数量
        //计算从 startTime 到 curTime 这段时间里，有多少个完整的“小窗口”已经过去。
        slideWindow(windowsNum);//滑动窗口 调用 slideWindow 方法，根据上一步计算出的步数，滑动并清空过期的小窗口。
        int count = 0;
        for(int i = 0;i < splitNum;i ++){
            count += counters[i]; //遍历所有小窗口的计数器，计算当前总窗口内的请求总数。
        }
        if(count >= limit){
            return false;
        }else{
            counters[index] ++;
            return true;
        }
    }

    private synchronized void slideWindow(long windowsNum){
        if(windowsNum == 0)
            return;
        long slideNum = Math.min(windowsNum,splitNum);
        for(int i = 0;i < slideNum;i ++){
            index = (index + 1) % splitNum;
            counters[index] = 0;
        }
        startTime = startTime + windowsNum * (windowSize / splitNum);//更新滑动窗口时间
    }

    //测试
    public static void main(String[] args) throws InterruptedException {
        //每秒20个请求
        int limit = 20;
        CounterSildeWindowLimiter counterSildeWindowLimiter = new CounterSildeWindowLimiter(1000,limit,10);
        int count = 0;

        Thread.sleep(3000);
        //计数器滑动窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器滑动窗口算法测试开始");
        System.out.println("开始模拟100组间隔150ms的50次请求");
        int faliCount = 0;
        for(int j = 0;j < 100;j ++){
            count = 0;
            for(int i = 0;i < 50;i ++){
                if(counterSildeWindowLimiter.tryAcquire()){
                    count ++;
                }
            }
            Thread.sleep(150);
            //模拟50次请求，看多少能通过
            for(int i = 0;i < 50;i ++){
                if(counterSildeWindowLimiter.tryAcquire()){
                    count ++;
                }
            }
            if(count > limit){
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                faliCount ++;
            }
            Thread.sleep((int)(Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + faliCount);
        System.out.println("===========================================================================================");


        //计数器固定窗口算法模拟100组间隔30ms的50次请求
        System.out.println("计数器固定窗口算法测试开始");
        //模拟100组间隔30ms的50次请求
        CounterLimiter counterLimiter = new CounterLimiter(1000,limit);
        System.out.println("开始模拟100组间隔150ms的50次请求");
        faliCount = 0;
        for(int j = 0;j < 100;j ++){
            count = 0;
            for(int i = 0;i < 50;i ++){
                if(counterLimiter.tryAcquire()){
                    count ++;
                }
            }
            Thread.sleep(150);
            //模拟50次请求，看多少能通过
            for(int i = 0;i < 50;i ++){
                if(counterLimiter.tryAcquire()){
                    count ++;
                }
            }
            if(count > limit){
                System.out.println("时间窗口内放过的请求超过阈值，放过的请求数" + count + ",限流：" + limit);
                faliCount ++;
            }
            Thread.sleep((int)(Math.random() * 100));
        }
        System.out.println("计数器滑动窗口算法测试结束，100组间隔150ms的50次请求模拟完成，限流失败组数：" + faliCount);
    }
}

