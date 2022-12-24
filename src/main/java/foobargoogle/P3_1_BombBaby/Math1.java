package foobargoogle.P3_1_BombBaby;

import org.junit.Test;

import java.math.BigInteger;

public class Math1 {

    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger ZERO = BigInteger.valueOf(0);

    public static String solution(String x, String y) {
        BigInteger i1 = new BigInteger(x);
        BigInteger i2 = new BigInteger(y);

        BigInteger[] result = gcd(i1, i2);
        if (result[0].compareTo(ONE) != 0) {
            return "impossible";
        } else {
            return result[1].add(BigInteger.valueOf(-1)).toString();
        }
    }

    private static BigInteger[] gcd(BigInteger i1, BigInteger i2) {
        if (i2.compareTo(ZERO) == 0) {
            return new BigInteger[] {i1, ZERO};
        } else {
            BigInteger[] result = gcd(i2, i1.mod(i2));
            result[1] = result[1].add(i1.divide(i2));
            return result;
        }
    }

    @Test
    public void test() {
        System.out.println(solution("2", "1"));
        System.out.println(solution("4", "7"));
    }

}
