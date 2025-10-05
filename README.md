# Max-Heap Implementation with Performance Analysis

## Overview
This project implements a Max-Heap in Java with support for:

- `insert(int key)` — add a new element
- `extractMax()` — remove and return the maximum element
- `increaseKey(int index, int newValue)` — increase an element's value

The implementation is integrated with a `PerformanceTracker` to measure key metrics like comparisons, swaps, array accesses, memory allocations, and recursion depth.

## Features
- Efficient heap operations with **O(log n)** worst-case time complexity.
- Metrics tracking for empirical validation.
- CLI `BenchmarkRunner` for running performance tests on different input sizes and scenarios:
    - **Input sizes:** 100, 1,000, 10,000, 100,000
    - **Input types:** random, sorted, reversed, nearly-sorted
- CSV export of metrics for further analysis.

## Installation & Usage
1. Clone the repository:

        git clone https://github.com/AizaSembaeva/DAA_Assignment_2.git
        cd assignment2-maxheap

2. Build with Maven:

        mvn clean install

3. Run tests:

       mvn test

4. Run benchmark:

       java -cp target/assignment2-maxheap-1.0-SNAPSHOT.jar org.example.cli.BenchmarkRunner

Metrics CSV includes:

       n,scenario,inputType,time,comparisons,swaps,allocations,arrayAccesses,maxDepth.

## Complexity Analysis

| Operation       | Worst Case (O) | Average Case (Θ) | Best Case (Ω) | Space Complexity |
|-----------------|----------------|-----------------|---------------|-----------------|
| `insert`        | O(log n)       | Θ(log n)        | Ω(1)          | O(1)            |
| `extractMax`    | O(log n)       | Θ(log n)        | Ω(log n)      | O(1)            |
| `increaseKey`   | O(log n)       | Θ(log n)        | Ω(1)          | O(1)            |

### Observations from benchmarks
- `insert` and `increaseKey` are fastest on **reversed** or **nearly-sorted** data due to minimal heap adjustments.
- `extractMax` is more expensive on **sorted data** because the heap property requires more swaps.
- Comparisons and array accesses grow roughly proportionally to `n log n`, confirming theoretical expectations.
- Memory allocations are minimal due to **in-place array implementation** and **dynamic resizing**.

### Example metrics for n = 10,000 (random)
- `insert`: 317,700 ns, 22,671 comparisons, 55,348 array accesses
- `extractMax`: 2,070,200 ns, 231,397 comparisons, 394,773 array accesses
- `increaseKey`: 713,300 ns, 27,901 comparisons, 80,813 array accesses

## Release Notes (v1.0)
- Fully implemented Max-Heap with insert, extractMax, increaseKey
- Integrated PerformanceTracker with detailed metrics
- Added CLI benchmark and CSV export
- Covered with comprehensive JUnit5 tests
- All edge cases fixed
- Verified asymptotic complexity and empirical benchmarks

## Conclusion
- The Max-Heap implementation is **correct, efficient, and scalable**.
- Empirical benchmarks confirm **O(log n)** behavior for all main operations.
- Optimizations (dynamic array resizing, minimal swaps) improve practical performance, especially on nearly-sorted data.
- Metrics tracking allows **detailed performance analysis** and verification of theoretical complexity.