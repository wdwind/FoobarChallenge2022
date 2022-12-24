package foobargoogle.P5_1_DisorderlyEscape;

import org.junit.Test;

public class Math1_Partition_WA {

    public static String solution(int w, int h, int s) {
        int total = Math.min(s, h) * w;

        int[][] partition = new int[total + 1][total + 1];

        for (int n = 1; n < partition.length; n++) {
            for (int k = 1; k <= n; k++) {
                if (k == 1) {
                    partition[n][k] = 1;
                } else if (n == k) {
                    partition[n][k] = 1;
                } else {
                    partition[n][k] = partition[n - 1][k - 1] + partition[n - k][k];
                }
            }
        }


        int[][] dp = new int[total + 1][total + 1];
        // dp[0][0] = 1;

        for (int i = 0; i < dp.length; i++) {
            for (int j = i; j < dp.length; j++) {
                if (i + j > total || i > w || j > w) {
                    continue;
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp.length; j++) {
                sum += dp[i][j];
            }
        }

        return "" + sum;
    }

    @Test
    public void test() {
        System.out.println(solution(2, 2, 2));
    }

}
