package foobargoogle.P3_2_DoomsdayFuel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Graph1_WA {

    /*
    This is a Markov chain problem.
     */

    private static class Fraction {
        private long numerator;
        private long denominator;

        public Fraction(long numerator, long denominator) {
            long g = gcd(numerator, denominator);

            this.numerator = numerator / g;
            this.denominator = denominator / g;
        }

        public long getNumerator() {
            return numerator;
        }

        public long getDenominator() {
            return denominator;
        }

        public Fraction add(Fraction b) {
            if (b == null) {
                return this;
            }

            // n1/d1 + n2/d2 = (n1*d2 + d1*n2)/(d1*d2)
            return new Fraction(this.numerator * b.getDenominator() + this.denominator * b.getNumerator(),
                    this.denominator * b.getDenominator());
        }

        public Fraction multiply(Fraction b) {
            return new Fraction(this.numerator * b.getNumerator(),
                    this.denominator * b.getDenominator());
        }

        public Fraction inverse() {
            return new Fraction(this.denominator, this.numerator);
        }

        public Fraction complement() {
            return new Fraction(this.denominator - this.numerator, this.denominator);
        }

        private long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }
    }

    public static int[] solution(int[][] m) {
        int nonTerms = 0;

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] != 0) {
                    nonTerms++;
                    break;
                }
            }
        }

        long[] sums = new long[m.length];
        for (int i = 0; i < m.length; i++) {
            long sum = Arrays.stream(m[i]).mapToLong(n -> (long) n).sum();
            sums[i] = sum;
        }

        Fraction[] possibilities = new Fraction[m.length];

        for (int i = 0; i < nonTerms; i++) {
            Fraction ff = null;
            // List<Fraction> cycles = findCycles(i, m, new HashSet<>());
            List<Fraction> cycles = null;
            for (Fraction f : cycles) {
                ff = f.multiply(f.complement().inverse()).add(ff);
            }
            ff = new Fraction(1, 1).add(ff);
            for (int j = nonTerms; j < m[0].length; j++) {
                if (m[i][j] != 0) {
                    possibilities[j] = new Fraction(m[i][j], sums[i]).multiply(ff).add(possibilities[j]);
                }
            }
        }

        int[] result = new int[m.length - nonTerms + 1];
        int denominator = 1;
        for (int i = nonTerms; i < m.length; i++) {
            if (possibilities[i] == null) {
                result[i - nonTerms] = 0;
            } else {
                denominator = (int) lcm(denominator, possibilities[i].denominator);
            }
        }

        for (int i = nonTerms; i < m.length; i++) {
            if (possibilities[i] != null) {
                result[i - nonTerms] = (int) (denominator / possibilities[i].getDenominator() * possibilities[i].getNumerator());
            }
        }

        return result;
    }

    private static List<List<Integer>> findCycles(int start, int[][] m, Set<String> visited) {
        for (int i = 0; i < m[start].length; i++) {
            if (m[start][i] != 0) {
                String trace = start + "-" + i;
                if (!visited.contains(trace)) {
                    visited.add(trace);
                    findCycles(i, m, visited);
                }
            }
        }

        return null;
    }

    public static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static long gcd(long a, long b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

}
