package sorting;

public class SortingRunner {
    public static void main(String[]args) {
        SelectionSort selectionSort = new SelectionSort(new int[]{64, 25, 12, 22, 11});
        selectionSort.sort();


        //BubbleSort bubbleSort = new BubbleSort(new int[]{6, 0, 3, 5});
        BubbleSort bubbleSort = new BubbleSort(new int[]{64, 34, 25, 12, 22, 11, 90});
        bubbleSort.sort();

        MergeSort mergeSort = new MergeSort(new int[]{ 12, 11, 13, 5, 6, 7 });
        mergeSort.sort();

        InsertionSort insertionSort = new InsertionSort(new int[]{ 12, 11, 13, 5, 6});
        insertionSort.sort();
    }
}
