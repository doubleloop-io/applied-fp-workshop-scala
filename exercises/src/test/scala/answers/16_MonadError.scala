package exercises.answers

import minitest._

/*
 * We can program in terms of type classes
 * and pospone the concrete monad.
 */

object MonadErrorTests extends SimpleTestSuite {
  import cats._

  type Throwing[F[_]] = MonadError[F, Throwable]

  object Throwing {
    def apply[F[_]](implicit T: Throwing[F]): Throwing[F] = T
  }

  def compute[F[_]: Throwing](magicValue: Int): F[Int] =
    if (magicValue > 0) Throwing[F].pure(magicValue * 2)
    else Throwing[F].raiseError(new Exception("invalid number"))

  test("MonadError - Either") {
    import cats.data._
    type ThrowableOr[A] = EitherT[Id, Throwable, A]

    val value = compute[ThrowableOr](-10)

    value.getOrElse(42); ()
  }

  test("MonadError - IO") {
    import cats.effect._

    val value = compute[IO](-10)

    intercept[Exception] {
      value.unsafeRunSync(); ()
    }
  }

  test("MonadError - Option") {
    // no type class instance for Option type
    ()
  }
}
