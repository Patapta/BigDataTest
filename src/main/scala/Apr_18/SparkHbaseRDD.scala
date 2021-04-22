package Apr_18

import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.mapreduce.{TableInputFormat, TableOutputFormat}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.spark.sql.SparkSession

object SparkHbaseRDD {
  def main(args: Array[String]): Unit = {
//    val localhost = "192.168.7.98"        // 公司
    val localhost = "192.168.101.61"        // 屋企
    val sparkSession = SparkSession.builder().appName("SparkToHBase").master("local[4]").getOrCreate()
    val sc = sparkSession.sparkContext

    val tableName = "emp"

    //创建HBase配置
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", localhost) //设置zookeeper集群，也可以通过将hbase-site.xml导入classpath，但是建议在程序里这样设置
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181") //设置zookeeper连接端口，默认2181
    hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName)

    //读取数据并转化成rdd
    val hbaseRDD = sc.newAPIHadoopRDD(hbaseConf,
      classOf[org.apache.hadoop.hbase.mapreduce.TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result])

//    hbaseRDD.foreach{ case (_ ,result) =>
//      //获取行键
//      val key = Bytes.toString(result.getRow)
//      //通过列族和列名获取列
//      val name = Bytes.toString(result.getValue("cf".getBytes,"name".getBytes))
//      val age = Bytes.toString(result.getValue("cf".getBytes,"gender".getBytes))
//      val addr = Bytes.toString(result.getValue("cf".getBytes,"addr".getBytes))
//      println("Row key:"+key+"\tcf.Name:"+name+"\tcf.Age:"+age+"\tcf.Addr:"+addr)
//    }
    sparkSession.stop()
  }
}
