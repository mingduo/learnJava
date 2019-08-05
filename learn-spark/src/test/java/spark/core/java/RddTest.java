package spark.core.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description: RDD 測試</th></tr>
 * <tr><td>@Date:Created in 2018-7-28</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class RddTest {

    JavaSparkContext sc = getSparkSc();

    private JavaSparkContext getSparkSc() {
        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local[2]");
        //创建sparkContext
        return new JavaSparkContext(conf);

    }

    @Test
    public void filterRdd() {
        List<Integer> list = Arrays.asList(5, 6, 4, 7, 3, 8, 2, 9, 1, 10);
        JavaRDD<Integer> rdd = sc.parallelize(list);
        //对rdd1里的每一个元素乘2然后排序
        JavaRDD<Integer> sortRdd = rdd.map(t -> t * 2).sortBy(x -> x, true, 2);
        //过滤出大于等于十的元素
        JavaRDD<Integer> filterRdd = sortRdd.filter(k -> k >= 10);

        System.out.println("filterRdd=>" + filterRdd.collect());

    }

    @Test
    public void flatMapRdd() {
        List<String> list = Arrays.asList("a b c", "d e f", "h i j");
        JavaRDD<String> rdd = sc.parallelize(list);
        //对rdd1里的每一个元素乘2然后排序
        JavaRDD<String> flatMapRdd = rdd.flatMap(s ->
                Arrays.asList(s.split(" ")).iterator());

        System.out.println("flatMapRdd=>" + flatMapRdd.collect());

    }

    @Test
    public void mapPartitionsWithIndex() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        JavaRDD<Integer> rdd = sc.parallelize(list, 3);

        JavaRDD<Tuple2<Integer, Integer>> tuple2JavaRDD = rdd.mapPartitionsWithIndex((index, integerIterator) -> {
            List<Tuple2<Integer, Integer>> list2 = new ArrayList<>();
            integerIterator.forEachRemaining(t -> {
                        list2.add(new Tuple2<>(index, t));
                    }
            );
            return list2.listIterator();
        }, true);

        System.out.println("mapPartitionsWithIndex=>" + tuple2JavaRDD.collect());

        JavaRDD<Integer> mapRdd = rdd.map(t -> t * 100);

        System.out.println("mapRdd=>" + mapRdd.collect());

        //对同一分区统一操作
        mapRdd.foreachPartition(t->t.forEachRemaining(System.out::println));



    }

    @Test
    public void JavaPairRDDGroupby() {
        List<Tuple2<String, Integer>> list = Arrays.asList(Tuple2.apply("tom", 1), Tuple2.apply("jerry", 3), Tuple2.apply("kitty", 1));
        List<Tuple2<String, Integer>> list2 = Arrays.asList(Tuple2.apply("jerry", 1), Tuple2.apply("shuke", 3));

        JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(list, 2);
        JavaPairRDD<String, Integer> rdd2 = sc.parallelizePairs(list2, 2);

        //求jion
        JavaPairRDD<String, Tuple2<Integer, Integer>> join = rdd.join(rdd2);
        System.out.println("join=>" + join.collect());
        //求并集
        JavaPairRDD<String, Integer> union = rdd.union(rdd2);
        System.out.println("union=>" + union.collect());
        //按key进行分组
        JavaPairRDD<String, Iterable<Integer>> objectIterableJavaPairRDD = union.groupByKey();
        System.out.println("groupByKey=>" + objectIterableJavaPairRDD.collect());
        //按key进行分组
        JavaPairRDD<String, Tuple2<Iterable<Integer>, Iterable<Integer>>> cogroup = rdd.cogroup(rdd2);
        System.out.println("cogroup=>" + cogroup.collect());

    }


    @Test
    public void reduce() {
        List<Tuple2<String, Integer>> list = Arrays.asList(Tuple2.apply("tom", 1), Tuple2.apply("jerry", 3), Tuple2.apply("kitty", 1));
        List<Tuple2<String, Integer>> list2 = Arrays.asList(Tuple2.apply("jerry", 1), Tuple2.apply("shuke", 3));

        JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(list, 2);
        JavaPairRDD<String, Integer> rdd2 = sc.parallelizePairs(list2, 2);


        JavaPairRDD<String, Integer> rdd3 = rdd.union(rdd2);

        //求reduceByKey
        JavaPairRDD<String, Integer> reduceByKey = rdd3.reduceByKey((m, n) -> m + n);

        System.out.println("reduceByKey=>" + reduceByKey.collect());

        JavaPairRDD<String, Integer> aggregateByKey = rdd3.aggregateByKey(0, (v1, v2) -> (v1 + v2), (v1, v2) -> (v1 + v2)
        );
        System.out.println("aggregateByKey=>" + aggregateByKey.collect());

    }

    @Test
    public void union() {
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
    }


}
