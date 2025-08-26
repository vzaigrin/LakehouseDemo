spark.sql("CREATE TABLE IF NOT EXISTS nessie.table (id bigint, data string) USING iceberg;")
spark.sql("INSERT INTO nessie.table VALUES (1, 'a'), (2, 'b'), (3, 'c');")
spark.table("nessie.table").show

import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
val schema = StructType( Array(
    StructField("vendor_id", LongType,true),
    StructField("trip_id", LongType,true),
    StructField("trip_distance", FloatType,true),
    StructField("fare_amount", DoubleType,true),
    StructField("store_and_fwd_flag", StringType,true)
))

val data1 = Seq(
    Row(1: Long, 1000371: Long, 1.8f: Float, 15.32: Double, "N": String),
    Row(2: Long, 1000372: Long, 2.5f: Float, 22.15: Double, "N": String),
    Row(2: Long, 1000373: Long, 0.9f: Float, 9.01: Double, "N": String),
    Row(1: Long, 1000374: Long, 8.4f: Float, 42.13: Double, "Y": String)
)
val df1 = spark.createDataFrame(spark.sparkContext.parallelize(data1), schema)
df1.show

df1.writeTo("nessie.data").create()

val data2 = Seq(
    Row(3: Long, 1000371: Long, 1.8f: Float, 15.32: Double, "N": String),
    Row(5: Long, 1000372: Long, 2.5f: Float, 22.15: Double, "N": String),
    Row(6: Long, 1000373: Long, 0.9f: Float, 9.01: Double, "N": String),
    Row(7: Long, 1000374: Long, 8.4f: Float, 42.13: Double, "Y": String)
)

val df2 = spark.createDataFrame(spark.sparkContext.parallelize(data2), schema)
df2.writeTo("nessie.data").append()

spark.table("nessie.data").show

