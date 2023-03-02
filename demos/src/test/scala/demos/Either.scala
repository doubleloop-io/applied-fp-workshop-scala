package demos

@munit.IgnoreSuite
class EitherDemo extends munit.FunSuite {

  import cats.syntax.applicativeError._
  import cats.syntax.either._

  test("creation") {
    // Right constructor fix only the right type parameter so,
    // e1 type is Either[Nothing, Int]
    val e1 = Right(5)

    // Explicit type to fix even the left type parameter
    val e2: Either[String, Int] = Right(5)

    // Instead of the data constructor we can use the extension method
    // e3 type is Either[Nothing, Int]
    val e3 = 5.asRight
    // e4 type is Either[String, Int]
    val e4 = 5.asRight[String]

    // Function definition helps the type inference
    def foo(e: Either[String, Int]) = e
    // The value pass as parameter has type Either[String, Int]
    foo(5.asRight)
  }

  test("manipulation") {
    val e1: Either[String, Int] = Right(5)

    // Change the right type parameter's type
    val e2: Either[String, String] = e1.map(_.toString)

    // Change either's state
    val e3: Either[String, String] = e2.flatMap(_ => "error".asLeft)

    // Change the left type parameter's type
    val e4: Either[Int, String] = e3.leftMap(_.length)

    // Change both type parameters types
    val e5: Either[String, Int] = e4.bimap(_.toString, _.length)
  }

  test("error handling") {
    val e1: Either[String, Int] = Left("unknown")

    // The Left[String] value is converted to a Right[Int]
    val e2: Either[String, Int] =
      e1.handleError(e => e.hashCode())

    val e3: Either[String, Int] = Left("unknown")

    // Same conversions as before
    val e4: Either[String, Int] = e3.handleErrorWith(e => Right(e.hashCode))
  }

}
