package algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/12/16 5:15 PM
 * 计数排序是一种适合于最大值和最小值的差值不是很大的排序
 */
public class CountSort {
    public static int[] countSort(int[] arr) {
        if (arr == null || arr.length < 2) return arr;
        int n = arr.length;
        int max = arr[0];
        // 寻找数组的最⼤值
        for (int i = 1; i < n; i++) {
            if (max < arr[i])
                max = arr[i];
        }
        //空间可以更小
        //创建⼤小为max的临时数组
        int[] temp = new int[max + 1]; //统计元素i出现的次数
        for (int i = 0; i < n; i++) {
            temp[arr[i]]++;
        }
        int k = 0; //把临时数组统计好的数据汇总到原数组
        for (int i = 0; i <= max; i++) {
            for (int j = temp[i]; j > 0; j--) {
                arr[k++] = i;
            }
        }
        return arr;
    }

    //优化空间
    public static int[] countSort2(int[] arr) {
        if (arr == null || arr.length < 2) return arr;
        int n = arr.length;
        int max = arr[0];
        int min = arr[0];
        // 寻找数组的最⼤值
        for (int i = 1; i < n; i++) {
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        //空间可以更小
        //创建⼤小为max的临时数组
        int tempLen = max - min + 1;
        int[] temp = new int[tempLen]; //统计元素i出现的次数
        for (int i = 0; i < n; i++) {
            temp[arr[i] - min]++;
        }
        int k = 0; //把临时数组统计好的数据汇总到原数组
        for (int i = 0; i < tempLen; i++) {
            for (int j = temp[i]; j > 0; j--) {
                arr[k++] = i;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{95,94,91,98,99,90,99,93,91,92};
//        int[] sortedArr = CountSort.countSort(arr);
//        System.out.println(Arrays.stream(sortedArr).mapToObj(String::valueOf).collect(Collectors.toList()));
        int[] sortedArr2 = CountSort.countSort(arr);
        System.out.println(Arrays.stream(sortedArr2).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
