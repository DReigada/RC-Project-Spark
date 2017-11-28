#!/usr/bin/env bash


${SPARK_HOME}/bin/spark-submit \
	--class "rc.FindSubredditsItemSet" \
	--master local[64] \
	--driver-memory 100g \
	--executor-memory 10g \
	--conf spark.network.timeout=240000 \
	--conf "spark.executor.extraJavaOptions=-Xss16m" \
	--conf "spark.driver.extraJavaOptions=-Xss16m" \
	#--conf "spark.local.dir=${SPARK_TMP_DIR}" \
	$1 \
	${@:2}
