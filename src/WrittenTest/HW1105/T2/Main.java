package WrittenTest.HW1105.T2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = br.readLine();
        String[] parts = line.split("\\|");
//        for (String str : parts) {
//            System.out.println(str);
//        }

        String ori = parts[0];
        ori = ori.toLowerCase();
        Map<String, Double> posWeight = positionWeight(ori);

        //每个parts[i]可能有多个词
        for (int i = 1; i < parts.length; ++i) {

            String[] everyKeyWord = parts[i].toLowerCase().split("\\s+");
            double res = matchScore(ori, parts[i]);
            double keyWordWeight = 0;
            for (String str : everyKeyWord) {
                if (posWeight.containsKey(str)) {
                    keyWordWeight += posWeight.get(str);
                }

            }



//            System.out.print( res);
//            System.out.print('%.2f', 3.1415);
//            System.out.print();
            if (i != parts.length - 1) {
                System.out.printf("%.4f|", res * keyWordWeight);
            } else {
                System.out.printf("%.4f", res * keyWordWeight);
            }

//            System.out.println();
        }




    }

    private static String removeChar(String ori) {
        StringBuilder sb = new StringBuilder();
        for (char c : ori.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static Map<String, Double> positionWeight(String ori) {
        Map<String, Double> posWeight = new HashMap<>();
        String[] partsOri = ori.toLowerCase().split("\\s+");

        for (int i = 0; i < partsOri.length; ++i) {
            partsOri[i] = removeChar(partsOri[i]);
        }
        int n = partsOri.length;
        for (int i = 0; i < n; ++i) {
            double wei = 1.0 - ((double)i / (n - 1));
            if (posWeight.containsKey(partsOri[i]) == false) { //如果有多个关键词只选取第一个元素的作为位置权重
                posWeight.put(partsOri[i], wei);
            }
//            boolean flag = false; //表示没有那个词
//            for (Map.Entry<String, Double> entry : posWeight.entrySet()) {
//                String key = entry.getKey();
//                if (key.contains(partsOri[i])) {
//                    flag = true;
//                            break;
//                }
//            }
//
//            if (flag == false) {
//                posWeight.put(partsOri[i], wei);
//            }

        }

        return posWeight;
    }

    private static double matchScore(String ori, String to_match) {


        if (matchCase1(ori, to_match) == true) {
            return 1.0;
        } else if (matchCase2(ori, to_match) == true) {
            return 0.8;
        }

        int i = matchCase3(ori, to_match);
        String[] matchOri = to_match.toLowerCase().split("\\s+");
        int k = matchOri.length;

        if (i != 0) {
            return 0.6 * i / k;
        }

        return 0.0;
    }

    private static boolean matchCase1(String ori, String to_match) {
        Map<String, Integer> ori_map = new HashMap<>();
        Set<String> match_set = new HashSet<>();

        String[] partsOri = ori.toLowerCase().split("\\s+");
        String[] matchOri = to_match.toLowerCase().split("\\s+");

        for (int i = 0; i < partsOri.length; ++i) {
            ori_map.put(removeChar(partsOri[i]), i);
        }

        for (int i = 0; i < matchOri.length; ++i) {
            match_set.add(removeChar(matchOri[i]));
        }
        int curIndex = -1;
        for (String str : match_set) {
            if (ori_map.containsKey(str)) {
                if (ori_map.get(str) < curIndex) {
                    return false;
                } else {
                    curIndex = ori_map.get(str);
                }
            } else {
                //发现map中没有
                return false;
            }
        }

        //都遍历完发现确实是子序列
        return true;




    }

    private static boolean matchCase2(String ori, String to_match) {
        Set<String> ori_set = new HashSet<>();


        String[] partsOri = ori.toLowerCase().split("\\s+");
        String[] matchOri = to_match.toLowerCase().split("\\s+");

        for (int i = 0; i < partsOri.length; ++i) {
            ori_set.add(removeChar(partsOri[i]));
        }


        for (String str : matchOri) {
            if (ori_set.contains(removeChar(str))) {
                //有就下一个
            } else {
                //发现map中没有
                return false;
            }
        }

        //都遍历完发现确实是全都在原始串中能找到
        return true;




    }

    private static Integer matchCase3(String ori, String to_match) {
        Set<String> ori_set = new HashSet<>();


        String[] partsOri = ori.toLowerCase().split("\\s+");
        String[] matchOri = to_match.toLowerCase().split("\\s+");

        for (int i = 0; i < partsOri.length; ++i) {
            ori_set.add(removeChar(partsOri[i]));
        }

        int k = matchOri.length;
        int cnt = 0;


        for (String str : matchOri) {
            if (ori_set.contains(removeChar(str))) {
                //有就下一个
                ++cnt;
            }
        }

        //都遍历完发现确实是全都在原始串中能找到
        return cnt;

    }
}
