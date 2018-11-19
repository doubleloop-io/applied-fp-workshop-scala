package inventory

import scala.Console._

import cats.effect._

import Examples._

object App {

  def run(): IO[Unit] =
    demoOk.attempt
      .flatMap(handle)

  def handle[A](either: Either[Throwable, A]): IO[Unit] =
    either.fold(
      e => IO(println(s"${RED}${e.getMessage}${RESET}")),
      a => IO(println(s"${GREEN}Final result: ${a}${RESET}"))
    )
}
