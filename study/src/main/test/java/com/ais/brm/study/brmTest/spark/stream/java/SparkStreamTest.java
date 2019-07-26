package com.ais.brm.study.brmTest.spark.stream.java;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.Test;
import scala.Tuple2;

import java.util.Arrays;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-8-24</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class SparkStreamTest {

    private static final String HOST="10.21.16.143";
    private  static final int PORT=9999;
private final  String path="F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\hive\\input\\";

    @Test
    public void testSocket() throws InterruptedException {
        JavaStreamingContext jssc = getJavaStreamingContext();

        // Create a DStream that will connect to hostname:port, like localhost:9999
        JavaReceiverInputDStream<String> lines = jssc
                .socketTextStream(HOST, PORT);

        // Split each line into words
        JavaDStream<String> words = lines.flatMap(x ->
                Arrays.asList(x.split(" ")).iterator());

        // Count each word in each batch
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey((i1, i2) -> i1 + i2);

// Print the first ten elements of each RDD generated in this DStream to the console
        wordCounts.print();

        // Start the computation
        jssc.start();
        // Wait for the computation to terminate
        jssc.awaitTermination();
    }

    /**
     * ll files directly under such a path will be processed as they are discovered.
     * @throws InterruptedException
     */
    @Test
    public void testFile() throws InterruptedException {


        JavaStreamingContext jssc = getJavaStreamingContext();


        JavaDStream<String> lines = jssc.textFileStream(path);

        // Split each line into words
        JavaDStream<String> words = lines.flatMap(x ->
                Arrays.asList(x.split(" ")).iterator());

        // Count each word in each batch
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey((i1, i2) -> i1 + i2);

// Print the first ten elements of each RDD generated in this DStream to the console
        wordCounts.print();

        // Start the computation
        jssc.start();
        // Wait for the computation to terminate
        jssc.awaitTermination();
    }

    @Test
    public void testReduceByWindow() throws InterruptedException {
        JavaStreamingContext jssc = getJavaStreamingContext();

        // Create a DStream that will connect to hostname:port, like localhost:9999
        JavaReceiverInputDStream<String> lines = jssc
                .socketTextStream(HOST, PORT);

        // Split each line into words
        JavaDStream<String> words = lines.flatMap(x ->
                Arrays.asList(x.split(" ")).iterator());

        // Count each word in each batch
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairDStream<String, Integer> windowedWordCounts  = pairs.reduceByKeyAndWindow((i1, i2) -> (i1 + i2)
                , Durations.seconds(5), Durations.seconds(5));

        // Print the first ten elements of each RDD generated in this DStream to the console
        windowedWordCounts.print();

      windowedWordCounts.foreachRDD(rdd->{

            rdd.foreachPartition(partitionOfRecords -> {
                //do what like connection
                partitionOfRecords.forEachRemaining(t->
                        System.out.println("t==>"+t));

            });
        });

        // Start the computation
        jssc.start();
        // Wait for the computation to terminate
        jssc.awaitTermination();
    }

    private JavaStreamingContext getJavaStreamingContext() {
        // Create a local StreamingContext with two working thread and batch interval of 1 second
        SparkConf conf = new SparkConf()
                .setMaster("local[2]")
                .setAppName("NetworkWordCount");
        return new JavaStreamingContext
                (conf, Durations.seconds(1));
    }
}
