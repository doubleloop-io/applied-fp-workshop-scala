package exercises.answers

import minitest._

object MapTests extends SimpleTestSuite {

  import scala.util.{Failure, Success, Try}

  case class NotAnIntException(s: String) extends RuntimeException(s"not an int: $s")

  val toiTry: String => Try[Int] =
    s =>
      if (s.matches("^[0-9]+$")) Success(s.toInt)
      else Failure(NotAnIntException(s))

  val dec: Int => Int =
    n => n - 1

  val tos: Int => String =
    n => n.toString

  test("chain one function") {
    val program: String => Try[Int] =
      s => toiTry(s).map(dec)

    val result = program("10")
    assertEquals(result, Success(9))
  }

  test("chain two functions") {
    val program: String => Try[String] =
      s => toiTry(s).map(dec).map(tos)

    val result = program("10")
    assertEquals(result, Success("9"))
  }

  test("fail safe") {
    val program: String => Try[String] =
      s => toiTry(s).map(dec).map(tos)

    val result = program("foo")
    assertEquals(result, Failure(NotAnIntException("foo")))
  }

  test("fail unsafe") {
    val program: String => Try[String] =
      s => toiTry(s).map(dec).map(tos)

    val result = program("foo")
    intercept[NotAnIntException] {
      result.get; ()
    }
  }
}
