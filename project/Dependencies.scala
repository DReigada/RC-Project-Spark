import sbt._

object Dependencies {

  lazy val sparkML = "org.apache.spark" %% "spark-mllib" % "2.2.0"
  lazy val sparkCore = "org.apache.spark" %% "spark-core" % "2.2.0"
}
