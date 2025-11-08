package WrittenTest.HWP3312;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//1108寄了
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int lastTime = -1;
        List<int[]> events = new ArrayList<>();
        String line = "";
        while ((line = br.readLine()) != null) {
//            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            int id = Integer.parseInt(parts[0]);
            int startTime = Integer.parseInt(parts[1]);

            if (lastTime == -1) {
                events.add(new int[]{id, startTime});
            } else {
                events.add(new int[]{id, startTime - lastTime});
            }
            lastTime = startTime;
        }


        int maxId = -1, maxDur = -1;
        int minId = -1, minDur = Integer.MAX_VALUE;

        for (int[] event : events) {
            if (maxDur < event[1] || (maxDur == event[1] && maxId > event[0])) {
                maxId = event[0];
                maxDur = event[1];
            }

            if (minDur > event[1] || (minDur == event[1] && minId > event[0])) {
                minId = event[0];
                minDur = event[1];
            }
        }

        System.out.println(minId + " " + maxId);



    }
}
