package WrittenTest.Pshunfeng.T1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * {110: 7, 111: 3, 115: 2}
 * {113: 7, 114: 3}输入两行这个，输出
 * {101: 5, 102: 3, 103: 2}
 * {102: 5, 103: 2, 104: 1}
 * {新增ID: 新增数量}
 * {删除ID: 删除数量}
 * {变化ID: 变化数量}
 */
//{110: 7, 111: 3, 115: 2}
// {113: 7, 114: 3}
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()); //{101: 5, 102: 3, 103: 2}
        Map<Integer, Integer> origin = new HashMap<>();
        List<Integer> tmp = new ArrayList<>();

        while (st.hasMoreTokens()) {
            String onegroup = st.nextToken();
            tmp.add(getData(onegroup));
        }

        int n = tmp.size();
        int i = 0;
        while (i < n) {
            int key = tmp.get(i);
            int value = tmp.get(i + 1);
            i += 2;
            origin.put(key, value);
        }

        st = new StringTokenizer(br.readLine());
        Map<Integer, Integer> cur = new HashMap<>();
        tmp.clear();

        while (st.hasMoreTokens()) {
            String onegroup = st.nextToken();
            tmp.add(getData(onegroup));
        }

        n = tmp.size();
        i = 0;
        while (i < n) {
            int key = tmp.get(i);
            int value = tmp.get(i + 1);
            i += 2;
            cur.put(key, value);
        }
        List<Integer> newAdd = new ArrayList<>();
        List<Integer> deleted = new ArrayList<>();
        List<Integer> changed = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : origin.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if (cur.containsKey(key)) {
                if (cur.get(key) != value) {
                    changed.add(key);
                    changed.add(cur.get(key) - value);
                }
            } else {
                deleted.add(key);
                deleted.add(value);
            }
        }

        for (Map.Entry<Integer, Integer> entry : cur.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            if (origin.containsKey(key) == false) {
                newAdd.add(key);
                newAdd.add(value);
            }
        }

        System.out.println(getResult(newAdd));
        System.out.println(getResult(deleted));
        System.out.println(getResult(changed));

    }

    private static  Integer getData(String str) {
        char[] s = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : s) {
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
        }

        return Integer.parseInt(sb.toString());
    }

    public static String getResult(List<Integer> nums) {
        StringBuilder sb = new StringBuilder();
        int n = nums.size();
        int i = 0;
        sb.append('{');
        while (i < n) {
            sb.append(nums.get(i).toString());
            sb.append(": ");
            sb.append(nums.get(i + 1).toString());
            i += 2;
            if (i < n) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
