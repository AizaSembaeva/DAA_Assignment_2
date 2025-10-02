package org.example.metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PerformanceTracker {
    private long comparisons;
    private long swaps;
    private long allocations;
    private int maxDepth;
    private int currentDepth;

    public void incrementComparisons() { comparisons++; }
    public void incrementSwaps() { swaps++; }
    public void incrementAllocations() { allocations++; }
    public void updateDepth(int depth) {
        currentDepth = depth;
        if (depth > maxDepth) maxDepth = depth;
    }
    public void reset() {
        comparisons = 0; swaps = 0; allocations = 0;
        maxDepth = 0; currentDepth = 0;
    }

    public void writeToCSV(String fileName, String algorithm, String inputCase,
                           int n, long timeNs) {
        boolean writeHeader = !new File(fileName).exists();
        try (FileWriter writer = new FileWriter(fileName, true)) {
            if (writeHeader) {
                writer.write("algorithm,case,n,time_ns,comparisons,allocations,maxDepth\n");
            }
            writer.write(String.format(
                    "%s,%s,%d,%d,%d,%d,%d%n",
                    algorithm,
                    inputCase,
                    n,
                    timeNs,
                    comparisons,
                    allocations,
                    maxDepth
            ));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в CSV: " + e.getMessage(), e);
        }
    }
}
