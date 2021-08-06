/* *****************************************************************************
 *  Name:              John Fox
 *  Coursera User ID:  4427046
 *  Last modified:     08/06/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double values[];
    private int its;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException();
        }
        values = new double[trials];
        its = trials;
        for (int i = 0; i < trials; i++) {
            Percolation net = new Percolation(n);
            while (!net.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                net.open(row, col);
            }
            double opens = net.numberOfOpenSites();
            double total = n * n;
            double val = (opens / total);
            values[i] = val;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(values);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(values);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (StdStats.mean(values) - 1.96 * StdStats.stddev(values) / (Math.sqrt(its)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (StdStats.mean(values) + 1.96 * StdStats.stddev(values) / (Math.sqrt(its)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // Stopwatch sw = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());

        StdOut.println("95% confidence interval = " + "[" + stats.confidenceLo() + ", " + stats
                .confidenceHi() + "]");
        // StdOut.println(sw.elapsedTime());
    }
}

