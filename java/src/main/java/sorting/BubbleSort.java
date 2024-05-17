package sorting;

import java.util.Arrays;
import java.util.stream.Collectors;

/***
 * In Bubble Sort algorithm,
 *      Traverse from left and compare adjacent elements and the higher one is placed at right side.
 *      In this way, the largest element is moved to the rightmost end at first.
 *      This process is then continued to find the second largest and place it and so on until the data is sorted.
 *
 *      Input --> [6, 0, 3, 5]
 *
 *         Inner Iteration --> [0, 6, 3, 5]
 *         Inner Iteration --> [0, 3, 6, 5]
 *         Inner Iteration --> [0, 3, 5, 6]
 *       Outer Iteration --> [0, 3, 5, 6]
 *         Inner Iteration --> [0, 3, 5, 6]
 *         Inner Iteration --> [0, 3, 5, 6]
 *       Outer Iteration --> [0, 3, 5, 6]
 *     Output --> [0, 3, 5, 6]
 *
 * */
public class BubbleSort {
    private int[] input;

    public BubbleSort(int[] input) {
        this.input = input;
        System.out.println("********* Bubble Sort ************");
        print("Input");
    }

    public void sort() {
        for (int i = 0; i < input.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < input.length - i - 1; j++) {
                if (input[j] > input[j + 1]) {
                    int temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                    swapped = true;
                }
                print("Inner Iteration");
            }
            print("Outer Iteration");
            if (!swapped)
                break;
        }
        print("Output");
    }

    private void print(String prefix) {
        System.out.println(prefix + " --> " + Arrays.stream(input).boxed().collect(Collectors.toList()));
    }
}
