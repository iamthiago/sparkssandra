import java.util.UUID

import com.datastax.driver.core.utils.UUIDs
import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * Created by tperei9 on 5/12/15.
 */
object DummyDataGenerate {

  def main(args: Array[String]) {
    val sparkConf =
      new SparkConf(true)
        .setAppName("fake")
        .setMaster("local")
        .set("spark.cassandra.connection.host", "127.0.0.1")

    val sc = new SparkContext(sparkConf)

    val random = new Random()
    val buffer = new ListBuffer[Dummy]

    for (i <- 1 to 1000000) {
      val byte = Array.fill[Byte](1000)((random.nextInt() % 26 + 'a').toByte)
      buffer += Dummy(UUIDs.timeBased(), byte.toString)
    }

    val collection = sc.parallelize(buffer)
    collection.saveToCassandra("test", "dummy", SomeColumns("id", "dummy"))

    sc.stop()
  }
}

case class Dummy(id: UUID, dummy: String)