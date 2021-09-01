package algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/20 2:41 下午
 * 选择排序，时间复杂度O(n*n)
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};

        //每次遍历获取最小值，并交换至合适的位置。遍历n-1次
        for (int i = 0; i < array.length - 1; i++) {
            int min = array[i];
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if(min > array[j]){
                    min = array[j];
                    minIndex = j;
                }

            }
            int tmp = array[i];
            array[i] = min;
            array[minIndex] = tmp;
        }
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
