package org.example.algorithms;

import org.example.metrics.PerformanceTracker;

import java.util.Arrays;

public class MaxHeap {
    private int[] heap;
    private int size;
    private PerformanceTracker tracker;

    public MaxHeap(int capacity, PerformanceTracker tracker) {
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = tracker;
    }

    public int getSize() {
        return size;
    }

    public void insert(int value) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
            tracker.incrementAllocations();
        }
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
        tracker.incrementArrayAccesses();
        heap[0] = heap[size - 1];

        size--;
        heapifyDown(0);
        return max;
    }

    public void increaseKey(int index, int newValue) {
        if (index < 0 || index >= size) throw new IllegalArgumentException("Index out of bounds");

        tracker.incrementArrayAccesses();
        if (newValue < heap[index]) throw new IllegalArgumentException("New value is smaller than current");

        tracker.incrementArrayAccesses();
        heap[index] = newValue;
        heapifyUp(index);
    }

    private void heapifyUp(int index) {
        tracker.updateDepth(1);
        while (index > 0) {
            int parent = (index - 1) / 2;

            tracker.incrementArrayAccesses();
            tracker.incrementArrayAccesses();
            tracker.incrementComparisons();

            if (heap[index] > heap[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        tracker.updateDepth(1);
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size) {
                tracker.incrementArrayAccesses();
                tracker.incrementArrayAccesses();
                tracker.incrementComparisons();
                if (heap[left] > heap[largest]) {
                    largest = left;
                }
            }
            if (right < size) {
                tracker.incrementArrayAccesses();
                tracker.incrementArrayAccesses();
                tracker.incrementComparisons();
                if (heap[right] > heap[largest]) {
                    largest = right;
                }
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        tracker.incrementSwaps();

        tracker.incrementArrayAccesses();
        tracker.incrementArrayAccesses();
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

        tracker.incrementArrayAccesses();
        tracker.incrementArrayAccesses();
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
}
