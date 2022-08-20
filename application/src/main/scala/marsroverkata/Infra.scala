package marsroverkata

object Infra {

  import scala.Console.{ GREEN, RED, RESET }
  import scala.io.Source
  import cats.effect.{ IO, Resource }

  def loadTuple(file: String): IO[(String, String)] =
    loadLines(file).map(lines =>
      lines match {
        case Array(first, second) => (first, second)
        case _                    => throw new RuntimeException(s"Invalid file content: $file")
      }
    )

  def ask(question: String): IO[String] =
    puts(question).flatMap(_ => reads())

  def logInfo(message: String): IO[Unit] =
    puts(green(s"[OK] $message"))

  def logError(message: String): IO[Unit] =
    puts(red(s"[ERROR] $message"))

  private def loadLines(file: String): IO[Array[String]] =
    Resource
      .fromAutoCloseable(IO(Source.fromURL(getClass.getClassLoader.getResource(file))))
      .use(source => IO(source.getLines().toArray))

  private def puts(message: String): IO[Unit] =
    IO.println(message)

  private def reads(): IO[String] =
    IO.readLine

  private def green(message: String): String =
    s"$GREEN$message$RESET"

  private def red(message: String): String =
    s"$RED$message$RESET"

}
