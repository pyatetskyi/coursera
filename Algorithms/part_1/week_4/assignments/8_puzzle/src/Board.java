/**
 * Coursera: Algorithms
 * Part 1 - Week 4: 8 Puzzle
 *
 * @author Pavlo Pyatetskyi
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Board {

    private final int[] cells;
    private final int dim;
    private int zero;
    private final int hamming;
    private final int manhattan;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {

        dim = blocks.length;
        cells = new int[dim * dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                cells[i * dim + j] = blocks[i][j];
                if (cells[i * dim + j] == 0) {
                    zero = i * dim + j;
                }
            }
        }

        hamming = calculateHamming(cells);
        manhattan = calculateManhattan(cells);
    }

    private Board(int[] cells, int dim, int zero, int hamming, int manhattan) {
        this.dim = dim;
        this.cells = cells;
        this.zero = zero;
        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    private int innerHamming(int val, int i) {
        if (val == 0) {
            return 0;
        }
        else if (val != (i + 1)) {
            return 1;
        }
        else {
            return 0;
        }
    }

    private int calculateHamming(int[] cellBlock) {
        int count = 0;
        for (int i = 0; i < cellBlock.length; i++) {
            count += innerHamming(cellBlock[i], i);
        }
        return count;
    }

    // number of blocks out of place
    public int hamming() {
        return hamming;
    }

    private int innerManhattan(int val, int i) {
        if (val == 0) {
            return 0;
        }
        else {
            return Math.abs(i / dim - (val - 1) / dim) + Math.abs(i % dim - (val - 1) % dim);
        }
    }

    private int calculateManhattan(int[] cellBlock) {
        int count = 0;
        for (int i = 0; i < cellBlock.length; i++) {
            count += innerManhattan(cellBlock[i], i);
        }
        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattan;
    }

    private void exchange(int[] cellBlock, int i, int j) {
        int temp = cellBlock[i];
        cellBlock[i] = cellBlock[j];
        cellBlock[j] = temp;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[] copy = cells.clone();

        for (int i = 0; i < cells.length; i++) {
            if (i % dim == 0 || cells[i] * cells[i - 1] == 0) {

                exchange(copy, i, i - 1);
                int ham = hamming;
                int man = manhattan;

                for (int j = i; j > i - 2; j--) {
                    ham += innerHamming(copy[j], j) - innerHamming(cells[j], j);
                    man += innerManhattan(copy[j], j) - innerManhattan(cells[j], j);
                }

                return new Board(copy, dim, zero, ham, man);
            }
        }
        return null;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (null == y || this.getClass() != y.getClass()) {
            return false;
        }

        return Arrays.equals(cells, ((Board) y).cells);
    }

    private Board neighbour(int i) {
        int[] copy = cells.clone();
        exchange(copy, zero, i);
        int h = hamming;
        int m = manhattan;
        h += innerHamming(copy[zero], zero) - innerHamming(copy[i], i);
        m += innerManhattan(copy[zero], zero) - innerManhattan(copy[i], i);
        return new Board(copy, dim, i, h, m);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbours = new ArrayList<>();

        if (zero / dim != 0) {
            neighbours.add(neighbour(zero - dim));
        }
        if (zero / dim != (dim - 1)) {
            neighbours.add(neighbour(zero + dim));
        }
        if (zero % dim != 0) {
            neighbours.add(neighbour(zero - 1));
        }
        if (zero % dim != (dim - 1)) {
            neighbours.add(neighbour(zero + 1));
        }
        return neighbours;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                 s.append(String.format("%d ", cells[i * dim + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    // unit tests (not graded)
    public static void main(String[] args) {

        In in = new In(args[0]);
        int size = in.readInt();
        int[][] blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board b = new Board(blocks);
        StdOut.println(b);
        System.out.println(b.hamming);
        for (Board board : b.neighbors()) {
            System.out.println(board);
            System.out.println(b.hamming);
        }

    }
}
