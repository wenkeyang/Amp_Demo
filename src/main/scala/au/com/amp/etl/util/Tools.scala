package au.com.amp.etl.util

import org.apache.spark.sql.SparkSession

import scala.io.Source

/**
  * Created by ywksu on 1/23/2018.
  */
class Tools extends LoggingSupport {
  def loadXML(path: String): scala.xml.Elem = {
    scala.xml.XML.loadFile(path)
  }

  def loadFile(path: String): String = {
    val bufferedSource = Source.fromFile(path)
    var buffString = "";
    for (line <- bufferedSource.getLines) {
      buffString += line + "\n"
    }
    bufferedSource.close
    buffString
  }

  def AppendSave(spark: SparkSession, sql: String, table_name: String): Unit = {
    try {
      spark.sql(sql).write.mode("append").saveAsTable(table_name)
      spark.sql("refresh table " + table_name)
    } catch {
      case e: Exception => log.error(e.getMessage)
    }
  }

  def RecreateSave(spark: SparkSession, sql: String, table_name: String): Unit = {
    try {
      spark.sql("drop table if exists " + table_name)
      spark.sql(sql).write.saveAsTable(table_name)
    } catch {
      case e: Exception => log.error(e.getMessage)
    }
  }

  def AppendSaveWithPartition(spark: SparkSession, sql: String, table_name: String, partition: String): Unit = {
    try {
      spark.sql(sql).write.partitionBy(partition)
        .mode("append").saveAsTable(table_name)
      spark.sql("refresh table " + table_name)
    } catch {
      case e: Exception => log.error(e.getMessage)
    }
  }

  def RecreateSaveWithPartition(spark: SparkSession, sql: String, table_name: String, partition: String): Unit = {
    try {
      spark.sql("drop table if exists " + table_name)
      spark.sql(sql).write.partitionBy(partition).saveAsTable(table_name)
    } catch {
      case e: Exception => log.error(e.getMessage)
    }
  }

  def StringToDate(inputSt: String): java.util.Date = {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    try {
      format.parse(inputSt)
    } catch {
      case e: Exception => log.error(e.getMessage)
        format.parse("0000-00-00")
    }
  }

  def DateToString(inputDt: java.util.Date): String = {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    try {
      format.format(inputDt)
    } catch {
      case e: Exception => log.error(e.getMessage)
        format.format("0000-00-00")
    }
  }

}