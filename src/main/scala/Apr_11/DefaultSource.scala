package Apr_11

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.{BaseRelation, RelationProvider}

/**
 * @author aSigma
 * @date 2021/4/10 1:16
 */
class DefaultSource extends RelationProvider{
  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {
    val path = parameters.get("path")
    path match {
      case Some(x) => new aSigmaTextDataSourceRelation(sqlContext, x)
      case _ => throw new IllegalArgumentException("path is required...")
    }
  }
}
