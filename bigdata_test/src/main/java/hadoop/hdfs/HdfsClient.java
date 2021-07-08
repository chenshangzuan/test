package hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Kled
 * @date 2021/6/18 11:33 上午
 */
public class HdfsClient {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {



//        FileSystem fileSystem = FileSystem.get(hadoop.HadoopUtils.initConf());
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://172.16.5.56:8020"), new Configuration(), "root");
        //System.out.println(fileSystem.getFileStatus(new Path("/0617/test")).getPath().toString()); //hdfs://172.16.5.56:8020/0617/test

//        fileSystem.mkdirs(new Path("/0617/test"));
        //...
        fileSystem.close();
    }
}
