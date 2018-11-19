package inventory

import cats.effect._

object Examples {

  def demoOk: IO[Int] =
    IO.pure(42)

}
