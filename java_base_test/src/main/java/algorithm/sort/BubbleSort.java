package algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/20 10:52 上午
 * 冒泡排序, 时间复杂度O(n*n), 空间复杂度O(1)
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};

        //循环n-1次
        for (int i = 0; i < array.length - 1; i++) {
            //每次循环，将最大值往后移动(冒泡)
            for (int j = 0; j < array.length - i; j++) {
                if(array[j] > array[j + 1]){
                    int tmp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
    }
}
