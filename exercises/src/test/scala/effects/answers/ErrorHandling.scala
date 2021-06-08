package effects.answers

class ErrorHandlingTests extends munit.FunSuite {

  test("Option - dynamic style") {
    def compute(value: Int): Option[Int] =
      if (value > 0) Some(value * 2)
      else None

    val value = compute(-10)

    intercept[Exception] {
      value.get;
    }
    ()
  }

  test("Try - dynamic style") {
    import scala.util.{ Failure, Success, Try }

    def compute(value: Int): Try[Int] =
      if (value > 0) Success(value * 2)
      else Failure(new Exception("invalid number"))

    val value = compute(-10)

    intercept[Exception] {
      value.get;
    }
    ()
  }

  test("Either - static style") {
    sealed trait ComputeError
    case class InvalidNumber(n: Int) extends ComputeError

    def compute(value: Int): Either[ComputeError, Int] =
      if (value > 0) Right(value * 2)
      else Left(InvalidNumber(value))

    val value = compute(-10)

    // value.left.get;
  }

  test("Future - dynamic style") {
    import scala.concurrent._
    import scala.concurrent.duration._
    import scala.concurrent.ExecutionContext.Implicits.global

    def compute(value: Int): Future[Int] =
      if (value > 0) Future.successful(value * 2)
      else Future.failed(new Exception("invalid number"))

    val value = compute(-10)

    intercept[Exception] {
      Await.result(value, 2.seconds);
    }
    ()
  }

  test("IO - dynamic style") {
    import cats._
    import cats.effect._

    def compute(value: Int): IO[Int] =
      if (value > 0) IO(value * 2)
      else IO(throw new Exception("invalid number"))

    val value = compute(-10)

    intercept[Exception] {
      value.unsafeRunSync();
    }
    ()
  }

  test("convert from Either to Option") {
    sealed trait AppError
    case class BadParam() extends AppError

    def convert[E, A](e: Either[E, A]): Option[A] =
      e.fold(_ => None, a => Some(a))

    assertEquals(convert(Right("foo")), Some("foo"))
    assertEquals(convert(Left(BadParam)), None)
  }

  test("convert from Try to Either") {
    import scala.util.{ Failure, Success, Try }

    case class BadParamException() extends RuntimeException("bad param")

    def convert[A](t: Try[A]): Either[Throwable, A] =
      t.fold(ex => Left(ex), a => Right(a))

    assertEquals(convert(Success("foo")), Right("foo"))
    assertEquals(convert(Failure(BadParamException())), Left(BadParamException()))
  }
}
