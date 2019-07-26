package com.ais.brm.study.brmTest.spark.sql.java;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.apache.spark.sql.functions.col;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-8-13</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class DataSourceTest {
    SparkSession spark;

    @Before
    public void setUp() {
        spark = SparkSession
                .builder()
                .appName("JdbcDataSource")
                .master("local[2]")
                .getOrCreate();
    }

    @Test
    public void JdbcDataSource() {
        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source
        Dataset<Row> jdbcDF = spark.read()
                .format("jdbc")
                .option("url", "jdbc:mysql://localhost:3306/mingduo?characterEncoding=UTF-8")
                .option("dbtable", "person")
                .option("user", "root")
                .option("password", "root")
                .load("");

        jdbcDF.printSchema();

        jdbcDF.show();

        Dataset<Row> filtered = jdbcDF.filter((FilterFunction<Row>) r -> r.getInt(2) > 12);

        filtered.show();

        Dataset<Row> result = filtered.select(col("name"),col("age"), col("address"));
        result.show();

        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");
        result.write().mode(SaveMode.Append)
                .jdbc("jdbc:mysql://localhost:3306/mingduo?characterEncoding=UTF-8",
                        "person_tmp", props);
        String path = "F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\";

        //DataFrame保存成text时出错(只能保存一列)
        //    result.write().text("F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\json");

          result.write().json(path+"json");
        // result.write().csv(path+"csv");

       result.write().parquet(path + "parquet");
    }

    //windows 不支持 demo 读写 parquet
    @Test
    public void JsonDataSource() {
        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source
        String path = "F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\";


        Dataset<Row> result = spark.read().format("json")
                .load(path+"inputJson");


        result.show();

        result.printSchema();


        result.write().parquet("hdfs://10.21.20.220:9000/parquet");

    }

    @Test
    public void csvDataSource() {
        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source
        String path = "F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\";


        Dataset<Row> csvDF = spark.read().csv(path+"csv");

        csvDF.printSchema();

        Dataset<Row> result = csvDF.toDF("id", "name", "age", "address");

        System.out.println("----->");
        result.show();

    }





}
