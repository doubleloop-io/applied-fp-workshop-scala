package demos

@munit.IgnoreSuite
class NaturalTransformationDemo extends munit.FunSuite {

  test("transformation from Try to Either") {
    import scala.util.{ Failure, Success, Try }

    case class BadParamException() extends RuntimeException("bad param")

    def convert[A](t: Try[A]): Either[Throwable, A] =
      t.fold(ex => Left(ex), a => Right(a))

    assertEquals(convert(Success("foo")), Right("foo"))
    assertEquals(convert(Failure(BadParamException())), Left(BadParamException()))
  }

  test("transformation from Either to Option") {
    sealed trait AppError
    case class BadParam() extends AppError

    def convert[E, A](e: Either[E, A]): Option[A] =
      e.fold(_ => None, a => Some(a))

    assertEquals(convert(Right("foo")), Some("foo"))
    assertEquals(convert(Left(BadParam)), None)
  }
}
