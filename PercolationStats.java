import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openSites;
    private int numberOfTrials;
    private double mean;
    private double standardDeviation;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials is <= 0");
        }
        openSites = new double[trials];
        numberOfTrials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);

            while (!test.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!test.isOpen(row, col)) {
                    test.open(row, col);
                }
            }
            openSites[i] = (double) test.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(openSites);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        standardDeviation = StdStats.stddev(openSites);
        return standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (1.96 * standardDeviation / Math.sqrt(numberOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96 * standardDeviation / Math.sqrt(numberOfTrials));
    }

    // test client (see below)
    public static void main(String[] args) {

        PercolationStats newTest = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + newTest.mean());
        System.out.println("stddev = " + newTest.stddev());
        // double[] confidenceInterval = {newTest.confidenceLo(), newTest.confidenceHi()};
        System.out.println("95% confidence interval = " + newTest.confidenceLo() + " " + newTest.confidenceHi());

    }

}
