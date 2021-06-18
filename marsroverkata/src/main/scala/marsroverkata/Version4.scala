package marsroverkata

import cats.effect._
import cats.implicits._

import scala.Console._
import scala.io._
import scala.util._

object Version4 {

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
