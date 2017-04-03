import org.scalajs.core.tools.linker.backend.ModuleKind.{CommonJSModule}

name := "tarea"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "com.lihaoyi" %%% "fastparse" % "0.4.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"


enablePlugins( ScalaJSPlugin )

artifactPath in fastOptJS := file("./dist")

scalaJSModuleKind := CommonJSModule