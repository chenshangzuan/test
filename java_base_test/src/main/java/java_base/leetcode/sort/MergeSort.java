package java_base.leetcode.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/12/20 3:04 PM
 */
public class MergeSort {

    public void mergeSort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = start + (end - start) / 2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    private void merge(int[] arr, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;
        while (i <= mid && j <= end) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= end) {
            temp[k++] = arr[j++];
        }

        for (int l = 0; l < k; l++) {
            arr[start + l] = temp[l];
        }
    }

    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};
        new MergeSort().mergeSort(array, 0, array.length - 1);
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
