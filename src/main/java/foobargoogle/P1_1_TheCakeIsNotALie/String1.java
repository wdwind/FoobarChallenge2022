package foobargoogle.P1_1_TheCakeIsNotALie;

import org.junit.Test;

public class String1 {

    public static int solution(String x) {
        for (int i = 0; i < x.length(); i++) {
            if (x.length() % (i + 1) == 0) {
                if (cutable(x, x.substring(0, i + 1))) {
                    return x.length() / (i + 1);
                }
            }
        }

        return 1;
    }

    private static boolean cutable(String x, String cut) {
        for (int i = 0; i < x.length(); i += cut.length()) {
//            if (x.indexOf(cut, i) != i) {
//                return false;
//            }
            if (!x.startsWith(cut, i)) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void test() {
        System.out.println(solution("abcabcabcabc"));
        System.out.println(solution("abccbaabccba"));
    }

}
