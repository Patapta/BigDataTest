package Apr_18

import org.apache.hadoop.hbase.client.{Put, Result, Scan}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil

import java.util.Base64
//import org.apache.hadoop.hbase.mapred.TableOutputFormat

import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants, TableName}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object SparkHbaseRDD {
  def main(args: Array[String]): Unit = {
    val localhost = "192.168.7.98"        // 公司
    //    val localhost = "192.168.101.61"        // 屋企
    val sparkSession = SparkSession.builder().appName("SparkToHBase").master("local[4]").getOrCreate()
    val sc = sparkSession.sparkContext

    val tableName = "emp"

    //创建HBase配置
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set(HConstants.ZOOKEEPER_QUORUM, localhost) //设置zookeeper集群，也可以通过将hbase-site.xml导入classpath，但是建议在程序里这样设置
    hbaseConf.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181") //设置zookeeper连接端口，默认2181
    hbaseConf.set(TableOutputFormat.OUTPUT_TABLE, tableName)

    val scan = new Scan()
    scan.addFamily(Bytes.toBytes("cf"))
    val proto = ProtobufUtil.toScan(scan)
    val scanToString = new String(Base64.getEncoder.encode(proto.toByteArray))
    hbaseConf.set(org.apache.hadoop.hbase.mapreduce.TableInputFormat.SCAN, scanToString)

    //读取数据并转化成rdd
    val hbaseRDD = sc.newAPIHadoopRDD(hbaseConf, classOf[org.apache.hadoop.hbase.mapreduce.TableInputFormat], classOf[ImmutableBytesWritable], classOf[Result])
  }
}
