package org.example.cli;

import org.example.algorithms.MaxHeap;
import org.example.metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        // аргументы: sizes=100,1000,10000,100000 output=benchmark.csv
        int[] sizes = {100, 1000, 10000, 100000};
        String outputFile = "benchmark_results.csv";

        // разбор аргументов
        for (String arg : args) {
            if (arg.startsWith("sizes=")) {
                sizes = Arrays.stream(arg.substring(6).split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            } else if (arg.startsWith("output=")) {
                outputFile = arg.substring(7);
            }
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("n,scenario,inputType,time,comparisons,swaps,allocations,arrayAccesses,maxDepth\n");

            for (int n : sizes) {
                for (String inputType : new String[]{"random", "sorted", "reversed", "nearly-sorted"}) {
                    runCase(writer, n, "insert", inputType);
                    runCase(writer, n, "extract", inputType);
                    runCase(writer, n, "increaseKey", inputType);
                }
            }

            System.out.println("Benchmark finished! Results saved in " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runCase(FileWriter writer, int n, String scenario, String inputType) throws IOException {
        PerformanceTracker tracker = new PerformanceTracker();
        MaxHeap heap = new MaxHeap(n, tracker);
        int[] data = generateInput(n, inputType);

        long start = System.nanoTime();

        switch (scenario) {
            case "insert":
                for (int value : data) {
                    heap.insert(value);
                }
                break;

            case "extract":
                for (int value : data) {
                    heap.insert(value);
                }
                for (int i = 0; i < n; i++) {
                    heap.extractMax();
                }
                break;

            case "increaseKey":
                for (int value : data) {
                    heap.insert(value);
                }
                Random rand = new Random();
                for (int i = 0; i < n / 2; i++) {
                    int index = rand.nextInt(heap.getSize());
                    int currentValue = heap.getValueAt(index);

                    long sum = (long) currentValue + rand.nextInt(1000) + 1;
                    int newValue = (sum > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) sum;

                    heap.increaseKey(index, newValue);
                }
                break;
        }

        long end = System.nanoTime();
        long elapsed = end - start;

        writer.write(String.format(
                "%d,%s,%s,%d,%d,%d,%d,%d,%d\n",
                n,
                scenario,
                inputType,
                elapsed,
                tracker.getComparisons(),
                tracker.getSwaps(),
                tracker.getAllocations(),
                tracker.getArrayAccesses(),
                tracker.getMaxDepth()
        ));
    }

    private static int[] generateInput(int n, String type) {
        int[] arr = new int[n];
        Random rand = new Random();

        // random
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt();
        }

        switch (type) {
            case "sorted":
                Arrays.sort(arr);
                break;
            case "reversed":
                Arrays.sort(arr);
                for (int i = 0; i < n / 2; i++) {
                    int temp = arr[i];
                    arr[i] = arr[n - 1 - i];
                    arr[n - 1 - i] = temp;
                }
                break;
            case "nearly-sorted":
                Arrays.sort(arr);
                // Перемешиваем 1% элементов
                int swaps = Math.max(1, n / 100);
                for (int i = 0; i < swaps; i++) {
                    int a = rand.nextInt(n);
                    int b = rand.nextInt(n);
                    int tmp = arr[a];
                    arr[a] = arr[b];
                    arr[b] = tmp;
                }
                break;
        }

        return arr;
    }
}
