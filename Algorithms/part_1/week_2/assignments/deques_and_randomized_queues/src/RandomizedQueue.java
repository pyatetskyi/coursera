/**
 * Coursera: Algorithms
 * <p>
 * Part 1 - Week 2: RandomizedQueue
 * </p>
 *
 * @since 1.0
 * @author Pavlo Pyatetskyi
 * @version 1.0
 */

// import java.lang.reflect.Array;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] elems = (Item[]) new Object[0];
    private int numElems = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // Class<Item> c) {
        // Use Array native method to create array
        // of a type only known at run time
        // @SuppressWarnings("unchecked")
        // final Item[] a = (Item[]) Array.newInstance(c, 0);
        // this.elems = a;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return numElems == 0;
    }

    // return the number of items on the queue
    public int size() {
        if (isEmpty()) return 0;
        int count = 0;
        for (Item i : this) {
            count++;
        }
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        final int size = elems.length;
        if (size == 0) {
            resize(1);
        } else if (size <= numElems) {
            resize(2 * size);
        }
        elems[numElems++] = item;
    }

    private void resize(int max) {
        final Item[] temp = (Item[]) new Object[max];
        final int arrLength = elems.length;
        int numElemToCopy;
        if (arrLength > max) {
            numElemToCopy = max;
        } else {
            numElemToCopy = arrLength;
        }
        System.arraycopy(elems, 0, temp, 0, numElemToCopy);
        elems = temp;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int rand = StdRandom.uniform(numElems);
        final Item obj = elems[rand];
        elems[rand] = elems[--numElems];
        final int arrLength = elems.length;
        if (numElems > 0 && numElems == arrLength / 4) {
            resize(arrLength / 2);
        }
        return obj;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item obj = null;
        while (obj == null) {
            int rand = StdRandom.uniform(numElems);
            obj = elems[rand];
        }
        return obj;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(elems);
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private RandomizedQueue<Item> newColl = new RandomizedQueue<Item>();

        public RandomizedQueueIterator(Item[] items) {
            for (Item i : items) {
                if (i == null) {
                    break;
                }
                newColl.enqueue(i);
            }
        }

        @Override
        public boolean hasNext() {
            return !newColl.isEmpty();
        }

        @Override
        public Item next() {
            if (newColl.isEmpty()) {
                throw new java.util.NoSuchElementException();
            }
            return newColl.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
