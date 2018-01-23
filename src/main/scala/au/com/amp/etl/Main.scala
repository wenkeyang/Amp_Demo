package au.com.amp.etl


import au.com.amp.etl.sql._
import org.apache.logging.log4j.Level
import org.apache.spark.sql.SparkSession
import org.apache.logging.log4j.scala.Logging

/**
  * Created by ywksu on 1/23/2018.
  */
object Main extends Logging {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .enableHiveSupport()
      .appName("Amp_Demo")
      .getOrCreate()

    val fc = new Features

    try {
      val bcc = spark.read.option("header", "true").option("inferSchema", "true").csv("/tmp/bcc.csv")
      val btc = spark.read.option("header", "true").option("inferSchema", "true").csv("/tmp/btc.csv")
      //temp view will disappear after sessino finished
      bcc.createOrReplaceTempView("bcc")
      btc.createOrReplaceTempView("btc")

      spark.sql("select t1.ts, t1.price/t2.price as ratio from btc as t1 inner join bcc as t2 on t1.ts=t2.ts")
        .write.saveAsTable("frank.check_ratio_function")

      //this is to show how API join works
      val output = spark.sql(fc.sbcc).join(spark.sql(fc.sbtc), Seq("ts"), joinType = "inner")
      output.write.saveAsTable("frank.check_join_function")


      val bcc_c = bcc.count()
      val btc_c = btc.count()
      //this info could be use for testing purpose
      logger.info("Loading Finished with bcc:" + bcc_c + " btc:" + btc_c)

    } catch {
      case e: Exception => logger.error(e.getMessage)
    }

  }
}
