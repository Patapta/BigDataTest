package Apr_11

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * @author aSigma
 * @date 2021/3/26 1:11
 */
object sum {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("SparkSumApp").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val lines: RDD[String] = sc.textFile("data/data01.txt")
    lines.map(x => {
      val array = x.split(",")
      (array(0), (array(1).toInt,array(2).toInt))
    }).reduceByKey((x,y) => {
      (x._1 + y._1, x._2 + y._2)
    }).foreach(println)
  }
}
