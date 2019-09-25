package exercises

import minitest._

object LazyTests extends SimpleTestSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE TEST OBJECT
   */

  case class Lazy[A](value: () => A) {
    def map[B](f: A => B): Lazy[B] = ???

    def flatMap[B](f: A => Lazy[B]): Lazy[B] = ???
  }

  object Lazy {
    def pure[A](a: () => A): Lazy[A] = ???
  }

  def expensiveComputation() =
    6 + 4

  def increment(x: Int): Int =
    x + 1

  def reversedString(x: Int): Lazy[String] =
    Lazy.pure(() => x.toString.reverse)

  test("lift a value into a container") {
    ignore("implement 'pure' function")
    val c = Lazy
      .pure(expensiveComputation)

    assertEquals(c.value(), 10)
  }

  test("chain not container-aware functions") {
    ignore("implement 'map' function")
    val c = Lazy
      .pure(expensiveComputation)
      .map(increment)

    assertEquals(c.value(), 11)
  }

  test("chain container-aware functions") {
    ignore("implement 'flatMap' function")
    val c = Lazy
      .pure(expensiveComputation)
      .flatMap(reversedString)

    assertEquals(c.value(), "01")
  }
}
