package effects

/*
 * Different effect has different
 * properties and fit different needs.
 * But, we can convert from one effect
 * to another and the other way around.
 */

class Transformation extends munit.FunSuite {

  test("convert from Try to Either".ignore) {
    import scala.util.{ Failure, Success, Try }

    case class BadParamException() extends RuntimeException("bad param")

    // TODO: implement convert function
    def convert[A](t: Try[A]): Either[Throwable, A] =
      ???

    assertEquals(convert(Success("foo")), Right("foo"))
    assertEquals(convert(Failure(BadParamException())), Left(BadParamException()))
  }

  test("convert from Either to Option".ignore) {
    sealed trait AppError
    case class BadParam() extends AppError

    // TODO: implement convert function
    def convert[E, A](e: Either[E, A]): Option[A] =
      ???

    assertEquals(convert(Right("foo")), Some("foo"))
    assertEquals(convert(Left(BadParam)), None)
  }
}
