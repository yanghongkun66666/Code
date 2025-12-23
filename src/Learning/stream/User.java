package Learning.stream;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

class User {
    private final long id;
    private final String name;
    private final int age;
    private final String dept;
    private final int salary;
    private final boolean vip;
    private final LocalDate joinDate;
    private final List<String> tags;

    public User(long id, String name, int age, String dept, int salary, boolean vip, LocalDate joinDate, List<String> tags) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dept = dept;
        this.salary = salary;
        this.vip = vip;
        this.joinDate = joinDate;
        this.tags = tags;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDept() { return dept; }
    public int getSalary() { return salary; }
    public boolean isVip() { return vip; }
    public LocalDate getJoinDate() { return joinDate; }
    public List<String> getTags() { return tags; }

    @Override public String toString() {
        return "User{id=" + id + ", name='" + name + "', age=" + age + ", dept='" + dept +
                "', salary=" + salary + ", vip=" + vip + ", joinDate=" + joinDate + ", tags=" + tags + "}";
    }
}

