package spark.core.scala.GameKPI

import java.text.SimpleDateFormat

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by laozhao on 2017/8/5.
  * 计算游戏的关键指标
  *
  */
object GameKPI_V2 {

  def main(args: Array[String]): Unit = {

    //条件的SimpleDateFormat
    val conditionSdf = new SimpleDateFormat("yyyy-MM-dd")
    val startTime = conditionSdf.parse("2016-02-01").getTime
    val endTime = conditionSdf.parse("2016-02-02").getTime
    //如果在Driver端new出一个SimpleDateFormat实例，每个Task内部都会一个独立的SimpleDateFormat实例
    //如何用一个SimpleDateFormat实例？定义一个object类型的工具类，让SimpleDateFormat作为一个成员变量
    //val sdf = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")

    val filterUtils = new FilterUtils_V2 with Serializable

    val conf = new SparkConf().setAppName("GameKPI_V2").setMaster("local[4]")

    val sc = new SparkContext(conf)

    val lines: RDD[String] = sc.textFile("/Users/zx/Desktop/user.log")

    val splitedData: RDD[Array[String]] = lines.map(_.split("[|]"))
    //对数据进行过滤
    val filtered = splitedData.filter(fields => {
      //FilterUtils.filterByTime(fields, startTime, endTime)
      //没一个Task一个filterUtils实例
      filterUtils.filterByTime(fields, startTime, endTime)
    })

    val r = filtered.collect()

    println(r.toBuffer)


  }

}
