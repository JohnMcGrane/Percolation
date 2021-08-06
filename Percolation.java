/* *****************************************************************************
 *  Name:              John Fox
 *  Coursera User ID:  4427046
 *  Last modified:     08/06/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF network;
    private boolean sites[][];
    private int gridsize;
    private int opensites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridsize = n;
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        sites = new boolean[n + 1][n + 1];
        network = new WeightedQuickUnionUF((n + 1) * (n + 1));
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                sites[i][j] = false;
            }
        }
    }

    private int xyTo1D(int x, int y) {
        return ((y - 1) + (gridsize * (x - 1)));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row <= 0) || (row > gridsize)) {
            throw new java.lang.IllegalArgumentException();
        }
        if ((col <= 0) || (col > gridsize)) {
            throw new java.lang.IllegalArgumentException();
        }
        int value = xyTo1D(row, col);
        // if (value < 0) {
        //     throw new IllegalArgumentException();
        // }

        if (value < gridsize) {
            network.union(value, (gridsize * gridsize) + 1);
            // top virtual node
        }
        if (((gridsize - 1) * gridsize <= value) && (value < (gridsize * gridsize))) {
            network.union(value, (gridsize * gridsize));
            // bottom virtual node
        }

        if (!sites[row][col]) {
            sites[row][col] = true;
            opensites++;
            if ((col != gridsize) && (sites[row][col + 1])) {
                int neighbor = xyTo1D(row, col + 1);
                network.union(value, neighbor);
            }
            if ((col != 1) && (sites[row][col - 1])) {
                int neighbor = xyTo1D(row, col - 1);
                network.union(value, neighbor);
            }
            if ((row != gridsize) && (sites[row + 1][col])) {
                int neighbor = xyTo1D(row + 1, col);
                network.union(value, neighbor);
            }
            if ((row != 1) && (sites[row - 1][col])) {
                int neighbor = xyTo1D(row - 1, col);
                network.union(value, neighbor);
            }
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row <= 0) || (row > gridsize)) {
            throw new java.lang.IllegalArgumentException();
        }
        if ((col <= 0) || (col > gridsize)) {
            throw new java.lang.IllegalArgumentException();
        }
        int value = xyTo1D(row, col);
        return network.find(value) == network.find((gridsize * gridsize) + 1);
    }

    // does the system percolate?
    public boolean percolates() {
        return network.find((gridsize * gridsize) + 1) == network
                .find(gridsize * gridsize);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row <= 0) || (row > gridsize)) {
            throw new java.lang.IllegalArgumentException();
        }
        if ((col <= 0) || (col > gridsize)) {
            throw new java.lang.IllegalArgumentException();
        }
        int value = xyTo1D(row, col);
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        return sites[row][col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = StdIn.readInt();
        Percolation net = new Percolation(size);
        while (!net.percolates()) {
            int row = StdRandom.uniform(size) + 1;
            int col = StdRandom.uniform(size) + 1;
            net.open(row, col);
        }
        double opens = net.numberOfOpenSites();
        double total = size * size;
        StdOut.print(opens / total + "\n");
    }
}
