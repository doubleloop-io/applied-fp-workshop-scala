package marsroverkata

import cats.effect._

import scala.Console._
import scala.io._

object Version5 {

  case class AppError(err: Error) extends RuntimeException(err.toString)

  def createApplication(planetFile: String, roverFile: String): IO[Unit] = ???

  def handleResult(result: Either[Throwable, String]): IO[Unit] =
    result match {
      case Right(value) => logInfo(value)
      case Left(t)      => logError(t.getMessage)
    }

  def logInfo(message: String): IO[Unit] =
    puts(s"${GREEN}OK: $message$RESET")

  def logError(message: String): IO[Unit] =
    puts(s"${RED}ERROR: $message$RESET")

  def loadPlanetData(file: String): IO[(String, String)] = loadTupled(file)
  def loadRoverData(file: String): IO[(String, String)]  = loadTupled(file)

  def loadTupled(file: String): IO[(String, String)] =
    Resource
      .fromAutoCloseable(IO(Source.fromURL(getClass.getClassLoader.getResource(file))))
      .use { source =>
        IO(source.getLines().toArray match {
          case Array(first, second) => (first, second)
          case _                    => throw new RuntimeException("invalid content")
        })
      }

  def puts(message: String): IO[Unit] = IO(println(message))
  def reads(): IO[String]             = IO(scala.io.StdIn.readLine())
  def ask(question: String): IO[String] =
    puts(question) *> reads()

  def askCommands(): IO[String] =
    ask("Waiting commands...")

}
