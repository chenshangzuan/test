package algorithm.sort;

/**
 * @author Kled
 * @date 2021/8/20 3:11 下午
 * 堆排序，时间复杂度O(nlog2n)
 */
public class HeapSort {
    public static void main(String[] args) {

        int[] array = {34, 21, 45, 93, 42, 64};
        int len = array.length;
        int end = len -1;
        //通过向下调整算法建立起二叉堆
        for (int i = (len - 2) / 2; i >= 0; i--) {
            adjustDown(array, len, i);
        }
        //每次将堆顶元素保存到最后一个节点
        while (end > 0) {
            int tmp = array[end];
            array[end] = array[0];
            array[0] = tmp;
            //选出次小的数
            adjustDown(array, end, 0);
            end--;
        }
    }

    //堆向下调整
    private static void adjustDown(int[] array, int len, int i) {
        //...
    }
}
