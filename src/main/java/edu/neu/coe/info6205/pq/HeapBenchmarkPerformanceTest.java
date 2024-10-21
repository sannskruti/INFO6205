package edu.neu.coe.info6205.pq;
import java.util.*;
import java.util.function.Supplier;

public class HeapBenchmarkPerformanceTest {

    private static final int HEAP_CAPACITY = 4095;  // Maximum heap size
    private static final int NUM_INSERTS = 16000;   // Total elements to insert
    private static final int NUM_REMOVALS = 4000;   // Total elements to remove
    private static final Random RANDOM_GENERATOR = new Random();

    public static void main(String[] args) {

        Comparator<Integer> minComparator = Comparator.naturalOrder(); // Min-Heap configuration

        // Test binary heap without applying Floyd's heapify technique
        System.out.println("Running Binary Heap Test (Without Floyd's Optimization):");
        runHeapBenchmark(() -> new PriorityQueue<>(HEAP_CAPACITY, false, minComparator, false));

        // Test binary heap using Floyd's heapify technique
        System.out.println("\nRunning Binary Heap Test (With Floyd's Optimization):");
        runHeapBenchmark(() -> new PriorityQueue<>(HEAP_CAPACITY, false, minComparator, true));

        // Test 4-ary heap without Floyd's optimization
        System.out.println("\nEvaluating 4-ary Heap (Without Floyd's Technique):");
        runHeapBenchmark(() -> new PriorityQueue<>(HEAP_CAPACITY, true, minComparator, false));

        // Test 4-ary heap using Floyd's optimization
        System.out.println("\nEvaluating 4-ary Heap (With Floyd's Technique):");
        runHeapBenchmark(() -> new PriorityQueue<>(HEAP_CAPACITY, true, minComparator, true));
    }

    /**
     * Runs a performance benchmark for the provided heap implementation.
     */
    private static void runHeapBenchmark(Supplier<PriorityQueue<Integer>> heapProvider) {
        PriorityQueue<Integer> heap = heapProvider.get();
        List<Integer> overflowElements = new ArrayList<>();

        // Measure insertion time
        long startInsert = System.nanoTime();
        for (int i = 0; i < NUM_INSERTS; i++) {
            int element = RANDOM_GENERATOR.nextInt(100000); // Generate random integer
            if (heap.size() >= HEAP_CAPACITY) {
                overflowElements.add(element); // Store overflowed elements
            } else {
                heap.give(element); // Insert into the heap
            }
        }
        long totalInsertTime = System.nanoTime() - startInsert;

        // Measure removal time
        long startRemove = System.nanoTime();
        for (int i = 0; i < NUM_REMOVALS; i++) {
            try {
                heap.take(); // Extract the minimum element
            } catch (PQException e) {
                e.printStackTrace(); // Handle potential exceptions
            }
        }
        long totalRemoveTime = System.nanoTime() - startRemove;

        // Identify the highest priority element among the overflowed elements
        Optional<Integer> maxOverflowElement = overflowElements.stream().max(Comparator.naturalOrder());

        // Display benchmark results
        System.out.println("Time for Insertions (ns): " + totalInsertTime);
        System.out.println("Time for Removals (ns): " + totalRemoveTime);
        maxOverflowElement.ifPresent(e -> System.out.println("Top Overflow Element: " + e));
    }
}
