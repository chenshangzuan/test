package algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/20 3:34 下午
 * 归并排序，时间复杂度O(nlog2n), 空间复杂度O(n)
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};

        mergeSort(array, 0, array.length-1);

        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
    }

    private static void mergeSort(int array[], int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            //折半成两个小集合，分别进行递归
            merge(array, start, mid, end); //把两个有序小集合，归并成一个大集合
        }
    }

    private static void merge(int array[], int start, int mid, int end) {
        int[] tempArray = new int[end - start + 1];
        int p1 = start;
        int p2 = mid + 1;
        int p = 0;
        //比较两个小集合的元素，依此放入大集合
        while ((p1 <= mid) && (p2 <= end)) {
            if (array[p1] <= array[p2]) {
                tempArray[p++] = array[p1++];
            } else {
                tempArray[p++] = array[p2++];
            }
        }
        //左侧小集合还有剩余元素，依次放入大集合尾部
        while (p1 <= mid)
        {
            tempArray[p++] = array[p1++];
        }
        //右侧小集合还有剩余元素，依次放入大集合尾部
        while (p2 <= end)
        {
            tempArray[p++] = array[p2++];
        }
        //把大集合的元素复制回原数组
        for (int i = 0; i < end - start + 1; i++) {
            array[i + start] = tempArray[i];
        }
    }
}
