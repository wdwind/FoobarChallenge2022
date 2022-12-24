package foobargoogle.P2_3_PrepareTheBunniesEscape;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class BSFAndDP1 {

    private static final int[][] DIRS = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static int solution(int[][] map) {
        int[][][] dp = new int[map.length][map[0].length][2];
        dp[0][0][0] = 1;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, 0});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            for (int[] dir : DIRS) {
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
                    boolean nextValid = false;
                    if (map[x][y] == 1) {
                        if (isValidMove(dp[curr[0]][curr[1]][0], dp[x][y][1], dp[curr[0]][curr[1]][0] + 1)) {
                            dp[x][y][1] = dp[curr[0]][curr[1]][0] + 1;
                            nextValid = true;
                        }
                    } else {
                        if (isValidMove(dp[curr[0]][curr[1]][0], dp[x][y][0], dp[curr[0]][curr[1]][0] + 1)) {
                            dp[x][y][0] = dp[curr[0]][curr[1]][0] + 1;
                            nextValid = true;
                        }
                        if (isValidMove(dp[curr[0]][curr[1]][1], dp[x][y][1], dp[curr[0]][curr[1]][1] + 1)) {
                            dp[x][y][1] = dp[curr[0]][curr[1]][1] + 1;
                            nextValid = true;
                        }
                    }
                    if (nextValid) {
                        queue.add(new int[] {x, y});
                    }
                }
            }
        }

        int[] last = dp[map.length - 1][map[0].length - 1];
        return last[0] == 0 ? last[1] : (last[1] == 0 ? last[0] : Math.min(last[0], last[1]));

//        if (dp[map.length - 1][map[0].length - 1][1] == 0) {
//            return dp[map.length - 1][map[0].length - 1][0];
//        } else if (dp[map.length - 1][map[0].length - 1][0] == 0) {
//            return dp[map.length - 1][map[0].length - 1][1];
//        } else {
//            return Math.min(dp[map.length - 1][map[0].length - 1][0], dp[map.length - 1][map[0].length - 1][1]);
//        }
    }

    private static boolean isValidMove(int currMove, int nextMove, int possibleNextMove) {
        return currMove > 0 && (nextMove == 0 || nextMove > possibleNextMove);
    }

    @Test
    public void test() {
        System.out.println(solution(new int[][] {{0, 1, 1, 0}, {0, 0, 0, 1}, {1, 1, 0, 0}, {1, 1, 1, 0}}));
    }

}
