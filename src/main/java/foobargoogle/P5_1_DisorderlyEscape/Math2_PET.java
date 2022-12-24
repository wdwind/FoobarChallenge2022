package foobargoogle.P5_1_DisorderlyEscape;

import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Math2_PET {

    static class Monomial {
        Map<Integer, Integer> variateToExp;
        public long coef = 1;

        public Monomial() {
            variateToExp = new HashMap<>();
        }

        public void multiply(Integer v, int exp) {
            variateToExp.put(v, variateToExp.getOrDefault(v, 0) + exp);
        }

        public void add(long i) {
            this.coef += i;
        }

        public Monomial copy() {
            Monomial monomial = new Monomial();
            monomial.coef = this.coef;
            monomial.variateToExp.putAll(this.variateToExp);
            return monomial;
        }

        private String getVariates() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, Integer> entry : this.variateToExp.entrySet()) {
                sb.append("x");
                sb.append(entry.getKey());
                sb.append("^");
                sb.append(entry.getValue());
                sb.append("_");
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            return this.coef + " " + this.getVariates();
        }

        @Override
        public int hashCode() {
            return this.getVariates().hashCode();
        }
    }

    public static String solution(int w, int h, int s) {
        // Cycle index for row and column individually
        // (based on the problem definition, row/column permutation is a symmetric group by itself)
        List<Monomial> cycleIndexW = cycleIndexOfSymmetricGroup(w);
        List<Monomial> cycleIndexH = cycleIndexOfSymmetricGroup(h);

        // row * column to construct a new group
        List<Monomial> product = new ArrayList<>();
        Map<Integer, Integer> monomialToInd = new HashMap<>();
        for (Monomial pw : cycleIndexW) {
            for (Monomial ph : cycleIndexH) {
                Monomial monomial = new Monomial();

                for (Map.Entry<Integer, Integer> vw : pw.variateToExp.entrySet()) {
                    int lenVw = vw.getKey();
                    int degreeVw = vw.getValue();

                    for (Map.Entry<Integer, Integer> vh : ph.variateToExp.entrySet()) {
                        int lenVh = vh.getKey();
                        int degreeVh = vh.getValue();

                        int gcd = gcd(lenVw, lenVh);
                        int lcm = lenVh / gcd * lenVw;

                        monomial.multiply(lcm, degreeVh * degreeVw * gcd);
                    }
                }
                monomial.coef = pw.coef * ph.coef;

                int hash = monomial.hashCode();
                if (monomialToInd.containsKey(hash)) {
                    product.get(monomialToInd.get(hash)).add(monomial.coef);
                } else {
                    product.add(monomial);
                    monomialToInd.put(hash, product.size() - 1);
                }
            }
        }

        // System.out.println(product);

        // Calculate result based on Burnside/PET
        BigInteger result = BigInteger.ZERO;
        BigInteger bigS = BigInteger.valueOf(s);
        for (Monomial monomial : product) {
            int exps = 0;
            for (int exp : monomial.variateToExp.values()) {
                exps += exp;
            }
            result = result.add(bigS.pow(exps).multiply(BigInteger.valueOf(monomial.coef)));
        }

        result = result.divide(BigInteger.valueOf(factorial(w)));
        result = result.divide(BigInteger.valueOf(factorial(h)));

        return result.toString();
    }

    private static long factorial(int w) {
        long fact = w;
        while (w > 1) {
            w--;
            fact *= w;
        }
        return fact;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? Math.abs(a) : gcd(b, a % b);
    }

    /*
    https://en.wikipedia.org/wiki/Cycle_index#Symmetric_group_Sn
    https://mathworld.wolfram.com/SymmetricGroup.html
     */
    private static List<Monomial> cycleIndexOfSymmetricGroup(int sp) {
        List<Monomial> zs1 = new ArrayList<>();
        zs1.add(new Monomial());
        zs1.get(0).multiply(1, 1);

        List<List<Monomial>> zs = new ArrayList<>();
        zs.add(List.of(new Monomial()));
        zs.add(zs1);

        for (int i = 2; i <= sp; i++) {
            List<Monomial> zsi = new ArrayList<>();
            Map<Integer, Integer> monomialToInd = new HashMap<>();
            long multiplier = 1;
            for (int j = 1; j <= i; j++) {
                List<Monomial> zsIMinusJ = zs.get(i - j);
                // To avoid fractions
                multiplier *= j == 1 ? 1 : i - j + 1;
                for (Monomial monomial : zsIMinusJ) {
                    Monomial copy = monomial.copy();
                    copy.multiply(j, 1);
                    copy.coef *= multiplier;
                    int hash = copy.hashCode();

                    if (monomialToInd.containsKey(hash)) {
                        zsi.get(monomialToInd.get(hash)).add(copy.coef);
                    } else {
                        zsi.add(copy);
                        monomialToInd.put(hash, zsi.size() - 1);
                    }
                }
            }
            zs.add(zsi);
        }

        // System.out.println(zs);
        return zs.get(zs.size() - 1);
    }

    @Test
    public void test() {
        System.out.println(solution(2, 2, 2));
        System.out.println(solution(2, 3, 4));
        System.out.println(solution(12, 12, 20));
    }

}
