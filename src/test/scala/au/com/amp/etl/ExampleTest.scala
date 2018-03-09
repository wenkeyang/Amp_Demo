package au.com.amp.etl

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import collection.mutable.Stack
import org.scalatest._
import java.util.Properties
import java.sql.DriverManager

/**
  * Created by ywksu on 3/9/2018.
  */
class ExampleTest extends FlatSpec with Matchers {
  "A spark instance" should "running in order and generate CSV file" in {


    val conf = new SparkConf()
    conf.setAppName("Datasets Test")
    conf.setMaster("local[2]")
    //    val sc = new SparkContext(conf)
    //    println(sc)

    val spark = SparkSession
      .builder()
      .config(conf)
   //   .enableHiveSupport()
      .getOrCreate()

    println(spark)

    val query = s"(select '**' as Ts, price from ada) test_table"

    var jdbcUsername = "root"
    var jdbcPassword = "root"

    Class.forName("com.mysql.jdbc.Driver")



    // Create the JDBC URL without passing in the user and password parameters.
    val jdbcUrl = "jdbc:mysql://localhost:3306/altcoin"

    val connectionProperties = new Properties()

    connectionProperties.put("user", s"${jdbcUsername}")
    connectionProperties.put("password", s"${jdbcPassword}")


    val connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)

    val ada = spark.read.jdbc(jdbcUrl, query, connectionProperties)
    //df = spark.read.jdbc(url=jdbcUrl, table='employees', column='emp_no', lowerBound=1, upperBound=100000, numPartitions=100)

    println(ada.printSchema())


    ada.write.mode("overwrite").csv("c:\\ada.csv")

    connection.isClosed()
//
//    val tab = spark
//      .read
//      .option("url", "jdbc:mysql://localhost:3306/altcoin")
//      .option("user", "root")
//      .option("password", "root")
//      .option("driver", "com.mysql.jdbc.Driver")
//      .option("dbtable", query)
//      .load()
//
//    spark.read.jdbc()


    spark.stop()
    //    val stack = new Stack[Int]
    //    stack.push(1)
    //    stack.push(2)
    //    stack.pop() should be(2)
    //    stack.pop() should be(1)
  }
}
