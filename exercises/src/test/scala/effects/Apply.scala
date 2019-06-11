package exercises

import minitest._

/*
 * When we compose functions in fact we chain them.
 *
 * f: A => B
 * g: B => C
 * f andThen g
 *
 * Effectful functions can't be combine in the normal
 * way because after a function invocation we don't have
 * a result value but a description of that result.
 * For example: maybe there is a value or maybe not.
 *
 * There are many operators that enable effectful function
 * combination but two are the most important: map and flatMap.
 *
 * The map function is bring to us by the Functor concept
 * and the flatMap function from the Monad concept.
 */

object ApplyTests extends SimpleTestSuite {

  /*
   * TODO: implements functions marked with `???`
   *
   * ADD YOUR CODE HERE INSIDE THE OBJECT
   */

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
    ignore("parse both strings into int and sum them")
    val program: (String, String) => Try[Int] =
      (x, y) => ???

    val result = program("10", "2")
    assertEquals(result, Success(12))
  }

  test("chain mix pure and effectful functions") {
    ignore("parse both strings into int and then sum, div, tos them")
    val program: (String, String) => Try[String] =
      (x, y) => ???

    val result = program("10", "12")
    assertEquals(result, Success("1"))
  }

  test("fail safe - on first parameter") {
    ignore("parse both strings into int and sum them")
    val program: (String, String) => Try[Int] =
      (x, y) => ???

    val result = program("foo", "5")
    assertEquals(result, Failure(NotAnIntException("foo")))
  }

  test("fail safe - on second parameter") {
    ignore("parse both strings into int and sum them")
    val program: (String, String) => Try[Int] =
      (x, y) => ???

    val result = program("5", "foo")
    assertEquals(result, Failure(NotAnIntException("foo")))
  }
}
