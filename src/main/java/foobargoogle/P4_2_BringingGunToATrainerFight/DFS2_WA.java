package foobargoogle.P4_2_BringingGunToATrainerFight;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFS2_WA {

    /*
    Added some corner case checks, but similar to DFS1, still WA.
     */
    public static int solution(int[] dimensions, int[] your, int[] trainer, int distance) {
        int[] current = your.clone();
        Set<Integer> visited = new HashSet<>();
        Set<String> vectorK = new HashSet<>();
        List<String> points = new ArrayList<>();

        Map<String, List<String>> vectorToPoints = new HashMap<>();

        dfs(dimensions, your, current, trainer, distance * distance, visited, vectorK, points, vectorToPoints);

//        System.out.println(vectorK);
//        System.out.println(points);
        System.out.println(vectorToPoints);

        return vectorK.size();
    }

    private static void dfs(int[] dimensions, int[] origin, int[] current, int[] trainer, int maxDist, Set<Integer> visited, Set<String> vectorK, List<String> points, Map<String, List<String>> vectorToPoints) {
        int hashcode = Arrays.hashCode(current);
        if (visited.contains(hashcode)) {
            return;
        }

        visited.add(hashcode);

        int currDist = dist(current, trainer);

        if (currDist <= maxDist) {
            if (origin[0] == current[0] && origin[1] == current[1]
                     || !sameLine(origin, current, trainer)
                        && !lineThroughCorner(dimensions, trainer, current)) {
                        // && !lineThroughCorner(dimensions, origin, current)) {
                // result++;
                String vector = getVectorK(trainer, current);
                String point = current[0] + "#" + current[1] + "_" + getVectorK(origin, current);

                if (!vectorToPoints.containsKey(vector)) {
                    vectorToPoints.put(vector, new ArrayList<>());
                }
                vectorToPoints.get(vector).add(point);

                vectorK.add(getVectorK(trainer, current));
                points.add(current[0] + "#" + current[1]);
            }

            for (int[] next : getMirrors(dimensions, current)) {
                dfs(dimensions, origin, next, trainer, maxDist, visited, vectorK, points, vectorToPoints);
            }
        }
    }

    private static boolean lineThroughCorner(int[] dimensions, int[] trainer, int[] current) {
        int[] leftBottom = new int[] {0, 0};

        if (sameLine(trainer, current, leftBottom)) {
            if (getVectorK(leftBottom, trainer).equals(getVectorK(current, leftBottom))) {
                return true;
            }
        }

        int[] leftTop = new int[] {0, dimensions[1]};
        if (sameLine(trainer, current, leftTop)) {
            if (getVectorK(leftTop, trainer).equals(getVectorK(current, leftTop))) {
                return true;
            }
        }

        int[] rightBottom = new int[] {dimensions[0], 0};
        if (sameLine(trainer, current, rightBottom)) {
            if (getVectorK(rightBottom, trainer).equals(getVectorK(current, rightBottom))) {
                return true;
            }
        }

        if (sameLine(trainer, current, dimensions)) {
            if (getVectorK(dimensions, trainer).equals(getVectorK(current, dimensions))) {
                return true;
            }
        }

        return false;
    }

    private static String getVectorK(int[] p0, int[] p1) {
        if (p0[0] == p1[0]) {
            return "vertical";
        }

        if (p0[1] == p1[1]) {
            return "horizontal";
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
//        Set<int[]> set = new HashSet<>();
//        set.add(new int[] {1,2});
//        set.add(new int[] {1,2});
//        System.out.println(set.size());

//        System.out.println(solution(new int[] {3,2}, new int[] {1,1}, new int[] {2,1}, 4));
//        System.out.println(solution(new int[] {300,275}, new int[] {150,150}, new int[] {185,100}, 500));
        System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 9));
        // System.out.println(solution(new int[] {2,5}, new int[] {1,2}, new int[] {1,4}, 11));
        // System.out.println(solution(new int[] {23,10}, new int[] {6,4}, new int[] {3,2}, 23));
        // System.out.println(solution(new int[] {1250,1250}, new int[] {1000,1000}, new int[] {500,400}, 10000));
    }

}
