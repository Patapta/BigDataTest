package Apr_11

import org.apache.spark.sql.types.{DataType, LongType, StringType}

/**
 * @author aSigma
 * @date 2021/4/10 1:51
 */
object Utils {
def castTo(value: String, dataType:DataType) = {
  dataType match {
    case _: LongType => value.toLong
    case _: StringType => value
  }
}
}
