import java.util.UUID

import com.datastax.driver.core.utils.UUIDs
import com.datastax.spark.connector._
import faker._
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

/**
 * Created by tperei9 on 5/12/15.
 */
object FakeUserDataGenerate {

  def main(args: Array[String]) {
    val sparkConf =
      new SparkConf(true)
        .setAppName("fake")
        .setMaster("local")
        .set("spark.cassandra.connection.host", "127.0.0.1")

    val sc = new SparkContext(sparkConf)

    val buffer = new ListBuffer[User]

    for (i <- 1 to 1000000) {
      val name = Name.name
      buffer += User(UUIDs.timeBased(), name, Internet.user_name(name), Internet.email(name))
    }

    val collection = sc.parallelize(buffer)
    collection.saveToCassandra("test", "user", SomeColumns("id", "name", "username", "email"))

    sc.stop()
  }
}

case class User(id: UUID, name: String, username: String, email: String)