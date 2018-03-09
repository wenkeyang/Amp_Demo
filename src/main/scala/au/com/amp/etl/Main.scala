package au.com.amp.etl


import au.com.amp.etl.sql._
import org.apache.logging.log4j.Level
import org.apache.spark.sql.SparkSession
import au.com.amp.etl.util.LoggingSupport

/**
  * Created by ywksu on 1/23/2018.
  */
object Main extends LoggingSupport {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .enableHiveSupport()
      .appName("Amp_Demo")
      .getOrCreate()


    try {
      val bcc = spark.read.option("header", "true").option("inferSchema", "true").csv("/tmp/bcc.csv")
      val btc = spark.read.option("header", "true").option("inferSchema", "true").csv("/tmp/btc.csv")

      //use to deal with broken file or possible broken file
      //the default can be configured via spark.sql.columnNameOfCorruptRecord
      spark.read.option("mode", "PERMISSIVE").option("columnNameOfCorruptRecord", "_corrupt_record").json("/path/corrput/").show()

      //use for drop the corrupt Records
      spark.read.option("mode", "DROPMALFORMED").json("/path/corrupt").show()

      //temp view will disappear after sessino finished
      bcc.createOrReplaceTempView("bcc")
      btc.createOrReplaceTempView("btc")

      spark.sql("select t1.ts, t1.price/t2.price as ratio from btc as t1 inner join bcc as t2 on t1.ts=t2.ts")
        .write.saveAsTable("frank.check_ratio_function")

      //this is to show how API join works
      val output = spark.sql(Features.sbcc).join(spark.sql(Features.sbtc), Seq("ts"), joinType = "inner")
      output.write.saveAsTable("frank.check_join_function")


      val bcc_c = bcc.count()
      val btc_c = btc.count()
      //this info could be use for testing purpose
      log.info("Loading Finished with bcc:" + bcc_c + " btc:" + btc_c)

    } catch {
      case e: Exception => log.error("Frank: " + e.getMessage)
    }

  }
}
