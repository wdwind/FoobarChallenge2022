package foobargoogle.P4_2_BringingGunToATrainerFight;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Iterative1 {

    /*
    Iterative solution: iterate through all possible points. Note that getVectorHashcode has hash collision.
     */
    public static int solution(int[] dimensions, int[] your, int[] trainer, int distance) {
        List<int[]> trainerCandidates = getCandidate(dimensions, trainer, your, distance);
        List<int[]> selfCandidates = getCandidate(dimensions, your, your, distance);

        Map<String, Integer> selfVectorToMinDist = new HashMap<>();
        for (int[] candidate : selfCandidates) {
            String vectorHash = getVectorK(candidate, your);
            int dist = dist(your, candidate[0], candidate[1]);
            if (!selfVectorToMinDist.containsKey(vectorHash) || selfVectorToMinDist.get(vectorHash) > dist) {
                selfVectorToMinDist.put(vectorHash, dist);
            }
        }

        Map<String, Integer> trainerVectorToMinDist = new HashMap<>();
        for (int[] candidate : trainerCandidates) {
            String vectorHash = getVectorK(candidate, your);
            int dist = dist(your, candidate[0], candidate[1]);
            if ((!selfVectorToMinDist.containsKey(vectorHash) || selfVectorToMinDist.get(vectorHash) > dist)
                    && (!trainerVectorToMinDist.containsKey(vectorHash) || trainerVectorToMinDist.get(vectorHash) > dist)) {
                trainerVectorToMinDist.put(vectorHash, dist);
            }
        }

        // System.out.println(trainerCandidates.size());
        return trainerVectorToMinDist.size();
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

    // Hash collision
    private static int getVectorHashcode(int[] p0, int[] p1) {
        if (p0[0] == p1[0]) {
            return Arrays.hashCode(new int[] {0, p0[1] > p1[1] ? 1 : -1});
        }

        if (p0[1] == p1[1]) {
            return Arrays.hashCode(new int[] {p0[0] > p1[0] ? 1 : -1, 0});
        }

        int gcd = gcd(Math.abs(p0[0] - p1[0]), Math.abs(p0[1] - p1[1]));
        return Arrays.hashCode(new int[] {(p0[0] - p1[0]) / gcd, (p0[1] - p1[1]) / gcd});
    }

    private static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    private static List<int[]> getCandidate(int[] dimensions, int[] source, int[] target, int distance) {
        List<int[]> candidates = new ArrayList<>();

        for (int x = target[0] - distance; x <= target[0] + distance; x++) {
            for (int y = target[1] - distance; y <= target[1] + distance; y++) {
                if ((x - source[0]) % (2 * dimensions[0]) == 0 || (x + source[0]) % (2 * dimensions[0]) == 0) {
                    if ((y - source[1]) % (2 * dimensions[1]) == 0 || (y + source[1]) % (2 * dimensions[1]) == 0) {
                        if (dist(target, x, y) <= distance * distance) {
                            candidates.add(new int[] {x, y});
                        }
                    }
                }
            }
        }

        return candidates;
    }

    private static int dist(int[] your, int x, int y) {
        return (x - your[0]) * (x - your[0]) + (y - your[1]) * (y - your[1]);
    }

    @Test
    public void test() {
//        System.out.println(solution(new int[] {3,2}, new int[] {1,1}, new int[] {2,1}, 4));
//        System.out.println(solution(new int[] {300,275}, new int[] {150,150}, new int[] {180,100}, 500));
//        System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 9));
//        System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 11));
//        System.out.println(solution(new int[] {23,10}, new int[] {6,4}, new int[] {3,2}, 23));

        // 7735 ms
        long start1 = System.currentTimeMillis();
        System.out.println(solution(new int[] {1250,1250}, new int[] {1000,1000}, new int[] {500,400}, 10000));
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        // 3305 ms
        long start2 = System.currentTimeMillis();
        System.out.println(solution(new int[] {10,10}, new int[] {4,4}, new int[] {3,3}, 5000));
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

}
