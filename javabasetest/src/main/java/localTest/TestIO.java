package localTest;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author kled
 * @version $Id: localTest.TestIO.java, v 0.1 2018-07-12 11:59:37 kled Exp $
 */
public class TestIO {
    public static void main(String[] args) throws Exception{
        OutputStream os = new FileOutputStream(TestIO.class.getClassLoader().getResource("").getPath()+"/test1.txt");
        String str = "中国test";
        byte[] b = str.getBytes("UTF8"); //字节数组 -->字节流
        //byte[] b = str.getBytes("GBK");  // txt文件的默认编码是UTF8 GBK编码会导致乱码
        System.out.println(b.length);
        os.write(b);
        os.close();
    }
}
