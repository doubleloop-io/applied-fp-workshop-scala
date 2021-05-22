package exercises.answers

class FinalTaglessTests extends munit.FunSuite {

  import cats.data._
  import cats.implicits._
  import cats.effect._

  trait Exp[F[_]] {
    def num(value: Int): F[Int]
    def plus(l: F[Int], r: F[Int]): F[Int]
    def times(l: F[Int], r: F[Int]): F[Int]
  }

  object IOEvaluator extends Exp[IO] {
    def num(value: Int): IO[Int] =
      IO.pure(value)

    def plus(l: IO[Int], r: IO[Int]): IO[Int] =
      (l, r).mapN(_ + _)

    def times(l: IO[Int], r: IO[Int]): IO[Int] =
      (l, r).mapN(_ * _)
  }

  type Logged[A] = Writer[String, A]

  object LoggedEvaluator extends Exp[Logged] {
    def num(value: Int): Logged[Int] =
      log("num", value.pure[Logged])

    def plus(l: Logged[Int], r: Logged[Int]): Logged[Int] =
      (log("plus - left", l), log("plus - right", r)).mapN(_ + _)

    def times(l: Logged[Int], r: Logged[Int]): Logged[Int] =
      (log("times - left", l), log("times - right", r)).mapN(_ * _)

    def log(s: String, l: Logged[Int]): Logged[Int] =
      for {
        v <- l
        _ <- (s + ", ").tell
      } yield v
  }

  test("execute program w/ IO monad") {
    def programF[F[_]](exp: Exp[F]): F[Int] = {
      import exp._
      times(plus(num(1), num(1)), num(2))
    }

    def programIO: IO[Int] = programF(IOEvaluator)

    val result = programIO.unsafeRunSync()
    assertEquals(result, 4)
  }

  test("execute program w/ Writer monad") {
    def programF[F[_]](exp: Exp[F]): F[Int] = {
      import exp._
      times(plus(num(1), num(1)), num(2))
    }

    def programLogged: Logged[Int] = programF(LoggedEvaluator)

    val (log, result) = programLogged.run
    assertEquals(result, 4)
    assertEquals(log, "num, plus - left, num, plus - right, times - left, num, times - right, ")
  }
}
