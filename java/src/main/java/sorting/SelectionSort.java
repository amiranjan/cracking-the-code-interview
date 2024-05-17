package sorting;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * // Identify the minimum value in each pass
 * // Swap it with the element where pass was started
 * // Time: O(N^2)
 * // Space: O(1)
 * // Advantages of Selection Sort Algorithm
 * //    1. Simple and easy to understand.
 * //    2. Works well with small datasets.
 * // Disadvantages of the Selection Sort Algorithm
 * //    1. Selection sort has a time complexity of O(n^2) in the worst and average case.
 * //    2. Does not work well on large datasets.
 * //    3. Does not preserve the relative order of items with equal keys which means it is not stable.
 *
 *
 *       Input --> [64, 25, 12, 22, 11]
 *           Iteration 1 --> [11, 25, 12, 22, 64]
 *           Iteration 2 --> [11, 12, 25, 22, 64]
 *           Iteration 3 --> [11, 12, 22, 25, 64]
 *           Iteration 4 --> [11, 12, 22, 25, 64]
 *       Output --> [11, 12, 22, 25, 64]
 *
 * **/

public class SelectionSort {
    private int[] input;

    public SelectionSort(int[] input) {
        this.input = input;
        System.out.println("********* Selection Sort ************");
        print("Input");
    }

    public void sort() {
        for (int i = 0; i < input.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < input.length; j++) {
                if (input[minIndex] > input[j])
                    minIndex = j;
            }
            int temp = input[i];
            input[i] = input[minIndex];
            input[minIndex] = temp;
            print("Iteration " + (i + 1));
        }
        print("Output");
    }

    private void print(String prefix) {
        System.out.println(prefix + " --> " + Arrays.stream(input).boxed().collect(Collectors.toList()));
    }
}
