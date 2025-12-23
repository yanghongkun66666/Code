package Learning.stream;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StreamPractice {
    static List<User> users = List.of(
            new User(1, "Alice", 23, "RD",  25000, true,  LocalDate.of(2023, 3, 1), List.of("java", "backend")),
            new User(2, "Bob",   19, "QA",  12000, false, LocalDate.of(2024, 1, 15), List.of("test", "python")),
            new User(3, "Cindy", 31, "RD",  35000, true,  LocalDate.of(2020, 7, 20), List.of("java", "arch")),
            new User(4, "David", 27, "OPS", 20000, false, LocalDate.of(2022, 11, 2), List.of("linux", "devops")),
            new User(5, "Eva",   27, "RD",  28000, false, LocalDate.of(2021, 5, 10), List.of("go", "backend")),
            new User(6, "Frank", 40, "QA",  22000, true,  LocalDate.of(2019, 9, 1), List.of("test", "lead")),
            // 故意加一个同名、一个同dept、以及 id 冲突练 toMap merge
            new User(7, "Alice", 29, "PM",  30000, false, LocalDate.of(2022, 2, 8), List.of("product")),
            new User(3, "Cindy2", 26, "RD",  26000, false, LocalDate.of(2025, 6, 1), List.of("java"))
    );

    public static void main(String[] args) {
        // 你把每题写成一个方法，main 里调用打印验证
        System.out.println(method());

    }

    //过滤出所有成年人（age >= 30），返回 List
//    private static List<User> method() {
//        return users.stream()
//                .filter(u ->  u.getAge() >= 30)
//                .toList();
//    }

    //只拿出所有人的名字，返回 List
    private static List<String> method() {
        return users.stream()
                .map(User::getName)
                .toList();
    }


}