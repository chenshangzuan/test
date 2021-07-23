package spark;

import org.apache.spark.launcher.InProcessLauncher;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/7/1 11:41 上午
 */
public class SparkLaunch {
    //zip -d hadoop_test-1.0-SNAPSHOT.jar 'META-INF/*.RSA' 'META-INF/*.DSA' 'META-INF/*.SF'
    public static void main(String[] args) throws IOException {
        if("inProcess".equals(args[0])){
            inProcessLauncher(args);
        }else {
            sparkLauncher(args);
        }
    }

    //java -jar hadoop_test-1.0-SNAPSHOT.jar process|handler ${SPARK_HOME}
    public static void sparkLauncher(String[] args) throws IOException {
        if("process".equals(args[0])){
            System.out.println("********** spark process start **********");
            Process process = new SparkLauncher()
                    .setAppName("sparkProcess")
                    .setSparkHome(args[1])
                    .setMaster("yarn")
                    .setConf(SparkLauncher.DRIVER_MEMORY, "2g")
                    .setConf(SparkLauncher.EXECUTOR_MEMORY, "1g")
                    .setConf(SparkLauncher.EXECUTOR_CORES, "3")
                    .setAppResource("/tmp/hadoop_test-1.0-SNAPSHOT.jar")
                    .setMainClass("spark.remote.SparkDsTest")
//                .addAppArgs("I come from Launcher")
                    .setDeployMode("client")
                    .launch();

            try {
                process.waitFor();
                System.out.println("**********  spark process end  **********");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else if("handler".equals(args[0])){
            SparkAppHandle handler = new SparkLauncher()
                    .setAppName("sparkLauncher")
                    .setSparkHome(args[1])
                    .setMaster("yarn")
                    .setConf(SparkLauncher.DRIVER_MEMORY, "2g")
                    .setConf(SparkLauncher.EXECUTOR_MEMORY, "1g")
                    .setConf(SparkLauncher.EXECUTOR_CORES, "3")
                    .setAppResource("/tmp/hadoop_test-1.0-SNAPSHOT.jar")
                    .setMainClass("spark.remote.SparkDsTest")
//                .addAppArgs("I come from Launcher")
                    .setDeployMode("client")
                    .startApplication(new MySparkListener());

            handleStatusCheck(handler);
        }
    }

    //java -jar hadoop_test-1.0-SNAPSHOT.jar inProcess ${SPARK_HOME}/conf/spark-defaults.conf
    //失败: org.apache.spark.SparkException: Could not load YARN classes. This copy of Spark may not have been compiled with YARN support.
    public static void inProcessLauncher(String[] args) throws IOException {
        System.out.println("inProcessLauncher start");
        SparkAppHandle handler = new InProcessLauncher()
                .setPropertiesFile(args[1])
                .setAppName("inProcessLauncher")
                .setMaster("yarn")
                .setConf(SparkLauncher.DRIVER_MEMORY, "2g")
                .setConf(SparkLauncher.EXECUTOR_MEMORY, "1g")
                .setConf(SparkLauncher.EXECUTOR_CORES, "3")
                .setConf("spark.yarn.archive", "hdfs://172.16.5.56:8020/spark-archive/spark3.1.2.zip")
                .setAppResource("/tmp/hadoop_test-1.0-SNAPSHOT.jar")
                .setMainClass("spark.remote.SparkDsTest")
//                .addAppArgs("I come from Launcher")
                .setDeployMode("client")
                .setVerbose(true)
                .startApplication(new MySparkListener());

        handleStatusCheck(handler);
        System.out.println("inProcessLauncher end");
    }

    private static void handleStatusCheck(SparkAppHandle handler){
        while (!"FINISHED".equalsIgnoreCase(handler.getState().toString()) && !"FAILED".equalsIgnoreCase(handler.getState().toString())) {
            System.out.println("id    " + handler.getAppId());
            System.out.println("state " + handler.getState());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MySparkListener implements SparkAppHandle.Listener{
        @Override
        public void stateChanged(SparkAppHandle handle) {
            System.out.println("**********  state  changed " + handle.getState().toString() + " **********");
            if("FAILED".equalsIgnoreCase(handle.getState().toString())) {
                handle.getError().ifPresent(throwable -> System.out.println(throwable.toString()));
            }
        }

        @Override
        public void infoChanged(SparkAppHandle handle) {
            System.out.println("**********  info  changed " + handle.getState().toString() + " **********");
        }
    }
}

