package algorithm.sort;

/**
 * @author Kled
 * @date 2021/8/20 3:11 下午
 * 堆排序，时间复杂度O(nlog2n)
 */
public class HeapSort {
    //堆排序就是把堆顶的元素与最后⼀一个元素交换，交换之后破坏了了堆的特性，我们再把堆中剩余的元素再 次构成⼀一个⼤大顶堆，然后再把堆顶元素与最后第⼆二个元素交换....如此往复下去
    public static void main(String[] args) {

        int[] arr = {34, 21, 45, 93, 42, 64};
        int n = arr.length;
        //通过向下调整算法建立起最大堆，i = n/2 - 1从非子叶节点开始，至根节点
        for (int i = (n - 2) / 2; i >= 0; i--) {
            adjustDown(arr, i, n - 1);
        }
        //进行堆排序
        for (int i = n - 1; i >= 1; i--) {
            // 把堆顶元素与最后⼀一个元素交换
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            // 把打乱的堆进⾏行行调整，恢复堆的特性
            adjustDown(arr, 0, i - 1);
        }
    }

    //堆向下调整
    private static void adjustDown(int[] arr, int parent, int n) {
        //临时保存要下沉的元素
        int temp = arr[parent]; //定位左孩⼦子节点的位置
        int child = 2 * parent + 1; //开始下沉
        while (child <= n) {
            // 如果右孩⼦节点⽐左孩⼦⼤，则定位到右孩⼦
            if (child + 1 <= n && arr[child] < arr[child + 1])
                child++;
            // 如果孩⼦子节点⼩小于或等于⽗父节点，则下沉结束
            if (arr[child] <= temp) break;
            // ⽗父节点进⾏下沉
            arr[parent] = arr[child];
            parent = child;
            child = 2 * parent + 1;
        }
        arr[parent] = temp;
    }
}
