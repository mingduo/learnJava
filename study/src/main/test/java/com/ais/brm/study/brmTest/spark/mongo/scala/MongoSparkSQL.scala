package java.brmTest.spark.mongo.scala

import com.mongodb.spark.MongoSpark
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Created by zx on 2017/10/8.
  * https://docs.mongodb.com/spark-connector/current/scala/datasets-and-sql/
  */
object MongoSparkSQL {

  def main(args: Array[String]): Unit = {

    val session = SparkSession.builder()
      .master("local")
      .appName("MongoSparkConnectorIntro")
      .config("spark.mongodb.input.uri", "mongodb://xiaoniu:123568@192.168.100.101:27017/xiaoniu.logs")
      .config("spark.mongodb.output.uri", "mongodb://xiaoniu:123568@192.168.100.101:27017/xiaoniu.reslut")
      .getOrCreate()

    val df: DataFrame = MongoSpark.load(session)

    df.createTempView("v_logs")

    //val result:DataFrame = session.sql("SELECT age, name FROM v_student WHERE age >= 30 ORDER BY age DESC")

    //val result = session.sql("SELECT age, name FROM v_student WHERE age is null")

    //val pv = session.sql("select count(*) from v_logs")

    val uv = session.sql("select count(*) pv, count(distinct openid) uv from v_logs")

    //pv.show()

    //uv.show()

    MongoSpark.save(uv)
    //MongoSpark

    session.stop()

  }
}
