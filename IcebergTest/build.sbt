ThisBuild / version := "1.0"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(name := "IcebergTest")
  .settings(assembly / mainClass := Some("ru.ibs.dar.das.bigdata.IcebergTest"))
  .settings(assembly / assemblyJarName := "IcebergTest.jar")
  .settings(assembly / assemblyMergeStrategy := {
    case m if m.toLowerCase.endsWith("manifest.mf")       => MergeStrategy.discard
    case m if m.toLowerCase.matches("meta-inf.*\\.sf$")   => MergeStrategy.discard
    case "module-info.class"                              => MergeStrategy.first
    case "version.conf"                                   => MergeStrategy.concat
    case "reference.conf"                                 => MergeStrategy.concat
    case _                                                => MergeStrategy.first
  })

lazy val sparkVersion = "3.5.6"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql"   % sparkVersion % "provided",
  "org.apache.hadoop" % "hadoop-aws" % "3.3.4",
  "com.amazonaws" % "aws-java-sdk-bundle" % "1.12.787",
  "org.apache.iceberg" %% "iceberg-spark-runtime-3.5" % "1.9.1"
)
