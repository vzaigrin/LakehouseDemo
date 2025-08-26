package ru.ibs.dar.das.bigdata

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

object S3Test {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("IcebergTest")
      .config("spark.log.level", "WARN")
      .getOrCreate()

    println(s"Spark: ${spark.version}")
    val schema = StructType( Array(
      StructField("vendor_id", LongType, nullable = true),
      StructField("trip_id", LongType, nullable = true),
      StructField("trip_distance", FloatType, nullable = true),
      StructField("fare_amount", DoubleType, nullable = true),
      StructField("store_and_fwd_flag", StringType, nullable = true)
    ))

    println("Create schema")

    val data = getSeq(1000000)
    val df1 = getDF(spark, data, schema)

    print("Create and write dataframe to table ... ")
    df1.write.mode(SaveMode.Overwrite).parquet("s3a://warehouse/data2")
    println("complete")

    print("Append dataframe to table ... ")
    df1.write.mode(SaveMode.Append).parquet("s3a://warehouse/data2")
    println("complete")

    println("Read from dataframe")
    val df2 = spark.read.parquet("s3a://warehouse/data2")
    println(s"Table count = ${df2.count}")

    spark.stop()
    sys.exit(0)
  }

  private def getRow: Row = {
    val random = new scala.util.Random
    Row(random.nextLong(), random.nextLong(), random.nextFloat(), random.nextDouble(), random.nextString(1))
  }

  private def getSeq(n: Int): Seq[Row] = {
    (1 to n).map { _ => getRow }
  }

  private def getDF(spark: SparkSession, data: Seq[Row], schema: StructType): DataFrame = {
    spark.createDataFrame(spark.sparkContext.parallelize(data), schema)
  }
}
