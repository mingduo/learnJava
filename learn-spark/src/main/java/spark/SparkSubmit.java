package spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-8-28</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class SparkSubmit {

    public static void main(String[] args) throws IOException {


        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("spark://node0:7077");
        conf.setJars(new String[]{"F:\\idea\\myLearn\\learn\\out\\artifacts\\study_jar\\study.jar"});

        JavaSparkContext sc=new JavaSparkContext(conf);

        List<Integer> list = Arrays.asList(5, 6, 4, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4);

        JavaRDD<Integer> rdd = sc.parallelize(list, 2);
        JavaRDD<Integer> rdd2 = sc.parallelize(list2, 2);

        //求union
        JavaRDD<Integer> union = rdd.union(rdd2);
        System.out.println("union=>" + union.collect());
        //求交集
        JavaRDD<Integer> intersection = rdd.intersection(rdd2);
        System.out.println("intersection=>" + intersection.collect());
        //按key进行分组
        JavaRDD<Integer> distinct = union.distinct();
        System.out.println("distinct=>" + distinct.collect());
        sc.stop();
    }



}
