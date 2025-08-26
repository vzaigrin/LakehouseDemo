ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(name := "S3Test")
  .settings(assembly / mainClass := Some("ru.ibs.dar.das.bigdata.S3Test"))
  .settings(assembly / assemblyJarName := "S3Test.jar")

lazy val sparkVersion = "3.5.6"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql"   % sparkVersion % "provided",
  "org.apache.hadoop" % "hadoop-aws" % "3.3.4",
  "com.amazonaws" % "aws-java-sdk-bundle" % "1.12.787"
)
