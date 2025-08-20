package ConcurrencyProgramming.T13SingleTon;

import java.security.spec.RSAOtherPrimeInfo;

public class SingleTonLazy {

    //volatile 关键字修饰变量，防止指令重排序
    private static volatile SingleTonLazy instance = null;

    private SingleTonLazy() {}

    /**
     * 1. 适用于单线程环境
     */
    public static SingleTonLazy getInstanceA() {
        if (null == instance) {
            instance = new SingleTonLazy();
        }
        return instance;
    }

    /**
     * 2. 适用于多线程环境，但是效率不高
     */
    public static synchronized SingleTonLazy getInstanceB() {
        if (null == instance) {
            instance = new SingleTonLazy();
        }
        return instance;
    }

    /**
     * 3. 双重监察所
     * @return
     */
    public static SingleTonLazy getInstanceC() {
        //先判断实例是否存在，不存在再对类对象进行加锁处理
        if (instance == null) {
            synchronized (SingleTonLazy.class) {
                if (instance == null) {
                    instance = new SingleTonLazy();
                }
            }
        }
        return instance;
    }
}
