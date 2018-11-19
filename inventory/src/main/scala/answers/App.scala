package inventory.answers

import cats._
import cats.data._
import cats.implicits._
import cats.effect._
import cats.mtl.implicits._

import scala.Console._

import interpreter.console._
import interpreter.randomid._
import interpreter.itemrepository._
import interpreter.itemservice._

import Console._

object App {

  type Result[A] = StateT[IO, ItemRepositoryState, A]

  def run(): IO[Unit] = {
    val prog1 = run(Examples.demoOk[Result])
    val prog2 = run(Examples.demoBad[Result])
    val prog3 = run(Examples.demoNotFound[Result])

    val progs = Applicative[IO].tuple3(prog1, prog2, prog3)

    progs *> IO.unit
  }

  def run[A](prog: Result[A]): IO[Unit] =
    prog
      .runS(ItemRepositoryState(Map.empty))
      .attempt
      .flatMap(handle(_))

  def handle[A](either: Either[Throwable, A]): IO[Unit] =
    either.fold(
      e => putLine[IO](s"${RED}${e.getMessage}${RESET}"),
      a => putLine[IO](s"${GREEN}Final result: ${a}${RESET}")
    )
}
