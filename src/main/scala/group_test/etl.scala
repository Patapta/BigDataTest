package group_test

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author aSigma
 * @date 2021/3/23 0:29
 */
class etl {
  val sparkConf = new SparkConf().setAppName("SparkEtlApp").setMaster("local")
  val sc = new SparkContext(sparkConf)

}
