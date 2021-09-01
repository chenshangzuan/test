package algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/20 11:18 上午
 * 快速排序，优化冒泡排序，时间复杂度O(nlog2n)
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};

        sort2(array, 0, array.length - 1);

        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
    }

    private static void sort1(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int targetIndex = low;
        int target = array[targetIndex];
        int start = low + 1;
        int end = high;
        int tmp;
        while (start <= end) {
            if (targetIndex < start) {
                if (target > array[end]) {
                    tmp = array[end];
                    array[end] = target;
                    array[targetIndex] = tmp;
                    targetIndex = end;
                    if (start == end) {
                        break;
                    }
                    continue;
                }
                end--;
            }

            if (targetIndex > start) {
                if (array[start] > target) {
                    tmp = array[start];
                    array[start] = target;
                    array[targetIndex] = tmp;
                    targetIndex = start;
                }
                start++;
            }
        }
        //分治
        sort1(array, low, targetIndex - 1);
        sort1(array, targetIndex + 1, high);
    }


    private static void sort2(int[] arr, int low, int high) {
        int i = low, j = high, temp; //(1)初始化i、j
        if (low >= high) //递归的终止条件
        {
            return;
        }
        temp = arr[i]; // (2) 以第一个数组元素为基准值,保存到temp中
        while (i < j) {
            while (j > i && arr[j] >= temp) {  //(3)j--,找小值
                j--;
            }
            arr[i] = arr[j]; //保存小值
            while (i < j && arr[i] <= temp) //(4)i++,找大值
            {
                i++;
            }
            arr[j--] = arr[i]; //保存大值
        }
        arr[i] = temp;
        sort2(arr, low, i - 1); //左侧元素递归
        sort2(arr, i + 1, high);
    }
}
