package Apr_11

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * @author aSigma
 * @date 2021/3/26 23:50
 */
object sum02 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkSumApp").setMaster("local")
    val sc = new SparkContext(sparkConf)
    val lines: RDD[String] = sc.textFile("data/data02.txt")
    lines.map(x => {
      val array = x.split(",")
      ((array(0),array(1)), (array(2).toInt, array(3).toInt))
    }).reduceByKey((x,y) => {
      (x._1 + y._1, x._2 + y._2)
    }).foreach(x => {
      println((x._1._1, x._1._2, x._2._1, x._2._2))
    })
  }
}
