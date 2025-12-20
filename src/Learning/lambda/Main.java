package Learning.lambda;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {

    // ====== 领域对象 ======
    static class User {
        final int id;
        final String name;
        final String dept;
        final int age;
        final boolean active;

        User(int id, String name, String dept, int age, boolean active) {
            this.id = id;
            this.name = name;
            this.dept = dept;
            this.age = age;
            this.active = active;
        }

        int getId() { return id; }
        String getName() { return name; }
        String getDept() { return dept; }
        int getAge() { return age; }
        boolean isActive() { return active; }

        @Override public String toString() {
            return "User{" + id + "," + name + "," + dept + ",age=" + age + ",active=" + active + "}";
        }
    }

    static List<User> demoUsers() {
        return Arrays.asList(
                new User(1, "Alice", "RD", 24, true),
                new User(2, "Cathy", "HR", 31, false),
                new User(3, "Bob", "RD", 17, true),
                new User(4, "David", "HR", 28, true)
        );
    }

    public static void main(String[] args) throws Exception {
        scenario1_streamSortFilterMapGroup();
        scenario2_strategyAsParam();
        scenario3_eventCallbackLike();
        scenario4_asyncCompletableFuture();
        scenario5_templateWrapper();
        scenario6_optionalNullChain();
        scenario7_mapComputeIfAbsent();
        scenario8_predicateCompose();
    }

    // 1) 集合处理：排序/过滤/映射/分组（Stream + Lambda）
    static void scenario1_streamSortFilterMapGroup() {
        System.out.println("\n--- 1) Stream: sort/filter/map/group ---");
        List<User> users = demoUsers();

        List<String> activeNamesSortedByAge = users.stream()
                .filter(User::isActive)                 // Lambda / 方法引用
                .sorted(Comparator.comparingInt(User::getAge))
                .map(User::getName)
                .collect(Collectors.toList());

        Map<String, List<User>> byDept = users.stream()
                .collect(Collectors.groupingBy(User::getDept));

        System.out.println("active names sorted by age = " + activeNamesSortedByAge);
        System.out.println("group by dept = " + byDept);
    }

    // 2) 策略/回调：把“行为”当参数传（替代 if-else 大分支/匿名类）
    static void scenario2_strategyAsParam() {
        System.out.println("\n--- 2) Strategy (Function) ---");
        int amount = 100;

        Function<Integer, Integer> vipPricing = x -> (int)(x * 0.8);   // 8折
        Function<Integer, Integer> normalPricing = x -> x;

        boolean isVip = true;
        Function<Integer, Integer> pricing = isVip ? vipPricing : normalPricing;

        System.out.println("final price = " + pricing.apply(amount));
    }

    // 3) 事件监听/回调：用 Lambda 实现“监听器接口”
    interface OnMessage {
        void onMessage(String msg);
    }
    static void scenario3_eventCallbackLike() {
        System.out.println("\n--- 3) Listener callback ---");
        subscribe("order.created", msg -> System.out.println("handle: " + msg));
    }
    static void subscribe(String topic, OnMessage listener) {
        // 假装“消息来了”
        listener.onMessage("[" + topic + "] id=12345");
    }

    // 4) 并发/异步：CompletableFuture（thenApply/thenCompose/exceptionally）
    static void scenario4_asyncCompletableFuture() throws Exception {
        System.out.println("\n--- 4) CompletableFuture pipeline ---");

        CompletableFuture<String> f = CompletableFuture
                .supplyAsync(() -> "42")                      // 异步取数据
                .thenApply(Integer::parseInt)                 // 转类型
                .thenApply(x -> x * 2)                        // 处理
                .thenApply(x -> "result=" + x)                // 变成字符串
                .exceptionally(e -> "fallback");              // 异常兜底

        System.out.println(f.get());
    }

    // 5) 模板封装：统一日志/耗时/异常边界（把核心逻辑用 Lambda 传入）
    static void scenario5_templateWrapper() {
        System.out.println("\n--- 5) Template wrapper ---");
        String r = withCost("loadUser", () -> {
            // 这里写“真实业务”
            sleepSilently(50);
            return "user:Alice";
        });
        System.out.println("return = " + r);
    }
    static <T> T withCost(String name, Supplier<T> supplier) {
        long t = System.currentTimeMillis();
        try {
            return supplier.get();
        } finally {
            System.out.println(name + " cost=" + (System.currentTimeMillis() - t) + "ms");
        }
    }
    static void sleepSilently(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    // 6) Optional：链式取值避免一堆 null 判断（但别过度嵌套）
    static void scenario6_optionalNullChain() {
        System.out.println("\n--- 6) Optional null-safe chain ---");

        User user = new User(5, "Eva", "RD", 20, true);
        String city = Optional.ofNullable(user)
                .map(u -> u.name)              // 这里演示链式 map
                .map(String::toUpperCase)
                .orElse("UNKNOWN");

        System.out.println("city(or name here) = " + city);
    }

    // 7) Map/缓存惰性计算：computeIfAbsent
    static void scenario7_mapComputeIfAbsent() {
        System.out.println("\n--- 7) computeIfAbsent ---");
        Map<Integer, String> cache = new HashMap<>();

        Function<Integer, String> loader = id -> "value#" + id; // 模拟“查库/远程调用”

        String v1 = cache.computeIfAbsent(1, loader);
        String v2 = cache.computeIfAbsent(1, loader); // 第二次不会再算

        System.out.println("v1=" + v1 + ", v2=" + v2);
        System.out.println("cache=" + cache);
    }

    // 8) Predicate 组合：把多个条件拼成一个（and/or/negate）
    static void scenario8_predicateCompose() {
        System.out.println("\n--- 8) Predicate compose ---");
        List<User> users = demoUsers();

        Predicate<User> isActive = User::isActive;
        Predicate<User> isAdult = u -> u.getAge() >= 18;

        List<User> result = users.stream()
                .filter(isActive.and(isAdult))   // 组合条件
                .collect(Collectors.toList());

        System.out.println("active & adult = " + result);
    }
}
