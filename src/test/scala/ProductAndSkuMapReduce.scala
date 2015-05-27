import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
 * Created by Thiago Pereira on 5/25/15.
 */
object ProductAndSkuMapReduce {

  def main(args: Array[String]) {

    val sparkConf =
      new SparkConf(true)
        .setAppName("product")
        .setMaster("local")
        .set("spark.cassandra.connection.host", "127.0.0.1")

    val sc = new SparkContext(sparkConf)

    val products = sc.cassandraTable("test", "product").cache()
    val skus = sc.cassandraTable("test", "sku").cache()

    val categoryId = 2830
    val volumes = new ListBuffer[Int]

    val productsByCategory = products.select("id", "status").where("category_id = ?", categoryId).collect()

    for {
      prod <- productsByCategory
      if prod.getBoolean("status")
      sku <- skus.select("length", "height", "width", "status").where("product_id = ?", prod.getLong("id")).collect()
      if sku.getBoolean("status")
    } yield {
      volumes += sku.getInt("length") * sku.getInt("height") * sku.getInt("width")
    }

    println(volumes.sum / volumes.size)

  }
}