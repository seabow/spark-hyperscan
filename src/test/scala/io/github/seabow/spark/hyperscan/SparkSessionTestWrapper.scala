package io.github.seabow.spark.hyperscan

import org.apache.spark.sql.SparkSession

trait SparkSessionTestWrapper {

  val spark: SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("spark session")
      .config("spark.sql.shuffle.partitions", "4")
      .config("spark.testing.memory","4718592000")
      .config("spark.kryoserializer.buffer.max","1024")
      .config("spark.local.dir","target/tmp")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.sql.extensions", "io.github.seabow.spark.hyperscan.Extension")
      .getOrCreate()
  }
}