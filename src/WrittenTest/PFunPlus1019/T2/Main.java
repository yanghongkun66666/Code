package WrittenTest.PFunPlus1019.T2;

import javax.xml.stream.FactoryConfigurationError;
import java.util.*;

public class Main {
    //"Player1", ["Admin", "Guest", "TestUser"]
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] s = line.toCharArray();
        List<String> to_judge = new ArrayList<>();

        int count = 0;
        int n = s.length;
        int i = 0;
        while (i < n) {
            StringBuilder sb = new StringBuilder();
            if (s[i] == '"' && count % 2 == 0) {
                ++i; //找到引号后面第一个字符
                ++count;
                while (i < n) {
                    if (s[i] == '"' && count % 2 == 1) {
                        ++count;
                        ++i;
                        to_judge.add(sb.toString());
                        break;
                    }
                    sb.append(s[i]);
                    ++i;
                }
            } else {
                ++i;
            }
        }
        Set<String> set = new HashSet<>();
        for (String str : to_judge) {
            if (set.contains(str)) {
                System.out.println("False");
                return;
            }
            set.add(str);
        }

        char[] first = to_judge.get(0).toCharArray();
        if (first.length >= 6 && first.length <= 16) {
            if ((first[0] >= 'a' && first[0] <= 'z') || (first[0] >= 'A' && first[0] <= 'Z')) {
                for (char c : first) {
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') {

                    } else {
                        System.out.println("False");
                        return;
                    }
                }
            } else {
                System.out.println(false);
                return;
            }
        } else {
            System.out.println(false);
            return;
        }

        System.out.println("True");
    }
}
