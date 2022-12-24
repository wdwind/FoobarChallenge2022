package foobargoogle.P2_2_ElevatorMaintenance;

import org.junit.Test;

import java.util.Arrays;

public class Sorting1 {

    public static String[] solution(String[] l) {
        Arrays.sort(l, (s1, s2) -> {
            String[] v1 = s1.split("\\.");
            String[] v2 = s2.split("\\.");

            if (versionCompare(v1[0], v2[0]) != 0) {
                return versionCompare(v1[0], v2[0]);
            } else {
                if (v1.length == 1 || v2.length == 1) {
                    return v1.length - v2.length;
                } else {
                    if (versionCompare(v1[1], v2[1]) != 0) {
                        return versionCompare(v1[1], v2[1]);
                    } else {
                        if (v1.length == 2 || v2.length == 2) {
                            return v1.length - v2.length;
                        } else {
                            return versionCompare(v1[2], v2[2]);
                        }
                    }
                }
            }
        });

        return l;
    }

    public static int versionCompare(String v1, String v2) {
        int i1 = Integer.parseInt(v1);
        int i2 = Integer.parseInt(v2);
        if (i1 != i2) {
            return Integer.compare(i1, i2);
        } else {
            return v1.length() - v2.length();
        }
    }

    @Test
    public void test() {
        System.out.println(Arrays.toString(solution(new String[]{"1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"})));
        System.out.println(Arrays.toString(solution(new String[]{"1.0.0", "1", "1.0"})));
        System.out.println(Arrays.toString(solution(new String[]{"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"})));
    }

}
