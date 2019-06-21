package com.headstorm.spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql._

object SparkApp {

  implicit val schemaEncoded = Encoders.product[SchemaClass]

  def main(args: Array[String]) {

    val dataFile = "src/main/resources/locationtime.csv"
    val spark = SparkSession.builder
      .master("local")
      .appName("SeasonFinder")
      .getOrCreate()
    val ltData= spark
      .read
      .schema(schemaEncoded.schema)
      .option("header", "true")
      .csv(dataFile)
      .cache()

    val numAs = ltData.map(o => o)(schemaEncoded)
    spark.stop()

  }
}

case class SchemaClass(longitude: Double, latitude: Double, timeStamp: Long)

