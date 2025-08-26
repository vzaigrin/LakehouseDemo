import org.apache.spark.sql._
import spark.implicits._

val data1 = Seq(
        (1, 1000371L, 1.8, 15.32, "N"),
        (2, 1000372L, 2.5, 22.15, "N"),
        (2, 1000373L, 0.9, 9.01, "N"),
        (1, 1000374L, 8.4, 42.13, "Y"))

val df1 = data1.toDF("vendor_id"
                     , "trip_id"
                     , "trip_distance"
                     , "fare_amount"
                     , "store_and_fwd_flag")

df1.printSchema()

df1.show()

df1.write.mode(SaveMode.Append).parquet("s3a://warehouse/data")

val df2 = spark.read.parquet("s3a://warehouse/data")

df2.show()

df2.exceptAll(df1)

