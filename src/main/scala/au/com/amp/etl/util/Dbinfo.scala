package au.com.amp.etl.util

import java.io._
import org.apache.spark.sql.SparkSession

/**
  * Created by ywksu on 1/24/2018.
  * This class used for create hadoop based db warehouse db_name, db_table, db_colum mapping
  */
object Dbinfo extends LoggingSupport {

  def dbinfoGen(spark: SparkSession): Unit = {
    val dbinfo = spark.sql("show databases").collect

    for (temp <- dbinfo) {
      val dbname = temp.getString(0)
      spark.sql("use " + dbname)
      val pw = new PrintWriter(new File(dbname + ".txt"))
      val tabinfo = spark.sql("show tables").collect

      for (temp1 <- tabinfo) {

        val tabname = temp1.getString(1)
        try {
          val tabdes = spark.sql("desc " + tabname).collect
          for (temp2 <- tabdes) {
            pw.write(dbname + "," + tabname + "," + temp2.getString(0) + "," + temp2.getString(1) + "\n")
          }
        }
        catch {
          case e: Exception => log.error(e.getMessage)
        }
      }
      pw.close

      import spark.implicits._

      case class allinfo(db_name: String, table_name: String, field_name: String, indicator: String)
      val fil = spark.read.csv("hdfs://nameservice1/user/hive/warehouse/user_yangw2.db/final.txt")

      val fil1 = fil.withColumnRenamed("_c0", "db_name").withColumnRenamed("_c1", "table_name").withColumnRenamed("_c2", "field_name")
        .withColumnRenamed("_c3", "indicator")
      fil1.write.saveAsTable("user_yangw2.dbinfo")

    }
  }

}
