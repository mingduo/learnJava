package java.brmTest.spark.core.scala.GameKPI

import org.apache.commons.lang3.time.FastDateFormat


object FilterUtils {

  //SimpleDateFormat是线程不安全的
  //val dateFormat = new SimpleDateFormat("yyyy年MM月dd日,E,HH:mm:ss")

  //线程安全的DateFormat
  //如果object使用了成员变量，那么会出现线程安全问题，因为object是一个单例，多线程可以同时调用这个方法

  val dateFormat = FastDateFormat.getInstance("yyyy年MM月dd日,E,HH:mm:ss")


  def filterByTime(fields: Array[String], startTime: Long, endTime: Long): Boolean = {
    val time = fields(1)
    val logTime = dateFormat.parse(time).getTime
    logTime >= startTime && logTime < endTime
  }

  def filterByType(fields: Array[String], evenType: String) : Boolean = {
    val _type = fields(0)
    evenType == _type
  }

  def filterByTypes(fields: Array[String], eventTypes: String*): Boolean = {
    val _type = fields(0)
    for(et <- eventTypes){
      if(_type == et)
        return true
    }
    false
  }

  def filterByTypeAndTime(fields: Array[String], eventType: String, beginTime: Long, endTime: Long): Boolean = {
    val _type = fields(0)
    val _time = fields(1)
    val logTime = dateFormat.parse(_time).getTime
    eventType == _type && logTime >= beginTime && logTime < endTime
  }
}
