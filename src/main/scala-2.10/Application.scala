import java.util

import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}
import com.datastax.spark.connector._


/**
  * Created by mthebault on 30/09/2016.
  */
object Application {
  def main(args: Array[String]) {
    val conf = ConfigFactory.load("project.conf")

    //setup some configuration elements
    val sparkConfiguration = new SparkConf()
      .setAppName("playAroundCassandra")
      .set("spark.event.Log.enabled", conf.getString("spark.eventLog.enabled"))
      .set("spark.serializer", conf.getString("spark.serializer"))
      .set("spark.driver.memory", conf.getString("spark.driver.memory"))
      .set("spark.executor.memory", conf.getString("spark.executor.memory"))
      .set("spark.cores.max", conf.getString("spark.cores.max"))
      .set("spark.cassandra.connection.host", conf.getString("cassandra.host"))
      .set("spark.cassandra.connection.port", conf.getString("cassandra.port"))

    //Test In local
//      .setMaster("local[*]")
//      .set("spark.cassandra.connection.host", "127.0.0.1")
//      .set("spark.cassandra.connection.port", "9042")



    //create a new spark context with the previous configuration
    val sc = new SparkContext(sparkConfiguration)



    //SETUP aws credentials
    val hadoopConf = sc.hadoopConfiguration
    hadoopConf.set("fs.s3n.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem")
    hadoopConf.set("fs.s3n.awsAccessKeyId", conf.getString("aws.accessKey"))
    hadoopConf.set("fs.s3n.awsSecretAccessKey", conf.getString("aws.secretKey"))


    //load file Name from configuration
    val bucket = conf.getString("aws.bucketName")
    val fileName = conf.getString("aws.fileName")
    var rdd = sc.textFile("s3n://"+bucket+"/"+fileName)

    //Extract header
    val header = rdd.first()
    val headerSplited = header.toLowerCase.split(",")
    println(header)


    //Launch the import
    rdd.repartition(200)
      .filter(row => row.toLowerCase != header.toLowerCase)
      .map(row => row.split(","))
      .map(row => CassandraRow.fromMap((headerSplited zip row) toMap))
      .saveToCassandra(conf.getString("cassandra.keyspace"), conf.getString("cassandra.table"))




    sc.stop()

  }

}
