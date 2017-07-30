/**
 * Coursera: Algorithms
 * <p>
 * Part 1 - Week 1: Percolation
 * </p>
 *
 * @since 1.0
 * @author Pavlo Pyatetskyi
 * @version 1.0
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public class Percolation {

    /**
     * Boolean matrix of sites.
     */
    private boolean[][] opened;
    /**
     * Size of array. Needs to be incremented to make numeration start from 1.
     */
    private int size;
    /**
     * Number of open sites.
     */
    private int openSites = 0;
    /**
     * Optimized data structure.
     */
    private WeightedQuickUnionUF quf;

    /**
     * Creates N-by-N grid, with all sites blocked.
     *
     * @param n Actual size of matrix (array).
     */
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n index out of bounds");
        }

        size = n + 1;
        opened = new boolean[size][size];

        /*
        For this task we assume that 0 is top opened site,
        cells from 1 to 'size' are bottom sites
        (approach used to avoid backwash).
        */
        quf = new WeightedQuickUnionUF(size * size);
    }

    /**
     * Check whether parameters meet requirements.
     *
     * @param row Row index (starts with 1).
     * @param col Col index (starts with 1).
     */
    private void isValidInput(final int row, final int col) {
        if (row <= 0 || row > size) {
            throw new IndexOutOfBoundsException("row index out of bounds");
        }
        if (col <= 0 || col > size) {
            throw new IndexOutOfBoundsException("col index out of bounds");
        }
    }


    /**
     * Opens site with indexes row and col if it is not opened already.
     *
     * @param row Row index (starts with 1).
     * @param col Col index (starts with 1).
     */
    public void open(final int row, final int col) {
        isValidInput(row, col);
        if (isOpen(row, col)) {
            return;
        }

        opened[row][col] = true;
        openSites++;

        if (row == 1) {
            quf.union(0, size + col);
        }

        if (row == size - 1) {
            quf.union(col, row * size + col);
        }

        if (row - 1 > 0 && isOpen(row - 1, col)) {
            quf.union((row - 1) * size + col, row * size + col);
        }

        if (row + 1 < size && isOpen(row + 1, col)) {
            quf.union((row + 1) * size + col, row * size + col);
        }

        if (col + 1 < size && isOpen(row, col + 1)) {
            quf.union(row * size + col + 1, row * size + col);
        }

        if (col - 1 > 0 && isOpen(row, col - 1)) {
            quf.union(row * size + col - 1, row * size + col);
        }

        /*
        String matrix = "";
        for(boolean[] t : opened) {
            for(boolean x : t) {
                if(x) {
                    matrix += "t ";
                }
                else {
                    matrix += "f ";
                }
            }
            matrix += "\n";
        }
        System.out.println(matrix);
        */
    }

    /**
     * Checks if site (row, col) is open.
     *
     * @param row Row index (starts with 1).
     * @param col Col index (starts with 1).
     *
     * @return true or false.
     */
    public boolean isOpen(final int row, final int col) {
        isValidInput(row, col);
        return opened[row][col];
    }

    /**
     * Checks if site (row, col) is full.
     *
     * @param row Row index (starts with 1).
     * @param col Col index (starts with 1).
     *
     * @return true or false.
     */
    public boolean isFull(final int row, final int col) {
        isValidInput(row, col);
        return quf.connected(0, row * size + col);
    }

    /**
     * Returns number of open sites.
     *
     * @return 0 <= value < n * n.
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Checks whether the system percolates.
     *
     * @return true or false.
     */
    public boolean percolates() {
        for (int i = 1; i < size; i++) {
            if (quf.find(i) == quf.find(0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test client (optional).
     *
     * @param args input parameter.
     */
    public static void main(final String[] args) { }
}
