package spark.core.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.JdbcRDD;
import scala.Tuple3;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018-8-7</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
public class JdbcRddTest {


    public static void main(String[] args) throws SQLException {
        JavaSparkContext jsc = getSparkSc();
        String sql = "select id,MODULE_NAME,CREATE_USER" +
                " from sys_logs  WHERE id >= ? AND id < ? ";
        /**
         *
         * org.apache.spark.SparkException: Job aborted due to stage failure:
         * Task not serializable: java.io.NotSerializableException: ...
         *
         * NotSerializable notSerializable = new NotSerializable();
         */

        JavaRDD<Tuple3<Integer, String, String>> result = JdbcRDD.create(jsc, () ->
                {
                    try {
                        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mingduo?characterEncoding=UTF-8", "root", "root");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                , sql,
                1,
                5,
                2, rs -> {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    String username = rs.getString(3);
                    return Tuple3.apply(id, name, username);
                });


        System.out.println("out=>[" + result.collect() + "]");

    }





    private static JavaSparkContext getSparkSc() {
        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local[2]");
        //创建sparkContext
        return new JavaSparkContext(conf);

    }
}
