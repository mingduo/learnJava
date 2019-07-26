package com.ais.brm.study.brmTest.spark.mongo.java;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.bson.Document;
import org.junit.Test;

import java.util.*;

public final class WriteToMongoDB {

    public static void main(final String[] args) throws InterruptedException {

        SparkSession spark = SparkSession.builder()
                .master("local")
                .appName("MongoSparkConnectorIntro")
                .config("spark.mongodb.input.uri", "mongodb://110.21.20.220/BRM_EVD_0515.telCollection")
                .config("spark.mongodb.output.uri", "mongodb://10.21.20.220/BRM_EVD_0515.telCollection")
                .getOrCreate();

        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Create a custom WriteConfig
        Map<String, String> writeOverrides = new HashMap<>();
        writeOverrides.put("collection", "telCollection");
        writeOverrides.put("writeConcern.w", "majority");
        WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);

        List<Long> telPrefix = Arrays.asList(13900000000L, 15200000000L,
                15000000000L, 15100000000L, 15300000000L, 13000000000L,
                13500000000L, 13700000000L, 18500000000L, 18300000000L,
                13800000000L, 15600000000L, 15700000000L, 18200000000L,
                18700000000L);

        for (Long prefix : telPrefix) {
            List<Long> list = getPhoneLists(prefix);
            // Create a RDD of 10 documents
            JavaRDD<Document> sparkDocuments = jsc.parallelize(list).map(
                    (i ->
                            Document.parse("{phoneNo: " + i + "}"))
            );

            /*Start Example: Save data from RDD to MongoDB*****************/
            MongoSpark.save(sparkDocuments, writeConfig);
        }
        /*End Example**************************************************/

        jsc.close();

    }

    private static List<Long> getPhoneLists(Long phone) {
        List<Long> list = new ArrayList<>(10000000);

        for (int i = 0; i < 10000000; i++) {
            list.add(phone++);
        }

        return list;
    }

    @Test
    public void test() {
        List<Long> list = new ArrayList<>(10000000);
        List<Long> telPrefix = Arrays.asList(13900000000L, 15200000000L,
                15000000000L, 15100000000L, 15300000000L, 13000000000L,
                13500000000L, 13700000000L, 18500000000L, 18300000000L,
                13800000000L, 15600000000L, 15700000000L, 18200000000L,
                18700000000L);

        for (Long prefix : telPrefix) {
            for (int i = 0; i < 1000000; i++) {
                list.add(prefix++);
            }
        }
        System.out.println("list'size  = [" + list.size() + "]");

    }

}