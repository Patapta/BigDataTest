package Apr_11

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author aSigma
 * @date 2021/4/4 21:31
 */
object joinTask {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("SparkTestApp").setMaster("local[2]")
    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.makeRDD(List(("a",1),("b",2),("c",3),("d",4)))
    val rdd2 = sc.makeRDD(List(("e",5),("f",6),("g",7),("h",8)))

    rdd1.join(rdd2,3).collect

    Thread.sleep(Int.MaxValue)

    sc.stop()
  }
}
