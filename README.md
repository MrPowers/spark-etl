# spark-etl

A lightweight framework for running ETL analyses in Spark.

spark-etl let's you define EtlDefinitions, group them in a collection, and provides helper methods to run the etls via jobs.

## Components of an ETL

An ETL starts with a DataFrame, runs a series of transformations (filter, custom transformations, repartition), and writes out data.

spark-etl is generic and can be molded to suit all ETL situations.  For example, it can read a CSV file from S3, run transformations, and write out Parquet files on your local filesystem.

## Code example

This snippet creates a DataFrame and writes it out as a CSV file in your the local filesystem.

```scala
val sourceDF = spark.createDF(
  List(
    ("bob", 14),
    ("liz", 20)
  ), List(
    ("name", StringType, true),
    ("age", IntegerType, true)
  )
)

def someTransform()(df: DataFrame): DataFrame = {
  df.withColumn("cool", lit("dude"))
}

def someWriter()(df: DataFrame): Unit = {
  val path = new java.io.File("./tmp/example").getCanonicalPath
  df.repartition(1).write.csv(path)
}

val etlDefinition = new EtlDefinition(
  name =  "example",
  sourceDF = sourceDF,
  transform = someTransform(),
  write = someWriter(),
  hidden = false
)

etlDefinition.process()
```

In production applications, it's more likely that you'll use Spark DataFrame readers to create the `sourceDF` (e.g. `spark.read.parquet("some_s3_path")`).
