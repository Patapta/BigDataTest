package Apr_11

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import java.io.{File, PrintWriter}

/**
 * @author aSigma
 * @date 2021/3/28 17:39
 */
object out {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("SparkOutApp").setMaster("local")
    val sc = new SparkContext(sparkConf)

    val in = "data/data03.txt"
    val lines: RDD[String] = sc.textFile(in)

    lines.map(x => {
      val data = x.split("\t")
      val total = data(0).split("//")
      val url = total(1).split("/")
      val domain = url(0).split("[.]") // 不能直接用. 我人他妈傻了
      (domain(1), x)
    }).reduceByKey((x,y) => {
      x + "\n" + y
    })
      .map(x => {
      val url = x._2.split("\t")(0).split("//")(1).split("/")(0)
      val fullPath = new File("E:/WorkSpace/HomeWork/log/"+ url + "/" + x._1 + ".log")
      val directory = new File(fullPath.getParent)
      directory.mkdirs()
      val file = new File(directory, fullPath.getName)
      val writer = new PrintWriter(file)
      writer.write(x._2)

      writer.close()
    }).collect()
  }
}
