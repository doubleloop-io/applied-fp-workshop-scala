package io.doubleloop.webapp

import cats.effect.{ IO, Resource }
import cats.syntax.either._
import Parsing._
import Rendering._
import scala.io.Source

object File {

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

  private def loadTuple(file: String): IO[(String, String)] =
    loadLines(file).map(lines =>
      lines match {
        case Array(first, second) => (first, second)
        case _                    => throw new RuntimeException(s"Invalid file content: $file")
      }
    )

  private def loadLines(file: String): IO[Array[String]] =
    Resource
      .fromAutoCloseable(
        IO(Source.fromURL(getClass.getClassLoader.getResource(file)))
      )
      .use(source => IO(source.getLines().toArray))
}
