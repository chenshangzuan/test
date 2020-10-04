package localTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author kled
 * @version $Id: TestFile2Zip.java, v 0.1 2019-03-11 14:26:11 kled Exp $
 */
public class TestFile2Zip {

    //public static void main(String[] args) throws IOException {
    //    //这个是文件夹的绝对路径，如果想要相对路径就自行了解写法
    //    String sourceFile = "/Users/chenpc/test_file";
    //    //这个是压缩之后的文件绝对路径
    //    FileOutputStream fos = new FileOutputStream(
    //            "/Users/chenpc/test_file.zip");
    //    ZipOutputStream zipOut = new ZipOutputStream(fos);
    //    File fileToZip = new File(sourceFile);
    //
    //    zipFile(fileToZip, "kled_" + fileToZip.getName(), zipOut);
    //    zipOut.close();
    //    fos.close();
    //
    //}
    //
    //public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
    //    if (fileToZip.isHidden()) {
    //        System.out.println("aaa");
    //        return;
    //    }
    //    if (fileToZip.isDirectory()) {
    //        if (fileName.endsWith("/")) {
    //            zipOut.putNextEntry(new ZipEntry(fileName));
    //            zipOut.closeEntry();
    //        } else {
    //            zipOut.putNextEntry(new ZipEntry(fileName + "/"));
    //            zipOut.closeEntry();
    //        }
    //        File[] children = fileToZip.listFiles();
    //        for (File childFile : children) {
    //            zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
    //        }
    //        return;
    //    }
    //    FileInputStream fis = new FileInputStream(fileToZip);
    //    ZipEntry zipEntry = new ZipEntry(fileName);
    //    zipOut.putNextEntry(zipEntry);
    //    byte[] bytes = new byte[1024];
    //    int length;
    //    while ((length = fis.read(bytes)) >= 0) {
    //        zipOut.write(bytes, 0, length);
    //    }
    //    fis.close();
    //}
}
