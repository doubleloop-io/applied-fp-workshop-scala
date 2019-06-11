package exercises.answers

import minitest._

object LazyTests extends SimpleTestSuite {

  case class Lazy[A](value: () => A) {
    def chain[B](f: A => B): Lazy[B] =
      Lazy.of(() => f(value()))

    def chainLazy[B](f: A => Lazy[B]): Lazy[B] =
      Lazy.of(() => f(value()).value())
  }

  object Lazy {
    def of[A](a: () => A): Lazy[A] = Lazy(a)
  }

  test("lift a value into a container") {
    def expensiveComputation() = 6 + 4
    val c = Lazy
      .of(expensiveComputation _)

    assertEquals(c.value(), 10)
  }

  def increment(x: Int): Int = x + 1

  test("chain not container-aware functions") {
    def expensiveComputation() = 6 + 4
    val c = Lazy
      .of(expensiveComputation _)
      .chain(increment)

    assertEquals(c.value(), 11)
  }

  def reversedString(x: Int): Lazy[String] = Lazy.of(() => x.toString.reverse)

  test("chain container-aware functions") {
    def expensiveComputation() = 6 + 4
    val c = Lazy
      .of(expensiveComputation _)
      .chainLazy(reversedString)

    assertEquals(c.value(), "01")
  }
}
