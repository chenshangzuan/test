package algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Kled
 * @date 2021/12/16 5:41 PM
 * 桶排序就是把最⼤值和最小值之间的数进行划分，例如分成 10 个区间，10个区间对应10个桶，我们把 各元素放到对应区间的桶中去，再对每个桶中的数进行排序，可以采⽤归并排序，也可以采用快速排序 之类的。
 * 之后每个桶⾥面的数据就是有序的了了，我们在进行合并汇总。
 */
public class BucketSort {
    public static int[] BucketSort(int[] arr) {
        if (arr == null || arr.length < 2) return arr;
        int n = arr.length;
        int max = arr[0];
        int min = arr[0];
        // 寻找数组的最⼤大值与最⼩小值
        for (int i = 1; i < n; i++) {
            if (min > arr[i])
                min = arr[i];
            if (max < arr[i])
                max = arr[i];
        }
        //和优化版本的计数排序一样，弄一个⼤小为 min 的偏移值
        int d = max - min;
        //创建 d/5+1 个桶，第 i 桶存放 5*i~5*i+5-1范围的数
        int bucketNum = d / 5 + 1;
        ArrayList<LinkedList<Integer>> bucketList = new ArrayList<>
                (bucketNum); //初始化桶
        for (int i = 0; i < bucketNum; i++) {
            bucketList.add(new LinkedList<Integer>());
        }
        //遍历原数组，将每个元素放⼊入桶中
        for (int i = 0; i < n; i++) {
            bucketList.get((arr[i] - min) / d).add(arr[i] - min);
        }
        //对桶内的元素进行排序，我这里采⽤用系统自带的排序工具
        for (int i = 0; i < bucketNum; i++) {
            Collections.sort(bucketList.get(i));
        }
        //把每个桶排序好的数据进行合并汇总放回原数组
        int k = 0;
        for (int i = 0; i < bucketNum; i++) {
            for (Integer t : bucketList.get(i)) {
                arr[k++] = t + min;
            }
        }
        return arr;
    }
}
