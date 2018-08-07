package org.swat.math;

/**
 * The type Dice probability.
 */
public class DiceProbability {
    private final int count;
    private final int radix;

    /**
     * Instantiates a new Dice probability.
     *
     * @param skewness the skewness
     * @param radix    the radix
     */
    public DiceProbability(int skewness, int radix) {
        this.count = skewness;
        this.radix = radix;
    }

    /**
     * Distribute the given total, with nearest step.
     *
     * @param total the total
     * @param step  the step
     * @return the distributions. 0th element contains the difference
     */
    public long[] distribute(final long total, final int step) {
        long[] distributions = new long[count * radix + 1];
        long sum = 0;
        for (int x = 1; x <= count * radix; x++) {
            long dist = (long) (total * probability(x) / 100.0);
            dist = Math.round(dist * 1.0 / step) * step;
            distributions[x] = dist;
            sum += dist;
        }
        distributions[0] = total - sum;
        return distributions;
    }

    /**
     * Probability double.
     *
     * @param number the number
     * @return the probability
     */
    public double probability(int number) {
        return chance(number) * 100.0 / Math.pow(radix, count);
    }

    /**
     * Chance of the number occurring.
     *
     * @param number the number
     * @return the chance
     */
    public long chance(int number) {
        long[][] numberChance = new long[count][];
        for (int x = 0; x < numberChance.length; x++) {
            numberChance[x] = new long[(x + 1) * radix];
        }
        return chance(number, count, numberChance);
    }

    private long chance(int number, int count, long[][] numberChance) {
        if (number < count) {
            return 0;
        }
        if (number > count * radix) {
            return 0;
        }
        if (count == 1) {
            return 1;
        }
        long chances = numberChance[count - 1][number - 1];
        if (chances > 0) {
            return chances;
        }
        chances = 0;
        for (int x = 1; x < number && x <= radix; x++) {
            chances += chance(number - x, count - 1, numberChance);
        }
        numberChance[count - 1][number - 1] = chances;
        return chances;
    }

    private long nCr(int n, int r) {
        return nPr(n, r) / fact(r);
    }

    private long nPr(int n, int r) {
        return fact(n) / fact(n - r);
    }

    private long fact(int n) {
        long value = 1;
        for (int x = 1; x <= n; x++) {
            value *= x;
        }
        return value;
    }
}

// 3 3C3 = 1
// 4 4C1 = 1
// 5 4C1 + 4C2 = 10
// 6 6C3 = 20
