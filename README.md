# Amp_Demo
This app created for demo basic spark operation on hadoop system.
It will handle two trading csv format data as input, 
then operate join and create table which can be query from spark-sql or hive

compile:
sbt assembly

Run:
 spark-submit --class au.com.amp.etl.Main --master yarn --deploy-mode client Amp_Demo-assembly-1.1.jar
 
 Output:
 [hadoop@ip-172-31-23-218 ~]$ hadoop fs -ls hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db
Found 4 items
drwxr-xr-x   - zeppelin spark          0 2018-01-23 09:19 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_join
drwxr-xr-x   - hadoop   spark          0 2018-01-23 14:47 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_join_function
drwxr-xr-x   - zeppelin spark          0 2018-01-23 09:19 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_ratio
drwxr-xr-x   - hadoop   spark          0 2018-01-23 14:47 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_ratio_function
