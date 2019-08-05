package spark;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * <table border="1">
 * <tr><th>@Description: spark远程提交到集群</th></tr>
 * <tr><td>@Date:Created in 2018-8-29</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class SparkLauncherDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        SparkAppHandle handle = new SparkLauncher()
                .setAppResource("F:\\idea\\myLearn\\learn\\out\\artifacts\\study\\study.jar")
                .setMainClass("com.ais.brm.study.spark.SparkSubmit")
                .setMaster("spark://node0:7077")
                .setConf(SparkLauncher.DRIVER_MEMORY, "2g")
                .startApplication();
        handle.addListener(new SparkAppHandle.Listener() {
            @Override
            public void stateChanged(SparkAppHandle handle) {
                if (handle.getState().isFinal()) {
                    countDownLatch.countDown();
                }
                System.out.println("state:" + handle.getState().toString());
            }

            @Override
            public void infoChanged(SparkAppHandle handle) {
                System.out.println("Info:" + handle.getState().toString());
            }
        });
        System.out.println("The task is executing, please wait ....");
        //线程等待任务结束
        countDownLatch.await();
        System.out.println("The task is finished!");
    }

}
