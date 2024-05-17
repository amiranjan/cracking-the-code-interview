package sorting;

import java.util.Arrays;
import java.util.stream.Collectors;

/***
 * Merge sort is a popular sorting algorithm known for its efficiency and stability.
 * It follows the divide-and-conquer approach to sort a given array of elements.
 *
 *      Divide: Divide the list or array recursively into two halves until it can no more be divided.
 *      Conquer: Each subarray is sorted individually using the merge sort algorithm.
 *      Merge: The sorted subarrays are merged back together in sorted order.
 *             The process continues until all elements from both subarrays have been merged.
 *
 *
 *  Input                   32 27 43 10
 *  Iteration 1     38 27                 43 10
 *  Iteration 2  38       27           43       10
 *  Iteration 3     27 38                 10 43
 *  Iteration 4            10 27 38 43
 *
 *
 *  Input --> [12, 11, 13, 5, 6, 7]
     * Iteration Left = 0, Right = 1 --> [11, 12, 13, 5, 6, 7]
     * Iteration Left = 0, Right = 2 --> [11, 12, 13, 5, 6, 7]
     * Iteration Left = 3, Right = 4 --> [11, 12, 13, 5, 6, 7]
     * Iteration Left = 3, Right = 5 --> [11, 12, 13, 5, 6, 7]
     * Iteration Left = 0, Right = 5 --> [5, 6, 7, 11, 12, 13]
 * Output --> [5, 6, 7, 11, 12, 13]

 * Complexity Analysis of Merge Sort:
 * Time Complexity:

     * Best Case: O(n log n), When the array is already sorted or nearly sorted.
     * Average Case: O(n log n), When the array is randomly ordered.
     * Worst Case: O(n log n), When the array is sorted in reverse order.
     * Space Complexity: O(n), Additional space is required for the temporary array used during merging.

 * Advantages of Merge Sort:
     * Stability: Merge sort is a stable sorting algorithm, which means it maintains the relative order of equal elements in the input array.
     * Guaranteed worst-case performance: Merge sort has a worst-case time complexity of O(N logN), which means it performs well even on large datasets.
     * Simple to implement: The divide-and-conquer approach is straightforward.

 * Disadvantage of Merge Sort:
     * Space complexity: Merge sort requires additional memory to store the merged sub-arrays during the sorting process.
     * Not in-place: Merge sort is not an in-place sorting algorithm, which means it requires additional memory to store the sorted data. This can be a disadvantage in applications where memory usage is a concern.

 * Applications of Merge Sort:
     * Sorting large datasets
     * External sorting (when the dataset is too large to fit in memory)
     * Inversion counting (counting the number of inversions in an array)
     * Finding the median of an array
 * ***/
public class MergeSort {
    private int[] input;

    public MergeSort(int[] input) {
        this.input = input;
        System.out.println("********* Merge Sort ************");
        print("Input");
    }

    public void sort() {
        int left = 0;
        int right = input.length - 1;

        sort(input, left, right);

        print("Output");
    }

    public void sort(int[] nums, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;

            // Sort first and second halves
            sort(nums, left, mid);
            sort(nums, mid + 1, right);

            // Merge the sorted halves
            merge(nums, left, mid, right);
        }
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Copy data in temp array
        int[] lArr = new int[n1];
        for (int i = 0; i < n1; i++)
            lArr[i] = nums[left + i];
        // Copy data in temp array
        int[] rArr = new int[n2];
        for (int i = 0; i < n2; i++)
            rArr[i] = nums[mid + 1 + i];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (lArr[i] <= rArr[j]) {
                nums[k] = lArr[i];
                i++;
            } else {
                nums[k] = rArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            nums[k] = lArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            nums[k] = rArr[j];
            j++;
            k++;
        }

        print("Iteration Left = " + left + ", Right = " + right);
    }

    private void print(String prefix) {
        System.out.println(prefix + " --> " + Arrays.stream(input).boxed().collect(Collectors.toList()));
    }
}
