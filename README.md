# Amp_Demo
This app created for demo basic spark operation on hadoop system.
It will handle two trading csv format data as input, 
then operate join and create table which can be query from spark-sql or hive

Compile:<br />
sbt assembly

Run:<br />
spark-submit --class au.com.amp.etl.Main --master yarn --deploy-mode client Amp_Demo-assembly-1.1.jar
 
Output:<br />
[hadoop@ip-172-31-23-218 ~]$ hadoop fs -ls hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db
Found 4 items<br />
drwxr-xr-x   - zeppelin spark          0 2018-01-23 09:19 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_join<br />
drwxr-xr-x   - hadoop   spark          0 2018-01-23 14:47 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_join_function<br />
drwxr-xr-x   - zeppelin spark          0 2018-01-23 09:19 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_ratio<br />
drwxr-xr-x   - hadoop   spark          0 2018-01-23 14:47 hdfs://ip-172-31-23-218.us-east-2.compute.internal:8020/user/spark/warehouse/frank.db/check_ratio_function<br />
