package exercises

import minitest._

object LazyTests extends SimpleTestSuite {

  /*
   * TODO: Follow the instruction in the ignores
   *
   * ADD YOUR CODE HERE INSIDE THE OBJECT
   */

  case class Lazy[A](value: () => A) {
    def map[B](f: A => B): Lazy[B] = ???

    def flatMap[B](f: A => Lazy[B]): Lazy[B] = ???
  }

  object Lazy {
    def pure[A](a: () => A): Lazy[A] = Lazy(a)
  }

  test("lift a value into a container") {
    ignore("implement 'pure' function")
    def expensiveComputation() = 6 + 4
    val c = Lazy
      .pure(expensiveComputation _)

    assertEquals(c.value(), 10)
  }

  def increment(x: Int): Int = x + 1

  test("chain not container-aware functions") {
    ignore("implement 'map' function")
    def expensiveComputation() = 6 + 4
    val c = Lazy
      .pure(expensiveComputation _)
      .map(increment)

    assertEquals(c.value(), 11)
  }

  def reversedString(x: Int): Lazy[String] =
    Lazy.pure(() => x.toString.reverse)

  test("chain container-aware functions") {
    ignore("implement 'flatMap' function")
    def expensiveComputation() = 6 + 4
    val c = Lazy
      .pure(expensiveComputation _)
      .flatMap(reversedString)

    assertEquals(c.value(), "01")
  }
}
