package spark.ml;

import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.ml.stat.ChiSquareTest;
import org.apache.spark.ml.stat.Correlation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-9-3</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class JavaMLExample {


    @Test
    public  void test() {
        SparkSession spark = getSparkSession();


        List<Row> data = Arrays.asList(
                RowFactory.create(Vectors.sparse(4, new int[]{0, 3}, new double[]{1.0, -2.0})),
                RowFactory.create(Vectors.dense(4.0, 5.0, 0.0, 3.0)),
                RowFactory.create(Vectors.dense(6.0, 7.0, 0.0, 8.0)),
                RowFactory.create(Vectors.sparse(4, new int[]{0, 3}, new double[]{9.0, 1.0}))
        );

        StructType schema = new StructType(new StructField[]{
                new StructField("features", new VectorUDT(), false, Metadata.empty()),
        });

        Dataset<Row> df = spark.createDataFrame(data, schema);
        /**
         * 计算两个数据系列之间的相关性是统计学中的常见操作。
         * 在spark.ml中，我们提供了计算多个系列之间成对相关性的灵活性。
         * 支持的相关方法目前是Pearson和Spearman的相关性。
         */
        Row r1 = Correlation.corr(df, "features").head();
        System.out.println("Pearson correlation matrix:\n" + r1.get(0).toString());

        Row r2 = Correlation.corr(df, "features", "spearman").head();
        System.out.println("Spearman correlation matrix:\n" + r2.get(0).toString());
    }

    private static SparkSession getSparkSession() {
        SparkSession spark = SparkSession.builder()
                .appName("JavaCorrelationExample")
                .master("local[2]").getOrCreate();

        return spark;
    }

    /**
     * ChiSquareTest针对标签对每个功能进行Pearson独立测试。
     * 对于每个特征，将（特征，标签）
     * 对转换为应急矩阵，对其计算卡方统计量。
     * 所有标签和特征值必须是分类的。
     */
    @Test
    public  void test2() {
        SparkSession spark = getSparkSession();

        List<Row> data = Arrays.asList(
                RowFactory.create(0.0, Vectors.dense(0.5, 10.0)),
                RowFactory.create(0.0, Vectors.dense(1.5, 20.0)),
                RowFactory.create(1.0, Vectors.dense(1.5, 30.0)),
                RowFactory.create(0.0, Vectors.dense(3.5, 30.0)),
                RowFactory.create(0.0, Vectors.dense(3.5, 40.0)),
                RowFactory.create(1.0, Vectors.dense(3.5, 40.0))
        );

        StructType schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty()),
        });

        Dataset<Row> df = spark.createDataFrame(data, schema);
        Row r = ChiSquareTest.test(df, "features", "label").head();
        System.out.println("pValues: " + r.get(0).toString());
        System.out.println("degreesOfFreedom: " + r.getList(1).toString());
        System.out.println("statistics: " + r.get(2).toString());
    }

}
