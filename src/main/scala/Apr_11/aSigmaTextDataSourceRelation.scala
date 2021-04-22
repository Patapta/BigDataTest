package Apr_11

import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.sql.sources.{BaseRelation, TableScan}
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
 * @author aSigma
 * @date 2021/4/10 1:23
 */
class aSigmaTextDataSourceRelation(val sqlContext: SQLContext, path:String)
extends BaseRelation with TableScan with Logging{

  override def schema: StructType = StructType(
    StructField("id",LongType, false) ::
      StructField("start_time",StringType, false) ::
      StructField("end_time",StringType, false) ::
      StructField("down_flow",LongType, false) :: Nil
  )

  override def buildScan(): RDD[Row] = {
    val lines = sqlContext.sparkContext.textFile(path)
    val fields = schema.fields

    lines.map(_.split("\t").map(_.trim))
      .map(x => x.zipWithIndex.map({
        case (value, index) => {
          val colName = fields(index).name
          Utils.castTo(value, fields(index).dataType)
        }
      })).map(x => Row.fromSeq(x))
    null
  }
}
