/**
 * Coursera: Algorithms
 * <p>
 * Part 1 - Week 2: Permutation
 * </p>
 *
 * @since 1.0
 * @author Pavlo Pyatetskyi
 * @version 1.0
 */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        final int numElem = Integer.parseInt(args[0]);
        final RandomizedQueue<String> elems = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            elems.enqueue(item);
        }
        for (int i = 0; i < numElem; i++) {
            System.out.println(elems.dequeue());
        }
    }
}
