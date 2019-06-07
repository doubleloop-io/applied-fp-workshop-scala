package exercises.answers

import minitest._

object MonadErrorTests extends SimpleTestSuite {
  import cats._

  type Throwing[F[_]] = MonadError[F, Throwable]

  def compute[F[_]](magicValue: Int)(implicit T: Throwing[F]): F[Int] =
    if (magicValue > 0) T.pure(magicValue * 2)
    else T.raiseError(new Exception("invalid number"))

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
    // no type class instance could be provided for the Option type
    ()
  }
}
