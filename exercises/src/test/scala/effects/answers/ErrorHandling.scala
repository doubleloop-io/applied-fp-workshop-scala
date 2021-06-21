package effects.answers

class ErrorHandling extends munit.FunSuite {

  test("Option - dynamic style") {
    def compute(value: Int): Option[Int] =
      if (value > 0) Some(value * 2)
      else None

    val value = compute(-10)

    intercept[Exception] {
      value.get
    }
  }

  test("Try - dynamic style") {
    import scala.util.{ Failure, Success, Try }

    def compute(value: Int): Try[Int] =
      if (value > 0) Success(value * 2)
      else Failure(new Exception("invalid number"))

    val value = compute(-10)

    intercept[Exception] {
      value.get
    }
  }

  test("Either - static style") {
    sealed trait ComputeError
    case class InvalidNumber(n: Int) extends ComputeError

    def compute(value: Int): Either[ComputeError, Int] =
      if (value > 0) Right(value * 2)
      else Left(InvalidNumber(value))

    val value = compute(-10)

    value.left.getOrElse(42)
  }

  test("Future - dynamic style") {
    import scala.concurrent._
    import scala.concurrent.duration._

    def compute(value: Int): Future[Int] =
      if (value > 0) Future.successful(value * 2)
      else Future.failed(new Exception("invalid number"))

    val value = compute(-10)

    intercept[Exception] {
      Await.result(value, 2.seconds)
    }
  }

  test("IO - dynamic style") {
    import cats.effect._

    def compute(value: Int): IO[Int] =
      if (value > 0) IO(value * 2)
      else IO(throw new Exception("invalid number"))

    val value = compute(-10)

    intercept[Exception] {
      value.unsafeRunSync()
    }
  }
}
