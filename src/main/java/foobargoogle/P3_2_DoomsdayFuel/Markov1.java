package foobargoogle.P3_2_DoomsdayFuel;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Markov1 {

    /*
    https://en.wikipedia.org/wiki/Absorbing_Markov_chain
    https://en.wikipedia.org/wiki/Absorbing_Markov_chain#Absorbing_probabilities
    https://zhuanlan.zhihu.com/p/274775796
    https://www.youtube.com/watch?v=i3AkTO9HLXo&list=PLM8wYQRetTxBkdvBtz-gw8b9lcVkdXQKV&ab_channel=NormalizedNerd

    Fraction: https://stackoverflow.com/questions/474535/best-way-to-represent-a-fraction-in-java
     */

    private static class Fraction {
        private long numerator;
        private long denominator;

        public Fraction(long numerator, long denominator) {
            // Always make denominator larger than 0
            if (denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            }

            long g = gcd(Math.abs(numerator), Math.abs(denominator));

            if (g != 0) {
                this.numerator = numerator / g;
                this.denominator = denominator / g;
            }
        }

        public long getNumerator() {
            return numerator;
        }

        public long getDenominator() {
            return denominator;
        }

        public Fraction add(Fraction b) {
            if (b.getNumerator() == 0) {
                return this;
            }
            if (this.getNumerator() == 0) {
                return b;
            }

            // n1/d1 + n2/d2 = (n1*d2 + d1*n2)/(d1*d2)
            return new Fraction(this.numerator * b.getDenominator() + this.denominator * b.getNumerator(),
                    this.denominator * b.getDenominator());
        }

        public Fraction minus(Fraction b) {
            if (b.getNumerator() == 0) {
                return this;
            }
            if (this.getNumerator() == 0) {
                return new Fraction(-1 * b.getNumerator(), b.denominator);
            }

            // n1/d1 - n2/d2 = (n1*d2 - d1*n2)/(d1*d2)
            return new Fraction(this.numerator * b.getDenominator() - this.denominator * b.getNumerator(),
                    this.denominator * b.getDenominator());
        }

        public Fraction multiply(Fraction b) {
            return new Fraction(this.numerator * b.getNumerator(),
                    this.denominator * b.getDenominator());
        }

        public Fraction multiply(int b) {
            return new Fraction(this.numerator * b, this.denominator);
        }

        public Fraction inverse() {
            if (this.numerator == 0) {
                throw new IllegalArgumentException("Can't inverse zero.");
            }

            return new Fraction(this.denominator, this.numerator);
        }

        public Fraction complement() {
            return new Fraction(this.denominator - this.numerator, this.denominator);
        }

        private long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }

        @Override
        public String toString() {
            return this.numerator == 0 ? "0" : this.numerator + "/" + this.denominator;
        }
    }

    private static Fraction[][] inverse(Fraction[][] matrix) {
        Fraction[][] inverse = new Fraction[matrix.length][matrix.length];

        if (matrix.length == 1) {
            inverse[0][0] = matrix[0][0].inverse();
            return inverse;
        }

        // minors and cofactors
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                inverse[i][j] = determinant(submatrix(matrix, i, j)).multiply((int) Math.pow(-1, i + j));

        // adjugate and determinant
        Fraction det = determinant(matrix).inverse();
        for (int i = 0; i < inverse.length; i++) {
            for (int j = 0; j <= i; j++) {
                Fraction temp = inverse[i][j];
                inverse[i][j] = det.multiply(inverse[j][i]);
                inverse[j][i] = det.multiply(temp);
            }
        }

        return inverse;
    }

    private static Fraction[][] submatrix(Fraction[][] matrix, int row, int column) {
        Fraction[][] minor = new Fraction[matrix.length - 1][matrix.length - 1];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; i != row && j < matrix[i].length; j++)
                if (j != column)
                    minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
        return minor;
    }

    private static Fraction determinant(Fraction[][] matrix) {
        if (matrix.length != matrix[0].length)
            throw new IllegalStateException("invalid dimensions");

        if (matrix.length == 1)
            return matrix[0][0];

        if (matrix.length == 2)
            return matrix[0][0].multiply(matrix[1][1]).minus(matrix[0][1].multiply(matrix[1][0]));

        Fraction det = new Fraction(0, 0);
        for (int i = 0; i < matrix[0].length; i++)
            det = matrix[0][i].multiply((int) Math.pow(-1, i)).multiply(determinant(submatrix(matrix, 0, i))).add(det);
        return det;
    }

    public static int[] solution(int[][] m) {
        if (m.length == 0) {
            return null;
        }

        if (m.length == 1) {
            return new int[] {1, 1};
        }

        boolean[] terminal = new boolean[m.length];
        Arrays.fill(terminal, true);
        terminal[0] = false;

        Queue<Integer> nonTerms = new LinkedList<>();
        nonTerms.add(0);

        // Use BFS to find all terminal rows
        while (!nonTerms.isEmpty()) {
            int row = nonTerms.poll();

            boolean allZeros = true;
            for (int col = 0; col < m[0].length; col++) {
                if (m[row][col] != 0) {
                    allZeros = false;
                    if (terminal[col]) {
                        nonTerms.add(col);
                    }
                }
            }
            if (!allZeros) {
                terminal[row] = false;
            }
        }
        terminal[0] = false;

        int nonTerminals = 0;
        for (int i = 0; i < m.length; i++) {
            if (!terminal[i]) {
                nonTerminals++;
            }
        }

        long[] sums = new long[m.length];
        for (int i = 0; i < m.length; i++) {
            long sum = Arrays.stream(m[i]).mapToLong(n -> (long) n).sum();
            sums[i] = sum;
        }

        Fraction[][] Q = new Fraction[nonTerminals][nonTerminals];
        // Initialize as identity matrix
        for (int i = 0; i < Q.length; i++) {
            for (int j = 0; j < Q.length; j++) {
                Q[i][j] = i == j ? new Fraction(1, 1) : new Fraction(0, 0);
            }
        }

        Fraction[][] R = new Fraction[nonTerminals][m.length - nonTerminals];

        // Populate R and (I - Q)
        int xNonTerminal = 0;
        for (int i = 0; i < m.length; i++) {
            if (terminal[i]) {
                continue;
            }
            int yNonTerminal = 0;
            int yTerminal = 0;
            for (int j = 0; j < m.length; j++) {
                if (terminal[j]) {
                    R[xNonTerminal][yTerminal] = new Fraction(m[i][j], sums[i]);
                    yTerminal++;
                } else {
                    Q[xNonTerminal][yNonTerminal] = Q[xNonTerminal][yNonTerminal].minus(new Fraction(m[i][j], sums[i]));
                    yNonTerminal++;
                }
            }
            xNonTerminal++;
        }

        Fraction[][] N = inverse(Q);

        // Absorbing probabilities
        Fraction[] B0 = multiply(N[0], R);

        int[] result = new int[B0.length + 1];

        int lcm = 1;
        for (Fraction b : B0) {
            if (b.getDenominator() != 0) {
                lcm = lcm(lcm, (int) b.getDenominator());
            }
        }

        result[result.length - 1] = lcm;

        // Normalize results
        for (int i = 0; i < result.length - 1; i++) {
            result[i] = B0[i].getDenominator() == 0 ? 0 : (int) (B0[i].getNumerator() * (lcm / B0[i].getDenominator()));
        }

        return result;
    }

    private static Fraction[] multiply(Fraction[] N0, Fraction[][] R) {
        Fraction[] B0 = new Fraction[R[0].length];
        for (int i = 0; i < B0.length; i++) {
            B0[i] = new Fraction(0, 0);
            for (int j = 0; j < N0.length; j++) {
                B0[i] = B0[i].add(N0[j].multiply(R[j][i]));
            }
        }

        return B0;
    }

    public static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    public static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    @Test
    public void test() {
        int[][] m1 = new int[][] {{0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}};
        System.out.println(Arrays.toString(solution(m1)));

        int[][] m2 = new int[][] {{0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0,0}, {0, 0, 0, 0, 0}};
        System.out.println(Arrays.toString(solution(m2)));

        int[][] m3 = new int[][] {{1, 1}, {0, 0}};
        System.out.println(Arrays.toString(solution(m3)));

        int[][] m4 = new int[][] {{1, 0, 1, 0}, {0, 0, 0, 0}, {2, 2, 2, 1}, {0, 0, 0, 0}};
        System.out.println(Arrays.toString(solution(m4)));

        int[][] m5 = new int[][] {{1}};
        System.out.println(Arrays.toString(solution(m5)));
    }

    @Test
    public void test2() {
        Fraction[][] matrix = new Fraction[][] {{new Fraction(0, 0), new Fraction(1, 2)},
                {new Fraction(4, 9), new Fraction(0, 0)}};
        Fraction[][] inverse = inverse(matrix);

        System.out.println(Arrays.deepToString(inverse));

        Fraction[][] matrix2 = new Fraction[][] {{new Fraction(1, 1), new Fraction(-1, 2)},
                {new Fraction(-4, 9), new Fraction(1, 1)}};
        Fraction[][] inverse2 = inverse(matrix2);

        System.out.println(Arrays.deepToString(inverse2));
    }

}
