package edu.udea.puertos.cli

import java.io.File


case class Cli(
  dir: Option[File] = None,
  file: Seq[File] = Seq.empty,
  output: Option[File] = None
)

object Cli {

  val parser = new scopt.OptionParser[Cli]("lr3") {
    head("grafos lr3")
      .text("Tarea 2 de lógica y representación 3 por Roger Sepúlveda cc. 1152186551")

    opt[File]('d', "dir")
      .valueName("<directorio>")
      .text("Directorio donde se encuentran los archivos con extensión .col")
      .validate(f =>
        if (f.isDirectory) success
        else failure("-d --dir debe ser un directorio")
      )
      .action((f, c) =>
        c.copy(dir = Some(f))
      )

    opt[File]('o', "output")
      .valueName("<directorio>")
      .text("Directorio donde guardar los archivos de salida")
      .validate(f =>
        if (f.isDirectory) success
        else failure("-o --output debe ser un directorio")
      )
      .action((f, c) => c.copy(output = Some(f)))


    opt[File]('f', "file")
      .valueName("<archivo>")
      .text("Archivo con la definición DIMACS que contiene un grafo")
      .validate(f =>
        if (f.exists() && f.canRead && f.isFile) success
        else failure(f.getAbsolutePath + " no es un archivo, no tiene extensión .col, no existe o no se puede leer")
      )
      .action((f, c) => c.copy(file = c.file :+ f))

    help("help").text("Imprime este texto de ayuda")

    note("\n\nejemplo.\n\tlr3 --dir /tmp")

  }

}
