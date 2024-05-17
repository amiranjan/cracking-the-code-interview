package sorting;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertionSort {

    private int[] input;

    public InsertionSort(int[] input) {
        this.input = input;
        System.out.println("********* Insertion Sort ************");
        print("Input");
    }

    public void sort() {
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;
            while(j >= 0  && input[j] > key) {
                input[j + 1] = input[j];
                j--;
            }
            input[j + 1] = key;
            print("Iteration " + i);
        }
        print("Output");
    }

    private void print(String prefix) {
        System.out.println(prefix + " --> " + Arrays.stream(input).boxed().collect(Collectors.toList()));
    }
}
