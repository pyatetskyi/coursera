/**
 * Coursera: Algorithms
 * <p>
 * Part 1 - Week 2: Deque
 * </p>
 *
 * @since 1.0
 * @author Pavlo Pyatetskyi
 * @version 1.0
 */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> firstNode;
    private Node<Item> lastNode;

    public Deque() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public boolean isEmpty() {
        return firstNode == null && lastNode == null;
    }

    private void validateDeque() {
        if (firstNode == null && lastNode == null) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new java.util.NoSuchElementException();
        }
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        validateItem(item);

        if (firstNode == null && lastNode == null) {
            Node<Item> newNode = new Node<Item>(item, null, null);
            firstNode = newNode;
            lastNode = newNode;
        } else {
            Node<Item> previousFirst = firstNode;
            firstNode = new Node<Item>(item, previousFirst, null);
            if (previousFirst != null) {
                previousFirst.setPrevious(firstNode);
            }
            size++;
        }
    }

    public void addLast(Item item) {
        validateItem(item);

        if (firstNode == null && lastNode == null) {
            Node<Item> newNode = new Node<Item>(item, null, null);
            firstNode = newNode;
            lastNode = newNode;
        } else {
            Node<Item> nextLast = new Node<Item>(item, null, lastNode);
            lastNode.setNext(nextLast);
            lastNode = nextLast;
            size++;
        }
    }

    public Item removeFirst() {
        validateDeque();
        final Node<Item> first = firstNode;
        firstNode = first.getNext();
        if (firstNode == null) {
            lastNode = null;
        } else {
            firstNode.setPrevious(null);
            size--;
        }
        return first.getItem();
    }

    public Item removeLast() {
        validateDeque();
        final Node<Item> last = lastNode;
        lastNode = last.getPrevious();
        if (lastNode != null) {
            lastNode.setNext(null);
            size--;
        } else {
            firstNode = null;
            lastNode = null;
        }
        last.setNext(null);
        last.setPrevious(null);
        return last.getItem();
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> currElement = firstNode;
            @Override
            public boolean hasNext() {
                return currElement != null;
            }

            @Override
            public Item next() {
                if (currElement == null) { throw new java.util.NoSuchElementException(); }
                final Item obj = currElement.getItem();
                currElement = currElement.getNext();
                return obj;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static class Node<Item> {

        private final Item item;
        private Node<Item> next;
        private Node<Item> previous;

        public Node(Item item, Node<Item> next, Node<Item> previous) {
            this.item = item;
            this.next = next;
            this.previous = previous;
        }

        public Item getItem() {
            return item;
        }

        public Node<Item> getNext() {
            return next;
        }

        public Node<Item> getPrevious() {
            return previous;
        }

        public void setNext(Node<Item> node) {
            this.next = node;
        }

        public void setPrevious(Node<Item> node) {
            this.previous = node;
        }
    }
}
