package inventory

import cats.effect.IO

import scala.Console.{GREEN, RED, RESET, println}

object App {

  def run(): IO[Unit] =
    IO.pure(42)
      .attempt
      .flatMap(handle)

  def handle[A](either: Either[Throwable, A]): IO[Unit] =
    either.fold(
      e => IO(println(s"${RED}${e.getMessage}${RESET}")),
      a => IO(println(s"${GREEN}Final result: ${a}${RESET}"))
    )
}
