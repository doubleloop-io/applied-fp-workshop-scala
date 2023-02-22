package application

object File {

  import cats.effect.{ IO, Resource }
  import scala.io.Source

  def loadTuple(file: String): IO[(String, String)] =
    loadLines(file).map(lines =>
      lines match {
        case Array(first, second) => (first, second)
        case _                    => throw new RuntimeException(s"Invalid file content: $file")
      }
    )

  private def loadLines(file: String): IO[Array[String]] =
    Resource
      .fromAutoCloseable(IO(Source.fromURL(getClass.getClassLoader.getResource(file))))
      .use(source => IO(source.getLines().toArray))
}
