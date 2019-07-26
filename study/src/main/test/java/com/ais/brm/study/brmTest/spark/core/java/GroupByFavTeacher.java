package com.ais.brm.study.brmTest.spark.core.java;

import org.apache.spark.Partitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import scala.Tuple2;

import java.net.URL;
import java.util.*;

/**
 * <table border="1">
 * <tr><th>@Description: 统计每个学科最受欢迎的老师</th></tr>
 * <tr><td>@Date:Created in 2018-7-31</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class GroupByFavTeacher {

    static final int N = 2;

    private final String hdfs_path = "hdfs://10.21.20.220:9000/spark/teacher.log";


    /**
     * 直接分组方式
     */
    @Test
    public void FavTeacherTopN() {

        JavaSparkContext jsc = getJavaSparkContext();
        //指定以后从哪里读取数据
        JavaRDD<String> lines = jsc.textFile(hdfs_path);
        //切分压平 http://tom.163.cn/laozhang
        //学科老师分组
        JavaPairRDD<String, Integer> sbjectAndteacher = lines.mapToPair(line -> {
            int index = line.lastIndexOf("/");
            String teacher = line.substring(index + 1);
            String httpHost = line.substring(0, index);
            String subject = new URL(httpHost).getHost().split("[.]")[0];
            return Tuple2.apply(String.join("_", subject, teacher), 1);
        });
        //聚合 学科老师分组
        JavaPairRDD<String, Integer> reduced = sbjectAndteacher.reduceByKey((m, n) -> m + n);
        //学科分组
        JavaPairRDD<String, Iterable<Tuple2<String, Integer>>> sbjectRDD = reduced.groupBy(t -> t._1.split("_")[0]);

        JavaPairRDD<String, List<Tuple2<String, Integer>>> result = sbjectRDD.mapValues(t -> {

            List<Tuple2<String, Integer>> tmp = new ArrayList<>();
            t.forEach(tmp::add);
            //list 里面排序 单一机器可能内存不足
            Collections.sort(tmp, (o1, o2) -> o1._2 - o2._2);
            Collections.reverse(tmp);
            /**
             * It's because, List returned by subList() method
             * is an instance of 'RandomAccessSubList' which
             * is not serializable. Therefore you need to create
             * a new ArrayList object from the list returned by the subList().
             */

            // list.addAll();
            return new ArrayList<>(tmp.subList(0, N));
        });
        //将结果保存 *

        System.out.println("out => [" + result.collect() + "]");     //

        //释放资源
        jsc.stop();


    }

    /**
     * 轮询方式
     */
    @Test
    public void FavTeacherTopN2() {

        JavaSparkContext jsc = getJavaSparkContext();
        //指定以后从哪里读取数据
        JavaRDD<String> lines = jsc.textFile(hdfs_path);

        List<String> subjects = Arrays.asList("tom", "yy");
        //切分压平 http://tom.163.cn/laozhang
        //学科老师分组
        JavaPairRDD<String, Integer> sbjectAndteacher = lines.mapToPair(line -> {
            int index = line.lastIndexOf("/");
            String teacher = line.substring(index + 1);
            String httpHost = line.substring(0, index);
            String subject = new URL(httpHost).getHost().split("[.]")[0];
            return Tuple2.apply(String.join("_", subject, teacher), 1);
        });
        //聚合 学科老师分组
        JavaPairRDD<String, Integer> reduced = sbjectAndteacher.reduceByKey((m, n) -> m + n);


        JavaPairRDD<String, Integer> cache = reduced.cache();

        for (String subject : subjects) {
            JavaPairRDD<String, Integer> filtered = cache.filter(t -> (Boolean) t._1.contains(subject));
            //排序
            JavaPairRDD<Integer, String> sorted = filtered.mapToPair(t -> t.swap()).sortByKey(false);
            //排序
            List<Tuple2<String, Integer>> result = sorted.mapToPair(Tuple2::swap).take(N);
            System.out.println("out => [" + result + "]");     //

        }

        //释放资源
        jsc.stop();

    }

    /**
     * 自定义分区器
     */
    @Test
    public void FavTeacherTopN3() {

        JavaSparkContext jsc = getJavaSparkContext();
        //指定以后从哪里读取数据
        JavaRDD<String> lines = jsc.textFile(hdfs_path);

        List<String> subjects = Arrays.asList("tom", "yy", "baidu");
        //切分压平 http://tom.163.cn/laozhang
        //学科老师分组
        JavaPairRDD<String, Integer> sbjectAndteacher = lines.mapToPair(line -> {
            int index = line.lastIndexOf("/");
            String teacher = line.substring(index + 1);
            String httpHost = line.substring(0, index);
            String subject = new URL(httpHost).getHost().split("[.]")[0];
            return Tuple2.apply(String.join("_", subject, teacher), 1);
        });

        Partitioner subjectPartitioner = new SubjectPartitioner(subjects);
        //聚合 学科老师分组
        JavaPairRDD<String, Integer> reduced = sbjectAndteacher.reduceByKey(subjectPartitioner, (m, n) -> m + n);
        System.out.println("reduced => [" + reduced.collect() + "]");     //

        //cache到内存
        //每个学科老师在各个分区中
        JavaRDD<Tuple2<String, Integer>> result = reduced.mapPartitions(tuple2Iterator -> {
            TreeSet<Tuple2<String, Integer>> treeSet = new TreeSet<>((o1, o2) -> o2._2 - o1._2);
            tuple2Iterator.forEachRemaining(t -> {
                //存放2条记录
                if (treeSet.size() == 2) {
                    //最小值大于当前值的
                    if (treeSet.last()._2 < t._2) {
                        treeSet.add(t);
                        treeSet.pollLast();
                    }
                } else {
                    treeSet.add(t);
                }
            });

            return treeSet.iterator();
        });
        System.out.println("out => [" + result.collect() + "]");     //

        //释放资源
        jsc.stop();


    }

    private JavaSparkContext getJavaSparkContext() {
        SparkConf conf = new SparkConf().setAppName("FavTeacher").setMaster("local[*]");

        //创建sparkContext
        return new JavaSparkContext(conf);
    }
}
