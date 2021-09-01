package hadoop.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Kled
 * @date 2021/6/18 11:33 上午
 */
public class HdfsClient {
//    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//        //方式一：
////        FileSystem fileSystem = FileSystem.get(hadoop.HadoopUtils.initConf());
//        //方式二：
////        FileSystem.newInstance(hadoop.HadoopUtils.initConf())
//        //方式三:
//        FileSystem fileSystem = FileSystem.get(new URI("hadoop.hdfs://172.16.5.56:8020"), new Configuration(), "root");
////        System.out.println(fileSystem.getFileStatus(new Path("/0617/test")).getPath().toString()); //hadoop.hdfs://172.16.5.56:8020/0617/test
////        fileSystem.mkdirs(new Path("/0617/test"));
//        fileSystem.close();
//    }

    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        mergeFileDownload();
    }

    //合并远端文件，并下载
    public static void mergeFileDownload() throws IOException, URISyntaxException, InterruptedException {
        //1:获取FileSystem（分布式文件系统）
        FileSystem remoteFileSystem = FileSystem.get(new URI("hdfs://172.16.5.56:8020"), new Configuration(),"root");

        //2:获取hdfs小文件目录详情
        FileStatus[] remoteFileStatuses = remoteFileSystem.listStatus(new Path("/mergeInput"));

        //3:创建本地大文件
        LocalFileSystem localFileSystem = FileSystem.getLocal(new Configuration());
        FSDataOutputStream localFsDataOutputStream = localFileSystem.create(new Path("/tmp/mergeFile.txt"));

        //4:遍历远端每个文件，获取每个文件的输入流
        for (FileStatus remoteFileStatus : remoteFileStatuses) {
            FSDataInputStream remoteFsDataInputStream = remoteFileSystem.open(remoteFileStatus.getPath());

            //5:将小文件的数据复制到本地大文件
            IOUtils.copy(remoteFsDataInputStream, localFsDataOutputStream);
            IOUtils.closeQuietly(remoteFsDataInputStream);
        }

        IOUtils.closeQuietly(localFsDataOutputStream);
        IOUtils.closeQuietly(localFileSystem);
        IOUtils.closeQuietly(remoteFileSystem);
    }

    //合并本地文件，并上传
    public static void mergeFileUpload() throws URISyntaxException, IOException, InterruptedException {
        //1:获取FileSystem（分布式文件系统）
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://172.16.5.56:8020"), new Configuration(),"root");
        //2:获取hdfs大文件的输出流
        FSDataOutputStream outputStream = fileSystem.create(new Path("/test_big.txt"));

        //3:获取一个本地文件系统
        LocalFileSystem localFileSystem = FileSystem.getLocal(new Configuration());

        //4:获取本地文件夹下所有文件的详情
        FileStatus[] fileStatuses = localFileSystem.listStatus(new Path("D:\\input"));

        //5:遍历每个文件，获取每个文件的输入流
        for (FileStatus fileStatus : fileStatuses) {
            FSDataInputStream inputStream = localFileSystem.open(fileStatus.getPath());

            //6:将小文件的数据复制到大文件
            IOUtils.copy(inputStream, outputStream);
            IOUtils.closeQuietly(inputStream);
        }

        //7:关闭流
        IOUtils.closeQuietly(outputStream);
        localFileSystem.close();
        fileSystem.close();
    }
}
