name := "Amp_Demo"

version := "1.2"

scalaVersion := "2.11.8"
val sparkVersion = "2.2.1"

resolvers += "Artifactory" at "https://artifactory.dev.cba/artifactory/omnia-dev"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "com.holdenkarau" %% "spark-testing-base" % "2.2.0_0.8.0" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % Test,
  "net.jcazevedo" %% "moultingyaml" % "0.4.0",
  "joda-time" % "joda-time" % "2.9.4",
  "org.joda" % "joda-convert" % "1.8.1",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "11.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.8.2",
  "org.apache.logging.log4j" % "log4j-core" % "2.8.2" % Runtime,
  "mysql" % "mysql-connector-java" % "5.1.6"


)