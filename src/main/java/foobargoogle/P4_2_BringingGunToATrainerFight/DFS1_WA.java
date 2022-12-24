package foobargoogle.P4_2_BringingGunToATrainerFight;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DFS1_WA {

    /*
    WA, didn't consider the case that the beam hit yourself first.
     */
    public static int solution(int[] dimensions, int[] your, int[] trainer, int distance) {
        int[] current = your.clone();
        Set<Integer> visited = new HashSet<>();

        return dfs(dimensions, your, current, trainer, distance * distance, visited);
    }

    private static int dfs(int[] dimensions, int[] origin, int[] current, int[] trainer, int maxDist, Set<Integer> visited) {
        int hashcode = Arrays.hashCode(current);
        if (visited.contains(hashcode)) {
            return 0;
        }

        visited.add(hashcode);

        int currDist = dist(current, trainer);

        int result = 0;

        if (currDist <= maxDist) {


            if (origin[0] == current[0] && origin[1] == current[1] || !sameLine(origin, current, trainer)) {
                result++;
            }

            for (int[] next : getMirrors(dimensions, current)) {
                result += dfs(dimensions, origin, next, trainer, maxDist, visited);
            }
        }

        return result;
    }

    private static int[][] getMirrors(int[] dimensions, int[] point) {
        int[][] mirrors = new int[4][2];
        mirrors[0] = new int[] {-point[0], point[1]};
        mirrors[1] = new int[] {point[0], -point[1]};
        mirrors[2] = new int[] {2 * dimensions[0] - point[0], point[1]};
        mirrors[3] = new int[] {point[0], 2 * dimensions[1] - point[1]};
        return mirrors;
    }

    private static boolean sameLine(int[] p1, int[] p2, int[] p3) {
        int left = (p1[0] - p2[0]) * p3[1] - (p1[1] - p2[1]) * p3[0];
        int right = p2[1] * p1[0] - p1[1] * p2[0];
        return left == right;
    }

    private static int dist(int[] your, int[] trainer) {
        return (trainer[0] - your[0]) * (trainer[0] - your[0]) + (trainer[1] - your[1]) * (trainer[1] - your[1]);
    }

    @Test
    public void test() {
        // System.out.println(solution(new int[] {3,2}, new int[] {1,1}, new int[] {2,1}, 4));
        // System.out.println(solution(new int[] {300,275}, new int[] {150,150}, new int[] {185,100}, 500));

        System.out.println(solution(new int[] {23,10}, new int[] {6,4}, new int[] {3,2}, 23));
    }

}
