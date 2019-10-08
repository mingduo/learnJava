package spark.core.scala.customSort

import java.sql.DriverManager

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zx on 2017/10/10.
  */
object JdbcRddDemo {

  val getConn = () => {
    DriverManager.getConnection("jdbc:mysql://localhost:3306/mingduo?characterEncoding=UTF-8", "root", "root")
  }


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("JdbcRddDemo").setMaster("local[*]")

    val sc = new SparkContext(conf)

    //创建RDD，这个RDD会记录以后从MySQL中读数据

    //new 了RDD，里面没有真正要计算的数据，而是告诉这个RDD，以后触发Action时到哪里读取数据
    val jdbcRDD: RDD[(Int, String, String)] = new JdbcRDD(
      sc,
      getConn,
      "select id,MODULE_NAME,CREATE_USER from sys_logs  WHERE id >= ? AND id < ? ",
      1,
      5,
      2, //分区数量
      rs => {
        val id = rs.getInt(1)
        val name = rs.getString(2)
        val username = rs.getString(3)

        (id, name, username)
      }
    )

    //


    //触发Action
    val r = jdbcRDD.collect()

    println("out-->" + r.toBuffer)

    sc.stop()


  }

}