package foobargoogle.P4_2_BringingGunToATrainerFight;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFS3 {

    /*
    Use both the trainer and yourself as the targets, so that we can check and make sure the beam will hit the trainer first.
    However, it is TLE when distance is large (due to too many stack).
     */
    public static int solution(int[] dimensions, int[] your, int[] trainer, int distance) {
        Map<String, List<String>> vectorToPoints = new HashMap<>();
        Map<String, Integer> vectorToMinDist = new HashMap<>();

        dfs(dimensions, your, trainer, trainer, distance * distance, new HashSet<>(), vectorToPoints, vectorToMinDist);

        Map<String, List<String>> selfVectorToPoints = new HashMap<>();
        Map<String, Integer> selfVectorToMinDist = new HashMap<>();

        dfs(dimensions, your, your, your, distance * distance, new HashSet<>(), selfVectorToPoints, selfVectorToMinDist);

        int hitSelfFirst = 0;
        for (Map.Entry<String, Integer> entry : vectorToMinDist.entrySet()) {
            if (selfVectorToMinDist.containsKey(entry.getKey()) && selfVectorToMinDist.get(entry.getKey()) <= entry.getValue()) {
                hitSelfFirst++;
            }
        }

//        System.out.println(vectorToMinDist);
//        System.out.println(selfVectorToMinDist);

        return vectorToMinDist.size() - hitSelfFirst;
    }

    private static void dfs(int[] dimensions, int[] origin, int[] current, int[] trainer, int maxDist, Set<Integer> visited, Map<String, List<String>> vectorToPoints, Map<String, Integer> vectorToMinDist) {
        int hashcode = Arrays.hashCode(current);
        if (visited.contains(hashcode)) {
            return;
        }

        visited.add(hashcode);

        int currDist = dist(current, origin);

        if (currDist <= maxDist) {
            String vector = getVectorK(current, origin);
            String point = current[0] + "#" + current[1];

            if (!vectorToPoints.containsKey(vector)) {
                vectorToPoints.put(vector, new ArrayList<>());
            }
            vectorToPoints.get(vector).add(point);

            if (!vectorToMinDist.containsKey(vector) || vectorToMinDist.get(vector) > currDist) {
                vectorToMinDist.put(vector, currDist);
            }

            for (int[] next : getMirrors(dimensions, current)) {
                dfs(dimensions, origin, next, trainer, maxDist, visited, vectorToPoints, vectorToMinDist);
            }
        }
    }

    private static String getVectorK(int[] p0, int[] p1) {
        if (p0[0] == p1[0]) {
            return "0_" + (p0[1] > p1[1] ? "1" : "-1");
        }

        if (p0[1] == p1[1]) {
            return (p0[0] > p1[0] ? "1" : "-1") + "_0";
        }

        int gcd = gcd(Math.abs(p0[0] - p1[0]), Math.abs(p0[1] - p1[1]));
        int x = (p0[0] - p1[0]) / gcd;
        int y = (p0[1] - p1[1]) / gcd;

        return x + "_" + y;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    private static int[][] getMirrors(int[] dimensions, int[] point) {
        int[][] mirrors = new int[4][2];
        mirrors[0] = new int[] {-point[0], point[1]};
        mirrors[1] = new int[] {point[0], -point[1]};
        mirrors[2] = new int[] {2 * dimensions[0] - point[0], point[1]};
        mirrors[3] = new int[] {point[0], 2 * dimensions[1] - point[1]};
        return mirrors;
    }

    private static int dist(int[] your, int[] trainer) {
        return (trainer[0] - your[0]) * (trainer[0] - your[0]) + (trainer[1] - your[1]) * (trainer[1] - your[1]);
    }

    @Test
    public void test() {
        System.out.println(solution(new int[] {3,2}, new int[] {1,1}, new int[] {2,1}, 4));
        System.out.println(solution(new int[] {300,275}, new int[] {150,150}, new int[] {180,100}, 500));
        // System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 9));
        System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 11));
        System.out.println(solution(new int[] {23,10}, new int[] {6,4}, new int[] {3,2}, 23));
        System.out.println(solution(new int[] {1250,1250}, new int[] {1000,1000}, new int[] {500,400}, 10000));

        // Stackoverflow
        // System.out.println(solution(new int[] {10,10}, new int[] {4,4}, new int[] {3,3}, 5000));
    }

}
