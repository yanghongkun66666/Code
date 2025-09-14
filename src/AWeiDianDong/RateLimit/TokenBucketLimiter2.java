//package AWeiDianDong.RateLimit;
//import com.google.common.util.concurrent.RateLimiter;
//public class TokenBucketLimit2 {
//
//    public static void main(String[] args) throws InterruptedException {
//
//        //允许10个，permitsPerSecond
//
//        RateLimiter limiter = RateLimiter.create(100);
//
//        for(int i=1;i<200;i++){
//
//            if (limiter.tryAcquire(1)){
//
//                System.out.println("第"+i+"次请求成功");
//
//            }else{
//
//                System.out.println("第"+i+"次请求拒绝");
//
//            }
//
//        }
//
//    }
//
//}