package spark.sql.java;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-8-21</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class UdafTest {
    SparkSession spark;

    @Before
    public void setUp() {
        spark = SparkSession
                .builder()
                .appName("udafDataSource")
                .master("local[2]")
                .getOrCreate();
    }

    @Test
    public void test() {
        //注册函数
        spark.udf().register("myAverage", new MyAverage());
        String path = "mrdata/spark/sql";


        Dataset<Row> df = spark.read().json(path + "inputJson");
        df.createOrReplaceTempView("employees");
        df.show();


        Dataset<Row> result = spark.sql("SELECT myAverage(salary) as average_salary FROM employees");
        result.show();
    }

    public static class MyAverage extends UserDefinedAggregateFunction {

        private StructType inputSchema;
        private StructType bufferSchema;

        public MyAverage() {
            List<StructField> inputFields = new ArrayList<>();
            inputFields.add(DataTypes.createStructField("inputColumn", DataTypes.LongType, true));
            inputSchema = DataTypes.createStructType(inputFields);

            List<StructField> bufferFields = new ArrayList<>();
            bufferFields.add(DataTypes.createStructField("sum", DataTypes.LongType, true));
            bufferFields.add(DataTypes.createStructField("count", DataTypes.LongType, true));
            bufferSchema = DataTypes.createStructType(bufferFields);
        }

        //  //输入数据的类型
        // Data types of input arguments of this aggregate function
        public StructType inputSchema() {
            return inputSchema;
        }

        //产生中间结果的数据类型
        // Data types of values in the aggregation buffer
        public StructType bufferSchema() {
            return bufferSchema;
        }

        //最终返回的结果类型
        // The data type of the returned value
        public DataType dataType() {
            return DataTypes.DoubleType;
        }

        //确保一致性 一般用true
        // Whether this function always returns the same output on the identical input
        public boolean deterministic() {
            return true;
        }

        // Initializes the given aggregation buffer. The buffer itself is a `Row` that in addition to
        // standard methods like retrieving a value at an index (e.g., get(), getBoolean()), provides
        // the opportunity to update its values. Note that arrays and maps inside the buffer are still
        // immutable.
        public void initialize(MutableAggregationBuffer buffer) {
            buffer.update(0, 0L);
            buffer.update(1, 0L);
        }


        //每有一条数据参与运算就更新一下中间结果(update相当于在每一个分区中的运算)
        // Updates the given aggregation buffer `buffer` with new input data from `input`
        public void update(MutableAggregationBuffer buffer, Row input) {
            if (!input.isNullAt(0)) {
                long updatedSum = buffer.getLong(0) + input.getLong(0);
                long updatedCount = buffer.getLong(1) + 1;
                buffer.update(0, updatedSum);
                buffer.update(1, updatedCount);
            }
        }

        //全局聚合
        // Merges two aggregation buffers and stores the updated buffer values back to `buffer1`
        public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
            long mergedSum = buffer1.getLong(0) + buffer2.getLong(0);
            long mergedCount = buffer1.getLong(1) + buffer2.getLong(1);
            buffer1.update(0, mergedSum);
            buffer1.update(1, mergedCount);
        }

        // Calculates the final result
        public Double evaluate(Row buffer) {
            return ((double) buffer.getLong(0)) / buffer.getLong(1);
        }
    }


}
