package exercises

import minitest._

/*
 * We can program in terms of type classes
 * and postpone the concrete monad.
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

  def compute[F[_]](magicValue: Int)(implicit T: Throwing[F]): F[Int] =
    if (magicValue > 0) ???
    else ???

  test("MonadError - Either") {
    ignore("implements compute, remove me and then uncomment the test")
    // import cats.data._
    // type ThrowableOr[A] = EitherT[Id, Throwable, A]

    // val value = compute[ThrowableOr](-10)

    // value.getOrElse(42); ()
  }

  test("MonadError - IO") {
    ignore("implements compute, remove me and then uncomment the test")
    // import cats.effect._

    // val value = compute[IO](-10)

    // intercept[Exception] {
    //   value.unsafeRunSync(); ()
    // }
  }

  test("MonadError - Option") {
    ignore("implements compute, remove me and then uncomment the test")
    // import cats.data._

    // val value = compute[Option](-10)

    // value.getOrElse(42); ()
    ()
  }
}
