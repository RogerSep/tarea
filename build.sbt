name := "tarea"

version := "1.0"

scalaVersion := "2.12.1"

enablePlugins(ScalaJSPlugin)

artifactPath in fastOptJS := file("./dist")


scalaJSModuleKind := ModuleKind.CommonJSModule