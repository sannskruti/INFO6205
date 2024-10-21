package edu.neu.coe.info6205.pq;
import java.util.Comparator;

public class FibonacciHeap<K> {
    private Node<K> minNode;
    private int size;
    private final Comparator<K> comparator;

    public FibonacciHeap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public void insert(K key) {
        Node<K> newNode = new Node<>(key);
        minNode = meld(minNode, newNode);
        size++;
    }

    public K removeMin() {
        Node<K> oldMin = minNode;
        if (oldMin != null) {
            if (oldMin.child != null) {
                Node<K> child = oldMin.child;
                do {
                    child.parent = null;
                    child = child.next;
                } while (child != oldMin.child);
            }

            Node<K> nextMin = (oldMin == oldMin.next) ? null : oldMin.next;
            minNode = meld(nextMin, oldMin.child);

            size--;
        }
        return oldMin == null ? null : oldMin.key;
    }

    private Node<K> meld(Node<K> a, Node<K> b) {
        if (a == null) return b;
        if (b == null) return a;
        if (comparator.compare(a.key, b.key) > 0) {
            Node<K> temp = a;
            a = b;
            b = temp;
        }
        Node<K> an = a.next;
        a.next = b.next;
        a.next.prev = a;
        b.next = an;
        b.next.prev = b;
        return a;
    }

    public int size() {
        return 0;
    }

    private static class Node<K> {
        K key;
        Node<K> next;
        Node<K> prev;
        Node<K> child;
        Node<K> parent;

        Node(K key) {
            this.key = key;
            next = prev = this;
        }
    }
}
