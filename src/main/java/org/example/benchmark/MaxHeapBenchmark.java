package org.example.benchmark;

import org.example.algorithms.MaxHeap;
import org.example.metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class MaxHeapBenchmark {

    @Param({"50", "200", "1000"})
    private int n;

    private MaxHeap heap;
    private PerformanceTracker tracker;
    private int[] data;
    private Random random;

    @Setup(Level.Trial)
    public void setup() {
        random = new Random(42);
        tracker = new PerformanceTracker();
        data = random.ints(n, 0, n * 10).toArray();
        heap = new MaxHeap(n, tracker);
    }

    @Benchmark
    public void testInsert() {
        tracker.reset();
        heap = new MaxHeap(n, tracker);
        long start = System.nanoTime();
        for (int value : data) heap.insert(value);
        long end = System.nanoTime();
        tracker.writeToCSV("benchmarks.csv", "MaxHeap", "insert", n, end - start);
    }

    @Benchmark
    public void testExtractMax() {
        tracker.reset();
        heap = new MaxHeap(n, tracker);
        for (int value : data) heap.insert(value);

        long start = System.nanoTime();
        while (heap.getSize() > 0) heap.extractMax();
        long end = System.nanoTime();
        tracker.writeToCSV("benchmarks.csv", "MaxHeap", "extractMax", n, end - start);
    }

    @Benchmark
    public void testIncreaseKey() {
        tracker.reset();
        heap = new MaxHeap(n, tracker);
        for (int value : data) heap.insert(value);

        long start = System.nanoTime();
        for (int i = 0; i < Math.max(1, n / 20); i++) {
            int index = random.nextInt(heap.getSize());
            int newValue = heap.getValueAt(index) + random.nextInt(100);
            heap.increaseKey(index, newValue);
        }
        long end = System.nanoTime();
        tracker.writeToCSV("benchmarks.csv", "MaxHeap", "increaseKey", n, end - start);
    }
}
