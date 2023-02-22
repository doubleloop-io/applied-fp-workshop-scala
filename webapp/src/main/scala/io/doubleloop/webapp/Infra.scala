package io.doubleloop.webapp

import Orientation._, Command._, ParseError._
import Parsing._, Rendering._
import cats.syntax.either._
import cats.syntax.traverse._
import cats.syntax.applicativeError._
import scala.Console.{ GREEN, RED, RESET }
import scala.io.Source
import cats.effect.{ IO, Resource }

object Infra {

  def eitherToIO[A](value: Either[ParseError, A]): IO[A] =
    IO.fromEither(value.leftMap(e => new RuntimeException(renderError(e))))

  def loadPlanet(file: String): IO[Planet] =
    loadTuple(file)
      .map(parsePlanet)
      .flatMap(eitherToIO)

  def loadRover(file: String): IO[Rover] =
    loadTuple(file)
      .map(parseRover)
      .flatMap(eitherToIO)

  def loadCommands(): IO[List[Command]] =
    ask("Waiting commands...")
      .map(parseCommands)

  def writeSequenceCompleted(rover: Rover): IO[Unit] =
    logInfo(renderComplete(rover))

  def writeObstacleDetected(rover: ObstacleDetected): IO[Unit] =
    logInfo(renderObstacle(rover))

  def writeError(error: Throwable): IO[Unit] =
    logError(error.getMessage)

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
      .fromAutoCloseable(
        IO(Source.fromURL(getClass.getClassLoader.getResource(file)))
      )
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
