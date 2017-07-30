/**
 * Coursera: Algorithms
 * <p>
 * Part 1 - Week 1: PercolationStats
 * </p>
 *
 * @since 1.0
 * @author Pavlo Pyatetskyi
 * @version 1.0
 */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

/**
 * PercolationStats class.
 */
public class PercolationStats {

    /**
     * Mean of percolation threshold.
     */
    private double mean;
    /**
     * Standard deviation of percolation threshold.
     */
    private double stddev;
    /**
     * Low endpoint of 95% confidence interval.
     */
    private double confidenceLo;
    /**
     * High endpoint of 95% confidence interval.
     */
    private double confidenceHi;
    /**
     * Calculated percolation threshold for each trial.
     */
    private double[] thresholdStats;
    /**
     * Used to calculate confidence bends.
     */
    private static final double MAGIC_NUMBER = 1.96;

    /**
     * Runs 'i = trial' independent experiments on 'n x n' grid.
     *
     * @param n size of the grid (one dimension)
     * @param trials number of experiments
     */
    public PercolationStats(final int n, final int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials index"
                    + " is out of bounds");
        }

        thresholdStats = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);

            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }

            thresholdStats[i] = perc.numberOfOpenSites() * 1.0 / (n * n);
        }

        mean = StdStats.mean(thresholdStats);
        stddev = StdStats.stddev(thresholdStats);
        confidenceLo = mean - (MAGIC_NUMBER * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (MAGIC_NUMBER * stddev) / Math.sqrt(trials);
    }

    /**
     * Method to retrieve 'mean' value for experiments.
     *
     * @return 0.0 <= value <= 1.0
     */
    public double mean() {
        return mean;
    }

    /**
     * Method to retrieve 'stddev' value for experiments.
     *
     * @return stddev
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Method to retrieve low endpoint of 95% confidence
     * interval for experiments.
     *
     * @return confidenceLo
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * Method to retrieve high endpoint of 95% confidence
     * interval for experiments.
     *
     * @return confidenceHi
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    /**
     * Runs experiments.
     *
     * @param args 1st = n, 2nd = trials
     */
    public static void main(final String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);

        System.out.println("Mean: " + percStats.mean());
        System.out.println("Standard deviation: " + percStats.stddev());
        System.out.println("95% confidence interval: ["
                + percStats.confidenceLo()
                + ", "
                + percStats.confidenceHi()
                + "]"
        );
    }
}
