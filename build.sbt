import sbt.Keys.libraryDependencies

name := "tarea"

version := "1.0"

scalaVersion := "2.12.1"

lazy val root = project.in(file("."))
  .settings(
    mainClass in assembly := Some("edu.udea.Main"),
    assemblyJarName in assembly := "tarea.jar",
    libraryDependencies := Seq(
      "com.lihaoyi" %% "fastparse" % "0.4.2",
      "com.github.scopt" %% "scopt" % "3.6.0",
      "edu.ucla.sspace" % "sspace" % "2.0.4",

      "org.scalactic" %% "scalactic" % "3.0.1" % "test",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )