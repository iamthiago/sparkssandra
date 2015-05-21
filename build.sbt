name := "sparkssandra"

version := "1.0"

scalaVersion := "2.10.5"

resolvers ++= Seq(
  "Akka Repository" at "http://repo.akka.io/releases/",
  "justwrote" at "http://repo.justwrote.it/releases/"
)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.2.0",
  "org.apache.spark" %% "spark-streaming" % "1.2.0",
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.2.0",
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.5",
  "it.justwrote" %% "scala-faker" % "0.3"
)
