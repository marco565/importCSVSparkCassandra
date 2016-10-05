name := "importCSV"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies ++=Seq(
  "org.apache.spark" %% "spark-core" % "1.6.1",
  "org.apache.spark" %% "spark-sql" % "1.6.0",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "com.datastax.spark" % "spark-cassandra-connector_2.10" % "1.6.1",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.0.0",
  "com.google.guava" % "guava" % "19.0",
  "net.java.dev.jets3t" % "jets3t" % "0.9.3",
  "org.apache.hadoop" % "hadoop-client" % "2.6.0",
  "org.apache.hadoop" % "hadoop-aws" % "2.6.0"
)

dependencyOverrides ++= Set(
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"
)


assemblyJarName in assembly := "PlayArround.jar"

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case PathList("com", "google", xs @ _*) => MergeStrategy.last
  case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
  case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
  case PathList("io","netty", xs @ _*) => MergeStrategy.last
  case PathList("javax","xml", xs @ _*) => MergeStrategy.last

  case PathList("META-INF", xs @ _*) => MergeStrategy.discard

  case x => MergeStrategy.first

}


resolvers += "Kaliber Internal Repository" at "https://jars.kaliber.io/artifactory/libs-release-local"