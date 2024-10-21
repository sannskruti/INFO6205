package edu.neu.coe.info6205.pq;

import java.util.*;
import java.util.function.Supplier;

public class FibonacciHeapPerformanceTest {

    private static final int NUM_INSERTS = 16000;  // Number of elements to insert
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Comparator<Integer> comparator = Integer::compareTo;

        // Run Fibonacci Heap Benchmark
        System.out.println("Running Fibonacci Heap Test:");
        runHeapBenchmark(() -> new FibonacciHeap<>(comparator));
    }

    /**
     * Runs a performance benchmark for the provided Fibonacci Heap.
     *
     * @param heapSupplier A supplier that creates a new Fibonacci Heap instance.
     */
    private static void runHeapBenchmark(Supplier<FibonacciHeap<Integer>> heapSupplier) {
        FibonacciHeap<Integer> heap = heapSupplier.get();

        // Measure insertion time
        long startInsert = System.nanoTime();
        for (int i = 0; i < NUM_INSERTS; i++) {
            heap.insert(RANDOM.nextInt(100000));
        }
        long totalInsertTime = System.nanoTime() - startInsert;

        // Display benchmark results
        System.out.println("Fibonacci Heap Benchmark Results:");
        System.out.println("Time for Insertions (ns): " + totalInsertTime);
        System.out.println("Final Heap Size: " + heap.size());
    }
}
