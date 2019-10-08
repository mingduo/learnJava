package spark.sql.java;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
public class SQLTest {
    @Test
    public void test() {
//spark2.x SQL的编程API(SparkSession)
        //是spark2.x SQL执行的入口
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local[2]")
                .getOrCreate();

        JavaRDD<Row> rowRDD = spark.read()
                .textFile("F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\input")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    Long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    Long age = Long.parseLong(parts[2]);
                    Double fv = Double.parseDouble(parts[3]);

                    return RowFactory.create(id, name, age, fv);
                });

        // Generate the schema based on the string of schema
        StructField id = DataTypes.createStructField("id", DataTypes.LongType, true);
        StructField name = DataTypes.createStructField("name", DataTypes.StringType, true);
        StructField age = DataTypes.createStructField("age", DataTypes.LongType, true);
        StructField fv = DataTypes.createStructField("fv", DataTypes.DoubleType, true);

        List<StructField> structFields = Arrays.asList(id, name, age, fv);

        // fields.add(name);
        StructType schema = DataTypes.createStructType(structFields);
        // Apply the schema to the RDD
        Dataset<Row> dataFrame = spark.createDataFrame(rowRDD, schema);
        // Creates a temporary view using the DataFrame
        dataFrame.createOrReplaceTempView("people");

        // SQL can be run over a temporary view created using DataFrames
        Dataset<Row> results = spark.sql("SELECT * FROM people");

        results.show();
    }


    @Test
    public void test2() {
        //spark2.x SQL的编程API(SparkSession)
        //是spark2.x SQL执行的入口
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local[2]")
                .getOrCreate();

        JavaRDD<Row> rowRDD = spark.read()
                .textFile("F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\input")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split(",");
                    Long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    Long age = Long.parseLong(parts[2]);
                    Double fv = Double.parseDouble(parts[3]) * 100;

                    return RowFactory.create(id, name, age, fv);
                });

        // Generate the schema based on the string of schema
        StructField id = DataTypes.createStructField("id", DataTypes.LongType, true);
        StructField name = DataTypes.createStructField("name", DataTypes.StringType, true);
        StructField age = DataTypes.createStructField("age", DataTypes.LongType, true);
        StructField fv = DataTypes.createStructField("fv", DataTypes.DoubleType, true);

        List<StructField> structFields = Arrays.asList(id, name, age, fv);

        // fields.add(name);
        StructType schema = DataTypes.createStructType(structFields);
        // Apply the schema to the RDD
        Dataset<Row> dataFrame = spark.createDataFrame(rowRDD, schema);
        //spark dsl query
        Dataset<Row> result = dataFrame
                .where(col("age").gt(10))
                .orderBy(col("age").desc());
        result.show();
    }

    @Test
    public void loadData() {
        //spark2.x SQL的编程API(SparkSession)
        //是spark2.x SQL执行的入口
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local[2]")
                .getOrCreate();

        JavaRDD<Row> rowRDD = spark.read()
                .textFile("F:\\idea\\myLearn\\learn\\learnJava\\mrdata\\spark\\sql\\ip")
                .javaRDD()
                .map(line -> {
                    String[] fileds = line.split("[|]");
                    String ipstart = fileds[0];
                    String ipend = fileds[1];

                    Long startNum = Long.parseLong(fileds[2]);
                    Long endNum = Long.parseLong(fileds[3]);
                    String province = fileds[6];
                    String area = fileds[7] + fileds[8];
                    String carrieroperator = fileds[9];


                    return RowFactory.create(ipstart, ipend, startNum, endNum, province, area, carrieroperator);
                });

        // Generate the schema based on the string of schema
        StructField ip_from = DataTypes.createStructField("ip_from", DataTypes.StringType, true);
        StructField ip_to = DataTypes.createStructField("ip_to", DataTypes.StringType, true);

        StructField treans_from = DataTypes.createStructField("trans_from", DataTypes.LongType, true);
        StructField treans_to = DataTypes.createStructField("trans_to", DataTypes.LongType, true);
        StructField province = DataTypes.createStructField("province", DataTypes.StringType, true);
        StructField area = DataTypes.createStructField("area", DataTypes.StringType, true);
        StructField carrieroperator = DataTypes.createStructField("carrieroperator", DataTypes.StringType, true);


        List<StructField> structFields = Arrays.asList(ip_from, ip_to, treans_from, treans_to, province, area, carrieroperator);

        // fields.add(name);
        StructType schema = DataTypes.createStructType(structFields);
        // Apply the schema to the RDD
        Dataset<Row> result = spark.createDataFrame(rowRDD, schema);
        //spark dsl query

        result.show();

        result.write()
                .format("jdbc")
                .option("url", "jdbc:mysql://10.21.20.221/BRM_DEV?autoReconnect=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8")
                .option("dbtable", "SYS_IPINFO")
                .option("user", "brmuser")
                .option("password", "123qwe")
                .save();

    }
}
