package java_base.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kled
 * @date 2021/11/15 7:16 下午
 */
public class YangHuiTriangle {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> firstNumRow = Collections.singletonList(1);
        ret.add(firstNumRow);
        for (int i = 1; i < numRows; i++) {
            List<Integer> lastNumRow = ret.get(i - 1);
            List<Integer> numRow = new ArrayList<>();
            for (int j = 0; j < lastNumRow.size() + 1; j++) {
                if(j == 0 || j == lastNumRow.size()){
                    numRow.add(1);
                }else {
                    numRow.add(lastNumRow.get(j - 1) + lastNumRow.get(j));
                }
            }
            ret.add(numRow);
        }
        return ret;
    }

    //优解：当前行第 ii 项的计算只与上一行第 i-1i−1 项及第 ii 项有关。
    public List<Integer> getRow2(int rowIndex) {
        List<Integer> row = new ArrayList<Integer>();
        row.add(1);
        for (int i = 1; i <= rowIndex; ++i) {
            //补一个初始值
            row.add(0);
            for (int j = i; j > 0; --j) {
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }
        return row;
    }

    //最优解：公式求解
    public List<Integer> getRow3(int rowIndex) {
        List<Integer> row = new ArrayList<Integer>();
        row.add(1);
        for (int i = 1; i <= rowIndex; ++i) {
            row.add((int) ((long) row.get(i - 1) * (rowIndex - i + 1) / i));
        }
        return row;
    }

    public static void main(String[] args) {
        System.out.println(new YangHuiTriangle().generate(5));
    }
}
