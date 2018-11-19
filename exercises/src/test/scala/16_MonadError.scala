package exercises

import minitest._

/*
 * We can program in terms of type classes
 * and pospone the concrete monad.
 */

object MonadErrorTests extends SimpleTestSuite {
  /*
   * TODO: implements compute like the compute
   *       present in ErrorHandling exercises
   *       but this time write the logic in terms
   *       of the MonadError type class.
   */

  import cats._

  type Throwing[F[_]] = MonadError[F, Throwable]

  object Throwing {
    def apply[F[_]](implicit T: Throwing[F]): Throwing[F] = T
  }

  def compute[F[_]: Throwing](magicValue: Int): F[Int] =
    if (magicValue > 0) ???
    else ???

  test("MonadError - Either") {
    import cats.data._
    type ThrowableOr[A] = EitherT[Id, Throwable, A]

    ignore("implements compute and then remove me")
    val value = compute[ThrowableOr](-10)

    value.getOrElse(42); ()
  }

  test("MonadError - IO") {
    import cats.effect._

    ignore("implements compute and then remove me")
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
