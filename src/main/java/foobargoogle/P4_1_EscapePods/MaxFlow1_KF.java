package foobargoogle.P4_1_EscapePods;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MaxFlow1_KF {

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        int n = path.length;

        Map<Integer, Integer>[] adjs = new Map[path.length + 2];
        for (int i = 0; i < adjs.length; i++) {
            adjs[i] = new HashMap<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (path[i][j] != 0) {
                    adjs[i].put(j, path[i][j]);
                }
            }
        }

        for (int en : entrances) {
            adjs[n].put(en, Integer.MAX_VALUE);
        }
        for (int ex : exits) {
            adjs[ex].put(n + 1, Integer.MAX_VALUE);
        }

        return maxFlow(n, n + 1, adjs);
    }

    private static int maxFlow(int start, int end, Map<Integer, Integer>[] adjs) {
        int maxFlow = 0;

        int flow = 0;
        while (flow != -1) {
            flow = dfs(start, end, Integer.MAX_VALUE, adjs, new boolean[adjs.length]);
            if (flow > 0) {
                maxFlow += flow;
            }
        }

        return maxFlow;
    }

    // Naive DFS implementation of max flow
    private static int dfs(int start, int end, int prevFlow, Map<Integer, Integer>[] adjs, boolean[] visited) {
        if (start == end) {
            return prevFlow;
        }

        visited[start] = true;
        if (adjs[start] != null && !adjs[start].isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : adjs[start].entrySet()) {
                int next = entry.getKey();
                int cost = entry.getValue();
                if (!visited[next] && cost > 0) {
                    int finalFlow = dfs(next, end, Math.min(prevFlow, cost), adjs, visited);
                    if (finalFlow != -1) {
                        if (start != adjs.length - 2 && next != adjs.length - 1) {
                            adjs[start].put(next, cost - finalFlow);
                            adjs[next].put(start, adjs[next].getOrDefault(start, 0) + finalFlow);
                        }
                        return finalFlow;
                    }
                }
            }
        }

        return -1;
    }

    @Test
    public void test() {
        System.out.println(solution(new int[] {0,1}, new int[] {4,5}, new int[][] {{0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4, 4}, {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}));
        System.out.println(solution(new int[] {0}, new int[] {3}, new int[][] {{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}}));
    }

}
