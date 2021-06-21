package effects.answers

class Lazy extends munit.FunSuite {

  case class Lazy[A](value: () => A) {
    def map[B](f: A => B): Lazy[B] =
      Lazy.pure(() => f(value()))

    def flatMap[B](f: A => Lazy[B]): Lazy[B] =
      Lazy.pure(() => f(value()).value())

    def run(): A =
      value()
  }

  object Lazy {
    def pure[A](a: () => A): Lazy[A] = Lazy(a)
  }

  def expensiveComputation() =
    6 + 4

  def increment(x: Int): Int =
    x + 1

  def reversedString(x: Int): Lazy[String] =
    Lazy.pure(() => x.toString.reverse)

  test("lift a value and run the effect") {
    val c = Lazy
      .pure(expensiveComputation _)

    assertEquals(c.run(), 10)
  }

  test("chain pure function") {
    val c = Lazy
      .pure(expensiveComputation _)
      .map(increment)

    assertEquals(c.run(), 11)
  }

  test("chain effectful function") {
    val c = Lazy
      .pure(expensiveComputation _)
      .flatMap(reversedString)

    assertEquals(c.run(), "01")
  }
}
