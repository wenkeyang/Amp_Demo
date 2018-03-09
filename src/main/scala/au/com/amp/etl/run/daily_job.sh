#!/usr/bin/env bash

set -e
set -x

RUN_DIR=${OMNIA_ENVIRONMENT_PATH}/dataproducts-extracts-gdw
JAR=$(ls ${RUN_DIR}/dataproducts-extracts-gdw-assembly-*.jar)
CONFIG_DIR=hdfs://nameservice1${OMNIA_HDFS_ENVIRONMENT}/etl/processing/features/extracts-gdw

# upload config file
hadoop fs -mkdir -p $CONFIG_DIR
hadoop fs -put -f ${RUN_DIR}/config-daily.yaml $CONFIG_DIR

YARN_CONF_DIR=${HADOOP_CONF_DIR} \
/opt/spark/spark-2.1.0-bin-hadoop2.7/bin/spark-submit \
--files ${RUN_DIR}/log4j.properties \
--conf spark.yarn.maxAppAttempts=1 \
--conf spark.yarn.max.executor.failures=200 \
--conf spark.driver.extraJavaOptions="-XX:PermSize=512m -XX:MaxPermSize=512m" \
--conf spark.driver.memory=7G \
--conf spark.sql.warehouse.dir=hdfs://nameservice1/user/hive/warehouse \
--conf spark.locality.wait=0 \
--master yarn \
--deploy-mode cluster \
--num-executors 100 \
--executor-memory 7G \
--executor-cores 2 \
--class au.com.cba.omnia.dataproducts.extracts.gdw.CSCtoRDV \
$JAR --hdfs-root $OMNIA_HDFS_ENVIRONMENT --config-file ${CONFIG_DIR}/config-daily.yaml "$@"
