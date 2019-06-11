package exercises.answers

import minitest._

object ApplyTests extends SimpleTestSuite {

  import scala.util.{Failure, Success, Try}
  import cats.implicits._

  case class NotAnIntException(s: String) extends RuntimeException(s"not an int: $s")
  case class DivByZeroException()         extends RuntimeException("divTry by zero")

  val toi: String => Try[Int] =
    s =>
      if (s.matches("^[0-9]+$")) Success(s.toInt)
      else Failure(NotAnIntException(s))

  val sum: (Int, Int) => Int =
    (x, y) => x + y

  val div: Int => Try[Int] =
    n =>
      if (n != 0) Success(n / n)
      else Failure(DivByZeroException())

  val tos: Int => String =
    n => n.toString

  test("chain binary pure function") {
    val program: (String, String) => Try[Int] =
      (x, y) => (toi(x), toi(y)).mapN(sum)

    val result = program("10", "2")
    assertEquals(result, Success(12))
  }

  test("chain mix pure and effectful functions") {
    val program: (String, String) => Try[String] =
      (x, y) => (toi(x), toi(y)).mapN(sum).flatMap(div).map(tos)

    val result = program("10", "12")
    assertEquals(result, Success("1"))
  }

  test("fail safe - on first parameter") {
    val program: (String, String) => Try[Int] =
      (x, y) => (toi(x), toi(y)).mapN(sum)

    val result = program("foo", "5")
    assertEquals(result, Failure(NotAnIntException("foo")))
  }

  test("fail safe - on second parameter") {
    val program: (String, String) => Try[Int] =
      (x, y) => (toi(x), toi(y)).mapN(sum)

    val result = program("5", "foo")
    assertEquals(result, Failure(NotAnIntException("foo")))
  }
}
