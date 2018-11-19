package exercises.answers

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
 * The map function is birng to us by the Functor concept
 * and the flatMap function from the Monad concept.
 */

object FlatMapTests extends SimpleTestSuite {

  import scala.util.{Failure, Success, Try}

  case class NotAnIntException(s: String) extends RuntimeException(s"not an int: $s")
  case class DivByZeroException()         extends RuntimeException("divTry by zero")

  val toi: String => Try[Int] =
    s =>
      if (s.matches("^[0-9]+$")) Success(s.toInt)
      else Failure(NotAnIntException(s))

  val dec: Int => Int =
    n => n - 1

  val divTry: Int => Try[Int] =
    n =>
      if (n != 0) Success(n / n)
      else Failure(DivByZeroException())

  val tos: Int => String =
    n => n.toString

  test("chain only pure functions") {
    val program: String => Try[Int] =
      s => toi(s).map(dec)

    val result = program("10")
    assertEquals(result, Success(9))
  }

  test("chain mix pure and effectful functions") {
    val program: String => Try[String] =
      s => toi(s).map(dec).flatMap(divTry).map(tos)

    val result = program("10")
    assertEquals(result, Success("1"))
  }

  test("for-comprehension") {
    val program: String => Try[String] =
      s =>
        for {
          n  <- toi(s)
          n1 = dec(n)
          n2 <- divTry(n1)
          s1 = tos(n2)
        } yield s1

    val result = program("10")
    assertEquals(result, Success("1"))
  }

  test("fail safe - on first operaton") {
    val program: String => Try[String] =
      s => toi(s).map(dec).flatMap(divTry).map(tos)

    val result = program("foo")
    assertEquals(result, Failure(NotAnIntException("foo")))
  }

  test("fail safe - on the middle operation") {
    val program: String => Try[String] =
      s => toi(s).map(dec).flatMap(divTry).map(tos)

    val result = program("1")
    assertEquals(result, Failure(DivByZeroException()))
  }
}
