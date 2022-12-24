package foobargoogle.P2_1_EnRouteSalute;

import org.junit.Test;

public class String1 {

    public static int solution(String x) {
        int left = 0;
        int salutes = 0;

        for (int i = x.length() - 1; i >= 0; i--) {
            if (x.charAt(i) == '<') {
                left++;
            } else if (x.charAt(i) == '>') {
                salutes += left;
            }
        }

        return salutes * 2;
    }

    @Test
    public void test() {
        System.out.println(solution("--->-><-><-->-"));
    }

}
