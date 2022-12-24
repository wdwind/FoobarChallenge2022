package foobargoogle.P4_2_BringingGunToATrainerFight;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BSF1 {

    /*
    Use BSF to generate the candidates
     */
    public static int solution(int[] dimensions, int[] your, int[] trainer, int distance) {
        List<int[]> trainerCandidates = getCandidate(dimensions, trainer, your, distance);
        List<int[]> selfCandidates = getCandidate(dimensions, your, your, distance);

        Map<String, Integer> selfVectorToMinDist = new HashMap<>();
        for (int[] candidate : selfCandidates) {
            String vectorHash = getVectorK(candidate, your);
            int dist = dist(your, candidate);
            if (!selfVectorToMinDist.containsKey(vectorHash) || selfVectorToMinDist.get(vectorHash) > dist) {
                selfVectorToMinDist.put(vectorHash, dist);
            }
        }

        Map<String, Integer> trainerVectorToMinDist = new HashMap<>();
        for (int[] candidate : trainerCandidates) {
            String vectorHash = getVectorK(candidate, your);
            int dist = dist(your, candidate);
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

    private static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    private static List<int[]> getCandidate(int[] dimensions, int[] target, int[] your, int distance) {
        List<int[]> queue = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        if (dist(your, target) > distance * distance) {
            return queue;
        }

        queue.add(target);
        visited.add(target[0] + "," + target[1]);

        int ind = 0;
        do {
            int[] curr = queue.get(ind);

            for (int[] next : getMirrors(dimensions, curr)) {
                if (!visited.contains(next[0] + "," + next[1]) && dist(your, next) < distance * distance) {
                    visited.add(next[0] + "," + next[1]);
                    queue.add(next);
                }
            }

            ind++;
        } while (ind < queue.size());

        return queue;
    }

    private static int[][] getMirrors(int[] dimensions, int[] point) {
        int[][] mirrors = new int[4][2];
        mirrors[0] = new int[] {-point[0], point[1]};
        mirrors[1] = new int[] {point[0], -point[1]};
        mirrors[2] = new int[] {2 * dimensions[0] - point[0], point[1]};
        mirrors[3] = new int[] {point[0], 2 * dimensions[1] - point[1]};
        return mirrors;
    }

    private static int dist(int[] p0, int[] p1) {
        return (p1[0] - p0[0]) * (p1[0] - p0[0]) + (p1[1] - p0[1]) * (p1[1] - p0[1]);
    }

    @Test
    public void test() {
//        System.out.println(solution(new int[] {3,2}, new int[] {1,1}, new int[] {2,1}, 4));
//        System.out.println(solution(new int[] {300,275}, new int[] {150,150}, new int[] {180,100}, 500));
//        System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 9));
//        System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 11));
//        System.out.println(solution(new int[] {23,10}, new int[] {6,4}, new int[] {3,2}, 23));

        // 90 ms
        long start1 = System.currentTimeMillis();
        System.out.println(solution(new int[] {1250,1250}, new int[] {1000,1000}, new int[] {500,400}, 10000));
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        // 5558 ms
        long start2 = System.currentTimeMillis();
        System.out.println(solution(new int[] {10,10}, new int[] {4,4}, new int[] {3,3}, 5000));
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);
    }

}
