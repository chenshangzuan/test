package java_base;

/**
 * @author Kled
 * @date 2021/8/17 10:03 上午
 */
public class TestLoveShape {
    public static void main(String[] args) {
        System.out.println(callBack("*"));
    }

    public static String callBack(String input) {

        int[] array = {0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1,
                0, 0, 1, 1, 4, 5, 2, 3, 4, 1, 0, 1,

                0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0};

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {

            if (i % 7 == 0)

                sb.append("  \n");

            if (array[i] == 0)

                sb.append("   ");

            else if (array[i] == 4)

                sb.append("  ");

            else if (array[i] == 5)

                sb.append("  ");

            else if (array[i] == 2)

                sb.append("  姜 姜");

            else if (array[i] == 3)

                sb.append("  ");

            else
                sb.append("  " + input);

        }

        return sb.toString();
    }

//    public static void main(String[] args) {
//        for (int i = 0, k = 0; i < 14; i++) {// 打印行
//
//            // 上部分 上分为 四个部分
//
//            if (i < 3) {
//
//                for (int j = 0; j < 5 - 2 * i; j++) {// 1、空心
//
//                    System.out.print(" ");
//
//                }
//
//                if (i == 2) {// 2、*
//
//                    for (int j = 0; j < 6 + 4 * i - 1; j++) {
//
//                        System.out.print("*");
//
//                    }
//
//                    for (int j = 0; j < 7 - 4 * i + 2; j++) {// 3、空心
//
//                        System.out.print(" ");
//
//                    }
//
//                    for (int j = 0; j < 6 + 4 * i - 1; j++) {// 4、*
//
//                        System.out.print("*");
//
//                    }
//
//                } else {
//
//                    for (int j = 0; j < 6 + 4 * i; j++) {// 2、*
//
//                        System.out.print("*");
//
//                    }
//
//                    for (int j = 0; j < 7 - 4 * i; j++) {// 3、空心
//
//                        System.out.print(" ");
//
//                    }
//
//                    for (int j = 0; j < 6 + 4 * i; j++) {// 4、*
//
//                        System.out.print("*");
//
//                    }
//
//                }
//
//            } else if (i < 6) {// 中间
//
//                for (int j = 0; j < 29; j++) {
//
//                    System.out.print("*");
//
//                }
//
//            } else {// 下部分 6
//
//                if (i == 13) {
//
//                    for (int j = 0; j < 2 * (i - 6); j++) {// 打印空格
//
//                        System.out.print(" ");
//
//                    }
//
//                    System.out.print("*");
//
//                } else {
//
//                    for (int j = 0; j < 2 * (i - 6) + 1; j++) {// 打印空格
//
//                        System.out.print(" ");
//
//                    }
//
//                    for (int j = 1; j < 28 - 4 * k; j++) {
//
//                        System.out.print("*");
//
//                    }
//
//                    k++;
//
//                }
//
//            }
//
//            System.out.println();// 换行
//
//        }
//    }
}
