package algorithm.sort;

/**
 * @author Kled
 * @date 2021/8/20 2:33 下午
 * 希尔排序(分组插入排序)，优化插入排序，时间复杂度O(n*n)
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {34, 21, 45, 93, 42, 64};
        int d = arr.length; //希尔排序增量
        while (d > 1) {
            d = d / 2; //希尔增量，每次折半
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < arr.length; i = i + d) {
                    int temp = arr[i];
                    int j;
                    for (j = i - d; j >= 0 && arr[j] > temp; j = j - d) {
                        arr[j + d] = arr[j];
                    }
                    arr[j + d] = temp;
                }
            }
        }
    }
}
