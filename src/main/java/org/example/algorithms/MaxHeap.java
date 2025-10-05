package org.example.algorithms;

import org.example.metrics.PerformanceTracker;

import java.util.Arrays;

public class MaxHeap {
    private int[] heap;
    private int size;
    private final PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
    }

    public int getSize() {
        return size;
    }

    private void ensureCapacity() {
        if (size >= heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
            tracker.incrementAllocations();
        }
    }

    public void insert(int value) {
        ensureCapacity();
        tracker.incrementArrayAccesses();
        heap[size] = value;
        heapifyUp(size);
        size++;
    }

    public int extractMax() {
        if (size == 0) throw new IllegalStateException("Heap is empty");

        tracker.incrementArrayAccesses();
        int max = heap[0];

        tracker.incrementArrayAccesses();
        heap[0] = heap[size - 1];
        size--;

        if (size > 0) heapifyDown(0);
        return max;
    }

    public void increaseKey(int index, int newValue) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);

        tracker.incrementArrayAccesses();
        if (newValue < heap[index])
            throw new IllegalArgumentException("New value is smaller than current");

        tracker.incrementArrayAccesses();
        heap[index] = newValue;
        heapifyUp(index);
    }

    private void heapifyUp(int index) {
        tracker.updateDepth(1);
        int current = heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            tracker.incrementComparisons();
            tracker.incrementArrayAccesses();
            if (current <= heap[parent]) break;

            tracker.incrementArrayAccesses();
            heap[index] = heap[parent];
            index = parent;
        }
        tracker.incrementArrayAccesses();
        heap[index] = current;
    }

    private void heapifyDown(int index) {
        tracker.updateDepth(1);
        int current = heap[index];

        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses();
                if (heap[left] > heap[largest]) largest = left;
            }

            if (right < size) {
                tracker.incrementComparisons();
                tracker.incrementArrayAccesses();
                if (heap[right] > heap[largest]) largest = right;
            }

            if (largest == index) break;

            tracker.incrementArrayAccesses();
            heap[index] = heap[largest];
            index = largest;
        }

        tracker.incrementArrayAccesses();
        heap[index] = current;
    }

    public int peek() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        tracker.incrementArrayAccesses();
        return heap[0];
    }

    public int[] toArray() {
        tracker.incrementArrayAccesses();
        return Arrays.copyOf(heap, size);
    }

    public int getValueAt(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        tracker.incrementArrayAccesses();
        return heap[index];
    }
}
