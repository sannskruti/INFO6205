package edu.neu.coe.info6205.sort.elementary;
import java.util.Arrays;
import java.util.Random;
public class MainSort {
    public static void main(String[] args) {
        InsertionSortBasic<Integer> insertionSort = InsertionSortBasic.create();
        int[] sizes = {1000, 2000, 4000, 8000, 16000};
        for (int n : sizes) {
            System.out.println("Array size: " + n);
            Integer[] randomArray = generateRandomArray(n);
            benchmarkSort("Random", randomArray, insertionSort);
            Integer[] orderedArray = generateOrderedArray(n);
            benchmarkSort("Ordered", orderedArray, insertionSort);
            Integer[] partiallyOrderedArray = generatePartiallyOrderedArray(n);
            benchmarkSort("Partially Ordered", partiallyOrderedArray, insertionSort);
            Integer[] reverseOrderedArray = generateReverseOrderedArray(n);
            benchmarkSort("Reverse Ordered", reverseOrderedArray, insertionSort);
            System.out.println();
        }
    }
    private static void benchmarkSort(String description, Integer[] array, InsertionSortBasic<Integer> insertionSort) {
        Integer[] copy = Arrays.copyOf(array, array.length);
        long startTime = System.nanoTime();
        insertionSort.sort(copy);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        System.out.println(description + " array took: " + duration + " ms");
    }

    private static Integer[] generateRandomArray(int n) {
        Random random = new Random();
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
        return array;
    }

    private static Integer[] generateOrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        return array;
    }
    private static Integer[] generateReverseOrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = n - i;
        }
        return array;
    }
    private static Integer[] generatePartiallyOrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n / 2; i++) {
            array[i] = i; // First half is ordered
        }
        Random random = new Random();
        for (int i = n / 2; i < n; i++) {
            array[i] = random.nextInt(n);
        }
        return array;
    }
}
