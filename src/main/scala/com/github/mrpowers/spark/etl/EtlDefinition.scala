package com.github.mrpowers.spark.etl

import org.apache.spark.sql.DataFrame

case class EtlDefinition(
  name: String,
  sourceDF: DataFrame,
  transform: (DataFrame => DataFrame),
  write: (DataFrame => Unit),
  hidden: Boolean = false
) {

  def process(): Unit = {
    write(sourceDF.transform(transform))
  }

}
