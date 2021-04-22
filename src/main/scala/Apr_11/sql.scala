package Apr_11

import org.apache.spark.sql.SparkSession


/**
 * @author aSigma
 * @date 2021/4/7 0:23
 */
object sql {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._
    import org.apache.spark.sql.functions._

    val data = spark.sparkContext.textFile("data/thanks_sunny.txt")
    val df = data.map(x => {
      val splits = x.split("\t")
      val id = splits(0).trim
      val begin_time = splits(1).trim
      val end_time = splits(2).trim
      val down = splits(3).trim
      TestData(id, begin_time, end_time, down)
    }).toDF()

    df.createOrReplaceTempView("thanks_sunny")

    spark.sql(
      """
        |select
        |id, begin_time, end_time,
        |sum(down) over(partition by begin_time,end_time) sum_down
        |from
        |thanks_sunny
        |order by id
        |""".stripMargin).show()

    spark.stop()
  }
}

case class TestData(id:String, begin_time:String, end_time:String, down:String)
