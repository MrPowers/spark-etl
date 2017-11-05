package com.github.mrpowers.spark.etl

class EtlCollection {

  private var etls = new scala.collection.mutable.ListBuffer[EtlDefinition]()

  def all(): List[EtlDefinition] = {
    etls.filterNot(_.hidden).toList
  }

  def append(etl: EtlDefinition): Unit = {
    if (etls.map(_.name).contains(etl.name)) {
      throw new Exception(s"An etl with the name '${etl.name}' was already added.")
    }

    etls.append(etl)
  }

  def get(name: String): EtlDefinition = {
    val etl = etls.find(_.name == name.toLowerCase())

    etl.getOrElse(throw new Exception(s"No etl with the name '${name}' was found."))
  }

  def clear(): Unit = {
    etls = new scala.collection.mutable.ListBuffer[EtlDefinition]()
  }

}
