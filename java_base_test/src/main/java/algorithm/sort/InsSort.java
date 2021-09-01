package algorithm.sort;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/19 4:56 下午
 * 插入排序: 时间复杂度O(n*n), 空间复杂度O(1)
 */
public class InsSort {

    //案例: 扑克牌理牌过程
    public static void main(String[] args) {
        int[] array = {34, 21, 45, 93, 42, 64};

        for (int i = 1; i < array.length; i ++){
            int tmp = array[i];
            for (int j = i -1 ; j >= 0; j--) {
                if(array[j] > tmp){
                    array[j + 1] = array[j];
                    if(j == 0){
                        //如果遍历至左侧第一个元素比当前元素小，则设置tmp为第一个元素
                        array[0] = tmp;
                    }
                }else {
                    //找到插入的位置
                    array[j + 1] = tmp;
                    break;
                }
            }
        }
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.toList()));
        //[21, 34, 42, 45, 64, 93]
    }
}
