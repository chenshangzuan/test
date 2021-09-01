package algorithm.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/19 4:56 下午
 * 折半插入排序: 时间复杂度O(n*n), 空间复杂度O(1)
 */
public class BinSort {
    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};

        for (int i = 1; i < array.length; i ++){
            int tmp = array[i];

            //通过二分查找定位插入的位置
            int low = 0;
            int high = i -1;
            while(low <= high){
                int mid = (low + high)/2;
                if(array[mid] < tmp){
                    low = mid + 1;
                }else {
                    high = mid - 1;
                }
            }
            for (int j = i -1; j >= low; j--) {
                array[j + 1] = array[j];
            }
            array[low] = tmp;
        }
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
        //[21, 34, 42, 45, 64, 93]
    }
}
