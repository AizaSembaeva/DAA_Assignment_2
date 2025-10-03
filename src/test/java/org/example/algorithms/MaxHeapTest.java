package org.example.algorithms;

import org.example.metrics.PerformanceTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {
    private MaxHeap heap;
    private PerformanceTracker tracker;

    @BeforeEach
    void setUp() {
        tracker = new PerformanceTracker();
        heap = new MaxHeap(4, tracker);
    }


    @Test
    void testPeekOnEmptyHeapThrows() {
        assertThrows(IllegalStateException.class, () -> heap.peek());
    }

    @Test
    void testExtractMaxOnEmptyHeapThrows() {
        assertThrows(IllegalStateException.class, () -> heap.extractMax());
    }

    @Test
    void testIncreaseKeyOnEmptyHeapThrows() {
        assertThrows(IllegalArgumentException.class, () -> heap.increaseKey(0, 10));
    }


    @Test
    void testSingleInsertAndPeek() {
        heap.insert(42);
        assertEquals(42, heap.peek());
        assertEquals(1, heap.getSize());
    }

    @Test
    void testSingleInsertAndExtract() {
        heap.insert(99);
        assertEquals(99, heap.extractMax());
        assertEquals(0, heap.getSize());
    }

    @Test
    void testIncreaseKeyOnSingleElement() {
        heap.insert(10);
        heap.increaseKey(0, 20);
        assertEquals(20, heap.peek());
    }


    @Test
    void testInsertAndExtractMaintainsMaxOrder() {
        heap.insert(10);
        heap.insert(5);
        heap.insert(30);
        heap.insert(20);

        assertEquals(30, heap.extractMax());
        assertEquals(20, heap.extractMax());
        assertEquals(10, heap.extractMax());
        assertEquals(5, heap.extractMax());
    }

    @Test
    void testInsertDuplicates() {
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(3, heap.getSize());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
        assertEquals(5, heap.extractMax());
    }

    @Test
    void testIncreaseKeyMovesElementUp() {
        heap.insert(10);
        heap.insert(15);
        heap.insert(5);
        heap.increaseKey(2, 50);

        assertEquals(50, heap.peek());
    }


    @Test
    void testHeapExpandsWhenCapacityExceeded() {
        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);

        assertEquals(5, heap.getSize());
        assertEquals(5, heap.peek());
    }


    @Test
    void testToArrayContainsAllElements() {
        heap.insert(7);
        heap.insert(3);
        heap.insert(10);

        int[] arr = heap.toArray();
        assertEquals(3, arr.length);
        assertTrue(contains(arr, 7));
        assertTrue(contains(arr, 3));
        assertTrue(contains(arr, 10));
    }

    private boolean contains(int[] arr, int val) {
        for (int v : arr) if (v == val) return true;
        return false;
    }
}
