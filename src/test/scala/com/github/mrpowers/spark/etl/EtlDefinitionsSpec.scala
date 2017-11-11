package com.github.mrpowers.spark.etl

import org.scalatest.FunSpec
import com.github.mrpowers.spark.fast.tests.DataFrameComparer
import com.github.mrpowers.spark.daria.sql.SparkSessionExt._
import org.apache.spark.sql.types.{IntegerType, StringType}

class EtlDefinitionsSpec
  extends FunSpec
    with SparkSessionTestWrapper
    with DataFrameComparer {

  describe("integration") {

    it("runs a full ETL process and writes out data to a folder") {

      val sourceDF = spark.createDF(
        List(
          ("bob", 14),
          ("liz", 20)
        ), List(
          ("name", StringType, true),
          ("age", IntegerType, true)
        )
      )

      val etlDefinition = new EtlDefinition(
        sourceDF = sourceDF,
        transform = EtlHelpers.someTransform(),
        write = EtlHelpers.someWriter()
      )

      val etls = scala.collection.mutable.Map[String, EtlDefinition]("example" -> etlDefinition)

      etls("example").process()

    }

  }

}
