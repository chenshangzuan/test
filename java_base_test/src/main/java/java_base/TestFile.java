package java_base;

import java.io.File;
import java.io.IOException;

/**
 * @author chenpc
 * @version $Id: java_base.TestFile.java, v 0.1 2018-05-02 10:15:15 chenpc Exp $
 */
public class TestFile {

    private static final String filePath = "/test";

    /**
     * 方法注释
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        /*
        方法内注释
         */

        // 行注释
        File directory = new File("../");//设定为当前文件夹
        System.out.println("absolute-path:"+directory.getAbsolutePath()); //绝对路径
        System.out.println("path:"+directory.getPath());                  //相对路径
        System.out.println("canonical-path:"+directory.getCanonicalPath()); //解析过的绝对路径
    }

}
