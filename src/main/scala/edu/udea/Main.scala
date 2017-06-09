package edu.udea

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import edu.udea.puertos.cli.Cli
import edu.udea.puertos.io.GrafoReader

import scala.util.{Failure, Success}

object Main {

  def main(args: Array[String]): Unit = {

    Cli.parser.parse(args, Cli()) match {
      case Some(cliArgs) =>
        if (cliArgs.dir.isDefined && !cliArgs.file.isEmpty) {
          System.err.println("Opción inválida, use solo una de las opciones --file o --dir")
        } else {
          run(cliArgs)
        }
      case None => Unit
    }

  }

  def run(args: Cli): Unit = {
    val colFiles = args.file match {
      case Nil =>
        args.dir
          .getOrElse(new File("."))
          .listFiles()
          .filter(f => f.isFile && f.getName.endsWith(".col"))
          .toSeq
      case f => f
    }

    colFiles.foreach { f =>
      GrafoReader.leerArchivo(f) match {
        case Success(grafo) => {
          val start = System.currentTimeMillis()
          val polinomio = grafo.polinomioVorder()
          val took = System.currentTimeMillis() - start

          Files.write(
            args.output
              .fold{
                Paths.get(f.getParent, f.getName.replace(".col", ".res"))
              }
              (outDir => Paths.get(outDir.getAbsolutePath, f.getName.replace(".col", ".res"))),
            s"""T:
              |${took}
              |P(G,x):
              |${polinomio.toString()}
            """.stripMargin
              .getBytes(StandardCharsets.UTF_8)
          )
        }
        case Failure(t) => System.err.println(s"Error al tratar de leer el archivo ${f.getName}: ${t.getMessage}")
      }
    }
  }

}
