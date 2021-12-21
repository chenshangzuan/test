package java_base.leetcode.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/12/20 3:37 PM
 */
public class QuickSort {

    public void quickSort(int[] arr, int start, int end) {
        if (start > end) {
            return;
        }
        int splitPos = split(arr, start, end);
        quickSort(arr, start, splitPos);
        quickSort(arr, splitPos + 1, end);
    }

    private int split(int[] arr, int start, int end) {
        int pivot = arr[start];
        int i = start + 1, j = end;
        while (true) {
            while (arr[i] <= pivot && i <= j) {
                i++;
            }

            while (arr[j] >= pivot && i <= j) {
                j--;
            }
            if (i >= j) {
                break;
            }
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        arr[j] = arr[start];
        arr[start] = pivot;
        return j;
    }

    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};
        new QuickSort().quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
